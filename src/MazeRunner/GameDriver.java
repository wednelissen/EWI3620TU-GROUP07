package MazeRunner;

import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;

import com.sun.opengl.util.Animator;

public class GameDriver {

	public static void main(String[] args){
		
		Window window = new Window();
		// First, we set up JOGL. We start with the default settings.
		GLCapabilities caps = new GLCapabilities();
		// Then we make sure that JOGL is hardware accelerated and uses double buffering.
		caps.setDoubleBuffered( true );
		caps.setHardwareAccelerated( true );
		GLCanvas canvas = new GLCanvas( caps );
//		StateMainMenu mainmenu = new StateMainMenu();
		//canvas.addGLEventListener(mainmenu);
		canvas.setSize(600,600);
		window.add(canvas);
		new MazeRunner(canvas);
		
//		/* We need to create an internal thread that instructs OpenGL to continuously repaint itself.
//		 * The Animator class handles that for JOGL.
//		 */
		Animator anim = new Animator( canvas );
		anim.start();
	}
}
