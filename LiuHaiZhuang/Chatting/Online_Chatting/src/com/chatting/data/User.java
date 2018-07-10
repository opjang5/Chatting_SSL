package com.chatting.data;

public class User {
	private String name;
	private String ip;
	private MyMsg msg;
	public MyMsg getMsg() {
		return msg;
	}
	public void setMsg(MyMsg msg) {
		this.msg = msg;
	}
	public String getName() {
		return name;
	}
	public String getIp() {
		return ip;
	}
	public User(){
		this.name=null;
		this.ip=null;
		this.msg=new MyMsg();
	}
	public User(String name, String ip) {
		super();
		this.name = name;
		this.ip = ip;
		this.msg=new MyMsg();
	}
	public boolean equals(Object o){
		User tmp=(User)o;
		System.out.println(tmp.getIp());
		if(tmp.ip.equals(this.ip)){
			return true;
		}
		return false;
		
	}
	
}
