package LevelEditor;

import java.awt.Point;

import javax.media.opengl.GL;

import com.sun.opengl.util.texture.Texture;

public class BuildingBlock extends Window{
	private Point positie = new Point();
	private boolean wall = false, floor = true, door = false, keyRequired = false;
	
	/**
	 * @param sizes, een array van float met 4 coordinaten. linksboven, rechtsboven, breedte, hoogte.
	 * @param screenWidthFrame, breedte van het hele frame.
	 * @param screenHeightFrame, hoogte van het hele frame.
	 * @param i, x-positie van het blok ten opzichten van alle andere blokken.
	 * @param j, y-positie van het blok ten opzichten van alle andere blokken.
	 */
	public BuildingBlock(float[] sizes, int screenWidthFrame, int screenHeightFrame, int i, int j) {
		super(sizes, screenWidthFrame, screenHeightFrame);
		positie.setLocation(i, j);
	}
	
	/**
	 * Creert een blok in maze met een texture. Maar alleen wanneer er een texture aanwezig is.
	 * @param gl
	 * @param myTexture
	 */
	public void drawBlock(GL gl, Texture myTexture) {
		float blockColor[] = { 1.0f, 1.0f, 1.0f, 0f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, blockColor, 0);
		if (myTexture != null){
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
	
	public void drawKey(GL gl) {
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2f(x + sizeX*2/6, y);
		gl.glVertex2f(x + sizeX*4/7, y);
		gl.glVertex2f(x + sizeX*4/7, y - sizeY);
		gl.glVertex2f(x + sizeX*2/6, y - sizeY);
		
		
		gl.glVertex2f(x, y);
		gl.glVertex2f(x + sizeX, y);
		gl.glVertex2f(x + sizeX, y - sizeY*2/8);
		gl.glVertex2f(x, y - sizeY*2/8);
		gl.glEnd();
	}
	
	//tekent een kruisje in het vlak wanneer een bewaker langs deze route loopt.
	public void drawGuardianPath(GL gl, Texture myTexture) {
		float guardianPathColor[] = { 1.0f, 1.0f, 1.0f, 0f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, guardianPathColor, 0);
		if (myTexture != null){
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
		myTexture.disable();
	}	
	
	public void drawCameras(GL gl, Texture myTexture) {
		float CameraColor[] = { 1.0f, 1.0f, 1.0f, 0f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, CameraColor, 0);		
		if (myTexture != null){
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
		myTexture.disable();
	}	
	
	public void drawControlCenter(GL gl, Texture myTexture) {
		float ControlCenterColor[] = { 1.0f, 1.0f, 1.0f, 0f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, ControlCenterColor, 0);		
		if (myTexture != null){
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
		myTexture.disable();
	}
	
	
	//aangepaste methode van http://www.java-gaming.org/index.php/topic,4140. van user: overnhet
 
	public void drawSpot(GL gl, Texture myTexture)
    {
//		float spotColor[] = { 1.0f, 1.0f, 1.0f, 0f };
//		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, spotColor, 0);
//		if (myTexture != null){
//			myTexture.enable();
//			myTexture.bind();
//		}
//		gl.glBegin(GL.GL_QUADS);
//		gl.glTexCoord2d(0, 1);
//		gl.glVertex2f(x, y);
//		gl.glTexCoord2d(1, 1);
//		gl.glVertex2f(x + sizeX, y);
//		gl.glTexCoord2d(1, 0);
//		gl.glVertex2f(x + sizeX, y - sizeY);
//		gl.glTexCoord2d(0, 0);
//		gl.glVertex2f(x, y - sizeY);
//		gl.glEnd();
//		myTexture.disable();
		
    	float radius = sizeX/4;
    	float slices = 20;
          float incr = (float) (2 * Math.PI / slices);

          gl.glBegin(GL.GL_TRIANGLE_FAN);

                gl.glVertex2f((0.5f*sizeX+x), (y-0.5f*sizeY));

                for(int i = 0; i < slices; i++)
                {
                      float angle = incr * i;

                      float xtemp = (float) Math.cos(angle) * radius;
                      float ytemp = (float) Math.sin(angle) * radius;

                      gl.glVertex2f((0.5f*sizeX+x)+xtemp, (y-0.5f*sizeY)+ytemp);
                }

                gl.glVertex2f((0.5f*sizeX+x)+radius, (y-0.5f*sizeY));

          gl.glEnd();
    }
    
	/**
	 * 
	 * @return Positie van het BuildingBlock
	 */
	public Point getPosition(){
		return positie;
	}
	
	/**
	 * maakt wall true en de rest false.
	 */
	public void setWall(){
		wall = true;
		floor = false;
		door = false;
		keyRequired = false;
	}
	
	/**
	 * maakt floor true en de rest false.
	 */
	public void setFloor(){
		wall = false;
		floor = true;
		door = false;
		keyRequired = false;
	}
	
	/**
	 * maakt Door true en de rest false.
	 */
	public void setDoor(){
		wall = false;
		floor = false;
		door = true;
		keyRequired = false;
	}
	
	/**
	 * maakt 'keyRequired' true zodat er een sleutel aan dit blok is gekoppeld.
	 */
	public void setKeyRequired(){
		keyRequired = true;
	}
	
	/**
	 * maakt 'keyRequired' false er is geen sleutel gekoppeld aan dit blok. 
	 */
	public void removeKeyRequired(){
		keyRequired = false;
	}
	
	/**
	 * 
	 * @return true als dit blok een muur is.
	 */
	public boolean getWall(){
		return wall;
	}

	/**
	 * 
	 * @return true als dit blok een vloer is.
	 */
	public boolean getFloor(){
		return floor;
	}
	
	/**
	 * 
	 * @return true als dit blok een door is.
	 */
	public boolean getDoor(){
		return door;
	}
	
	/**
	 * 
	 * @return true als dit blok, een deur, een sleutel nodig heeft.
	 */
	public boolean getKeyRequired(){
		return keyRequired;
	}
	
	

}
