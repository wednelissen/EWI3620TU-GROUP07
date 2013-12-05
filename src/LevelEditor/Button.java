package LevelEditor;

import javax.media.opengl.GL;

import com.sun.opengl.util.texture.Texture;

public class Button extends Window {

	public Button(float[] sizes, int screenWidthFrame, int screenHeightFrame) {
		super(sizes, screenWidthFrame, screenHeightFrame);
		// TODO Auto-generated constructor stub
	}
	
	public void draw(GL gl, Texture myTexture) {
		float windowColour[] = { 1.0f, 1.0f, 1.0f, 0f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, windowColour, 0);
		if (myTexture != null)
		{
			myTexture.enable();
			myTexture.bind();
		}
		gl.glBegin(GL.GL_QUADS);
		gl.glTexCoord2d(0, 1);
		gl.glVertex2f(x, y);
		gl.glTexCoord2d(1, 1);
		gl.glVertex2f(x + sizeX, y);
		gl.glTexCoord2d(1, 0);
		gl.glVertex2f(x + sizeX, y - sizeY);
		gl.glTexCoord2d(0, 0);
		gl.glVertex2f(x, y - sizeY);
		gl.glEnd();
		if (myTexture != null)
		{
		myTexture.disable();
		}
	}
}
