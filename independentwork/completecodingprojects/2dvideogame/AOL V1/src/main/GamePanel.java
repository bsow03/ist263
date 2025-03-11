package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{

	// SCREEN SETTINGS
	final int originalTileSize = 16; //16x16 tile
	final int scale = 4; 
	
	final int tileSize = originalTileSize * scale; // 64x64 tile
	final int maxScreenCol = 16;
	final int maxScreenRow = 16;
	final int screenWidth = tileSize * maxScreenCol; // 1024 pixels
	final int screenHeight = tileSize * maxScreenRow; // 1024 pixels
	
	//FPS
	int FPS = 60;
	
	
	KeyHandler keyH = new KeyHandler();
	Thread gameThread; // Something that can start and stop, it keeps program running until user stops it
	
	// Set player's default position
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH); // Game panel can recognize key input
		this.setFocusable(true);
		
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
	}


	@Override
	public void run() {
		/* The next couple of lines will be the first game loop
		 * I will be constructing this game loop using a sleep loop
		 * 
		 */
		
		double drawInterval = 1000000000/FPS; // we draw the screen 0.016666 seconds or 60 frames per second
		double nextDrawTime = System.nanoTime() + drawInterval; // next draw time will be current time + next draw interval
		
		// When gameThread starts it automatically calls run
		while(gameThread != null) {
			
		
		// 1 UPDATE: update information such as character positions
			update();
			
			
		// 2 DRAW: draw the screen with the updated information
			repaint(); // This calls paintComponent method 
			
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime(); // how much time is remaining until the next draw time
				remainingTime = remainingTime/1000000;
				
				if(remainingTime < 0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long) remainingTime);
				
				nextDrawTime += drawInterval;
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void update() {
		if(keyH.upPressed == true) {
			playerY -= playerSpeed;
		}
		else if(keyH.downPressed == true) {
			playerY += playerSpeed;
		}
		else if(keyH.leftPressed == true) {
			playerX -= playerSpeed;
		}
		else if(keyH.rightPressed == true) {
			playerX += playerSpeed;
		}
	}
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g; // Changes Graphics g to Graphics 2D, 2D Graphics is more sophisticated and helps us call functions later that can't be called with Graphics
		
		g2.setColor(Color.white);
		
		g2.fillRect(playerX, playerY, tileSize, tileSize);
		
		g2.dispose(); // Program still works, it is good practice to save memory
	}
}
