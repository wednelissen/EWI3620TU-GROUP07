package LevelEditor;

import javax.media.opengl.GL;

public class Window {

	protected float x;
	protected float y;
	protected float sizeX;
	protected float sizeY;
	protected int screenWidth, screenHeight;
	protected float[] originalSizes;

	public Window(float[] sizes, int screenWidthFrame, int screenHeightFrame) {

		if (sizes.length == 4) {

			originalSizes = sizes;
			float x_linksOnder = sizes[0];
			float y_rechtsOnder = sizes[1];
			float buttonSizeX = sizes[2];
			float buttonSizeY = sizes[3];

			screenWidth = screenWidthFrame;
			screenHeight = screenHeightFrame;

			x = x_linksOnder / 800 * screenWidth;
			y = (600 - y_rechtsOnder) / 600 * screenHeight;
			
			sizeX = buttonSizeX / 800 * screenWidth;
			sizeY = buttonSizeY / 600 * screenHeight;
		} else {
			System.out.println("geen geldige lengte voor input button");
		}
	}

	public void update(int screenWidthFrame, int screenHeightFrame) {

		float x_linksOnder = originalSizes[0];
		float y_rechtsOnder = originalSizes[1];
		float buttonSizeX = originalSizes[2];
		float buttonSizeY = originalSizes[3];

		screenWidth = screenWidthFrame;
		screenHeight = screenHeightFrame;

		x = x_linksOnder / 800 * screenWidth;
		y = (600 - y_rechtsOnder) / 600 * screenHeight;
		
		sizeX = buttonSizeX / 800 * screenWidth;
		sizeY = buttonSizeY / 600 * screenHeight;
	}
	
	public void draw(GL gl) {
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2f(x, y);
		gl.glVertex2f(x + sizeX, y);
		gl.glVertex2f(x + sizeX, y - sizeY);
		gl.glVertex2f(x, y - sizeY);
		gl.glEnd();
	}
	
	public boolean clickedOnIt(int xclick, int yclick) {
		if(xclick > x && xclick < (x + sizeX) && yclick > (screenHeight - y) && yclick < ((screenHeight - y) + sizeY)){
			return true;
		}
		
		else{
			return false;
		}
	}

}