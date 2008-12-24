import java.sql.*;
/**
 * This model will talk to the database and get whatever information about a contact
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public class FaceLockModel {
    private final String DRIVER_NAME = "com.mysql.jdbc.Driver";//"org.gjt.mm.mysql.Driver";
	private final String SERVER = "localhost";
	private final String DATABASE_NAME = "FaceLockDB";
	private final String USERNAME = "FLUser";
	private final String PASSWORD = "FLPassword";
	private final String JDBC_URL = "jdbc:mysql://" + SERVER + ":3306/" + DATABASE_NAME;
	
	protected static Connection connection;
	// {{{ FaceLockModel constructor
    /**
     * 
     */
    public FaceLockModel() {
        try {
			Class.forName(DRIVER_NAME);
        	connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
			System.out.println("Connected Successfully!");
			
        } catch (ClassNotFoundException e) {
        	System.out.println("ERROR: Could not Find Database Driver!");
        } catch(SQLException e) {
        	System.out.println("ERROR: Could not Connect to the Database!");	
			e.printStackTrace();
        }
		
    }
	// }}}
	
}

