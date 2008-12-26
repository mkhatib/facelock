import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class HandleRequest implements Runnable{		
		
	    Socket socket;
		DataOutputStream os;
		BufferedReader br;
		UserModel loggedUser;
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
	            os = new DataOutputStream(socket.getOutputStream());
				// Set up input stream filters. 
	            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				// Get the request line of the request message.
	            String requestLine = br.readLine();
				//determine the type of the message
				determineMessageType(requestLine);
				
				
	}		
	
	public void determineMessageType(String requestLine) {
			// Extract the first token of the request which is the message type:: 
			StringTokenizer tokens = new StringTokenizer(requestLine);
			String messageType = tokens.nextToken();  //  take the message ID
			
			if(messageType.equals("L")) // as message arrive L username password encr 
			{	// check for the username and encrypted password
				UserModel userModel = checkLogin(tokens.nextToken(),tokens.nextToken(), tokens.nextToken());
				if( userModel != null) // if login is successful ==>
				{
					loggedUser = new UserModel(userModel.getUser());
					ArrayList<Contact> contactList = user.getAllContacts();
					
					// send the list of contacts to the client
					for(int k = 0 ; k< contactList.length() ; k++)
					{
						os.writeByte(contactList.get(k).getID());
					}
				}	
				else // if the login was not successful ==> username and password wrong 
					  // do we want to specify , wrong match of username and password , OR the username doesn't exist??
				{
					String wrongLogin = "Wrong UserName and Password";
					os.writeBytes(wrongLogin);
					// or 
					//os.writeBoolean(false);
				}
			}
			else if (messageType.equals("E")) // log out
			{
				// Close streams and socket.
		        os.close();
		        br.close();
			}
			else if (messageType.equals("A")) // add new contact to the loggedUser
			{	
				// message format:     A id fname mname lname phone address email icon
				Contact newContact = new Contact(Integer.parseInt(tokens.nextToken()), tokens.nextToken(), tokens.nextToken(), tokens.nextToken(), tokens.nextToken(), tokens.nextToken(), tokens.nextToken(), Integer.parseInt(tokens.nextToken()))
				boolean addStatus = loggedUser.addContact(newContact); // insert new contact to the DB
				os.writeBoolean(addStatus);
			}
			else if (messageType.equals("U")) // U ContactID
			{	// get the ID of the contact to update
				ContactModel contactToUpdate = ContactModel.findById(tokens.nextToken());
				boolean updateStatus = contactToUpdate.updated();
				os.writeBoolean(updateStatus);
			}
			
			else if (messageType.equals("D")) // D ContactID
			{ 	// get the ID of the contact to delete
				ContactModel contactToDelete = ContactModel.findById(tokens.nextToken());
				boolean deleteStatus = contactToDelete.delete();
				os.writeBoolean(deleteStatus);
			
			}
			else // messageType.equals("N")    ====> new User    ====> N username password repassword encr
			{
				String username = tokens.nextToken();
				String password = tokens.nextToken();
				String rePassword = tokens.nextToken();
				String encr = tokens.nextToken();
				String decryptPassword = decryptPassword(password, encr);
				String decryptRePassword = decryptPassword(rePassword, encr);
				boolean newUserStatus;
				if(decryptPassword.equals(decryptRePassword))
				{	// add user to DB HOW
					// add new User ************* what about the ID ******************
					UserModel newUserModel = new UserModel(new User(username,password));
					newUserStatus = newUserModel.save();
					os.writeBoolean(newUserStatus);
				}
				else // password mismatch::::
				{
					os.writeBoolean(false);
				}
					
			}	
				
				
	}
		
	public UserModel checkLogin(String userName , String password, String encr)
	{ 
		
		// decrypt the password:
		String decryptPassword = decryptPassword(password, encr);
		// search for that user
		UserModel um = UserModel.findByUsernameAndPassword(userName, decryptPassword);
		
		return um;

	}
	
	public String decryptPassword(String password, String encr)
	{
		String decryptPassword = null;
		for(int i=0; i< password.length() ; i++)
			decryptPassword += password.charAt(i) - Integer.parseInt(encr);
		return decryptPassword;
	}
				
}				

			
