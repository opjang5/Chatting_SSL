package com.chatting.data;

import java.util.Set;
import java.util.TreeSet;

public class MyMsg {
	private int num;
	private Set<String> msg;
	public MyMsg() {
		num = 0;
		msg = new TreeSet<String>();
	}
	/*
	 * 无msg为null
	 * */
	public String getMsg() {
		String x = null;
		/* 取出一个 */
		for(String y : msg){
			x = y;
			break;
		}
		if(x!=null){
			msg.remove(x);
		}
		return x;
		
	}
	/*
	 * x为编码后
	 * */
	public void setMsg(String x) {
		this.msg.add(x);
	}
	
}
