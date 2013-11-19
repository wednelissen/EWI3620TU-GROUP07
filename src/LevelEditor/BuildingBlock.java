package LevelEditor;

import javax.media.opengl.GL;

public class BuildingBlock extends Window{
	

	public BuildingBlock(float[] sizes, int screenWidthFrame, int screenHeightFrame) {
		super(sizes, screenWidthFrame, screenHeightFrame);
		// TODO Auto-generated constructor stub
	}
	
	public void drawBlock(GL gl) {
		gl.glBegin(GL.GL_LINE_LOOP);
		gl.glVertex2f(x, y);
		gl.glVertex2f(x + sizeX, y);
		gl.glVertex2f(x + sizeX, y - sizeY);
		gl.glVertex2f(x, y - sizeY);
		gl.glEnd();
	}
	
	

}
