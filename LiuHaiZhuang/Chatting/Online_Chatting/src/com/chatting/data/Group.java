package com.chatting.data;

import java.util.Set;
import java.util.TreeSet;

public class Group {
	/* Ⱥ��ip */
	private String ip;
	/* Ⱥ������ */
	private String name;
	/* Ⱥ�еĳ�Ա */
	public static Set<User>Users;
	/*
	 * op
	 * user1
	 * user2 ����ʱuser2д�ɶ���ģʽ name1:name2:name3:...:namen
	 * Message
	 * */
	public Group(String t_ip,String t_name,String members){
		this.ip = t_ip;
		this.name = t_name;
		Users = new TreeSet<User>();
		String[] member = members.split(":");
		/* ��0��Ϊ */
		for(int i=1;i<member.length;i++){
			String[] ttmp = member[i].split(",");
			Users.add(new User(ttmp[1], ttmp[0]));
		}
		for(User user : Users){
			System.out.println(user.getName() + " : " + user.getIp());
		}
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean have(User user){
		if(Users.contains(user))
			return true;
		return false;
	}
	public void addMember(User user){
		if(Users.contains(user))
			return;
		Users.add(user);
	}
	public String getGroupList(){
		String ans="";
		int n=0;
		for(User value:this.Users){
			n++;
			if(n<this.Users.size()){
				ans=ans+value.getName()+","+value.getIp()+":";
			}
			else{
				ans=ans+value.getName()+","+value.getIp();
			}
			
		}
		return ans;
	}
}
