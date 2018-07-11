package DHjiami;
import java.util.ArrayList;  

import java.util.List;  

import java.util.Random;

import java.math.*;

 
public class DH{
	/*
	 * ��������: Generateg()
	 * �������ܣ�����xλ�Ĵ�����������String���͵Ĵ�����
	 */
	public static String Generateg(int x){
		Random rnd=new Random();
		BigInteger tmp=BigInteger.probablePrime(x, rnd);
		String ans = tmp.toString(); //Long.toHexString( createRadomPrimeNunber(x));
		return ans;
	}
	
	/*
	 * ��������: Momi()
	 * �������ܣ�����A^b(mod p)��ģ������Ľ��
	 */
	public static String Momi(String a, String b,String p){
		BigInteger aa = new BigInteger(a);
		BigInteger bb = new BigInteger(b);
		BigInteger pp = new BigInteger(p);
		BigInteger rr = new BigInteger("1");
		BigInteger zero = new BigInteger("0");
		BigInteger two = new BigInteger("2");
		BigInteger one = new BigInteger("1");
		
		//������ȡģ
		aa = aa.mod(pp);
		for(;bb.equals(zero)==false;bb = bb.divide(two)){
			if(bb.mod(two).equals(one))
				rr = (rr.multiply(aa)).mod(pp);
			aa = (aa.multiply(aa)).mod(pp);
		}
		
		String res = rr.toString();
		return res;
	}
}


   
