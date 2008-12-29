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
			Random random = new Random();
			int salt = random.nextInt(25)+1;
			System.out.println("SALT::" + salt);
			
			String encryptedPassword = "";
			for(int i=0; i<password.length(); i++) encryptedPassword += (char)(password.charAt(i) + salt);
			System.out.println("encryptedPassword::" + encryptedPassword);
			
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
				contacts.clear();
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
	 * sort
	 *
	 * @param  
	 * @return 
	 */
	public void sort(int by, int order ) {
		switch(by)
		{
			case 0: Collections.sort(contacts, new IDComparator()); break;
			case 1: Collections.sort(contacts, new FirstNameComparator()); break;
			case 2: Collections.sort(contacts, new MiddleNameComparator()); break;
			case 3: Collections.sort(contacts, new LastNameComparator()); break;
			case 4: Collections.sort(contacts, new PhoneComparator()); break;
			case 5: Collections.sort(contacts, new AddressComparator()); break;
			case 6: Collections.sort(contacts, new EmailComparator()); break;
			case 7: Collections.sort(contacts, new IconComparator()); break;
		}
		if(order == 1) Collections.reverse(contacts);
		notifyListeners(new ActionEvent(this,0,"Sorted Successfully"));
	}

	/**
	 * search
	 *
	 * @param key 
	 * @return 
	 */
	public boolean search(String key, int by, int from) {
		int pos = -1;
		from++;
		from = (from % contacts.size());
		switch(by)
		{
			case 0:  

				for(int i=from,  j=0; j < contacts.size(); j++ ){
					System.out.println("Searching: " + (key) + " == " + contacts.get(i).getID());
					
					if(contacts.get(i).getID() == Integer.parseInt(key)){
						pos = i;
						break;
					}
					i++;
					i = (i % contacts.size());
				}
				break;//case 0: sort(0,0); pos = Collections.binarySearch(contacts/*.subList(from,contacts.size())*/, Integer.parseInt(key), new IDComparator()); break;       
			case 1: 
				for(int i=from,  j=0; j < contacts.size(); j++ ){
					if(contacts.get(i).getFirstName().indexOf(key) != -1){
						pos = i;
						break;
					}
					i++;
					i = (i % contacts.size());
				}
			 	break;//case 1: sort(1,0); pos = Collections.binarySearch(contacts/*.subList(from,contacts.size())*/, key/*, new FirstNameComparator()*/); break;       
			case 2: 
				for(int i=from,  j=0; j < contacts.size(); j++ ){
					if(contacts.get(i).getMiddleName().indexOf(key) != -1){
						pos = i;
						break;
					}
					i++;
					i = (i % contacts.size());
				}
			 	break;//case 2: sort(2,0); pos = Collections.binarySearch(contacts/*.subList(from,contacts.size())*/, key/*, new MiddleNameComparator())*/; break;       
			case 3:  
				for(int i=from,  j=0; j < contacts.size(); j++ ){
					if(contacts.get(i).getLastName().indexOf(key) != -1){
						pos = i;
						break;
					}
					i++;
					i = (i % contacts.size());
				}
				break;//case 3: sort(3,0); pos = Collections.binarySearch(contacts/*.subList(from,contacts.size())*/, key/*, new LastNameComparator()*/); break;       
			case 4:  
				for(int i=from,  j=0; j < contacts.size(); j++ ){
					if(contacts.get(i).getPhone().indexOf(key) != -1){
						pos = i;
						break;
					}
					i++;
					i = (i % contacts.size());
				}
				break;//case 4: sort(4,0); pos = Collections.binarySearch(contacts/*.subList(from,contacts.size())*/, key/*, new PhoneComparator()*/); break;       
			case 5:  
				for(int i=from,  j=0; j < contacts.size(); j++ ){
					if(contacts.get(i).getAddress().indexOf(key) != -1){
						pos = i;
						break;
					}
					i++;
					i = (i % contacts.size());
				}
				break;//case 5: sort(5,0); pos = Collections.binarySearch(contacts/*.subList(from,contacts.size())*/, key/*, new AddressComparator()*/); break;       
			case 6:  
				for(int i=from,  j=0; j < contacts.size(); j++ ){
					if(contacts.get(i).getEmail().indexOf(key) != -1){
						pos = i;
						break;
					}
					i++;
					i = (i % contacts.size());
				}
				break;//case 6: sort(6,0); pos = Collections.binarySearch(contacts/*.subList(from,contacts.size())*/, key/*, new EmailComparator()*/); break;       
			case 7:  
				for(int i=from,  j=0; j < contacts.size(); j++ ){
					if(contacts.get(i).getIcon() == Integer.parseInt(key)){
						pos = i;
						break;
					}
					i++;
					i = (i % contacts.size());
				}
				break;//case 7: sort(7,0); pos = Collections.binarySearch(contacts/*.subList(from,contacts.size())*/, key/*, new IconComparator()*/); break;       
			default:
				for(int i=from,  j=0; j < contacts.size(); j++ ){
					if(contacts.get(i).toString().indexOf(key) != -1){
						pos = i;
						break;
					}
					i++;
					i = (i % contacts.size());
				}
		}
		//System.out.println("SEARCH: " + pos);
		if (pos == -1) return false;
		notifyListeners(new ActionEvent(this, pos/*+from*/, "Search Done"));
		return true;
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

	/**
	 * setHostServer
	 *
	 * @param server 
	 * @return 
	 */
	public void setHostServer(String server) {
		this.IP = server;
	}
	
	/**
	 * setHostPort
	 *
	 * @param port 
	 * @return 
	 */
	public void setHostPort(int port) {
		this.port = port;
	}
	
	/**
	 * getHostServer
	 *
	 * @param  
	 * @return 
	 */
	public String getHostServer() {
		return IP;
	}
	
	/**
	 * getHostPort
	 *
	 * @param  
	 * @return 
	 */
	public int getHostPort() {
		return port;
	}
}

