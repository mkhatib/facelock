import java.sql.*;
import java.util.*;
import com.mysql.jdbc.exceptions.*;
/**
 * Interact with the database User Table
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public final class UserModel extends FaceLockModel{
    private User user;
	// Flag to check whether the object has been changed
	private boolean changed=false;
	// Flag to check whether the object is already saved in the database or not
	private boolean isNew=false;
	
	// {{{ UserModel constructor
    /**
     * 
     */
    public UserModel() {
		// Initialize the connection
        super();
    }
	// }}}
	public UserModel(User u){
		this.user = u;
		isNew = true;
	}
	
	/**
	 * findByUsernameAndPassword
	 *
	 * @param  username, password
	 * @return UserModel
	 */
	public static UserModel findByUsernameAndPassword(String username, String password){
		// Initializing the connection to the database
		UserModel model = new UserModel();
		try {
			Statement s = connection.createStatement();
			// Find if there's any record in User table in the database that has the username and password
			ResultSet r = s.executeQuery("select * from User where username='"+username+"' and password='"+password+"'");
			if(r.next()){
				// Create a new user object 
				User u =  new User(r.getInt("id"), r.getString("username"));
				model.setUser(u);
				return model;
			}
		} catch (Exception e) {
			// expression
		}
		return null;
	}
	
	
	/**
     * getAllContacts
     *
     * @param  
     * @return ArrayList<Contact>
     */
	public ArrayList<Contact> getAllContacts(){
		// The User Id
		int id = user.getID();
		// The ArrayList that would be filled with Contacts of the User
		ArrayList<Contact> contactList = new ArrayList<Contact>();
		try {
			Statement s = connection.createStatement();
			// Find All Contacts that are on the ContactList of the user.
			ResultSet r = s.executeQuery("select C.* from Contact C where C.user_id="+id);
			while(r.next()){
				// Create New Contact Per Each Row in the ResultSet and Add it to the contactList
				Contact c =  new Contact(r.getInt("id"),r.getString("firstname"), r.getString("middlename"), r.getString("lastname"), r.getString("Phone"), r.getString("Address"), r.getString("Email"),r.getInt("Icon"));
				contactList.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return contactList;
	}
	
	/**
	 * contacts - another alias for the getAllContacts
	 * 
	 * @param  
	 * @return ArrayList<Contact>
	 */
	public ArrayList<Contact> contacts() {
		return getAllContacts();
	}

	/**
	 * addContact
	 *
	 * @param  contact
	 * @return Contact
	 */
	public boolean addContact(Contact contact){
		// Create new ContactModel with contact and User id
		ContactModel cm = new ContactModel(contact, user.getID());		
		// Save the contact
		return cm.save();
	}
	
	public boolean deleteContact(int id){
		// Find the Contact and return it's ContactModel
		ContactModel cm = ContactModel.findById(id);
		// Delete the Contact if it found
		return (cm != null && cm.delete());
	}
	
	
	/**
	 * save
	 *
	 * @param  
	 * @return boolean
	 */
	public boolean save() {
		// IF there's no user then return false
		if(user == null) return false;
		// if it has been already saved to the database then call update instead
		if(!isNew) return update();
		try {
			Statement s = connection.createStatement();
			// Insert the User into the Database
			int status = s.executeUpdate("insert into User(username,password) values ('"+user.getUsername()+"','"+user.getPassword()+"')");
			if(status > 0) {
				isNew = false;
				ResultSet r = s.executeQuery("select id from User where username='"+user.getUsername()+"'");
				if(r.next()) user.setID(r.getInt("id"));
				return true;
			}
			return false;
		} catch(com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e){
			System.out.println("Username already Taken!");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * update
	 *
	 * @param  
	 * @return boolean
	 */
	public boolean update() {
		// IF there's no user then return false
		if(user == null) return false;
		// if the user is still not saved in the database then call save instead of update
		if(isNew) return save();
		// if it's not changed
		if(!changed) return false;
		try {
			Statement s = connection.createStatement();
			// Update the user 
			int status = s.executeUpdate("Update User set username='"+user.getUsername()+"', password='"+user.getPassword()+"' where id="+user.getID());
			if(status > 0){
				changed = false;
				return true;
			}
			return false;
		} catch(MySQLIntegrityConstraintViolationException e){
			System.out.println("Username already Taken!");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	
	/**
	 * delete
	 *
	 * @param  
	 * @return boolean
	 */
	public boolean delete() {
		// if there is no User or the contact is not yet saved to the database then return false
		if(user == null || user.getID() == 0) return false;
		// Else get the User ID
		int id = user.getID();
		try {
			Statement s = connection.createStatement();
			// Delete the user
			int status = s.executeUpdate("delete from User where id="+id);
			if(status > 0) {
				status = s.executeUpdate("delete from Contact where user_id="+id);
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
	}

	
	
	
	// Just for testing! 
	public static void main(String[] args) {
		// Login
		UserModel user = UserModel.findByUsernameAndPassword("mkhatib","mkhatib");
		if(user != null) 
		{
			System.out.println("Login Successfully!");
			System.out.println(user.getUser());
			ArrayList<Contact> contacts = user.contacts();
			for(int i=0; i<contacts.size(); i++ )
				System.out.println(contacts.get(i));
			// Find Contact
			ContactModel cm = ContactModel.findById(1);
			if(cm != null)
			{
				// Update it
				cm.setFirstName("Noura");
				cm.setMiddleName("Yousef");
				cm.setLastName("Salhi");
				cm.setPhone("+9720000000");
				cm.setAddress("Beit Hanina");
				cm.setEmail("nsalhi@facelock.com");
				cm.setIcon(1);
				if(cm.update())
					System.out.println("Updated Successfully!");
				else System.out.println("Faild to Update!");
			}
			// Delete Contact
			if(user.deleteContact(8))
				System.out.println("Contact Deleted Successfully!");
			else System.out.println("Delete Faild!");
			
		}
		// Register new user
		UserModel newUser = new UserModel(new User("Hasan","hassal"));
		// If Saved Successfully
		if(newUser.save())
		{
			System.out.println("New User Created Successfully!");
			
			// Insert Contact
			Contact newContact = new Contact("Hammam", "Ahmad", "Samarah", "+972599000000", "Jeneen", "hsamarah@gmail.com", 1);
			if(newUser.addContact(newContact)){
				System.out.println("Contact Added Successfully!");
			}
			else System.out.println("Faild to add contact!");			
			
			// Change Password
			newUser.setPassword("r33m0");
			if (newUser.update()) System.out.println(newUser+" has been updated Successfully!");
			else System.out.println("Update Faild!");
			
		}
		// Register new user
		UserModel newUser2 = new UserModel(new User("abd","abd"));
		// If Saved Successfully
		if(newUser2.save())
		{
			System.out.println("New User Created Successfully!");
			
			// Insert Contact
			Contact newContact = new Contact("Hammam", "Ahmad", "Samarah", "+972599000000", "Jeneen", "hsamarah@gmail.com", 1);
			if(newUser2.addContact(newContact)){
				System.out.println("Contact Added Successfully!");
			}
			else System.out.println("Faild to add contact!");			
			
			// delete the newUser2
			if (newUser2.delete()) System.out.println("User Deleted Successfully!");
			else System.out.println("Delete Faild!");
			
			
		}
		
	}
	
	
	
	
	/**
	 * setUser
	 *
	 * @param  User
	 * @return 
	 */
	public void setUser(User u){
		this.user = u;
	}
	/**
	 * setUsername
	 *
	 * @param username 
	 * @return 
	 */
	public void setUsername(String username) {
		if(user == null) return;
		user.setUsername(username);
		changed = true;
	}
	/**
	 * setPassword
	 *
	 * @param password
	 * @return 
	 */
	public void setPassword(String password) {
		if(user == null) return;
		user.setPassword(password);
		changed = true;
	}

	/**
	 * getUsername
	 *
	 * @param  
	 * @return String
	 */
	public String getUsername() {
		if(user == null) return null;
		return user.getUsername();
	}
	/**
	 * getPassword
	 *
	 * @param  
	 * @return String
	 */
	public String getPassword() {
		if(user == null) return null;
		return user.getPassword();
	}
	
	/**
	 * getUser
	 *
	 * @param  
	 * @return User
	 */
	public User getUser() {
		return user;
	}
	
	
	
}

