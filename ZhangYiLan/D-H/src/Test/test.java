package Test;

import java.math.BigInteger;

import DHjiami.DH;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println(DH.Generateg(1024));
		String p = DH.Generateg(5);
		String g = DH.Generateg(3);
		String a = DH.Generateg(3);
		String A= DH.Momi(g,a,p);
		System.out.println("p = "+p);
		System.out.println("g = "+g);
		System.out.println("a = "+a);
		System.out.println("A=g^a mod p:"+A);
	}

}
