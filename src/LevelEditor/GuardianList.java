package LevelEditor;

import java.util.ArrayList;

public class GuardianList {
	private ArrayList<Guardian> Guards;
	
	public GuardianList(){
		Guards = new ArrayList<Guardian>();	
	}
	
	public void add(Guardian guard){
		Guards.add(guard);
	}
	
	public int size(){
		return Guards.size();
	}

}
