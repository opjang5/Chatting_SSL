package com.ustb.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ustb.rsa.RSA;
import com.ustb.rsa.RSAEncrypt;

public class SSSPT extends Thread {
	private Socket socket;
	private DataOutputStream dout;
	private DataInputStream din;
	private String name;
	private String cask;
	private String capk;
	
	public SSSPT(Socket socket,String cask,String capk) {
		this.cask = cask;
		this.capk = capk;
		this.socket = socket;
		try {
			dout = new DataOutputStream(socket.getOutputStream());
			din = new DataInputStream(socket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void run() {
		while (true) {
			try {
				try {
					String line = din.readUTF();
					String values[] = line.split("@@");
					if (values[0].equals("login")) {

						RSAEncrypt.genKeyPair("./");
						String ursk = RSAEncrypt.loadPrivateKeyByFile("./");
						String urpk = RSAEncrypt.loadPublicKeyByFile("./");
						UserMaps.dbMaps.put(values[1], this);
						UserMaps.skMaps.put(values[1], ursk);
						UserMaps.pkMaps.put(values[1], urpk);
						
						name = values[1];
						String list = "";
						for(String key : UserMaps.dbMaps.keySet()){
							list += "%"+key;
						}
						
						
						dout.writeUTF("login"+list);
						dout.writeUTF("capk%"+capk);
						dout.writeUTF("ursk%"+RSA.priEncrypt(cask, ursk));
						
						for(String key : UserMaps.dbMaps.keySet()){
							if(key.equals(values[1]))continue;
							UserMaps.dbMaps.get(key).dout.writeUTF("list"+list);
						}
					}
					else if (values[0].equals("msg")) {
						//String message = "*** Private >> From "+values[3]+" to "+values[1]+" : "+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))+"\n"+values[2]+"\n";
						String message = "pri%"+values[3]+"%"+values[1]+"%"+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))+"%"+values[2];
						UserMaps.dbMaps.get(values[1]).dout.writeUTF(message);
						UserMaps.dbMaps.get(values[3]).dout.writeUTF(message);
					}
					else if (values[0].equals("common")) {
						//String message = "Public >>"+values[2]+" : "+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))+"\n"+values[1]+"\n";
						String message = "pub%"+values[2]+"%"+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))+"%"+values[1];
						for (String userName : UserMaps.dbMaps.keySet()) {
							UserMaps.dbMaps.get(userName).dout.writeUTF(message);
						}
					}
					else if (values[0].equals("ask")) {
						UserMaps.dbMaps.get(values[1]).dout.writeUTF(line.replaceAll("@@", "%"));
					}
					else if (values[0].equals("ans")) {
						UserMaps.dbMaps.get(values[1]).dout.writeUTF(line.replaceAll("@@", "%"));
					}

				} catch (Exception e) {
					System.out.println("-->Server : "+socket+" is out.");
					UserMaps.dbMaps.remove(name);
					String list = "";
					for(String key : UserMaps.dbMaps.keySet()){
						list += "%"+key;
					}
					for (String userName : UserMaps.dbMaps.keySet()) {
						try {
							UserMaps.dbMaps.get(userName).dout.writeUTF("list"+list);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					break;
				}
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

