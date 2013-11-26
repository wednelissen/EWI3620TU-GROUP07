package LevelEditor;

import java.util.ArrayList;

import javax.media.opengl.GL;

//in de classe ItemsMenu komt een arraylist met Guards en met Keys
//tijdens het toevoegen van een Guard of een Key worden alle posities geupdate
//er moet met variable worden geset hoeveel keys en guards er maximaal in een rij mogen
//dit kan met aantal keys+guards/variable en als dit dan 1 is mag je naar de volgende rij.

public class PlacedItemsMenu extends Window{
	
	private ArrayList<Guardian> Guards;
	private ArrayList<Key> Keys;
	private float maxItemsInRij = 5; 

	public PlacedItemsMenu(float[] sizes, int screenWidthFrame, int screenHeightFrame) {
		super(sizes, screenWidthFrame, screenHeightFrame);
		Guards = new ArrayList<Guardian>();	
		Keys = new ArrayList<Key>();	
	}
	
	public void addGuard(Guardian guard){
		if(guard.routeSize()>0){ //er mogen geen guard toegevoegd worden zonder route.
			Guards.add(guard);
			//hier worden alle item posities opnieuw geset na het toevoegen van een guard of een key.	
			setGuardAndKeySizes();
		}
	}

	
	
	
	public int guardSize(){
		return Guards.size();
	}
	
	//TO DO Keys moeten nog gedrawd worden!!!!!!!!!!! 
	public void drawItems(GL gl){
		int TotalGuards = Guards.size();
		for(int i=0; i<TotalGuards; i++){
			Guards.get(i).draw(gl);
		}		
	}
	
	//TO DO Keys moeten nog geupdate worden!!!!!!!!!!! 
	public void updateItems(int screenWidthFrame, int screenHeightFrame){
		int TotalGuards = Guards.size();
		int TotalKeys= Keys.size();
		for(int i=0; i<TotalGuards; i++){
			Guards.get(i).update(screenWidthFrame, screenHeightFrame);
		}		
		for(int i=0; i<TotalKeys; i++){
			Keys.get(i).update(screenWidthFrame, screenHeightFrame);
		}	
	}
	
	/**
	//wanneer het scherm reshaped, zullen ook de elementen in de map gereshaped worden
	public void updateBlocks(int screenWidthFrame, int screenHeightFrame){
		for(int j=0; j < TotalBuildingBlockY; j++){
			for(int i=0; i < TotalBuildingBlockX; i++){
				BuildingBlocks[i][j].update(screenWidthFrame, screenHeightFrame);	
			}
		}
	}
	**/
	
	
	//alle item posities worden opnieuw geset.	
	private void setGuardAndKeySizes() {
		int TotalGuards = Guards.size();
		int TotalKeys= Keys.size();
		int TotalItems = TotalGuards + TotalKeys;
		int teller = 0;
		
		int tempY = (TotalGuards+TotalKeys)/(int)(maxItemsInRij+1); //bij de 6de guard is dit 1.
		float itemSizeX = originalSizes[2]/maxItemsInRij; //zorgt voor de juiste breedte van een item
		float itemSizeY = originalSizes[3]/(tempY+1); //tempY+1 anders kan het gedeeld door nul zijn.
		

		for(int j=0; j < (tempY+1); j++){
			float item_LinksBovenY = originalSizes[1] + (itemSizeY * (j));
			for(int i=0; i < maxItemsInRij; i++){
				float item_LinksBovenX = originalSizes[0] + (itemSizeX * (i));	
				
				float[] itemSizes = new float[] {item_LinksBovenX,item_LinksBovenY,itemSizeX,itemSizeY};
				if(teller >= TotalItems){
					break;
				}
				
				if(teller < TotalGuards){	
					System.out.println("Guard: "+item_LinksBovenX+" , "+item_LinksBovenY+" , "+itemSizeX+" , " + itemSizeY);
					Guards.get(teller).updatePosition(itemSizes, screenWidth, screenHeight);
				}
				else if(teller >= TotalGuards){
					System.out.println("Key: "+item_LinksBovenX+" , "+item_LinksBovenY+" , "+itemSizeX+" , " + itemSizeY);
					System.out.println("update keys");
				}
				teller++;
			}
		}
	}

}
