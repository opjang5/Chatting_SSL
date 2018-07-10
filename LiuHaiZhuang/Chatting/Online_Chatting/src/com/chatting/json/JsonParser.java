package com.chatting.json;

import java.util.HashMap;
import java.util.Map;

public class JsonParser {
	private Map<String,String>JsonMap=new HashMap<String,String>();
	private String querry;
	public JsonParser(String querry) {
		super();
		this.querry = querry;
		this.Parse();
	}
	public String get(String key){
		return JsonMap.get(key);
	}
	/*
	 * ��������
	 * */
	private void Parse(){
		int len=this.querry.length();
		int flag=0;
		/*
		 * 0״̬Ϊ��{��
		 * 1״̬Ϊ��key��
		 * 2״̬Ϊ��value��
		 * 3Ϊ��
		 * 4Ϊ�ұߡ�
		 * 5Ϊð����
		 * 6Ϊ������ ��Ϊ��
		 * */
		String key="",value="";
		for(int i=0;i<len;i++){
			if(this.querry.charAt(i)=='}')break;
			if(flag==0 && this.querry.charAt(i)=='{'){
				flag=0;
				continue;
			}
			if(flag==0 && this.querry.charAt(i)=='\"'){
				flag=1;
				continue;
			}
			if(flag==1 && (this.querry.charAt(i)!='\"' || this.querry.charAt(i)==':' )){
				key+=this.querry.charAt(i);
				continue;
			}
			if(flag==1 && this.querry.charAt(i)=='\"' && this.querry.charAt(i-1)!='\\'){
				flag=4;
				continue;
			}
			if(flag==4 && this.querry.charAt(i)==':'){
				flag=5;
				continue;
			}
			if(flag==5 && this.querry.charAt(i)=='\"'){
				flag=2;
				continue;
			}
			if(flag==2 && (this.querry.charAt(i)!='\"' || this.querry.charAt(i)==':' )){
				value+=this.querry.charAt(i);
				continue;
			}
			if(flag==2 && this.querry.charAt(i)=='\"'){
				flag=6;
				this.JsonMap.put(key, value);
				key="";value="";
				continue;
			}
			if(flag==6 && this.querry.charAt(i)==','){
				flag=0;
				continue;
			}
		}
	}
	
}
