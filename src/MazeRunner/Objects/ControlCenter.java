package MazeRunner.Objects;

import javax.media.opengl.GL;

import MazeRunner.Fundamental.LoadTexturesMaze;

public class ControlCenter extends GameObject implements VisibleObject{

	Model modelTable;
	private double sizeX = 2; //geef hier de maten van de tafel in
	private double sizeZ = 1; //geef hier de maten van de tafel in
	
	public ControlCenter(double xPosition, double yPosition, double zPosition){
		super(xPosition * Maze.SQUARE_SIZE+(0.5*Maze.SQUARE_SIZE), yPosition, zPosition * Maze.SQUARE_SIZE+(0.5*Maze.SQUARE_SIZE));
		modelTable = LoadTexturesMaze.getModel("modelTable");
	}
	public double getSizeX(){
		return sizeX;
	}
	
	public double getSizeZ(){
		return sizeZ;
	}

	@Override
	public void display(GL gl) {
		float cubeColor[] = { 1f, 0.5f, 0.5f, 0.7f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, cubeColor, 0);
		gl.glPushMatrix();

		gl.glTranslated(locationX, 1, locationZ);
		
		
		gl.glDisable(GL.GL_CULL_FACE);//zorgt dat de achterkant zichtbaar is
		gl.glScaled(2, 2, 2);
		modelTable.draw(gl, LoadTexturesMaze.getTexture("modelTable"));
		gl.glPopMatrix();

		gl.glEnable(GL.GL_CULL_FACE); // zet de instellingen weer terug zoals ze stonden
	}

}
