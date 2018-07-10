import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.TreeSet;

public class ServerCls {
	//public static Set<User> userList = new TreeSet<User>();
	public static void accpetConnection(){
		try {
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(8088);
			while(true){
				Socket socket = serverSocket.accept();
				System.out.println("-->Server : "+socket+" is in.");
				SSSPT t = new SSSPT(socket);
				t.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		accpetConnection();
	}
}