// Comparators

class IDComparator implements Comparator
{
	public int compare(Object messageWrapper1, Object messageWrapper2)
	{
		try
		{
			Integer messageSubject1 = ((Contact)messageWrapper1).getID();
			Integer messageSubject2 = ((Contact)messageWrapper2).getID();
			if (messageSubject1 == null)
				return -1;
			else
				return messageSubject1.compareTo(messageSubject2);
		}
		catch (Exception e)
		{
			return 0;
		}
	}
}
class FirstNameComparator implements Comparator
{
	public int compare(Object messageWrapper1, Object messageWrapper2)
	{
		try
		{
			String messageSubject1 = ((Contact)messageWrapper1).getFirstName();
			String messageSubject2 = ((Contact)messageWrapper2).getFirstName();
			if (messageSubject1 == null)
				return -1;
			else
				return messageSubject1.compareTo(messageSubject2);
		}
		catch (Exception e)
		{
			return 0;
		}
	}
}
class MiddleNameComparator implements Comparator
{
	public int compare(Object messageWrapper1, Object messageWrapper2)
	{
		try
		{
			String messageSubject1 = ((Contact)messageWrapper1).getMiddleName();
			String messageSubject2 = ((Contact)messageWrapper2).getMiddleName();
			if (messageSubject1 == null)
				return -1;
			else
				return messageSubject1.compareTo(messageSubject2);
		}
		catch (Exception e)
		{
			return 0;
		}
	}
}
class LastNameComparator implements Comparator
{
	public int compare(Object messageWrapper1, Object messageWrapper2)
	{
		try
		{
			String messageSubject1 = ((Contact)messageWrapper1).getLastName();
			String messageSubject2 = ((Contact)messageWrapper2).getLastName();
			if (messageSubject1 == null)
				return -1;
			else
				return messageSubject1.compareTo(messageSubject2);
		}
		catch (Exception e)
		{
			return 0;
		}
	}
}
class PhoneComparator implements Comparator
{
	public int compare(Object messageWrapper1, Object messageWrapper2)
	{
		try
		{
			String messageSubject1 = ((Contact)messageWrapper1).getPhone();
			String messageSubject2 = ((Contact)messageWrapper2).getPhone();
			if (messageSubject1 == null)
				return -1;
			else
				return messageSubject1.compareTo(messageSubject2);
		}
		catch (Exception e)
		{
			return 0;
		}
	}
}

class AddressComparator implements Comparator
{
	public int compare(Object messageWrapper1, Object messageWrapper2)
	{
		try
		{
			String messageSubject1 = ((Contact)messageWrapper1).getAddress();
			String messageSubject2 = ((Contact)messageWrapper2).getAddress();
			if (messageSubject1 == null)
				return -1;
			else
				return messageSubject1.compareTo(messageSubject2);
		}
		catch (Exception e)
		{
			return 0;
		}
	}
}
class EmailComparator implements Comparator
{
	public int compare(Object messageWrapper1, Object messageWrapper2)
	{
		try
		{
			String messageSubject1 = ((Contact)messageWrapper1).getEmail();
			String messageSubject2 = ((Contact)messageWrapper2).getEmail();
			if (messageSubject1 == null)
				return -1;
			else
				return messageSubject1.compareTo(messageSubject2);
		}
		catch (Exception e)
		{
			return 0;
		}
	}
}
class IconComparator implements Comparator
{
	public int compare(Object messageWrapper1, Object messageWrapper2)
	{
		try
		{
			Integer messageSubject1 = ((Contact)messageWrapper1).getIcon();
			Integer messageSubject2 = ((Contact)messageWrapper2).getIcon();
			if (messageSubject1 == null)
				return -1;
			else
				return messageSubject1.compareTo(messageSubject2);
		}
		catch (Exception e)
		{
			return 0;
		}
	}
}

