package LevelEditor;

import javax.media.opengl.GL;

public class BuildingBlock extends Window{
	private int positieX, positieY;
	private boolean wall = false, floor = true;
	

	public BuildingBlock(float[] sizes, int screenWidthFrame, int screenHeightFrame, int i, int j) {
		super(sizes, screenWidthFrame, screenHeightFrame);
		positieX = i;
		positieY = j;
		// TODO Auto-generated constructor stub
	}
	
	public void drawBlock(GL gl) {
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2f(x, y);
		gl.glVertex2f(x + sizeX, y);
		gl.glVertex2f(x + sizeX, y - sizeY);
		gl.glVertex2f(x, y - sizeY);
		gl.glEnd();
	}
	
	public void drawGuardianPath(GL gl) {
		gl.glBegin(GL.GL_LINES);
		gl.glColor3f(0.5f, 0f, 0f);
		gl.glVertex2f(x, y);
		gl.glVertex2f(x + sizeX, y - sizeY);
		gl.glVertex2f(x + sizeX, y);
		gl.glVertex2f(x, y - sizeY);
		gl.glColor3f(0f, 0.5f, 0f);
		gl.glEnd();
	}	
	
	public int[] getPosition(){
		int[] Positie = {positieX, positieY};
		return Positie;
	}
	
	public void setWall(){
		wall = true;
		floor = false;
	}
	
	public void setFloor(){
		wall = false;
		floor = true;
	}
	
	public boolean getWall(){
		return wall;
	}
	
	public boolean getFloor(){
		return floor;
	}
	
	

}
