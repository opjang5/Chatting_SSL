package com.chatting.api;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chatting.data.User;
import com.chatting.data.UserList;
import com.chatting.json.JsonParser;

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
		String querryStr=request.getQueryString();
		if(this.checkLoginPara(querryStr)==false){
			this.doGetWrong(response);
			return ;
		}
		/* 此处以下是正常的参数 */
		String []operation=this.getLoginPara(querryStr,response);
		String user=operation[1];String ip=operation[2];
		if(operation[0].equals("login")){
			if(UserList.Add(user, ip)==false){
				this.doGetWrong(response);
				return ;
			}
			this.doGetJump(response);
			return ;
		}
		else if(operation[0].equals("getuserlist")){
			String userlist=UserList.getUserList();
			this.doGetUserListResponse(response, userlist);
			return ;
		}
		else if(operation[0].equals("logout")){
			UserList.delete(ip);
		}
		else{
			this.doGetWrong(response);
			return ;
		}
		this.doGetAccept(response);
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(), "utf-8"));
		StringBuffer sb = new StringBuffer("");
		String temp;
		while ((temp = br.readLine()) != null) {
		sb.append(temp);
		}
		br.close();
		String params = sb.toString();
		JsonParser jsonp=new JsonParser(params);
		String operation=jsonp.get("op");
		if (operation.equals("receive")){
			String user1=jsonp.get("user1");
			User re=UserList.getUser(user1);
			String Msg=re.getMsg().getMsg();
			this.SendMsg(response, Msg);
		}
		else if(operation.equals("send")){
			
			String user1=jsonp.get("user1");
			String []user2=jsonp.get("user2").split(":");
			String Msg=jsonp.get("Msg");
			String tmp=user1+"::"+Msg;
			for(int i=0;i<user2.length;i++){
				UserList.addUserMsg(user2[i],tmp);
			}
			this.doGetAccept(response);
//			if(user2.length==1){
//				/* 单人模式 */
//				String tmp=user1+"::"+Msg;
//				User re=UserList.getUser(user2[0]);
//				re.getMsg().setMsg(tmp);
//			}
//			else{
//				/* 多人模式 */
//				String tmp=user1+"::"+Msg;
//				for(int i=0;i<user2.length;i++){
//					User re=UserList.getUser(user2[i]);
//					re.getMsg().setMsg(tmp);
//				}
//			}
		}
		else {
			this.doGetWrong(response);
		}
		return ;
	}
	/*
	 * 检查参数是否正确传递
	 * */
	private boolean checkLoginPara(String str){
		if(str==null)return false;
		String[] temp=str.split("&");
		if(temp.length!=3)return false;
 		String[] lr = null;
// 		int n = temp.length;
// 		for(String tmp:temp){
// 			System.out.println(tmp);
// 		}
// 		System.out.println(n);
// 		if(n!=3) return false;
 		for(int i=0;i<3;i++){
 			lr=temp[i].split("=");
 			if(lr.length!=2) return false;
 			switch (i) {
			case 0:
				if(!(lr[0].equals("op"))) return false;
				break;
			case 1:
				if(!(lr[0].equals("user"))) return false;
				break;
			case 2:
				if(!(lr[0].equals("ip"))) return false;
				break;
			default:
				break;
			}
 		}
 		return true;
	}
	/* 
	 * 参数传递错误
	 *  */
	private void doGetWrong(HttpServletResponse response) throws IOException{
		String data="failed";
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		GZIPOutputStream gout = new GZIPOutputStream(bout); //buffer
		gout.write(data.getBytes());
		gout.close();
		byte g[] = bout.toByteArray();
		response.setHeader("Content-Encoding", "gzip");
		response.setHeader("Content-Length",g.length +"");
		response.getOutputStream().write(g);
		return ;
	}
	/*
	 * 执行成功
	 * */
	private void doGetAccept(HttpServletResponse response) throws IOException{
		String data="accept";
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		GZIPOutputStream gout = new GZIPOutputStream(bout); //buffer
		gout.write(data.getBytes());
		gout.close();
		byte g[] = bout.toByteArray();
		response.setHeader("Content-Encoding", "gzip");
		response.setHeader("Content-Length",g.length +"");
		response.getOutputStream().write(g);
		return ;
	}
	/*
	 * 得到参数
	 * */
	private String[] getLoginPara(String str,HttpServletResponse response) throws IOException{
		String[] result = new String[3];
 		String[] lr = new String[2];
 		String[] temp=str.split("&");
 		try{
 			for(int i=0;i<3;i++){
 	 	 		lr = temp[i].split("=");
 	 	 		result[i]=lr[1];
 	 		}
 		}catch(Exception e){
 			this.doGetWrong(response);
 		}
 		return result;
	}
	/*
	 * 传送userlist
	 * */
	private void doGetUserListResponse(HttpServletResponse response,String userlist) throws IOException{
		String data=userlist;	
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		GZIPOutputStream gout = new GZIPOutputStream(bout); //buffer
		gout.write(data.getBytes());
		gout.close();
		byte g[] = bout.toByteArray();
		response.setHeader("Content-Encoding", "gzip");
		response.setHeader("Content-Length",g.length +"");
		response.getOutputStream().write(g);
		return ;
	}
	private void doGetJump(HttpServletResponse response){
		response.setStatus(302);//设置服务器的响应状态码
		/*
		 * 设置响应头，服务器通过 Location这个头，来告诉浏览器跳到哪里，这就是所谓的请求重定向
		 * */
		response.setHeader("Location", "/Chatting_Html/Test/TestLoginAccept.jsp");
	}
	private void SendMsg(HttpServletResponse response,String Msg) throws IOException{
		String data=Msg;
		if(Msg==null)data="null";
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		GZIPOutputStream gout = new GZIPOutputStream(bout); //buffer
		gout.write(data.getBytes());
		gout.close();
		byte g[] = bout.toByteArray();
		response.setHeader("Content-Encoding", "gzip");
		response.setHeader("Content-Length",g.length +"");
		response.getOutputStream().write(g);
		return ;
	}
}
