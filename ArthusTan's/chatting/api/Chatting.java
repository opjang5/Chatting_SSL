package chatting.api;

import java.io.IOException;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chatting.data.Group;
import chatting.data.GroupList;
import chatting.data.Person;
import chatting.data.UserList;

/**
 * Servlet implementation class Chatting
 */
@WebServlet("/Chatting")
public class Chatting extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Chatting() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
	private static GroupList gList = new GroupList();
	private static UserList  uList = new UserList();
	private String getMsg(String ip){
		Person g = null;
		for(Person g0 : uList.Users){
			if(g0.getIp().equals(ip)){
				g = g0;
				break;
			}
		}
		if(g == null)return null;
		return g.getMsg();
	}
	private String sendMsg(String ip1,String ip2,String msg){
		if(ip2.charAt(0)=='G'){//is a group
			Group g = null;
			for(Group g0 : gList.Groups){
				if(g0.getIp().equals(ip2)){
					g = g0;
					break;
				}
			}
			if(g==null)
				return "failed";
			for(Person user : g.Users){
				if(user.getIp().equals(ip1))continue;
				user.setMsg(ip2+":"+ip1+":"+msg);
			}
		}
		else{
			Person g = null;
			for(Person g0 : uList.Users){
				if(g0.getIp().equals(ip2)){
					g = g0;
					break;
				}
			}
			if(g == null)
				return "failed";
			g.setMsg(ip1+"::"+msg);
		}
		return "accept";
	}
}
