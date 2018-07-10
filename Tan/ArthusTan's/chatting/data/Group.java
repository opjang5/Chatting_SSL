package chatting.data;

import java.util.Set;
import java.util.TreeSet;

public class Group {
	private String ip;
	private String name;
	public static Set<Person>Users;
	
	public Group(String t_ip,String t_name,String members){
		this.ip = t_ip;
		this.name = t_name;
		Users = new TreeSet<Person>();
		String[] member = members.split("%");
		for(int i=1;i<member.length;i++){
			String[] ttmp = member[i].split(",");
			Users.add(new Person(ttmp[1], ttmp[0]));
		}
		for(Person user : Users){
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
	public boolean have(Person user){
		if(Users.contains(user))
			return true;
		return false;
	}
	public void addMember(Person user){
		if(Users.contains(user))
			return;
		Users.add(user);
	}
}
