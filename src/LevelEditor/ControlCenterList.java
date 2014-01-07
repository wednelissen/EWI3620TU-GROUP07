package LevelEditor;

import java.awt.Point;
import java.util.ArrayList;

public class ControlCenterList {
	private ArrayList<ControlCenterEditor> controlCenters;
	
	/**
	 * Maakt een ArrayList van controlCenters.
	 */
	public ControlCenterList(){
		controlCenters = new ArrayList<ControlCenterEditor>();
	}
	
	/**
	 * Voegt een ControlCenter aan de arraylist van ControlCenters toe.
	 * een ControlCenter die al in de arraylist zit wordt niet toegevoegt.
	 * @param s
	 */
	public void addControlCenter(ControlCenterEditor s){
		boolean duplicated = false;
		Point a = s.getPosition();
		for(ControlCenterEditor temp: controlCenters){
			if(temp.getPosition().equals(a)){
				duplicated = true;
				break;
			}
		}		
		if(!duplicated)
			controlCenters.add(s);
		else{
			System.out.println("deze controlCenter bestaat al");
		}
	}
	
	/**
	 * genereerd een arraylist van controlCenters en vervangt de oude arraylist
	 * @param loadedControlCentersPoints
	 */
	public void loadControlCenters(ArrayList<Point> loadedControlCenterPoints){
		
		for(Point a: loadedControlCenterPoints){
			ControlCenterEditor temp = new ControlCenterEditor();
			temp.setControlCenter(a);
			this.addControlCenter(temp);
		}
	}
	
	/**
	 * aantal controlCenters in de ArrayList.
	 * @return
	 */
	public int size(){
		return controlCenters.size();
	}
	
	/**
	 * verwijderd een controlCenter indien deze in de lijst zit.
	 * anders gebeurd er niets.
	 * @param s
	 */
	public void removeControlCenter(ControlCenterEditor s){
		Point a = s.getPosition();
		for(ControlCenterEditor temp: controlCenters){
			if(temp.getPosition().equals(a)){
				controlCenters.remove(temp);	
				break;
			}
		}
	}
	
	/**
	 * 
	 * @return een ArrayList van opgeslagen controlCenters
	 */
	public ArrayList<ControlCenterEditor> getControlCenters(){
		return controlCenters;
	}

}