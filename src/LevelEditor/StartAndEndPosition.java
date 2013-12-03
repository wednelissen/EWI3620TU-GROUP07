package LevelEditor;

import java.awt.Point;

public class StartAndEndPosition {
	
	private Point start;
	private Point end;
	private boolean setStart = false;
	private boolean setEnd = false;
	
	public StartAndEndPosition(){
		start = new Point();
		end = new Point();
	}
	
	public void setStart(Point a){
		start = a;
		setStart = true;
	}
	
	public void setEnd(Point a){
		end = a;
		setEnd = true;
	}
	
	public boolean hasStart(){
		return setStart;
	}
	
	public boolean hasEnd(){
		return setEnd;
	}
	
	public Point getStart(){
		return start;
	}
	
	public Point getEnd(){
		return end;
	}
	

}
