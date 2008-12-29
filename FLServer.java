import java.net.*;
/**
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public final class FLServer {
	private static int PORT = 7488;
    
	public FLServer() {
    }
	
	public static void main(String[] args) {
		try{
			ServerSocket server = new ServerSocket(PORT);
			while(true){
				Socket client = server.accept();
				System.out.println(client + " Connected!");
				
				HandleRequest hr = new HandleRequest(client);
				Thread t = new Thread(hr);
				t.start();
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
}

