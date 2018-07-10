package com.testserversocket.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ServerListener extends Thread {
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(23456);
            while (true) {
                Socket socket = serverSocket.accept();
                JOptionPane.showMessageDialog(null, "Port[23456] has connected.");
                ChatSocket cs= new ChatSocket(socket);
                cs.start();
                ChatManager.getChatManager().add(cs);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}