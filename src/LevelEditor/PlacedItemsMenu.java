package LevelEditor;

import java.util.ArrayList;

import javax.media.opengl.GL;


/**
 * Placed items is het linker middelste menu. hierin worden alle Key en Guard objecten geplaatst, die
 * in de map zijn geplaatst.
 * tijdens het toevoegen van een Guard of een Key worden alle posities direct geupdate.
 * er wordt met 'maxItemsInRij' bekeken hoeveel keys en guards er maximaal in een rij mogen.
 * @author mvanderreek
 *
 */
public class PlacedItemsMenu extends Window{
	
	private ArrayList<Guardian> Guards;
	private ArrayList<Key> Keys;
	
	private double maxItemsInRij = 5; 

	/**
	 * 
	 * @param sizes, een array van float met 4 coordinaten. linksboven, rechtsboven, breedte, hoogte.
	 * @param screenWidthFrame, breedte van het hele frame.
	 * @param screenHeightFrame, hoogte van het hele frame.
	 */
	public PlacedItemsMenu(float[] sizes, int screenWidthFrame, int screenHeightFrame) {
		super(sizes, screenWidthFrame, screenHeightFrame);
		Guards = new ArrayList<Guardian>();	
		Keys = new ArrayList<Key>();	
	}
	
	/**
	 * vervangt de huidige ArrayList van Guardians, wanneer deze uit een file worden geladen.
	 * @param loadedGuards Arraylist van Guardian
	 */
	public void loadGardians(ArrayList<Guardian> loadedGuards){
		Guards = loadedGuards;
		setGuardAndKeySizes();
	}
	
	/**
	 * vervangt de huidige ArrayList van Key, wanneer deze uit een file worden geladen.
	 * @param loadedKeys Arraylist van Key
	 */
	public void loadKeys(ArrayList<Key> loadedKeys){
		Keys = loadedKeys;
		setGuardAndKeySizes();
	}
	
	/**
	 * een Guardian wordt toegevoegd aan de Arraylist van Guards wanneer deze een route van meer dan 
	 * 0 stappen bevat. 
	 * @param guard
	 */
	public void addGuard(Guardian guard){
		if(guard.routeSize()>0){ //er mogen geen guard toegevoegd worden zonder route.
			Guards.add(guard);
			//hier worden alle items posities opnieuw geset na het toevoegen van een guard of een key.	
			setGuardAndKeySizes();
		}
	}

	/**
	 * de Guard wordt verwijderd indien deze in de Arraylist van Guardian zit.
	 * anders gebeurd er niets.
	 * @param guard
	 */
	public void removeGuard(Guardian guard){
		Guards.remove(guard);
		setGuardAndKeySizes();
	}
	
	/**
	 * 
	 * @return het aantal Guardians in de Arraylist van Guardian.
	 */
	public int guardSize(){
		return Guards.size();
	}
	
	/**
	 * 
	 * @return de ArrayList van alle Guardians
	 */
	public ArrayList<Guardian> getAllGuards(){
		return Guards;
	}
	
	/**
	 * voegt een Key toe aan de ArrayList van Key indien deze nog niet in de lijst zit en 
	 * zelf een positie heeft en verwijst naar een positie naar een deur.
	 * @param key
	 */
	public void addKey(Key key){
		if(!Keys.contains(key)){
			if(key.hasPosition() && key.hasDoor()){ //er wordt gekeken of de key een positie heeft.
				Keys.add(key);
				//hier worden alle items posities opnieuw geset na het toevoegen van een guard of een key.	
				setGuardAndKeySizes();
			}
		}
	}
	
	/**
	 * verwijderd de Key indien deze in de ArrayList van Key bevind. anders gebeurd er niets.
	 * @param key
	 */
	public void removeKey(Key key){
		Keys.remove(key);
		setGuardAndKeySizes();
	}
	
	/**
	 * 
	 * @return het aantal Keys in de ArrayList van Key
	 */
	public int keySize(){
		return Keys.size();
	}
	
	/**
	 * 
	 * @return de ArrayList van alle opgeslagen Keys
	 */
	public ArrayList<Key> getAllKeys(){
		return Keys;
	}
	
	/**
	 * 
	 * @param xclick mouse Click
	 * @param yclick mouse Click
	 * @return true indien het aangeklikte object een Key is. anders false.
	 */
	public boolean typeIsKey(int xclick, int yclick){
		Key temp = this.getClickedKey(xclick,yclick);
		if(temp == null){
			return false;
		}else
			return true;
	}
	
	/**
	 * 
	 * @param xclick mouse Click
	 * @param yclick mouse Click
	 * @return true indien het aangeklikte object een Guardian is. anders false.
	 */
	public boolean typeIsGuardian(int xclick, int yclick){
		Guardian temp = this.getClickedGuardian(xclick,yclick);
		if(temp == null){
			return false;
		}else
			return true;
	}
	
	/**
	 * 
	 * @param xclick mouse Click
	 * @param yclick mouse Click
	 * @return de aangeklikte Guardian
	 */
	public Guardian getClickedGuardian(int xclick, int yclick){
		int TotalGuards = Guards.size();
		for(int i=0; i < TotalGuards; i++){	
			if(Guards.get(i).clickedOnIt(xclick, yclick)){
				return Guards.get(i);
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param xclick mouse Click
	 * @param yclick mouse Click
	 * @return de aangeklikte Key
	 */
	public Key getClickedKey(int xclick, int yclick){
		int TotalKeys= Keys.size();
		for(int i=0; i < TotalKeys; i++){	
			if(Keys.get(i).clickedOnIt(xclick, yclick)){
				return Keys.get(i);
			}
		}
		return null;
	}
	
	/**
	 * zorgt dat alle opgeslagen Guardians en Keys worden getekent in het PlacedItemsMenu.
	 * @param gl
	 */
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
	
	/**
	 * zorgt dat wanneer het scherm is gereshaped alle opgeslagen items van groote mee veranderen. 
	 * @param screenWidthFrame nieuwe breedte van het hele frame.
	 * @param screenHeightFrame nieuwe hoogte van het hele frame.
	 */
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
	 * alle posities en hoogte van de opgeslagen Guardians en Keys worden opnieuw geset.
	 * dit wordt iedere keer uitgevoerd wanneer er een Guard of Key is toegevoegd.
	 * wanneer er meer dan 'maxItemsInRij' staan zal de hoogte van het object worden verkleint zodat
	 * er een volgende rij kan worden gevormd. de eerste rij van de opgeslagen items is altijd de helft
	 * van het PlacedItemsMenu hoogte zodat de plaatjes waar op gedrukt kan worden een beetje in 
	 * verhouding blijven, wanneer deze kleiner schalen door meer items. eerst worden alle Guardians
	 * opnieuw geset en daarna alle Keys, waardoor deze ook netjes geordend in het menu komen te staan.
	 */
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
