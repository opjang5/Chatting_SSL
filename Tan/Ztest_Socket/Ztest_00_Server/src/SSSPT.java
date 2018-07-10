import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class SSSPT extends Thread {
	private Socket socket;
	private DataOutputStream dout;
	private DataInputStream din;
	private String name;
	public SSSPT(Socket socket) {
		this.socket = socket;
		try {
			dout = new DataOutputStream(socket.getOutputStream());
			din = new DataInputStream(socket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void run() {
		while (true) {
			try {
				try {
					String line = din.readUTF();
					String values[] = line.split("@@");
					if (values[0].equals("login")) {
						UserMaps.dbMaps.put(values[1], this);
						name = values[1];
						String list = "";
						for(String key : UserMaps.dbMaps.keySet()){
							list += "%"+key;
						}
						dout.writeUTF("login"+list);
						for(String key : UserMaps.dbMaps.keySet()){
							if(key.equals(values[1]))continue;
							UserMaps.dbMaps.get(key).dout.writeUTF("list"+list);
						}
					}
					if (values[0].equals("msg")) {
						String message = "Private>>"+values[3]+" :   "+values[2];
						UserMaps.dbMaps.get(values[1]).dout.writeUTF(message);
						UserMaps.dbMaps.get(values[3]).dout.writeUTF(message);
					}
					if (values[0].equals("common")) {
						String message = "Public >>"+values[2]+" :   "+values[1];
						for (String userName : UserMaps.dbMaps.keySet()) {
							UserMaps.dbMaps.get(userName).dout.writeUTF(message);
						}
					}

				} catch (Exception e) {
					System.out.println("-->Server : "+socket+" is out.");
					UserMaps.dbMaps.remove(name);
					break;
				}
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

