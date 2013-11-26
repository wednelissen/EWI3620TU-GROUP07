package LevelEditor;

import java.util.ArrayList;

import javax.media.opengl.GL;

//in de classe ItemsMenu komt een arraylist met Guards en met Keys
//tijdens het toevoegen van een Guard of een Key worden alle posities geupdate
//er moet met variable worden geset hoeveel keys en guards er maximaal in een rij mogen
//dit kan met aantal keys+guards/variable en als dit dan 1 is mag je naar de volgende rij.

public class GuardianList extends Window{
	private ArrayList<Guardian> Guards;
		
	public GuardianList(float[] sizes, int screenWidthFrame, int screenHeightFrame){
		super(sizes, screenWidthFrame, screenHeightFrame);		
		Guards = new ArrayList<Guardian>();	
		}
	
	public void add(Guardian guard){
		Guards.add(guard);
	}
	
	public int size(){
		return Guards.size();
	}
	
	public void drawGuardians(GL gl){
		int aantal = Guards.size();
		if(aantal>0){
			/*
			originalSizes = sizes;
			float x_linksOnder = sizes[0];
			float y_rechtsOnder = sizes[1];
			float buttonSizeX = sizes[2];
			float buttonSizeY = sizes[3];
			*/
			
		}
	}

}
