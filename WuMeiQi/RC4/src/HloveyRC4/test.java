package HloveyRC4;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    String inputStr = "����������";      
	    String key = "abcdefg";         
	   
	    String str =HloveyRC4.RC4(inputStr,key);  
	      
	    //��ӡ���ܺ���ַ���      
	    System.out.println(str);    
	      
	    //��ӡ���ܺ���ַ���      
	    System.out.println(HloveyRC4.RC4(str,key));  
	}

}
