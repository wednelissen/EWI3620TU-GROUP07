package LevelEditor;

import java.awt.Point;
import java.util.ArrayList;

public class SpotList {
	
	private ArrayList<Spot> spots;
	
	public SpotList(){
		 spots = new ArrayList<Spot>();
	}
	
	public void addSpot(Spot s){
		if(spots.size()<8){
			boolean duplicated = false;
			Point a = s.getPosition();
			for(Spot temp: spots){
				if(temp.getPosition().equals(a)){
					duplicated = true;
					break;
				}
			}		
			if(!duplicated)
				spots.add(s);
			else{
				System.out.println("deze spot bestaat al");
			}
		}
		else{
			System.out.println("er mogen maar maximaal 8 spots in een level");			
		}
	}
	
	public void loadSpots(ArrayList<Point> loadedSpotPoints){
		
		for(Point a: loadedSpotPoints){
			Spot temp = new Spot();
			temp.setSpot(a);
			this.addSpot(temp);
		}
	}
	
	public int size(){
		return spots.size();
	}
	
	public void removeSpot(Spot s){
		Point a = s.getPosition();
		for(Spot temp: spots){
			if(temp.getPosition().equals(a)){
				spots.remove(temp);	
				break;
			}
		}
	}
	
	public ArrayList<Spot> getSpots(){
		return spots;
	}

}
