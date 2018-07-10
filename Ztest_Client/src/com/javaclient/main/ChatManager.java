package com.javaclient.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.javaclient.view.MainWindow;

public class ChatManager {
    private ChatManager(){}
    private static final ChatManager instance=new ChatManager();
    public static ChatManager getChatManager(){
        return instance;
    }
    MainWindow window;
    Socket socket;
    private String IP;
    BufferedReader bReader;
    PrintWriter pWriter;
    public void setWindow(MainWindow window) {
        this.window = window;
    }
    public void connect(String ip) {
        this.IP = ip;
        new Thread(){

            @Override
            public void run() {
                try {
                    socket = new Socket(IP, 23456);
                    pWriter = new PrintWriter(
                            new OutputStreamWriter(
                                    socket.getOutputStream()));
                    bReader = new BufferedReader(
                            new InputStreamReader(
                                    socket.getInputStream()));

                    String line = null;
                    while ((line = bReader.readLine())!=null) {
                        window.appendText("Recieve : "+line);
                    }
                    pWriter.close();
                    bReader.close();
                    pWriter = null;
                    bReader = null;

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void send(String sendMsg){
        if (pWriter!=null) {
            pWriter.write(sendMsg+"\n");
            pWriter.flush();
        } else {
            window.appendText("Lost Connection...");
        }
    }
}