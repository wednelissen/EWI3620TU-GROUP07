package MazeRunner;

import java.awt.Point;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

public class ControlCenter extends GameObject implements VisibleObject{
	private double SQUARE_SIZE;
	
	public ControlCenter(double xPosition, double yPosition, double zPosition, double SQUARE_SIZE){
		super(xPosition * SQUARE_SIZE+(0.5*SQUARE_SIZE), yPosition, zPosition * SQUARE_SIZE+(0.5*SQUARE_SIZE));
		this.SQUARE_SIZE = SQUARE_SIZE;
	}

	@Override
	public void display(GL gl) {
		GLUT glut = new GLUT();

		float cubeColor[] = { 1f, 0.5f, 0.5f, 0.7f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, cubeColor, 0);
		gl.glPushMatrix();

		gl.glTranslated(locationX, 1, locationZ);
		
		
		gl.glDisable(GL.GL_CULL_FACE);//zorgt dat de achterkant zichtbaar is
		glut.glutSolidCube(2);
		gl.glPopMatrix();

		gl.glEnable(GL.GL_CULL_FACE); // zet de instellingen weer terug zoals ze stonden
	}

}