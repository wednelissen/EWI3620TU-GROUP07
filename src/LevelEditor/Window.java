package LevelEditor;

import javax.media.opengl.GL;

public class Window {

	protected float x;
	protected float y;
	protected float sizeX;
	protected float sizeY;
	protected int screenWidth, screenHeight;
	protected float[] originalSizes;

	/**
	 * maakt een object waar op geklikt kan worden. De coordinaten zijn naar verhouding: 
	 * 800 pixels breed bij 600 pixels hoog.	 * 
	 * 
	 * @param sizes, een array van float met 4 coordinaten. linksboven, rechtsboven, breedte, hoogte.
	 * @param screenWidthFrame, breedte van het hele frame.
	 * @param screenHeightFrame, hoogte van het hele frame.
	 */
	public Window(float[] sizes, int screenWidthFrame, int screenHeightFrame) {

		if (sizes.length == 4) {

			originalSizes = sizes;
			float x_linksBoven = sizes[0];
			float y_rechtsBoven = sizes[1];
			float buttonSizeX = sizes[2];
			float buttonSizeY = sizes[3];

			screenWidth = screenWidthFrame;
			screenHeight = screenHeightFrame;

			x = x_linksBoven / 800 * screenWidth;
			y = (600 - y_rechtsBoven) / 600 * screenHeight;
			
			sizeX = buttonSizeX / 800 * screenWidth;
			sizeY = buttonSizeY / 600 * screenHeight;
		} else {
			System.out.println("geen geldige lengte voor input button");
		}
	}

	/**
	 * berekend de nieuwe breedte en hoogte van het vlak aan de hand van de nieuwe breedte en hoogte
	 * van het Frame.
	 * @param screenWidthFrame
	 * @param screenHeightFrame
	 */
	public void update(int screenWidthFrame, int screenHeightFrame) {

		float x_linksBoven = originalSizes[0];
		float y_rechtsBoven = originalSizes[1];
		float buttonSizeX = originalSizes[2];
		float buttonSizeY = originalSizes[3];

		screenWidth = screenWidthFrame;
		screenHeight = screenHeightFrame;

		x = x_linksBoven / 800 * screenWidth;
		y = (600 - y_rechtsBoven) / 600 * screenHeight;
		
		sizeX = buttonSizeX / 800 * screenWidth;
		sizeY = buttonSizeY / 600 * screenHeight;
	}
	
	/**
	 * tekent een omlijning van het vlak.
	 * @param gl
	 */
	public void draw(GL gl) {
		gl.glBegin(GL.GL_LINE_LOOP);
		gl.glVertex2f(x, y);
		gl.glVertex2f(x + sizeX, y);
		gl.glVertex2f(x + sizeX, y - sizeY);
		gl.glVertex2f(x, y - sizeY);
		gl.glEnd();
	}
	
	/**
	 * 
	 * @param xclick mouse click
	 * @param yclick mouse click
	 * @return true indien er op dit object is geklikt.
	 */
	public boolean clickedOnIt(int xclick, int yclick) {
		if(xclick >= x && xclick < (x + sizeX) && yclick >= (screenHeight - y) && yclick < ((screenHeight - y) + sizeY)){
			return true;
		}
		
		else{
			return false;
		}
	}

}