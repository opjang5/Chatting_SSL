import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class SocketClient extends Thread{
	private UpdateUiListener loginFrame;
	public UpdateUiListener getLoginFrame() {
		return loginFrame;
	}
	public void setLoginFrame(UpdateUiListener loginFrame) {
		this.loginFrame = loginFrame;
	}
	private Socket socket;
	private DataInputStream din;
	private DataOutputStream dout;
	public SocketClient() {
		try {
			socket=new Socket("localhost", 8088);
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


