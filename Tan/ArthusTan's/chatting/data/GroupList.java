package chatting.data;

import java.util.HashSet;
import java.util.Set;

public class GroupList {
	public static Set<Group>Groups;
	public GroupList(){
		Groups = new HashSet<Group>();
	}
	public Group NewGroup(String members){
		Integer numb = Groups.size()+1;
		Group group = new Group("G"+numb.toString(),"G"+numb.toString(),members);
		Groups.add(group);
		return group;
	}
}
