package MazeRunner;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;

import com.sun.opengl.util.Animator;

public class StateMainMenu implements GLEventListener{
	
	protected static GLCanvas canvas;

	
	public StateMainMenu(){
		
		//this.addGLEventListenter(this);
	}
	
	@Override
	public void display(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void init(GLAutoDrawable drawable) {
		System.out.println("Init Called");
		GL gl = drawable.getGL();
		gl.glClearColor(0, 1, 0, 1);
		
	}



	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3,
			int arg4) {
		// TODO Auto-generated method stub
		
	}

}
