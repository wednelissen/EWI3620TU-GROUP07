package MazeRunner;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

public class Gun extends GameObject implements VisibleObject{
	
	private boolean pickedUp;
	protected double verAngle;
	
	public Gun(double xGun, double yGun, double zGun, double SQUARE_SIZE){
		super(xGun * SQUARE_SIZE+(0.5*SQUARE_SIZE), yGun, zGun * SQUARE_SIZE+(0.5*SQUARE_SIZE));
		pickedUp = false;
		verAngle = 0;
	}

	public void pickedUp(){
		pickedUp = true;
	}
	@Override
	public void display(GL gl) {
		/*
		GLUT glut = new GLUT();
		
		//gl.glColorMaterial(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE);
		float keyColour[] = { 0.0f, 0.0f, 1.0f, 1f };
		gl.glEnable(GL.GL_COLOR_MATERIAL);
		
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT_AND_DIFFUSE, keyColour,0);

		//DIT MOET WEG
		gl.glColor3f(0, 0, 1);
		//System.out.println(verAngle);
		
		gl.glPushMatrix();
		gl.glTranslated(locationX, 0.5*locationY, locationZ);
		
		if(pickedUp){
			
			
			//gl.glLoadIdentity(); // Reset the current matrix.
			//gl.glTranslated(0, -0.1, 0);
			
			//gl.glTranslated(0, 0.1, 0);
			//gl.glTranslated(3, 0, 0);			
			
			double vrpX = Math.sin( Math.PI * (horAngle) / 180 )* Math.cos( Math.PI * (verAngle) / 180 );
			double vrpY = -.1*Math.sin( Math.PI * (verAngle) / 180 );
			double vrpZ = Math.cos( Math.PI * (horAngle) / 180 )* Math.cos( Math.PI * (verAngle) / 180 );
			gl.glTranslated(vrpX, vrpY, vrpZ);
			gl.glRotatef((float)(horAngle+180), 0, 1, 0);
			float verthoek = (float) verAngle;
			if(verthoek>30){
				verthoek = 30;
			}
			else if(verthoek<=-15){
				verthoek = -15;
			}
			gl.glRotatef((float)(-(verthoek)), 1, 0, 0);
			
		}
		//gl.glTranslated(0.5*SQUARE_SIZE, 0, 0.5*SQUARE_SIZE); //zorgt dat de sleutel in het midden van het blok komt.
		//gl.glRotatef(0, 1, 0, 0);
		
		glut.glutSolidCylinder(0.2, 3, 40, 10);
		gl.glPopMatrix();
		
		gl.glDisable(GL.GL_COLOR_MATERIAL);
		
		//DIT MOET WEG
		gl.glColor3f(1, 1, 1);
		*/
		
		
		//TESTEN MET CONE
		double SQUARE_SIZE = 5;
		
		GLUT glut = new GLUT();

		float cubeColor[] = { 1f, 0.5f, 0.5f, 0.7f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, cubeColor, 0);
		gl.glPushMatrix();
		gl.glTranslated(locationX, SQUARE_SIZE/4, locationZ);
//		gl.glTranslated(locationX - (SQUARE_SIZE / 2), SQUARE_SIZE / 4,
//				locationZ + (SQUARE_SIZE / 2));
		gl.glRotatef((float) (0), 0f, 1f, 0f);
		glut.glutSolidCone(SQUARE_SIZE / 2, SQUARE_SIZE / 2, 20, 20);

		gl.glPopMatrix();
		
	}
	

}
