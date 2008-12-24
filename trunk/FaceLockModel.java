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
	
	
	public Contact findContact(int id){
		try {
			Statement s = connection.createStatement();
			ResultSet r = s.executeQuery("select C.*,CO.name,Ci.name,T.name, A.street from Contact C,Address A, Country CO, City Ci, Town T where A.id=C.location and A.town_id=T.id and T.city_id=Ci.id and Ci.country_id=Co.id and C.id='"+id+"'");

			if(r.next())
			{
				Address add = new Address(r.getString(9),r.getString(10),r.getString(11),r.getString(12));
				return (new Contact(id,r.getString("firstname"), r.getString("middlename"), r.getString("lastname"), r.getInt("sex"), r.getDate("birthday"), add,r.getInt("status")));
			}
		} catch (Exception e) {
			// expression
		} finally {
			// expression
		}
		return null;
	}
	
	public Contact findContactById(int id){
		return findContact(id);
	}
	
	public Contact find(int id){
		return findContact(id);
	}
	
	public static void main(String[] args) {
		FaceLockModel model = new FaceLockModel();
		Contact contact = model.findContact(2);
		if(model != null){
			System.out.println("Contact Found!");
			System.out.println(contact);			
		}
		else
			System.out.println("Not Found!");
			
	}
	
}

