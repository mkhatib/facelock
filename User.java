/**
 * Wrapping Up User Information
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
 
public final class User {
    private String username;
	private String password;
	private int id;

    public User(int id, String username,String password) {
        setUsername(username);
		setPassword(password);
		setID(id);
    }
	
	public User(String username,String password){
		setUsername(username);
		setPassword(password);
	}

	public User(int id, String username) {
        setUsername(username);
		setID(id);
    }

	/**
	 * setUsername
	 *
	 * @param username 
	 * @return 
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * setPassword
	 *
	 * @param password 
	 * @return 
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * getUsername
	 *
	 * @param 
	 * @return String
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * getPassword
	 *
	 * @param 
	 * @return String 
	 */
	public String getPassword() {
		return password;
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
	 * toString
	 *
	 * @param  
	 * @return String
	 */
	public String toString() {
		return username;
	}
}
