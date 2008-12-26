import java.net.*;
/**
 * <<Class summary>>
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public final class FLServer {
   
	//private String IP = "localhost";
	private int PORT = 7488;
	// {{{ FLServer constructor
    /**
     * 
     */
    public FLServer() {
        try{
			ServerSocket server = new ServerSocket(PORT);
			while(true){
				Socket client = server.accept();
				HandleRequest hr = new HandleRequest(client);
				Thread t = new Thread(hr);
				t.start();
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
    }
	// }}}
}

class HandleRequest implements Runnable{
	private Socket client;
	
	public HandleRequest(Socket client){
		this.client = client;
	}
	
	
	
	public void run(){
		
	}
}