import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class LoginFrame extends JFrame implements UpdateUiListener,MouseListener,MouseMotionListener{
	private static final long serialVersionUID = 1L;
	
	private JTextField txtName=new JTextField(10);
	private JTextField txtIp=new JTextField(10);
	
	private MPanel panel=new MPanel("img/login.png");
	
	private JButton btnSend=new JButton("");//Login
	private JButton quiSend=new JButton("");//Quit
	
	private SocketClient socketClient = new SocketClient();
	
	public LoginFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		txtIp.setText(socketClient.getIp()+":8088");
		txtIp.setEditable(false);
		
		this.setLayout(null);
		
		panel.setLayout(null);
		panel.setBounds(0, 0, 377, 510);

		txtName.setOpaque(false);
		txtName.setBounds(40, 255, 297, 50);
		txtName.setBorder(new EmptyBorder(0,0,0,0));
		txtName.setHorizontalAlignment(JTextField.CENTER);
		txtName.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		
		txtIp.setOpaque(false);
		txtIp.setBounds(40, 307, 297, 50);
		txtIp.setBorder(new EmptyBorder(0,0,0,0));
		txtIp.setHorizontalAlignment(JTextField.CENTER);
		txtIp.setFont(new Font("consolas", Font.PLAIN, 20));
		
		btnSend.setOpaque(false);
		btnSend.setBackground(Color.BLACK);
		btnSend.setBounds( 60, 458, 116, 35);
		btnSend.setBorder(new EmptyBorder(0,0,0,0));
		
		quiSend.setOpaque(false);
		quiSend.setBackground(Color.BLACK);
		quiSend.setBounds(203, 458, 116, 35);
		quiSend.setBorder(new EmptyBorder(0,0,0,0));
		
		panel.add(txtName);
		panel.add(txtIp);
		panel.add(btnSend);
		panel.add(quiSend);
		
		quiSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		

		socketClient.start();
		socketClient.setLoginFrame(this);
		btnSend.addActionListener(new LoginListener());

		this.add(panel);
		
		setTitle("Login");		

		this.setUndecorated(true);

		this.setSize(377,510);
		this.setLocation(700,250);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		
		this.setVisible(true);
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
	
	
	Boolean tBoolean = false;
	Point fPoint;
	Point sPoint;
	Point tPoint;
	private void move() {
		Double fx,fy;
		Double sx,sy;
		Double tx,ty;
		fx = fPoint.getX(); fy = fPoint.getY();
		sx = sPoint.getX(); sy = sPoint.getY();
		tx = tPoint.getX(); ty = tPoint.getY();
		fPoint.setLocation(fx+tx-sx, fy+ty-sy);
		setLocation(fPoint);
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		 //TODO Auto-generated method stub
		 tPoint = e.getPoint();
		 move();
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		fPoint = getLocation();
		sPoint = e.getPoint();
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	 
}

class BackgroundPanel extends JPanel
{
	Image im;
	public BackgroundPanel(Image im)
	{
		this.im=im;
		this.setOpaque(true);
	}
	//Draw the back ground.
	public void paintComponent(Graphics g)
	{
		super.paintComponents(g);
		g.drawImage(im,0,0,this.getWidth(),this.getHeight(),this);
		
	}
}
