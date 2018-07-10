package com.testserversocket.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
public class ChatSocket extends Thread {
    Socket socket;

    public ChatSocket(Socket s) {
        this.socket = s;
    }

    public void out(String out) {
        try {
            socket.getOutputStream().write(out.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream(), "UTF-8"));
            String line=null;
            while ((line = br.readLine())!=null) {
                System.out.println(line);
                ChatManager.getChatManager().publish(this, line);
            }
            br.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}