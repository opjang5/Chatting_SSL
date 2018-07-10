package com.chatting.data;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class UserList implements Comparator {
	public static Set<User>Users=new HashSet<User>();
	/*
	 * 存在重复则返回false
	 * */
	public static boolean Add(String user,String ip){
		User tmp=new User(user, ip);
		if(Querry(ip)){
			return false;
		}
		Users.add(tmp);
		return true;
	}
	/*
	 * user包含name,ip
	 * */
	public static User getUser(String user){
		String[] tmp=user.split(",");
		User ans=null;
		for(User value:Users){
			if(value.getIp().equals(tmp[1])){
				ans=value;
				break;
			}
		}
		return ans;
	}
	public static String getUserMsg(String user){
		String[] tmp=user.split(",");
		String ans=null;
		for(User value:Users){
			if(value.getIp().equals(tmp[1])){
				ans=value.getMsg().getMsg();
				break;
			}
		}
		return ans;
	}
	public static void addUserMsg(String user,String Msg){
		String[] tmp=user.split(",");
		for(User value:Users){
			if(value.getIp().equals(tmp[1])){
				value.getMsg().setMsg(Msg);
				break;
			}
		}
		return ;
	}
	/* 
	 * 查询函数
	 * 查询列表中是否有这个用户
	 *  */
	public static boolean Querry(String ip){
		for(User tmp:Users){
			if(tmp.getIp().equals(ip)){
				return true;
			}
		}
		return false;
	}
	/*
	 * 删除用户
	 * */
	public static void delete(String ip){
		User select=null;
		for(User tmp:Users){
			if(tmp.getIp().equals(ip)){
				select=tmp;
				break ;
			}
		}
		Users.remove(select);
		return ;
	}
	public static String getUserList(){
		String ans="";
		int i=0;
		for(User tmp:Users){
			String user=tmp.getName();
			String ip=tmp.getIp();
			i++;
			if(i!=Users.size()){
				ans=ans+user+","+ip+":";
			}
			else {
				ans=ans+user+","+ip;
			}
		}
		return ans;
	}
	public static void Print(){
		for(User value:Users){
			System.out.print(value.getName()+":");
			System.out.println(value.getIp());
		}
	}
	@Override
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		return 0;
	}
}
