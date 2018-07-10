import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginFrame extends JFrame implements UpdateUiListener {
	private static final long serialVersionUID = 1L;
	private JTextField txtName=new JTextField(10);
	private JPanel panel=new JPanel();
	private JButton btnSend=new JButton("Login");
	private SocketClient socketClient=new SocketClient();
	public LoginFrame() {
		panel.add(new JLabel("Name :"));
		panel.add(txtName);
		panel.add(btnSend);
		socketClient.start();
		socketClient.setLoginFrame(this);
		btnSend.addActionListener(new LoginListener());
		add(panel);		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("LoginFrame");
		setLocation(500, 500);
		setSize(280, 80);
		setVisible(true);
	}
	class LoginListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("登录");
			String msg="login@@"+txtName.getText();
			socketClient.sendMsg(msg);
		}	
	}
	@Override
	public void message(String result) {
		if("login".equals((result.split("%"))[0])) {
			new MainUI(socketClient,txtName.getText(),result);
			dispose();
		}
		
	}
}
