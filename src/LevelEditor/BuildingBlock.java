package LevelEditor;

import java.awt.Point;

import javax.media.opengl.GL;

import com.sun.opengl.util.texture.Texture;

public class BuildingBlock extends Window{
	private Point positie = new Point();
	private boolean wall = false, floor = true, door = false, keyRequired = false;
	

	public BuildingBlock(float[] sizes, int screenWidthFrame, int screenHeightFrame, int i, int j) {
		super(sizes, screenWidthFrame, screenHeightFrame);
		positie.setLocation(i, j);
	}
	
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
		myTexture.disable();
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
		gl.glColor3f(1.0f, 1.0f, 1.0f);
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
		float spotColor[] = { 1.0f, 1.0f, 1.0f, 0f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, spotColor, 0);
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
		
//    	float radius = sizeX/4;
//    	float slices = 20;
//          float incr = (float) (2 * Math.PI / slices);
//
////          gl.glBegin(GL.GL_TRIANGLE_FAN);
////
////                gl.glVertex2f((0.5f*sizeX+x), (y-0.5f*sizeY));
////
////                for(int i = 0; i < slices; i++)
////                {
////                      float angle = incr * i;
////
////                      float xtemp = (float) Math.cos(angle) * radius;
////                      float ytemp = (float) Math.sin(angle) * radius;
////
////                      gl.glVertex2f((0.5f*sizeX+x)+xtemp, (y-0.5f*sizeY)+ytemp);
////                }
////
////                gl.glVertex2f((0.5f*sizeX+x)+radius, (y-0.5f*sizeY));
////
////          gl.glEnd();
    }
    
	
	public Point getPosition(){
		return positie;
	}
	
	public void setWall(){
		wall = true;
		floor = false;
		door = false;
		keyRequired = false;
	}
	
	public void setFloor(){
		wall = false;
		floor = true;
		door = false;
		keyRequired = false;
	}
	
	public void setDoor(){
		wall = false;
		floor = false;
		door = true;
		keyRequired = false;
	}
	
	public void setKeyRequired(){
		keyRequired = true;
	}
	
	public void removeKeyRequired(){
		keyRequired = false;
	}
	
	public boolean getWall(){
		return wall;
	}
	
	public boolean getFloor(){
		return floor;
	}
	
	public boolean getDoor(){
		return door;
	}
	
	public boolean getKeyRequired(){
		return keyRequired;
	}
	
	

}
