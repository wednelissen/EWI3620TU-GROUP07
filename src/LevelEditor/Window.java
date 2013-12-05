package LevelEditor;

import javax.media.opengl.GL;

import com.sun.opengl.util.texture.Texture;

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
	
	public void draw(GL gl, Texture myTexture) {
		float windowColor[] = { 1.0f, 1.0f, 1.0f, 0f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, windowColor, 0);
		if (myTexture != null)
		{
			myTexture.enable();
			myTexture.bind();
		}
		gl.glBegin(GL.GL_LINE_LOOP);
		gl.glTexCoord2d(0, 0);
		gl.glVertex2f(x, y);
		gl.glTexCoord2d(0, 1);
		gl.glVertex2f(x + sizeX, y);
		gl.glTexCoord2d(1, 1);
		gl.glVertex2f(x + sizeX, y - sizeY);
		gl.glTexCoord2d(1, 0);
		gl.glVertex2f(x, y - sizeY);
		gl.glEnd();
		if (myTexture != null)
		{
		myTexture.disable();
		}
		
//		float windowBorderColor[] = {0.0f, 0.0f, 0.0f, 0.0f};
//		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, windowBorderColor, 0);
//		gl.glBegin(GL.GL_QUADS);
//		gl.glVertex2f(x, y);
//		gl.glVertex2f(x + sizeX, y);
//		gl.glVertex2f(x + sizeX, y - sizeY);
//		gl.glVertex2f(x, y - sizeY);
//		gl.glEnd();
	}
	
	public boolean clickedOnIt(int xclick, int yclick) {
		if(xclick >= x && xclick < (x + sizeX) && yclick >= (screenHeight - y) && yclick < ((screenHeight - y) + sizeY)){
			return true;
		}
		
		else{
			return false;
		}
	}

}