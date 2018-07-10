package com.javaclient.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.javaclient.main.ChatManager;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextArea txt;
    private JTextField txtip;
    private JTextField txtSend;

    public MainWindow() {
        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        txt = new JTextArea();
        txt.setText("ready...");

        txtip = new JTextField();
        txtip.setText("127.0.0.1");
        txtip.setColumns(10);

        JButton btnConnect = new JButton("connect");
        btnConnect.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ChatManager.getChatManager().connect(txtip.getText());
            }
        });

        txtSend = new JTextField();
        txtSend.setText("hello");
        txtSend.setColumns(10);

        JButton btnSend = new JButton("send");
        btnSend.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ChatManager.getChatManager().send(txtSend.getText());
                appendText("Send : "+txtSend.getText());
                txtSend.setText("");
            }
        });
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                            .addComponent(txtSend, GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))
                        .addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
                            .addComponent(txtip, GroupLayout.PREFERRED_SIZE, 294, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(btnConnect, GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                        .addComponent(txt, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE))
                    .addContainerGap())
        );
        gl_contentPane.setVerticalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(txtip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnConnect))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(txt, GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                        .addComponent(btnSend)
                        .addComponent(txtSend, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        );
        contentPane.setLayout(gl_contentPane);
    }
    public void appendText(String in) {
        txt.append("\n" + in);
    }
}