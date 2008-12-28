import java.util.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;
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
	
	private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();
	
	
	/**
	 * addActionListener
	 *
	 * @param listener 
	 * @return 
	 */
	public void addActionListener(ActionListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * login
	 *
	 * @param username password
	 * @return 
	 */
	public boolean login(String username, String password) {
		try {
			Socket client = new Socket(IP, port);
			oos = new ObjectOutputStream(client.getOutputStream());
	        ois = new ObjectInputStream(client.getInputStream());
			char action = 'L';
			int salt = (int)Math.random()*25;
			String encryptedPassword = "";
			for(int i=0; i<password.length(); i++) encryptedPassword += (char)(password.charAt(i) + salt);
			oos.writeObject(action + " " + username + " " + encryptedPassword + " " + salt);

			int numOfContacts = (Integer)ois.readObject();
			System.out.println(numOfContacts);

			if(numOfContacts != -1){
				System.out.println("Logged In Successfully!");

				for(int i= 0; i < numOfContacts; i++)
				{
					Contact c = (Contact)ois.readObject();
					System.out.println("ID GET" + c.getID());
					
					contacts.add(c);
					System.out.println(c);
				}	
				notifyListeners(new ActionEvent(this,0,"Login Succeeded"));
				return true;
			}
			//notifyListeners(new ActionEvent(this,0,"Login Faild"));
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		//notifyListeners(new ActionEvent(this,0,"Login Faild"));
		return false;
	}

	/**
	 * register
	 * -1: password mismatch | -2: User Already Exists | -3: Error | 0: Registerd
	 * @param  
	 * @return 
	 */
	public int register(String username, String password, String repassword) {
		try {
			Socket client = new Socket(IP, port);
			oos = new ObjectOutputStream(client.getOutputStream());
	        ois = new ObjectInputStream(client.getInputStream());
			
			int salt = (int)Math.random()*25;
			char action = 'N';
			String encryptedPassword="",encryptedRePassword="";
			if(!password.equals(repassword)) return -1;
			for(int i=0; i<password.length(); i++) encryptedPassword += (char)(password.charAt(i) + salt);
			for(int i=0; i<password.length(); i++) encryptedRePassword += (char)(password.charAt(i) + salt);
			oos.writeObject(action + " " + username + " " + encryptedPassword + " " + encryptedPassword + " " + salt);
			boolean created = (Boolean)ois.readObject();
			if(created) return 0;//System.out.println("User Created Successfully!");
			else return -2;//System.out.println("Can't Create User!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -3;
	}

	/**
	 * addContact
	 *
	 * @param  
	 * @return 
	 */
	public boolean addContact(String firstName, String middleName, String lastName, String phone, String address, String email, int icon ) {
		try {
			char action = 'A';
			oos.writeObject(action + " " + firstName.replaceAll(" ", "%s") + " " + middleName.replaceAll(" ", "%s") + " " + lastName.replaceAll(" ", "%s") +
						" " + phone.replaceAll(" ", "%s") + " " + address.replaceAll(" ", "%s") + " " + email.replaceAll(" ", "%s") + " " + icon);
			Contact newContact = (Contact)ois.readObject();
			if(newContact != null){
				contacts.add(newContact);
				System.out.println("Contact Added Successfully!");
				notifyListeners(new ActionEvent(this,0,"Added Successfully"));
				return true;
			}
			else 
			{
				System.out.println("Faild to Add Contact!");
				return false;
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
	}

	
	/**
	 * deleteContact
	 *
	 * @param contactID 
	 * @return 
	 */
	public boolean deleteContact(int contactID) {
		try {
			char action = 'D';
			oos.writeObject(action + " " + contactID);
			boolean deleted = (Boolean)ois.readObject();
			if(deleted){	
				for(int i=0; i < contacts.size(); i++)
					if(contacts.get(i).getID() == contactID){
						contacts.remove(i);
						notifyListeners(new ActionEvent(this,contactID,"Deleted Successfully"));
						return true;
						//break;
					}
				System.out.println("Deleted Successfully!");
			}
			else{
				System.out.println("Deletion Faild!");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
	}

	/**
	 * updateContact
	 *
	 * @param  
	 * @return 
	 */
	public boolean updateContact(int contactID, String firstName, String middleName, String lastName, String phone, String address, String email, int icon ) {
		try {
			char action = 'U';
			oos.writeObject(action + " " + contactID + " " + firstName.replaceAll(" ", "%s") + " " + middleName.replaceAll(" ", "%s") + " " + lastName.replaceAll(" ", "%s") +
						" " + phone.replaceAll(" ", "%s") + " " + address.replaceAll(" ", "%s") + " " + email.replaceAll(" ", "%s") + " " + icon);
			boolean updated = (Boolean)ois.readObject();
			if(updated){
				Contact con = null;
				for(int i=0; i < contacts.size(); i++)
					if((con = contacts.get(i)).getID() == contactID){
						con.setFirstName(firstName);
						con.setLastName(lastName);
						con.setMiddleName(middleName);
						con.setPhone(phone);
						con.setAddress(address);
						con.setEmail(email);
						con.setIcon(icon);
						break;
					}
				System.out.println("Contact Updated Successfully!");
				notifyListeners(new ActionEvent(this,contactID,"Updated Successfully"));
				return true;
			}
			else System.out.println("Update Faild!");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
	}

	
	/**
	 * logout
	 *
	 * @param  
	 * @return 
	 */
	public boolean logout() {
		try {
			char action = 'E';
			oos.writeObject("" + action);
			boolean loggedOut = (Boolean)ois.readObject();
			if(loggedOut){
				System.out.println("Logged Out Successfully!");	
				notifyListeners(new ActionEvent(this,0,"Logout Succeeded"));
				return true;
			} 
			else System.out.println("Faild To Logout!");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
	}
	
	
	/**
	 * notiftListeners
	 *
	 * @param  
	 * @return 
	 */
	public void notifyListeners(ActionEvent e) {
		if(listeners.size() == 0) return;
		ArrayList<ActionListener> listenersCopy;
		synchronized(this){
			listenersCopy = (ArrayList<ActionListener>)listeners.clone();
		}
		for (int i=0; i<listenersCopy.size(); i++)
			listenersCopy.get(i).actionPerformed(e);
	}
	
	// {{{ FLClient constructor
    /**
     * 
     */
    public FLClient() {
    }

	/**
	 * getContacts
	 *
	 * @param  
	 * @return 
	 */
	public ArrayList<Contact> getContacts() {
		return contacts;
	}

	
}
