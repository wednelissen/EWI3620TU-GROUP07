package MazeRunner.Player;

import java.util.ArrayList;

import MazeRunner.Objects.Keys;

public class Inventory {
	private ArrayList<Keys> keyList;
	
	public Inventory(){
		keyList = new ArrayList<Keys>();	
	}
	
	public void addKey(Keys k){
		if(!k.hasPickedUp()){
			keyList.add(k);
			k.isPickedUp();
		}
	}
	
	public void removeKey(Keys k){
		keyList.remove(k);
	}
	
	public ArrayList<Keys> getKeys(){
		return keyList;
	}
}
