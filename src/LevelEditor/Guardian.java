package LevelEditor;

import java.awt.Point;
import java.util.ArrayList;

public class Guardian extends Window{
	private ArrayList<Point> route;
	private int xPrevious, yPrevious;
	
	public Guardian(float[] sizes, int screenWidthFrame, int screenHeightFrame) {
		super(sizes, screenWidthFrame, screenHeightFrame); //er wordt een display positie geset maar niet meteen gebruikt.
		route = new ArrayList<Point>();
	}
	
	
	//hier wordt de werkelijke positie van een guard geset.
	public void updatePosition(float[] sizes, int screenWidthFrame, int screenHeightFrame){
		if (sizes.length == 4) {

			originalSizes = sizes;
			float x_linksOnder = sizes[0];
			float y_rechtsOnder = sizes[1];
			float buttonSizeX = sizes[2];
			float buttonSizeY = sizes[3];

			screenWidth = screenWidthFrame;
			screenHeight = screenHeightFrame;

			x = x_linksOnder / 800 * screenWidth;
			y = (600 - y_rechtsOnder) / 600 * screenHeight;
			
			sizeX = buttonSizeX / 800 * screenWidth;
			sizeY = buttonSizeY / 600 * screenHeight;
		} else {
			System.out.println("geen geldige lengte voor input");
		}		
		
	}
	
	
	//wanneer het punt in de route nog niet bestaat en deze verticaal of horizontaal 
	//naast het vorige punt ligt wordt deze toegevoegd.
	public void addRoute(Point a){
		if(route.size()>0){
			if(!route.contains(a)){
				int xNew = (int)a.getX();
				int yNew = (int)a.getY();
				if((Math.abs(xPrevious - xNew) == 1) && (Math.abs(yPrevious - yNew) == 0) || (Math.abs(xPrevious - xNew) == 0) && (Math.abs(yPrevious - yNew) == 1)){
					route.add(a);
					xPrevious = xNew;
					yPrevious = yNew;
				}
			}
		}
		else{
			route.add(a);
			xPrevious = (int)a.getX();
			yPrevious = (int)a.getY();
			
		}
	}
	
	public void setTotalRoute(ArrayList<Point> newRoute, int x, int y){
		route = newRoute;
		xPrevious = x;
		yPrevious = y;
		
	}
	//lengte van het aantal punten dat een bewaker afloopt.
	public int routeSize(){
		return route.size();
	}
	
	//geeft alle punten die de bewaker afloopt
	public ArrayList<Point> getCopyRoutes(){
		ArrayList<Point> newRoute = new ArrayList<Point>();
		for(Point p: route){
			newRoute.add(p);
		}
		return newRoute;
	}
	
	//geeft 1 specifiek punt die een bewaker afloopt
	public Point getRoute(int i){
		if (route.size() > 0){
			return route.get(i);	
		}
		else
			return null;
	}
	
	public int geXprevious(){
		return xPrevious;
	}
	
	public int geYprevious(){
		return yPrevious;
	}
	
	//verwijderd laatste punt en update ook de x en y previous.
	public void removeLastPoint(){
		if(route.size()>0){
			int i = route.size();
			route.remove(i-1);
			if(route.size() != 0){
				Point a = route.get(i-2);
				xPrevious = (int)a.getX();
				yPrevious = (int)a.getY();
			}
		}
	}
}

