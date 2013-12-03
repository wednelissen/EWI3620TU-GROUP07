package LevelEditor;

import java.awt.Point;

import javax.media.opengl.GL;

public class BuildingBlock extends Window{
	private Point positie = new Point();
	private boolean wall = false, floor = true, door = false, keyRequired = false;
	

	public BuildingBlock(float[] sizes, int screenWidthFrame, int screenHeightFrame, int i, int j) {
		super(sizes, screenWidthFrame, screenHeightFrame);
		positie.setLocation(i, j);
	}
	
	public void drawBlock(GL gl) {
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2f(x, y);
		gl.glVertex2f(x + sizeX, y);
		gl.glVertex2f(x + sizeX, y - sizeY);
		gl.glVertex2f(x, y - sizeY);
		gl.glEnd();
	}
	
	//tekent een kruisje in het vlak wanneer een bewaker langs deze route loopt.
	public void drawGuardianPath(GL gl) {
		gl.glBegin(GL.GL_LINES);
		gl.glVertex2f(x, y);
		gl.glVertex2f(x + sizeX, y - sizeY);
		gl.glVertex2f(x + sizeX, y);
		gl.glVertex2f(x, y - sizeY);
		gl.glEnd();
	}	
	
	public void drawCameras(GL gl) {
		gl.glBegin(GL.GL_TRIANGLES);
		gl.glVertex2f(x + sizeX/2, y); // midden boven		
		gl.glVertex2f(x + sizeX, y - sizeY); //rechts onder		
		gl.glVertex2f(x, y - sizeY); // links onder
		gl.glEnd();
	}	
	
	
	//aangepaste methode van http://www.java-gaming.org/index.php/topic,4140. van user: overnhet
 
	public void drawSpot(GL gl)
    {
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
