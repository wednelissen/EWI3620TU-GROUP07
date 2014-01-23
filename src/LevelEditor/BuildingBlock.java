package LevelEditor;

import java.awt.Point;

import javax.media.opengl.GL;

import com.sun.opengl.util.texture.Texture;

public class BuildingBlock extends Window {
	private Point positie = new Point();
	private boolean wall = false, floor = true, door = false,
			keyRequired = false;

	/**
	 * @param sizes
	 *            een array van float met 4 coordinaten. linksboven,
	 *            rechtsboven, breedte, hoogte.
	 * @param screenWidthFrame
	 *            Breedte van het hele frame.
	 * @param screenHeightFrame
	 *            Hoogte van het hele frame.
	 * @param i
	 *            x-positie van het blok ten opzichten van alle andere blokken.
	 * @param j
	 *            y-positie van het blok ten opzichten van alle andere blokken.
	 */
	public BuildingBlock(float[] sizes, int screenWidthFrame,
			int screenHeightFrame, int i, int j) {
		super(sizes, screenWidthFrame, screenHeightFrame);
		positie.setLocation(i, j);
	}

	/**
	 * Creëert een blok in maze met een texture. Maar alleen wanneer er een
	 * texture aanwezig is.
	 * 
	 * @param gl
	 * @param myTexture
	 *            De meegegeven texture
	 */
	public void drawBlock(GL gl, Texture myTexture) {
		float blockColor[] = { 1.0f, 1.0f, 1.0f, 0f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, blockColor, 0);
		if (myTexture != null) {
			myTexture.enable();
			myTexture.bind();
		}
		gl.glBegin(GL.GL_QUADS);
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
	}

	/**
	 * Creëert een key in de maze met een texture. Maar alleen wanneer er een
	 * texture aanwezig is
	 * 
	 * @param gl
	 * @param myTexture
	 *            De meegegeven texture
	 */
	public void drawKey(GL gl, Texture myTexture) {
		float keyColor[] = { 1.0f, 1.0f, 1.0f, 0f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, keyColor, 0);
		if (myTexture != null) {
			myTexture.enable();
			myTexture.bind();
		}
		gl.glBegin(GL.GL_QUADS);
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
	}

	/**
	 * Creëert een guard in de maze met een texture. Maar alleen wanneer er een
	 * texture aanwezig is
	 * 
	 * @param gl
	 * @param myTexture
	 *            De meegegeven texture
	 */
	public void drawGuardianPath(GL gl, Texture myTexture) {
		float guardianPathColor[] = { 1.0f, 1.0f, 1.0f, 0f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, guardianPathColor, 0);
		if (myTexture != null) {
			myTexture.enable();
			myTexture.bind();
		}
		gl.glBegin(GL.GL_QUADS);
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
	}

	/**
	 * Creëert een camera in de maze met een texture. Maar alleen wanneer er een
	 * texture aanwezig is
	 * 
	 * @param gl
	 * @param myTexture
	 *            De meegegeven texture
	 */
	public void drawCameras(GL gl, Texture myTexture) {
		float CameraColor[] = { 1.0f, 1.0f, 1.0f, 0f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, CameraColor, 0);
		if (myTexture != null) {
			myTexture.enable();
			myTexture.bind();
		}
		gl.glBegin(GL.GL_QUADS);
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
	}

	/**
	 * Creëert een control center in de maze met een texture. Maar alleen
	 * wanneer er een texture aanwezig is
	 * 
	 * @param gl
	 * @param myTexture
	 *            De meegegeven texture
	 */
	public void drawControlCenter(GL gl, Texture myTexture) {
		float ControlCenterColor[] = { 1.0f, 1.0f, 1.0f, 0f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, ControlCenterColor, 0);
		if (myTexture != null) {
			myTexture.enable();
			myTexture.bind();
		}
		gl.glBegin(GL.GL_QUADS);
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
	}

	/**
	 * Creëert een spot in de maze met een texture. Maar alleen wanneer er een
	 * texture aanwezig is
	 * 
	 * @param gl
	 * @param myTexture
	 *            De meegegeven texture
	 */
	public void drawSpot(GL gl, Texture myTexture) {
		float radius = sizeX / 4;
		float slices = 20;
		float incr = (float) (2 * Math.PI / slices);

		gl.glBegin(GL.GL_TRIANGLE_FAN);

		gl.glVertex2f((0.5f * sizeX + x), (y - 0.5f * sizeY));

		for (int i = 0; i < slices; i++) {
			float angle = incr * i;

			float xtemp = (float) Math.cos(angle) * radius;
			float ytemp = (float) Math.sin(angle) * radius;

			gl.glVertex2f((0.5f * sizeX + x) + xtemp, (y - 0.5f * sizeY)
					+ ytemp);
		}

		gl.glVertex2f((0.5f * sizeX + x) + radius, (y - 0.5f * sizeY));

		gl.glEnd();
	}

	/**
	 * De get functie om de positie (x, y) van het opgevraagde blok te krijgen
	 * 
	 * @return een punt bestaande uit een x en y positie
	 */
	public Point getPosition() {
		return positie;
	}

	/**
	 * Set de wall op true en zet de floor, door en keyRequired boolean op fale
	 */
	public void setWall() {
		wall = true;
		floor = false;
		door = false;
		keyRequired = false;
	}

	/**
	 * Set de floor op true en zet de wall, door en keyRequired boolean op fale
	 */
	public void setFloor() {
		wall = false;
		floor = true;
		door = false;
		keyRequired = false;
	}

	/**
	 * Set de door op true en zet de floor, wall en keyRequired boolean op fale
	 */
	public void setDoor() {
		wall = false;
		floor = false;
		door = true;
		keyRequired = false;
	}

	/**
	 * maakt 'keyRequired' true zodat er een sleutel aan dit blok is gekoppeld.
	 */
	public void setKeyRequired() {
		keyRequired = true;
	}

	/**
	 * maakt 'keyRequired' false zodat er is geen sleutel gekoppeld aan dit
	 * blok.
	 */
	public void removeKeyRequired() {
		keyRequired = false;
	}

	/**
	 * De get functie om te kijken of op dit punt een wall is of niet
	 * 
	 * @return true als dit blok een muur is.
	 */
	public boolean getWall() {
		return wall;
	}

	/**
	 * De get functie om te kijken of op dit punt een floor is of niet
	 * 
	 * @return true als dit blok een vloer is.
	 */
	public boolean getFloor() {
		return floor;
	}

	/**
	 * De get functie om te kijken of op dit punt een door is of niet
	 * 
	 * @return true als dit blok een door is.
	 */
	public boolean getDoor() {
		return door;
	}

	/**
	 * De get functie om te kijken of de deur een sleutel nodig heeft
	 * 
	 * @return true als dit blok, een deur, een sleutel nodig heeft.
	 */
	public boolean getKeyRequired() {
		return keyRequired;
	}

}
