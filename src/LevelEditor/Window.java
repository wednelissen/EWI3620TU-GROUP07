package LevelEditor;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;
import com.sun.opengl.util.texture.Texture;

public class Window {

	protected float x;
	protected float y;
	protected float sizeX;
	protected float sizeY;
	protected int screenWidth, screenHeight;
	protected float[] originalSizes;

	/**
	 * maakt een object waar op geklikt kan worden. De coordinaten zijn naar
	 * verhouding: 800 pixels breed bij 600 pixels hoog. *
	 * 
	 * @param sizes
	 *            , een array van float met 4 coordinaten. linksboven,
	 *            rechtsboven, breedte, hoogte.
	 * @param screenWidthFrame
	 *            , breedte van het hele frame.
	 * @param screenHeightFrame
	 *            , hoogte van het hele frame.
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
	 * berekend de nieuwe breedte en hoogte van het vlak aan de hand van de
	 * nieuwe breedte en hoogte van het Frame.
	 * 
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
	 * 
	 * @param gl
	 */
	public void draw(GL gl, Texture myTexture) {
//		 float windowColor[] = { 1.0f, 1.0f, 1.0f, 0f };
//		 gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, windowColor, 0);
//		 if (myTexture != null)
//		 {
//		 myTexture.enable();
//		 myTexture.bind();
//		 }
//		 gl.glBegin(GL.GL_QUADS);
//		 gl.glTexCoord2d(0, 0);
//		 gl.glVertex2f(x, y);
//		 gl.glTexCoord2d(0, 1);
//		 gl.glVertex2f(x + sizeX, y);
//		 gl.glTexCoord2d(1, 1);
//		 gl.glVertex2f(x + sizeX, y - sizeY);
//		 gl.glTexCoord2d(1, 0);
//		 gl.glVertex2f(x, y - sizeY);
//		 gl.glEnd();
//		 if (myTexture != null)
//		 {
//		 myTexture.disable();
//		 }

		float windowColour[] = { 0.4f, 1.60f, 0.6f, 0f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, windowColour, 0);
		if (myTexture != null) {
			myTexture.enable();
			myTexture.bind();
		}
		gl.glBegin(GL.GL_TRIANGLE_STRIP);
		gl.glTexCoord2d(0, 1);
		gl.glVertex2f(x, y);
		gl.glTexCoord2d(1, 1);
		gl.glVertex2f(x + sizeX, y);
		gl.glTexCoord2d(1, 0);
		gl.glVertex2f(x + sizeX, y - sizeY);
		gl.glTexCoord2d(0, 0);
		gl.glVertex2f(x, y - sizeY);

		gl.glTexCoord2d(0, 1);
		gl.glVertex2f(x, y);
		gl.glTexCoord2d(1, 1);
		gl.glVertex2f(x + sizeX, y);
		gl.glTexCoord2d(1, 0);
		gl.glVertex2f(x + sizeX, y - sizeY);
		gl.glTexCoord2d(0, 0);
		gl.glVertex2f(x, y - sizeY);

		gl.glEnd();
		if (myTexture != null) {
			myTexture.disable();
		}

		// float windowBorderColor[] = {0.0f, 0.0f, 0.0f, 0.0f};
		// gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, windowBorderColor, 0);
		// gl.glBegin(GL.GL_QUADS);
		// gl.glVertex2f(x, y);
		// gl.glVertex2f(x + sizeX, y);
		// gl.glVertex2f(x + sizeX, y - sizeY);
		// gl.glVertex2f(x, y - sizeY);
		// gl.glEnd();
	}

	/**
	 * BRON:
	 * http://stackoverflow.com/questions/10145701/jogl-opengl-n-newline-does
	 * -not-working-for-text-rendering Wel zelf de enters, tabs, en spatie
	 * lengte geimplementeerd. ook het meeschalen is zelf gemaakt.
	 **/
	public void renderString(GL gl, String s) {
		float witdhString = 0;
		float translateY = -120;
		// System.out.println(screenWidth+" "+screenHeight);
		float scaleX = (float) screenWidth / 800f;
		float scaleY = (float) screenHeight / 600f;
		// System.out.println(scaleX+" "+scaleY);
		final GLUT glut = new GLUT();
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glPushMatrix();
		gl.glLoadIdentity();
		gl.glOrtho(0, screenWidth, 0, screenHeight, -1, 1);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glPushMatrix();
		gl.glLoadIdentity();
		gl.glTranslatef(x, y, 0);
		float offsetBeginPositionString = scaleY * -18f;
		gl.glTranslatef(0, offsetBeginPositionString, 0);
		gl.glScalef(0.15f, 0.15f, 1f); // zorgt voor de goede grote lettertype
		gl.glScalef(scaleX, scaleY, 1f);
		for (int i = 0; i < s.length(); i++) {
			char temp = s.charAt(i);
			if (temp == '\n') {
				gl.glTranslatef(-witdhString, translateY, 0);
				witdhString = 0;
			} else if (temp == '\t') {
				gl.glTranslatef(350, 0, 0);
				witdhString += 350f;
			} else if (temp == ' ') {
				gl.glTranslatef(50, 0, 0);
				witdhString += 50f;
			} else {
				glut.glutStrokeCharacter(GLUT.STROKE_ROMAN, temp);
				witdhString += glut.glutStrokeWidthf(GLUT.STROKE_ROMAN, temp);

			}

		}
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glPopMatrix();
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glPopMatrix();
	}

	/**
	 * 
	 * @param xclick
	 *            mouse click
	 * @param yclick
	 *            mouse click
	 * @return true indien er op dit object is geklikt.
	 */
	public boolean clickedOnIt(int xclick, int yclick) {
		if (xclick >= x && xclick < (x + sizeX) && yclick >= (screenHeight - y)
				&& yclick < ((screenHeight - y) + sizeY)) {
			return true;
		}

		else {
			return false;
		}
	}

}