package com.game.battleship.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.IOException;

public class Game extends Canvas implements Runnable {
	
	// GAME VARIABLES _________________________________________________

	private static final long serialVersionUID = -1996880149373614464L;
	
	private static final int WIDTH = 730, HEIGHT = WIDTH / 12 * 9;
	
	private static boolean running = false;
	
	// ------------------------------------------------------------------
	
	// GAME CLASSES INIT ______________
	
	private Thread thread;
	private Handler handler;
	private Menu menu;
	
	// --------------------------------
	
	// STATE ENUM INIT __________________________________
	
	public static STATE gameState = STATE.Menu;
	
	public enum STATE {
		Game,
		Menu,
	}
	
	// METHODS ______________________________________________
	
	public Game() {
		handler = new Handler();
		try {
			menu = new Menu(this, handler);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		new Window(WIDTH, HEIGHT, "Battleship", this);
	}
	
	public void tick() {
		handler.tick();
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void render() {
		
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		
		handler.render(g);
		
		try {
			menu.render(g);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		g.dispose();
		bs.show();
	}
	
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			if(running) {
				render();
			frames++;
			}
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	public static float clamp(float var, float min, float max) {
		if(var >=  max) {
			return var= max;
		}
		else if(var <= min) {
			return var = min;
		}
		else {
			return var;
		}
	}
	
	// END METHODS --------------------------------
	
	public static void main(String[] args) {
		new Game();
	}
	
}
