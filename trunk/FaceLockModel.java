// This model will talk to the database and get whatever information about a contact
//package .Users.mkhatib.Desktop.Network-Project.facelock;
import java.sql.*;
/**
 * <<Class summary>>
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public final class FaceLockModel {
    private final String DRIVER_NAME = "com.mysql.jdbc.Driver";//"org.gjt.mm.mysql.Driver";
	private final String SERVER = "localhost";
	private final String DATABASE_NAME = "FaceLockDB";
	private final String USERNAME = "FLUser";
	private final String PASSWORD = "FLPassword";
	private final String JDBC_URL = "jdbc:mysql://" + SERVER + ":3306/" + DATABASE_NAME;
	
	private Connection connection;
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
	
	public static void main(String[] args) {
		FaceLockModel model = new FaceLockModel();
		
	}
}

