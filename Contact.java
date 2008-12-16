//package .Users.mkhatib.Desktop.Network-Project.facelock;
import java.util.*;
/**
 * <<Class summary>>
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public final class Contact {
    private int id;
	private String firstName,middleName,lastName;
	private int sex, status;
	private Address address;
	private Date birthday;
	// {{{ Contact constructor
    /**
     * 
     */
    public Contact() {
        
    }
	// }}}
	
	public Contact(int id, String fname, String mname, String lname, String status,Address address, Date birthday){
		// set them
	}
	
	// Database Constructor
	public Contact(int id, String fname, String mname, String lname, int sex, Date birthday, Address location, int status){
		setID(id);
		setFirstName(fname);
		setMiddleName(mname);
		setLastName(lname);
		setStatus(status);
		setAddress(location);
		setSex(sex);
		setBirthday(birthday);
		
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
	 * setBirthday
	 *
	 * @param bdate 
	 * @return 
	 */
	public void setBirthday(Date bdate) {
		this.birthday = bdate;
	}

	
	
	/**
	 * setSex
	 *
	 * @param sex 
	 * @return 
	 */
	public void setSex(int sex) {
		this.sex = sex;
	}
	
	/**
	 * setStatus
	 *
	 * @param status 
	 * @return 
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	/**
	 * setAddress
	 *
	 * @param add 
	 * @return 
	 */
	public void setAddress(Address add ) {
		this.address = add;
	}
	
	/**
	 * getSex
	 *
	 * @param  
	 * @return sex
	 */
	public String getSex() {
		return ((sex==1)?"Male":"Female");
	}

	/**
	 * getStatus
	 *
	 * @param  
	 * @return 
	 */
	public String getStatus() {
		switch(status){
			case 1: return "Single"; 
			case 2: return "In a Relationship"; 
			case 3: return "Engaged"; 
			case 4: return "Married"; 
			case 5: return "It's Complicated";
			case 6: return "In An open Relationship";
			default: return null;
		}
	}

	
	/**
	 * getAddress
	 *
	 * @param  
	 * @return address
	 */
	public Address getAddress( ) {
		return address;
	}

	
	

	
	/**
	 * toString
	 *
	 * @param  
	 * @return String
	 */
	public String toString( ) {
		return (firstName + " " + middleName + " " + lastName + " - " + getAge(birthday) + " Year Old - From " + address + " - " + getStatus());
	}

	
	
	/**
	 * getAge
	 *
	 * @param  
	 * @return 
	 */
	public int getAge(Date bd) {
		Date now = new Date();
		int age = (now.getYear() - bd.getYear());
		Date newDate = (Date)bd.clone();
		newDate.setYear(now.getYear()+age);
		return ((now.before(newDate)) ? age : age-1);
	}

	
	
	

	

	// Numbers in Words

	
	
}
