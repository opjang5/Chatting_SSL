package com.ustb.client;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class MPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	ImageIcon icon;
	Image img;
	public MPanel(String path) {
		icon=new ImageIcon(path);
		img=icon.getImage();
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage( img, 0, 0, this.getWidth(), this.getHeight(), this) ;
	}
}
