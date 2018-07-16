package com.ustb.server;

import java.net.ServerSocket;
import java.net.Socket;

import com.ustb.rsa.RSA;

public class ServerCls {
	public static void accpetConnection(){
		try {

			RSA.genKeyPair("./");
			String cask = RSA.getPriKey("./");
			String capk = RSA.getPubKey("./");
			
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(8088);
			while(true){
				Socket socket = serverSocket.accept();
				System.out.println("-->Server : "+socket+" is in.");
				SSSPT t = new SSSPT(socket,cask,capk);
				t.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		accpetConnection();
	}
}
