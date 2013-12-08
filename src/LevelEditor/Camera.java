package LevelEditor;

import java.awt.Point;

public class Camera {
	
	Point position;
	boolean setCamera;
	
	public Camera(){
		position = new Point();
		setCamera = false;
	}
		
	public void setCamera(Point a){
		position = a;
		setCamera = true;
	}
	
	/**
	 * returnt true als camera op een positie is geset
	 * @return
	 */
	public boolean hasCamera(){
		return setCamera;
	}
	
	public void removeCamera(){
		position = null;
		setCamera =  false;
	}
	
	public Point getPosition(){
		return position;
	}
}