package com.javaclient.main;

import java.awt.EventQueue;

import com.javaclient.view.MainWindow;

public class StartClient {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainWindow frame = new MainWindow();
                    frame.setVisible(true);
                    ChatManager.getChatManager().setWindow(frame);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}