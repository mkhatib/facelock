import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class HandleRequest implements Runnable{		
		
	    private Socket socket;
		// private DataOutputStream os;
		private ObjectOutputStream oos;
		// private BufferedReader br;
		private ObjectInputStream ois;
		// The UserModel which will interact with the database
		private UserModel loggedUser;
		// Logged out ?
		private boolean loggedOut = false;
	    // Constructor
	    
	    public HandleRequest(Socket client) throws Exception {
			this.socket = client;
	    }

	    // Implement the run() method of the Runnable interface.
	    public void run(){
	        try {
	            processRequest();
				socket.close();
				//br.close();
	        } catch (Exception e) {
	            System.out.println(e);
	       	}
	    }

	    private void processRequest() throws Exception{
				// get the output stream of the socket
	            //os = new DataOutputStream(socket.getOutputStream());
				oos = new ObjectOutputStream(socket.getOutputStream());
				// Set up input stream filters. 
	            ois = new ObjectInputStream(socket.getInputStream());
				while(true){
					// Get the request line of the request message.
		            String requestLine = (String)ois.readObject();
					System.out.println(requestLine);

					//determine the type of the message
					determineMessageType(requestLine);
					if(loggedOut) break;
				}
	}		
	
	public void determineMessageType(String requestLine) {
			// Extract the first token of the request which is the message type:: 
			StringTokenizer tokens = new StringTokenizer(requestLine);
			String messageType = tokens.nextToken();  //  take the message ID
			try {
			if(messageType.equals("L")) // as message arrive L username password encr 
			{	// check for the username and encrypted password
				System.out.println("Action: Login");
				String username = tokens.nextToken();
				String password = tokens.nextToken();
				int salt = Integer.parseInt(tokens.nextToken());
				
				UserModel userModel = checkLogin(username,password,salt);
				if( userModel != null) // if login is successful ==>
				{
					System.out.println("User Found!");
					
					loggedUser = userModel; //new UserModel(userModel.getUser()); // No Need to create another model u already have it
					ArrayList<Contact> contactList = loggedUser.getAllContacts();
					// Login Successful
						//oos.writeObject(userModel.getUser());
						oos.writeObject(contactList.size());
						// send the list of contacts to the client 
						for(int k = 0 ; k< contactList.size() ; k++) // Not .length() it's .size()
						{
							//os.writeByte(contactList.get(k).getID()); // Why U R writing the ID! We need the whole Contact
							oos.writeObject(contactList.get(k));
						}
						// Finish Sending!
						//oos.writeObject(null);
				}
				else // if the login was not successful ==> username and password wrong 
					  // do we want to specify , wrong match of username and password , OR the username doesn't exist??
				{
					//String wrongLogin = "Wrong UserName and Password";
					//os.writeBytes(wrongLogin);
					//oos.writeBytes(wrongLogin);
					// or 
					//os.writeBoolean(false);
					oos.writeObject(-1);
				}
			}
			else if (messageType.equals("E")) // log out
			{
				// Close streams and socket.
				oos.writeObject(true);
		        oos.close();
		        ois.close();
				loggedOut = true;
			}
			else if (messageType.equals("A")) // add new contact to the loggedUser
			{	
				// message format:     A fname mname lname phone address email icon
				Contact newContact = new Contact(tokens.nextToken().replaceAll("%s", " "), tokens.nextToken().replaceAll("%s", " "), tokens.nextToken().replaceAll("%s", " "), tokens.nextToken().replaceAll("%s", " "), tokens.nextToken().replaceAll("%s", " "), tokens.nextToken().replaceAll("%s", " "), Integer.parseInt(tokens.nextToken()));
				boolean addStatus = loggedUser.addContact(newContact); // insert new contact to the DB
				if(addStatus)
					oos.writeObject(newContact);
				else oos.writeObject(null);
			}
			else if (messageType.equals("U")) // U ContactID
			{	// get the ID of the contact to update
				ContactModel contactToUpdate = ContactModel.findById(Integer.parseInt(tokens.nextToken()));
				contactToUpdate.setFirstName(tokens.nextToken().replaceAll("%s", " "));
				contactToUpdate.setMiddleName(tokens.nextToken().replaceAll("%s", " "));
				contactToUpdate.setLastName(tokens.nextToken().replaceAll("%s", " "));
				contactToUpdate.setPhone(tokens.nextToken().replaceAll("%s", " "));
				contactToUpdate.setAddress(tokens.nextToken().replaceAll("%s", " "));
				contactToUpdate.setEmail(tokens.nextToken().replaceAll("%s", " "));
				contactToUpdate.setIcon(Integer.parseInt(tokens.nextToken()));
				boolean updateStatus = contactToUpdate.update();
				oos.writeObject(updateStatus);
			}
			
			else if (messageType.equals("D")) // D ContactID
			{ 	// get the ID of the contact to delete
				ContactModel contactToDelete = ContactModel.findById(Integer.parseInt(tokens.nextToken()));
				boolean deleteStatus = contactToDelete.delete();
				oos.writeObject(deleteStatus);
			}
			else //if(messageType.equals("N")) //    ====> new User    ====> N username password repassword encr
			{
				String username = tokens.nextToken();
				String password = tokens.nextToken();
				String rePassword = tokens.nextToken();
				int encr = Integer.parseInt(tokens.nextToken());
				String decryptPassword = decryptPassword(password, encr);
				String decryptRePassword = decryptPassword(rePassword, encr);
				boolean newUserStatus;
				if(decryptPassword.equals(decryptRePassword))
				{	// add user to DB HOW
					// add new User ************* what about the ID ******************
					// Don't worry about the id, you won't need it 
					// You only want to create a new user, then he will have to login these information he provided!
					// This is good you don't have to edit it
					UserModel newUserModel = new UserModel(new User(username,/*password*/ decryptPassword));
					newUserStatus = newUserModel.save();
					oos.writeObject(newUserStatus);
				}
				else // password mismatch::::
				{
					oos.writeObject(false);
				}
					
			}	
			} catch (IOException e) {
				e.printStackTrace();
			}	
				
	}
		
	public UserModel checkLogin(String userName , String password, int encr)
	{ 
		// decrypt the password:
		String decryptPassword = decryptPassword(password, encr);
		// search for that user
		UserModel um = UserModel.findByUsernameAndPassword(userName, decryptPassword);		
		return um;
	}
	
	public String decryptPassword(String password, int encr)
	{
		String decryptPassword = "";
		System.out.println(password);
		
		for(int i=0; i< password.length() ; i++)
			decryptPassword += (char)(password.charAt(i) - encr);
		System.out.println(decryptPassword);
		
		return decryptPassword;
	}
				
}				

			
