package MazeRunner;

import javax.media.opengl.GL;

public class MainMenu implements VisibleObject {

	@Override
	public void display(GL gl) {
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2f(50, 50);
		gl.glVertex2f(200,50);
		gl.glVertex2f(200,200);
		gl.glVertex2f(50,200);
		gl.glEnd();
		
	}

	
}
