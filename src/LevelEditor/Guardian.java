package LevelEditor;

import java.awt.Point;
import java.util.ArrayList;

public class Guardian extends Window{
	private ArrayList<Point> route;
	private int xPrevious, yPrevious;
	
	/**
	 * hier wordt een guardian gemaakt maar de positie die wordt opgegeven is nog niet belangrijk
	 * deze wordt geautomatiseerd goed geset doordat de functie updatePositie wordt aangeroepen,
	 * door de classe PlacedItems.
	 * @param sizes
	 * @param screenWidthFrame
	 * @param screenHeightFrame
	 */
	public Guardian(float[] sizes, int screenWidthFrame, int screenHeightFrame) {
		super(sizes, screenWidthFrame, screenHeightFrame); //er wordt een display positie geset maar niet meteen gebruikt.
		route = new ArrayList<Point>();
	}
	
	
	
	/**
	 * hier wordt de werkelijke positie van een bewaker geset. wanneer hij in het placedItems menu
	 * is geplaatst.
	 * @param sizes
	 * @param screenWidthFrame
	 * @param screenHeightFrame
	 */
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
	
	
	
	/**
	 * wanneer het punt in de route nog niet bestaat en deze verticaal of horizontaal 
	 * naast het vorige geplaatste punt ligt wordt deze toegevoegd aan de route.
	 * @param a
	 */
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
	
	/**
	 * de oude route wordt vervangen door een nieuwe route, ook de laatste x en y coordinaten worden
	 * vervangen.
	 * @param newRoute
	 * @param x
	 * @param y
	 */
	public void setTotalRoute(ArrayList<Point> newRoute, int x, int y){
		route = newRoute;
		xPrevious = x;
		yPrevious = y;
		
	}
	
	/**
	 * lengte van het aantal punten dat een bewaker afloopt.
	 * @return
	 */
	public int routeSize(){
		return route.size();
	}
	
	/**
	 * returnt de kopie van de routelijst van de bewaker.
	 * het is een kopie zodat wanneer er een nieuwe bewaker aangemaakt wordt deze de route van
	 * de andere bewaker kan overnemen en vervolgens hiermee zelf verder zijn eigen route kan creeren.
	 * @return 
	 */
	public ArrayList<Point> getCopyRoutes(){
		ArrayList<Point> newRoute = new ArrayList<Point>();
		for(Point p: route){
			newRoute.add(p);
		}
		return newRoute;
	}
	
	/**
	 * geeft 1 specifiek punt op plek i die een bewaker afloopt
	 * @param i
	 * @return
	 */
	public Point getRoute(int i){
		if (route.size() > 0){
			return route.get(i);	
		}
		else
			return null;
	}
	
	/**
	 * geeft het laaste x coordinaat waar de bewaker zijn punt uit de route heeft geplaatst
	 * @return
	 */
	public int geXprevious(){
		return xPrevious;
	}
	
	/**
	 * geeft het laaste y coordinaat waar de bewaker zijn punt uit de route heeft geplaatst
	 * @return
	 */
	public int geYprevious(){
		return yPrevious;
	}
	
	
	/**
	 * verwijderd laatste punt uit de route en update ook de x en y previous 
	 * naar het ener laatste punt.
	 */
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

