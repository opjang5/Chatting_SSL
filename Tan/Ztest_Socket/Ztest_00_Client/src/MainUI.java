import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.omg.CORBA.FREE_MEM;

public class MainUI extends JFrame implements UpdateUiListener {
	private static final long serialVersionUID = 1L;
	private JTextArea chatArea = new JTextArea(10, 10);
	private JTree friendsTree = new JTree();
	private JCheckBox chkBox = new JCheckBox("Private");
	private JTextField txtMessage = new JTextField(10);
	private JButton btnSend = new JButton("Send");
	private String userName="";
	private SocketClient sc;
	private String name;
	private String list;
	private String[] userList;
	public MainUI(SocketClient sc, String name,String list) {
		userList = list.split("%");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.name = name;
		setTitle("Welcome : " + name);
		this.sc = sc;
		sc.setLoginFrame(this);
		addCenter();
		addFriendsList();
		addMessage();
		setLocation(500, 500);
		setSize(500, 500);
		btnSend.addActionListener(new SendMsg());
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
	public void addMessage() {
		Box messageBox = Box.createHorizontalBox();
		messageBox.add(chkBox);
		messageBox.add(txtMessage);
		messageBox.add(btnSend);
		add(messageBox, BorderLayout.SOUTH);
	}
	private DefaultMutableTreeNode root;
	private DefaultTreeModel model;
	public void addFriendsList() {
		friendsTree.addTreeSelectionListener(new TreeListener());
		root = new DefaultMutableTreeNode("Friends");
		DefaultMutableTreeNode[] child = new DefaultMutableTreeNode[userList.length];
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
		add(pane, BorderLayout.EAST);
	}
	public void addCenter() {
		JScrollPane pane = new JScrollPane(chatArea);
		pane.setBorder(BorderFactory.createTitledBorder("Record"));
		add(pane);
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
	}
}
