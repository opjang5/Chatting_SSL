package com.chatting.Test;

import com.chatting.data.User;
import com.chatting.data.UserList;

public class TestUserList {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		UserList.Add("Denny", "10.15.2.6");
		UserList.Add("opjang5", "10.15.2.7");
		UserList.Add("Test", "10.15.2.8");
		UserList.Print();
		UserList.delete("10.15.2.8");
		User user1=new User("Denny", "10.2.9.1");
		User user2=new User("opjang5", "10.2.9.1");
		System.out.println(user1.equals(user2));
		System.out.println(UserList.Querry("10.15.2.7"));
		System.out.println(UserList.Querry("10.15.2.10"));
		UserList.Print();
	}

}
