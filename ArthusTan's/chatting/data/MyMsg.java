package chatting.data;

import java.util.Set;
import java.util.TreeSet;

public class MyMsg {
	private int num;
	private Set<String> msg;
	public MyMsg() {
		num = 0;
		msg = new TreeSet<String>();
	}
	public String getMsg() {
		String x = null;
		for(String y : msg){
			x = y;
			break;
		}
		if(x!=null){
			msg.remove(x);
		}
		return x;
		
	}
	public void setMsg(String x) {
		this.msg.add(x);
	}
	
}
