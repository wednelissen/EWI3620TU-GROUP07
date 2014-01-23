package LevelEditor;

import java.awt.Point;
import java.util.ArrayList;

public class Guardian extends Window {
	private ArrayList<Point> route;
	private int xPrevious, yPrevious;

	/**
	 * Hier wordt een guardian gemaakt maar de positie die wordt opgegeven is
	 * nog niet belangrijk deze wordt geautomatiseerd goed geset doordat de
	 * functie updatePositie wordt aangeroepen, door de classe PlacedItems.
	 * 
	 * @param sizes
	 *            Een floatarray waar de positie en grootte van de knop in staat
	 * @param screenWidthFrame
	 *            De breedte van het scherm
	 * @param screenHeightFrame
	 *            De hoogte van het scherm
	 */
	public Guardian(float[] sizes, int screenWidthFrame, int screenHeightFrame) {
		super(sizes, screenWidthFrame, screenHeightFrame); // er wordt een
															// display positie
															// geset maar niet
															// meteen gebruikt.
		route = new ArrayList<Point>();
	}

	/**
	 * Hier wordt de werkelijke positie van een bewaker geset, wanneer hij in
	 * het placedItems menu is geplaatst.
	 * 
	 * @param sizes
	 * @param screenWidthFrame
	 *            De breedte van het scherm
	 * @param screenHeightFrame
	 *            De hoogte van het scherm
	 */
	public void updatePosition(float[] sizes, int screenWidthFrame,
			int screenHeightFrame) {
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
	 * Wanneer het punt in de route nog niet bestaat en deze verticaal of
	 * horizontaal naast het vorige geplaatste punt ligt wordt deze toegevoegd
	 * aan de route.
	 * 
	 * @param a
	 *            Het punt dat toegevoegd moet worden aan de guard zijn route
	 */
	public void addRoute(Point a) {
		if (route.size() > 0) {
			if (!route.contains(a)) {
				int xNew = (int) a.getX();
				int yNew = (int) a.getY();
				if ((Math.abs(xPrevious - xNew) == 1)
						&& (Math.abs(yPrevious - yNew) == 0)
						|| (Math.abs(xPrevious - xNew) == 0)
						&& (Math.abs(yPrevious - yNew) == 1)) {
					route.add(a);
					xPrevious = xNew;
					yPrevious = yNew;
				}
			}
		} else {
			route.add(a);
			xPrevious = (int) a.getX();
			yPrevious = (int) a.getY();

		}
	}

	/**
	 * De oude route wordt vervangen door een nieuwe route, ook de laatste x en
	 * y coordinaten worden vervangen.
	 * 
	 * @param newRoute
	 *            Een arraylijst van punten die de nieuwe route moet voorstellen
	 *            van de guard
	 * @param x
	 * @param y
	 */
	public void setTotalRoute(ArrayList<Point> newRoute, int x, int y) {
		route = newRoute;
		xPrevious = x;
		yPrevious = y;

	}

	/**
	 * De get functie die de grootte van de arraylijst van de route terug geeft
	 * 
	 * @return De grootte van de arraylijst waar de route in staat opgeslagen
	 */
	public int routeSize() {
		return route.size();
	}

	/**
	 * De get functie die de route geeft die de bewaker zal gaan lopen Het is
	 * een kopie zodat wanneer er een nieuwe bewaker aangemaakt wordt deze de
	 * route van de andere bewaker kan overnemen en vervolgens hiermee zelf
	 * verder zijn eigen route kan creeren.
	 * 
	 * @return Een arraylijst van punten die de route van de bewaker moeten
	 *         voorstellen
	 */
	public ArrayList<Point> getCopyRoutes() {
		ArrayList<Point> newRoute = new ArrayList<Point>();
		for (Point p : route) {
			newRoute.add(p);
		}
		return newRoute;
	}

	/**
	 * Geeft 1 specifiek punt op plek i die een bewaker afloopt
	 * 
	 * @param i
	 * @return
	 */
	public Point getRoute(int i) {
		if (route.size() > 0) {
			return route.get(i);
		} else
			return null;
	}

	/**
	 * Geeft het laaste x coordinaat waar de bewaker zijn punt uit de route
	 * heeft geplaatst
	 * 
	 * @return Een int waarde
	 */
	public int geXprevious() {
		return xPrevious;
	}

	/**
	 * Geeft het laaste y coordinaat waar de bewaker zijn punt uit de route
	 * heeft geplaatst
	 * 
	 * @return Een int waarde
	 */
	public int geYprevious() {
		return yPrevious;
	}

	/**
	 * Verwijderd laatste punt uit de route en update ook de x en y previous
	 * naar het een-na-laatste punt.
	 */
	public void removeLastPoint() {
		if (route.size() > 0) {
			int i = route.size();
			route.remove(i - 1);
			if (route.size() != 0) {
				Point a = route.get(i - 2);
				xPrevious = (int) a.getX();
				yPrevious = (int) a.getY();
			}
		}
	}
}
