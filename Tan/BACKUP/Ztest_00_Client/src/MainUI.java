import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
//import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
//import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

public class MainUI extends JFrame implements UpdateUiListener,MouseListener,MouseMotionListener{
	private static final long serialVersionUID = 1L;
	private JTextArea chatArea = new JTextArea(10, 10);
	private JLabel txtName = new JLabel();
	private JTree friendsTree = new JTree();
	//private JCheckBox chkBox = new JCheckBox("Private");
	private JRadioButton chkBox = new JRadioButton();
	//private JTextField txtMessage = new JTextField(10);
	private JTextArea txtMessage = new JTextArea(3,10);
	private JButton btnSend = new JButton("");//Send
	private JButton quitBtn = new JButton("");//Send
	private String userName="";
	private SocketClient sc;
	private String name;
	private String[] userList;
	private MPanel mp = new MPanel("img/chat.png");
	
	public MainUI(SocketClient sc, String name,String list) {
		userList = list.split("%");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.name = name;
		this.setLayout(null);
		this.setUndecorated(true);
		
		mp.setLayout(null);
		
		setTitle("Welcome : " + name);
		
		txtName.setText("Welcome : " + name);
		txtName.setFont(new Font("微软雅黑",Font.BOLD,20));
		txtName.setBounds(234, 40, 500, 22);
		txtName.setForeground(new Color(155, 155, 50));
		mp.add(txtName);
		
		this.sc = sc;
		sc.setLoginFrame(this);
		
		mp.setSize(779, 573);
		
		btnSend.setBounds(571, 486, 170, 53);
		btnSend.setBackground(Color.RED);
		btnSend.setOpaque(false);
		btnSend.setBorder(new EmptyBorder(0,0,0,0));
		btnSend.addActionListener(new SendMsg());

		quitBtn.setBounds(36, 507, 120, 37);
		quitBtn.setBackground(Color.BLUE);
		quitBtn.setOpaque(false);
		quitBtn.setBorder(new EmptyBorder(0,0,0,0));
		quitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		
		chkBox.setBounds(240, 503, 30, 30);
		chkBox.setOpaque(false);
		
		this.add(mp);
		this.setLocation(500, 500);
		this.setSize(779, 573);
		
		JScrollPane pane1 = new JScrollPane(chatArea);
		
		chatArea.setEditable(false);
		chatArea.setLineWrap(true);
		chatArea.setBackground(Color.WHITE);
		chatArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		
		pane1.setBorder(BorderFactory.createTitledBorder("Record"));
		pane1.setBackground(Color.GREEN);
		pane1.setBorder(new EmptyBorder(0,0,0,0));
		pane1.setOpaque(false);
		pane1.setBounds(234,80,515,216);
		
		JScrollPane pane2 = new JScrollPane(txtMessage);
		
		txtMessage.setEditable(true);
		txtMessage.setLineWrap(true);
		txtMessage.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		
		pane2.setBorder(new EmptyBorder(0,0,0,0));
		pane2.setBackground(Color.WHITE);
		pane2.setBounds(234, 360, 515, 88);
		
		mp.add(pane1);
		mp.add(pane2);
		
		addFriendsList();
		
		mp.add(chkBox);
		mp.add(btnSend);
		mp.add(quitBtn);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		
		setVisible(true);
	}
	class SendMsg implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(chkBox.isSelected())
				sc.sendMsg("msg@@"+userName+"@@"+txtMessage.getText()+"@@"+name);
			else
				sc.sendMsg("common@@"+txtMessage.getText()+"@@"+name);
		}
		
	}
	private DefaultMutableTreeNode root;
	private DefaultTreeModel model;
	public void addFriendsList() {
		friendsTree.addTreeSelectionListener(new TreeListener());
		root = new DefaultMutableTreeNode(" - - - LIST - - - ");
		DefaultMutableTreeNode[] child = new DefaultMutableTreeNode[userList.length];
		
		Icon icon0 = new ImageIcon("src/leaf_def.gif");
		Icon icon1 = new ImageIcon();
		Icon icon2 = new ImageIcon();
		DefaultTreeCellRenderer cellRenderer = new DefaultTreeCellRenderer();
		cellRenderer.setLeafIcon(icon0);
		cellRenderer.setOpenIcon(icon1);
        cellRenderer.setClosedIcon(icon2);
        cellRenderer.setBackgroundNonSelectionColor(new Color(251, 248, 241));
		friendsTree.setCellRenderer(cellRenderer);

		for(int i=1;i<userList.length;i++){
			child[i-1] = new DefaultMutableTreeNode(userList[i]);
		}
		for(int i=1;i<userList.length;i++)
			root.add(child[i-1]);
		model = new DefaultTreeModel(root);
		friendsTree.setModel(model);
		JScrollPane pane = new JScrollPane(friendsTree);
		pane.setBorder(BorderFactory.createTitledBorder("Friend_List"));
		pane.setPreferredSize(new Dimension(150, 600));
		
		friendsTree.setBackground(new Color(251, 248, 241));

		pane.setOpaque(false);
		pane.setBackground(new Color(251, 248, 241));
		pane.setBounds(27, 20, 140, 450);
		
		mp.add(pane, BorderLayout.EAST);
	}
	class TreeListener implements TreeSelectionListener	{
		@Override
		public void valueChanged(TreeSelectionEvent e) {
			DefaultMutableTreeNode node=(DefaultMutableTreeNode) friendsTree.getLastSelectedPathComponent();
			String userName1=node.getUserObject().toString();
			userName=userName1;
			System.out.println(userName);
		}
	}
	@Override
	public void message(String result) {
		if((result.split("%"))[0].equals("list")){
			while(root.getChildCount()>0)root.remove(0);
			userList = result.split("%");
			DefaultMutableTreeNode[] child = new DefaultMutableTreeNode[userList.length];
			for(int i=1;i<userList.length;i++){
				child[i-1] = new DefaultMutableTreeNode(userList[i]);
			}
			for(int i=1;i<userList.length;i++)
				root.add(child[i-1]);
			model = new DefaultTreeModel(root);
			friendsTree.setModel(model);
			model.reload();
		}
		else
			chatArea.append(result+"\r\n");

		chatArea.setCaretPosition(chatArea.getText().length());
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
