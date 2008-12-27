import java.util.*;
import java.net.*;
import java.io.*;
/**
 * <<Class summary>>
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public final class FLClient {
    private  String IP = "localhost";
	private int port = 7488;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private ArrayList<Contact> contacts = new ArrayList<Contact>();
	// {{{ FLClient constructor
    /**
     * 
     */
    public FLClient() {
        try {
			Socket client = new Socket(IP, port);
			oos = new ObjectOutputStream(client.getOutputStream());
            ois = new ObjectInputStream(client.getInputStream());
			String username = "newUser";
			String password = "lll";
			int xx = 10;
			char action = 'N';
			String encryptedPassword="";
			for(int i=0; i<password.length(); i++) encryptedPassword += (char)(username.charAt(i) + xx);
			
			oos.writeObject(action + " " + username + " " + encryptedPassword + " " + encryptedPassword + " " + xx);
			boolean created = (Boolean)ois.readObject();
			if(created) System.out.println("User Created Successfully!");
			else System.out.println("Can't Create User!");
			
			
			// Log in
			action = 'L';
			oos.writeObject(action + " " + username + " " + encryptedPassword + " " + xx);
			
			int numOfContacts = (Integer)ois.readObject();
			System.out.println(numOfContacts);
			
			if(numOfContacts != -1){
				System.out.println("Logged In Successfully!");
				
				for(int i= 0; i < numOfContacts; i++)
				{
					Contact c = (Contact)ois.readObject();
					contacts.add(c);
					System.out.println(c);
				}	
					
				// Add Contact
				//To add for the add contatc button an action listener which guid to add contact window as following:
				/*
				action = 'A';
				String firstName = "Ramiz";
				String middleName = "Isa";
				String lastName = "Abu Khiran";
				String phone = "+972599801295";
				String address = "Arroub Camp - Hebron - Palestine";
				String email = "ramiz2050@hotmail.com";
				int icon = 1;
				oos.writeObject(action + " " + firstName.replaceAll(" ", "%s") + " " + middleName.replaceAll(" ", "%s") + " " + lastName.replaceAll(" ", "%s") +
							" " + phone.replaceAll(" ", "%s") + " " + address.replaceAll(" ", "%s") + " " + email.replaceAll(" ", "%s") + " " + icon);
				Contact newContact = (Contact)ois.readObject();
				if(newContact != null){
					contacts.add(newContact);
					System.out.println("Contact Added Successfully!");
					System.out.println(newContact);
				}
				else 
					System.out.println("Faild to Add Contact!");
				*/
				/*
				// Delete Contact who's id=10 WORKS
				int contactID = 12;
				action = 'D';
				oos.writeObject(action + " " + contactID);
				boolean deleted = (Boolean)ois.readObject();
				if(deleted){	
					for(int i=0; i < contacts.size(); i++)
						if(contacts.get(i).getID() == contactID){
							contacts.remove(i);
							break;
						}
					System.out.println("Deleted Successfully!");
				}
				else System.out.println("Deletion Faild!");
				*/
				/*
				// Update
				action = 'U';
				int contactID2 = 14;
				String firstName2 = "Hasan";
				String middleName2 = "Mohammad";
				String lastName2 = "Salameh";
				String phone2 = "+97259900000";
				String address2 = "Tulkarem - Palestine";
				String email2 = "has-sal@hotmail.com";
				int icon2 = 1;
				oos.writeObject(action + " " + contactID2 + " " + firstName2.replaceAll(" ", "%s") + " " + middleName2.replaceAll(" ", "%s") + " " + lastName2.replaceAll(" ", "%s") +
							" " + phone2.replaceAll(" ", "%s") + " " + address2.replaceAll(" ", "%s") + " " + email2.replaceAll(" ", "%s") + " " + icon2);
						
				boolean updated = (Boolean)ois.readObject();
				if(updated){
					System.out.println("Contact Updated Successfully!");
				}
				else System.out.println("Update Faild!");
			
				*/
				
				action = 'E';
				oos.writeObject("" + action);
				boolean loggedOut = (Boolean)ois.readObject();
				if(loggedOut) System.out.println("Logged Out Successfully!");
				else System.out.println("Faild To Logout!");
				
				
			}
			else System.out.println("Logging In faild!");
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// expression
		}
		
    }
	// }}}
	
	// Testing the server
	public static void main(String[] args) {
		FLClient client = new FLClient();
	}
}
