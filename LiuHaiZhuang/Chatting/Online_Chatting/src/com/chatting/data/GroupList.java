package com.chatting.data;

import java.util.HashSet;
import java.util.Set;

public class GroupList {
	public static Set<Group>Groups=new HashSet<Group>();
	public static Group Add(String members){
		Integer numb = Groups.size()+1;
		Group group = new Group("G"+numb.toString(),"G"+numb.toString(),members);
		Groups.add(group);
		return group;
	}
	/* 
	 * 是否包含此ip成员
	 *  */
	public static boolean contain(String ip){
		for(Group value:Groups){
			if(value.getIp().equals(ip)){
				return true;
			}
		}
		return false;
	}
}
