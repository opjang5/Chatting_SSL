package chatting.data;

public class Person {
	private String ip;
	private String name;
	private MyMsg msg;
	
	public Person(String t_ip,String t_name){
		this.ip = t_ip;
		this.name = t_name;
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
	public String getMsg(){
		return msg.getMsg();
	}
	public void setMsg(String x){
		msg.setMsg(x);
	}
}
