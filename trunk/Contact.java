import java.io.*;
import java.util.*;
/**
 * Wrap up Contact Information 
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */


public final class Contact implements Serializable{
    // Parameters
	private int id=0;
	private String firstName,middleName,lastName;
	private int icon;
	private String address, phone, email;
	
	// Constructors
	// {{{ Contact constructor
    /**
     * 
     */
    public Contact() {        
    }
	// }}}
	
	public Contact(int id, String fname, String mname, String lname, String phone, String address, String email, int icon){
		setID(id);
		setFirstName(fname);
		setMiddleName(mname);
		setLastName(lname);
		setPhone(phone);
		setAddress(address);
		setEmail(email);
		setIcon(icon);
	}
	// Constructor for new contacts that haven't been saved to the database and got an id yet
	public Contact(String fname, String mname, String lname, String phone, String address, String email, int icon){
		this(0,fname,mname,lname,phone,address,email,icon);
	}

	/*
	 * toString
	 *
	 * @param  
	 * @return String
	 */
	public String toString( ) {
		return (firstName + " " + middleName + " " + lastName + " - From " + address + " - " + email + " - " + phone);
	}

	
	// Setters
	/**
	 * setEmail
	 *
	 * @param email 
	 * @return 
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * setPhone
	 *
	 * @param phone 
	 * @return 
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * setAddress
	 *
	 * @param address 
	 * @return 
	 */
	public void setAddress(String address) {
		this.address = address;
	}	
	/**
	 * setID
	 *
	 * @param id 
	 * @return 
	 */
	public void setID(int id) {
		this.id = id;
	}
	/**
	 * setFirstName
	 *
	 * @param fname 
	 * @return 
	 */
	public void setFirstName(String fname) {
		this.firstName = fname;
	}
	/**
	 * setMiddleName
	 *
	 * @param mname 
	 * @return 
	 */
	public void setMiddleName(String mname) {
		this.middleName = mname;
	}
	/**
	 * setLastName
	 *
	 * @param lname 
	 * @return 
	 */
	public void setLastName(String lname) {
		this.lastName = lname;
	}
	/**
	 * setIcon
	 *
	 * @param icon 
	 * @return 
	 */
	public void setIcon(int icon) {
		this.icon = icon;
	}
	/**
	 * getID
	 *
	 * @param  
	 * @return int
	 */
	public int getID() {
		return id;
	}
	/**
	 * getFirstName
	 *
	 * @param  
	 * @return String
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * getMiddleName
	 *
	 * @param  
	 * @return String
	 */
	public String getMiddleName() {
		return middleName;
	}
	/**
	 * getLastName
	 *
	 * @param  
	 * @return String
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * getIcon
	 *
	 * @param  
	 * @return int
	 */
	public int getIcon() {
		return icon;
	}
	// Getters 
	/**
	 * getEmail
	 *
	 * @param  
	 * @return 
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * getPhone
	 *
	 * @param  
	 * @return 
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * getAddress
	 *
	 * @param  
	 * @return 
	 */
	public String getAddress() {
		return address;
	}
}
