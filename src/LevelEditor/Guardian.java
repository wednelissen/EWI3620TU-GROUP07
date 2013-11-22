package LevelEditor;

import java.awt.Point;
import java.util.ArrayList;

public class Guardian {
	private Point beginPositie;
	private ArrayList<Point> route;
	
	public Guardian(Point begin){
		route = new ArrayList<Point>();
		beginPositie = begin;
	}
	
	public void addRoute(Point a){
		if(!route.contains(a)){
			route.add(a);
		}
	}
	
	public int size(){
		return route.size();
	}
}

