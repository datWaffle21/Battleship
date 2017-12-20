package com.game.battleship.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Menu extends MouseAdapter {

	private Game game;
	private Handler handler;
	
	private Image backround;
	private Image logo;
	
	public Menu(Game game, Handler handler) throws IOException {
		this.game = game;
		this.handler = handler;
		
		Image x1 = ImageIO.read(new File("battleship.png"));
		Image x2 = ImageIO.read(new File("backround.png"));
		
		backround = x2;
		logo = x1;
	}
	
	public void render(Graphics g) throws IOException {
		
		Font font = new Font("arial", 1, 36);
		
		g.setFont(font);
		
		
		
		g.drawImage(backround, 0, 50, null);
		g.drawImage(logo, -7, 0, null);	
		
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		
	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if(mx > x && mx < x + width) {
			if(my > y && my < y + height) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
}
