package MazeRunner;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;

import java.awt.Toolkit;


public class GameDriver implements KeyListener{
	
	private boolean gamerunning = false;
	private MazeRunner mazerunner;
	private static GLCanvas canvas;
	private static int screenWidth = 600, screenHeight = 600;		// Default screen size (not used).
	
	public GameDriver(){
	}
	
	public static void main(String[] args){
		
		//Initialize Window
		initWindow();
		//Show Main Menu
		new StateMainMenu(canvas, true);
	}
	
	private static void initWindow(){
		//Automatically detect screen resolution
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = (int) screenSize.getWidth();
		screenHeight = (int) screenSize.getHeight();
		
		//Initializes a window with the specified dimensions
		Window window = new Window(screenWidth,screenHeight);
		// First, we set up JOGL. We start with the default settings.
		GLCapabilities caps = new GLCapabilities();
		// Then we make sure that JOGL is hardware accelerated and uses double buffering.
		caps.setDoubleBuffered( true );
		caps.setHardwareAccelerated( true );
		// Now we add the canvas, where OpenGL will actually draw for us. We'll use settings we've just defined. 
		canvas = new GLCanvas( caps );
		canvas.setSize(screenWidth,screenHeight);
		//Add a GameDriver as a KeyListener
		GameDriver gamedriver = new GameDriver();
		canvas.addKeyListener(gamedriver);
		window.add(canvas);
		canvas.requestFocus();
	}

	@Override
	public void keyPressed(KeyEvent event) {
		//not used
		
	}

	@Override
	public void keyReleased(KeyEvent event) {
		//These keyEvents are available at any point in the game.
		//For instance, pressing escape exits the game.
		int code = event.getKeyCode();
		
		switch(code){
		case KeyEvent.VK_ESCAPE:
			//System.out.println("exiting...");
			//System.exit(0);
			break;
		case KeyEvent.VK_P:
			
		}
	}	

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static GLCanvas getCanvas(){
		return GameDriver.canvas;
	}
	
}
