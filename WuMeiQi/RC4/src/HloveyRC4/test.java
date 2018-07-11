package HloveyRC4;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    String inputStr = "做个好男人";      
	    String key = "abcdefg";         
	   
	    String str =HloveyRC4.RC4(inputStr,key);  
	      
	    //打印加密后的字符串      
	    System.out.println(str);    
	      
	    //打印解密后的字符串      
	    System.out.println(HloveyRC4.RC4(str,key));  
	}

}
