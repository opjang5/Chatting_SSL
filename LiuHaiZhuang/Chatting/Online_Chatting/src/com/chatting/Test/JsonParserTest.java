package com.chatting.Test;

import com.chatting.json.JsonParser;

public class JsonParserTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * {"op":"1","user1":"opjang5","user2":"Denny","Msg":"I love you!"}
		 * */
		String tmp="{\"op\":\"1\",\"user1\":\"opjang5\",\"user2\":\"Denny\",\"Msg\":\"I love you!\"}";
		System.out.println(tmp);
		JsonParser JsonP=new JsonParser(tmp);
		System.out.println(JsonP.get("op"));
		System.out.println(JsonP.get("user1"));
		System.out.println(JsonP.get("user2"));
		System.out.println(JsonP.get("Msg"));
		String tmp1="opjang5";
		String []tmp2=tmp1.split(":");
		System.out.println(tmp2[0]);
//		System.out.println(tmp2[1]);
	}

}
