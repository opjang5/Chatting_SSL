package chatting.data;

import java.util.HashSet;
import java.util.Set;

public class UserList {
	public static Set<Person>Users;
	public UserList(){
		Users = new HashSet<Person>();
	}
	public static String Add(Person user){
		if(check(user)){
			Users.add(user);
			return "accept";
		}
		else{
			return "failed";
		}
	}
	public static boolean check(Person user){
		if(Users.contains(user))
			return false;
		return true;
	}
	public static void delete(String user){
		Users.remove(user);
	}
	public String showList(){
		String list = "";
		for(Person tmp : Users){
			list = list + "%" + tmp.getName() + "," + tmp.getIp();
		}
		return list;
	}
}
