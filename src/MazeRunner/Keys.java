package MazeRunner;

import java.awt.Point;
import java.util.Calendar;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

public class Keys extends GameObject implements VisibleObject{
	
	private boolean pickedUp;
	private Point door;
	private double openingCounter;
	private float rotate;
	private double SQUARE_SIZE_MAZE;
	private int deltaTime;
	private float rotateSpeed = 0.1f;
	private float doorSpeed = 0.0015f;
	private float r, g, b;
	
	
	public Keys(double xKey, double yKey, double zKey, double xDoor, double yDoor, double SQUARE_SIZE){
		super(xKey * SQUARE_SIZE+(0.5*SQUARE_SIZE), yKey, zKey * SQUARE_SIZE+(0.5*SQUARE_SIZE));
		pickedUp = false;
		door = new Point((int)xDoor, (int)yDoor);	
		SQUARE_SIZE_MAZE = SQUARE_SIZE;
		openingCounter = 0;
		rotate = 0;
		deltaTime = 0;
		r = (float)Math.random();
		g = (float)Math.random();
		b = (float)Math.random();
	}
	
	public boolean hasPickedUp(){
		return pickedUp;
	}
	
	public void isPickedUp(){
		pickedUp = true;
	}
	
	public Point getDoor(){
		return door;
	}

	public double openingDoor(){
		openingCounter+= doorSpeed*deltaTime;
		return openingCounter;
	}
	
	public void updateDeltaTime(int time){
		deltaTime = time;
	}
	
	public float[] getKeyColor(){
		float[] rgb  = {r,g,b};
		return rgb;
	}
	
	public void display(GL gl) {
		
		float keyColour[] = { 0.0f, 0.0f, 1.0f, 1f };
		gl.glEnable(GL.GL_COLOR_MATERIAL);
		
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT_AND_DIFFUSE, keyColour,0);

		//DIT MOET WEG
		gl.glColor3f(r, g, b);
		
		gl.glPushMatrix();
		gl.glTranslated(locationX, 0, locationZ);
		gl.glRotatef(rotate, 0, 1, 0);

		double breedte = SQUARE_SIZE_MAZE / 20;
		double hoogte = SQUARE_SIZE_MAZE / 5;
		
		gl.glDisable(GL.GL_CULL_FACE);
		//linkerkant
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex3d(0, hoogte, 0);
		gl.glVertex3d(0, 0, 0);
		gl.glVertex3d(breedte, 0, 0);
		gl.glVertex3d(breedte, hoogte, 0);
		
		//rechterkant
		gl.glVertex3d(0, hoogte, 0);
		gl.glVertex3d(0, 0, 0);
		gl.glVertex3d(-breedte, 0, 0);
		gl.glVertex3d(-breedte, hoogte, 0);
		
		gl.glEnd();
		
		gl.glPopMatrix();
		gl.glDisable(GL.GL_COLOR_MATERIAL);
		rotate += rotateSpeed*deltaTime; 
		if(rotate > 360){
			rotate -= 360;
		}
		//DIT MOET WEG
		gl.glColor3f(1, 1, 1);
		
		gl.glEnable(GL.GL_CULL_FACE);
		
	}
	
		
		
}
