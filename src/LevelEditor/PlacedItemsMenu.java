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
	
	private double maxItemsInRij = 5; 

	public PlacedItemsMenu(float[] sizes, int screenWidthFrame, int screenHeightFrame) {
		super(sizes, screenWidthFrame, screenHeightFrame);
		Guards = new ArrayList<Guardian>();	
		Keys = new ArrayList<Key>();	
	}
	
	public void addGuard(Guardian guard){
		if(guard.routeSize()>0){ //er mogen geen guard toegevoegd worden zonder route.
			Guards.add(guard);
			//hier worden alle items posities opnieuw geset na het toevoegen van een guard of een key.	
			setGuardAndKeySizes();
		}
	}

	public void removeGuard(Guardian guard){
		Guards.remove(guard);
		setGuardAndKeySizes();
		
	}
	public int guardSize(){
		return Guards.size();
	}
	
	public ArrayList<Guardian> getAllGuards(){
		return Guards;
	}
	
	
	public void addKey(Key key){
		Keys.add(key);
		//hier worden alle items posities opnieuw geset na het toevoegen van een guard of een key.	
		setGuardAndKeySizes();
	}
	
	public int keySize(){
		return Keys.size();
	}
	
	public boolean typeIsKey(int xclick, int yclick){
		Key temp = this.getClickedKey(xclick,yclick);
		if(temp == null){
			return false;
		}else
			return true;
	}
	
	public boolean typeIsGuardian(int xclick, int yclick){
		Guardian temp = this.getClickedGuardian(xclick,yclick);
		if(temp == null){
			return false;
		}else
			return true;
	}
	
	public Guardian getClickedGuardian(int xclick, int yclick){
		int TotalGuards = Guards.size();
		for(int i=0; i < TotalGuards; i++){	
			if(Guards.get(i).clickedOnIt(xclick, yclick)){
				return Guards.get(i);
			}
		}
		return null;
	}
	
	public Key getClickedKey(int xclick, int yclick){
		int TotalKeys= Keys.size();
		for(int i=0; i < TotalKeys; i++){	
			if(Keys.get(i).clickedOnIt(xclick, yclick)){
				return Keys.get(i);
			}
		}
		return null;
	}
	
	public void drawItems(GL gl){
		int TotalGuards = Guards.size();
		int TotalKeys= Keys.size();
		for(int i=0; i<TotalGuards; i++){
			Guards.get(i).draw(gl);
		}	
		
		for(int i=0; i<TotalKeys; i++){
			Keys.get(i).draw(gl);
		}
	}
	
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
	
	
	//alle item posities worden opnieuw geset.	
	private void setGuardAndKeySizes() {
		int TotalGuards = Guards.size();
		int TotalKeys= Keys.size();
		int TotalItems = TotalGuards + TotalKeys;
		int teller = 0;
		
		//tempY zorgt ervoor dat de items van hoogte veranderen wanneer er meer dan maxItemsInRij items zijn.
		double tempY = Math.ceil((TotalGuards+TotalKeys)/(maxItemsInRij)); //bij de 6de guard is dit 2.
		float itemSizeX = originalSizes[2]/(float)maxItemsInRij; //zorgt voor de juiste breedte van een item
		float itemSizeY = originalSizes[3]/((float)tempY); 
		
		if(TotalItems<6){
			itemSizeY = originalSizes[3]/((float)tempY+1); //tempY+1 anders kan het gedeeld door nul zijn.
		}


		for(int j=0; j < (tempY); j++){
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
					int tempIndex = teller - TotalGuards;
					Keys.get(tempIndex).updatePosition(itemSizes, screenWidth, screenHeight);
					System.out.println("Key: "+item_LinksBovenX+" , "+item_LinksBovenY+" , "+itemSizeX+" , " + itemSizeY);
				}
				teller++;
			}
		}
	}

}
