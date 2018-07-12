import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;

public class SocketClient extends Thread{
	private String ip = "localhost";
	private UpdateUiListener loginFrame;
	public UpdateUiListener getLoginFrame() {
		return loginFrame;
	}
	public void setLoginFrame(UpdateUiListener loginFrame) {
		this.loginFrame = loginFrame;
	}
	public String getIp(){
		return ip;
	}
	private Socket socket;
	private DataInputStream din;
	private DataOutputStream dout;
	public SocketClient() {
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(new File("src/host.txt"));
			byte[] host = new byte[4];
			int len = 0;
			ip = "";
			while((len = fin.read(host))!=-1)
				ip += new String(host,0,len);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			socket=new Socket(ip, 8088);
			//socket=new Socket("localhost", 8088);
			din=new DataInputStream(socket.getInputStream());
			dout=new DataOutputStream(socket.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendMsg(String msg)	{
		try {
			dout.writeUTF(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	@Override
	public void run() {
		while(true)	{
			try {
				String result=din.readUTF();
				loginFrame.message(result);
				System.out.println(result+" Client");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}


