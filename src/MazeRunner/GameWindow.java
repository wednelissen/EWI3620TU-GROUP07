package MazeRunner;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GLCanvas;


public class GameWindow extends Frame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5978973788071119247L;
	private GLCanvas canvas;
	
	public GameWindow(int screenWidth,int screenHeight) {
		
		super("Spelletje");
		setSize(screenWidth, screenHeight);
		setBackground(Color.white);
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing( WindowEvent e)
			{
				System.exit(0); 
			}
		});
		this.setUndecorated(true);
		setVisible(true);
		
	}
	
	public GLCanvas getCanvas(){
		return this.canvas;
	}

}
