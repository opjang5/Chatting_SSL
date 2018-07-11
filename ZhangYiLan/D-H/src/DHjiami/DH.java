package DHjiami;
import java.util.ArrayList;  

import java.util.List;  

import java.util.Random;

import java.math.*;

 
public class DH{
	/*
	 * 方法名称: Generateg()
	 * 方法功能：生成x位的大素数，返回String类型的大素数
	 */
	public static String Generateg(int x){
		Random rnd=new Random();
		BigInteger tmp=BigInteger.probablePrime(x, rnd);
		String ans = tmp.toString(); //Long.toHexString( createRadomPrimeNunber(x));
		return ans;
	}
	
	/*
	 * 方法名称: Momi()
	 * 方法功能：返回A^b(mod p)的模幂运算的结果
	 */
	public static String Momi(String a, String b,String p){
		BigInteger aa = new BigInteger(a);
		BigInteger bb = new BigInteger(b);
		BigInteger pp = new BigInteger(p);
		BigInteger rr = new BigInteger("1");
		BigInteger zero = new BigInteger("0");
		BigInteger two = new BigInteger("2");
		BigInteger one = new BigInteger("1");
		
		//快速幂取模
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


   
