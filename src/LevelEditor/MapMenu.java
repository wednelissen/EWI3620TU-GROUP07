package LevelEditor;

import java.awt.Point;
import java.util.ArrayList;

import javax.media.opengl.GL;

import com.sun.opengl.util.texture.Texture;

public class MapMenu extends Window {
	
	private BuildingBlock[][] BuildingBlocks; 
	private int TotalBuildingBlockX, TotalBuildingBlockY;
	private int width, height;
	
	/**
	 * maakt de map aan waarin op ieder blok kan worden geklikt om er bijvoorbeeld een vloer, muur
	 * of deur aan toe te voegen. standaard is dit scherm nog niet gevuld met blokken. deze worden 
	 * toegevoegd met behulp van de functie 'setTotoalBuildingBlocks'. 
	 * 
	 * @param sizes, een array van float met 4 coordinaten. linksboven, rechtsboven, breedte, hoogte.
	 * @param screenWidthFrame, breedte van het hele frame.
	 * @param screenHeightFrame, hoogte van het hele frame.
	 */
	public MapMenu(float[] sizes, int screenWidthFrame, int screenHeightFrame) {
		super(sizes, screenWidthFrame, screenHeightFrame);
		TotalBuildingBlockX = 0;
		TotalBuildingBlockY = 0;
		width = 0;
		height = 0;
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * geeft het aantal blokken in de breedte, die in het mapMenu komen.
	 * @param tempWidth
	 */
	public void setWidth(int tempWidth){
		width = tempWidth;
	}
	
	/**
	 * geeft het aantal blokken in de hoogte, die in het mapMenu komen.
	 * @param tempHeight
	 */
	public void setHeight(int tempHeight){
		height = tempHeight;
	}
	
	/**
	 * 
	 * @return aantal blokken in de breedte.
	 */
	public int getWidth(){
		return width;
	}
	
	/**
	 * 
	 * @return aantal blokken in de hoogte.
	 */
	public int getHeight(){
		return height;
	}
	
	/**
	 * 
	 * @return true als er blokken in de breedte en in de hoogte zijn geset.
	 * anders return deze functie false.
	 */
	public boolean BuildingBlocksExists(){
		if(width>0 && height>0){
			return true;
		}
		else return false;
	}
	/**
	 * zorgt dat de opgegeven blokken in de breedte en in de hoogte worden geinitaliseerd en creert
	 * vervolgens deze objecten met behulp van de functie 
	 * 'createTotoalBuildingBlocks, zodat ze getekent kunnen worden.
	 */
	public void setTotalBuildingBlocks(){
		TotalBuildingBlockX = width;
		TotalBuildingBlockY = height;
		BuildingBlocks = new BuildingBlock[TotalBuildingBlockX][TotalBuildingBlockY];
		this.createTotalBuildingBlocks();
	}
	
	/**
	 * berekent voor ieder blok zijn positie in de map met bijbehorende breedte en hoogte.
	 * hierna worden deze bloken gecreert als objecten. ieder blok is dus een eigen object.
	 */
	public void createTotalBuildingBlocks(){
		float BuildingBlockSizeX = originalSizes[2]/TotalBuildingBlockX;
		float BuildingBlockSizeY = originalSizes[3]/TotalBuildingBlockY;
							
		for(int j=0; j < TotalBuildingBlockY; j++){
			float BuildingBlock_LinksBovenY = originalSizes[1] + (BuildingBlockSizeY * (j));
			for(int i=0; i < TotalBuildingBlockX; i++){
				float BuildingBlock_LinksBovenX = originalSizes[0] + (BuildingBlockSizeX * (i));	
				
				float[] BuildingBlockSizes = new float[] {BuildingBlock_LinksBovenX,BuildingBlock_LinksBovenY,BuildingBlockSizeX,BuildingBlockSizeY};
				
				//System.out.println(BuildingBlock_LinksBovenX+" , "+BuildingBlock_LinksBovenY+" , "+BuildingBlockSizeX+" , " + BuildingBlockSizeY);
				BuildingBlocks[i][j] = new BuildingBlock(BuildingBlockSizes,screenWidth, screenHeight, i, j);	
			}
		}		
	}
	
	/**
	 * creert uit een eerder opgeslagen level de bijbehorende hoeveelheid blokken met de
	 * gesetten waardes zoals vloer,muur,deur.
	 * 
	 * @param floorPlan String van 0,1 en 2 van de plattegrond van de Maze.
	 * @param LoadedWidth integer van het aantal buildingblocks in de breedte.
	 * @param LoadedHeight integer van het aantal buildingblocks in de hoogte.
	 */
	public void loadBuildingBlocks(String[] floorPlan, int LoadedWidth, int LoadedHeight){
		setWidth(LoadedWidth);
		setHeight(LoadedHeight);
		setTotalBuildingBlocks();
		
		for(int j = 0; j < height; j++){
			for(int i = 0; i < width; i++){
				String temp = floorPlan[j].charAt(i) + "";
				int block = Integer.parseInt(temp);
				if(block == 0){
					BuildingBlocks[i][j].setFloor();
				}
				if(block == 1){
					BuildingBlocks[i][j].setWall();
				}
				if(block == 2){
					BuildingBlocks[i][j].setDoor();
				}
			}	
		}
		
	}
	
	
	/**
	 * alle sleutels uit een opgelsagen level worden nagelopen om te controlleren
	 * welke deuren een key nodig hebben. deze deuren zullen worden geset dat deze een key nodig hebben.
	 * @param loadedKeys Arraylist van Key
	 */
	public void loadKeysAndDoors(ArrayList<Key> loadedKeys){
		for(Key k: loadedKeys){
			getBuildingBlockByPosition(k.getDoor()).setKeyRequired();	
		}		
	}
	
	/**
	 * 
	 * @return true indien het aantal ingegeven blokken in de breedte en hoogte tussen de 0 en 101 liggen.
	 * geeft anders false.
	 */
	public boolean hasHeightAndWidth(){
		if(TotalBuildingBlockX>0 && TotalBuildingBlockY>0 && TotalBuildingBlockX<101 & TotalBuildingBlockY<101){return true;}
		else if(TotalBuildingBlockX==0 && TotalBuildingBlockY==0){return false;}
		//System.out.println("De maze moet tussen de 0 en 100 groot zijn");
		return false;
	}

	//er worden blokjes in het vlak map getekend, waar op 
	//geklikt kan worden zodat ieder element uit de maze items kunnen worden toegekent
	/**
	 * tekent alle blokken met hun gesetten toestand. bijvoorbeeld een blok waar een deur is geset
	 * zal als deur worden getekent.
	 * @param gl
	 */
	public void drawBlocks(GL gl) {
		for(int j=0; j < TotalBuildingBlockY; j++){
			for(int i=0; i < TotalBuildingBlockX; i++){
				if(BuildingBlocks[i][j].getFloor()){
					BuildingBlocks[i][j].draw(gl, LoadTexturesEditor.getTexture("editorFloor"));
				}
				else if(BuildingBlocks[i][j].getDoor()){
						if(BuildingBlocks[i][j].getKeyRequired()){
							//gl.glColor3f(0.0f, 1f, 0.90f);
						}
						else{
							//gl.glColor3f(0.5f, 0.1f, 0.90f);
						}
						BuildingBlocks[i][j].drawBlock(gl, null);
//						gl.glColor3f(0.0f, 0, 0f);
//					gl.glColor3f(0.5f, 0.1f, 0f);
					BuildingBlocks[i][j].drawBlock(gl, LoadTexturesEditor.getTexture("editorDoor"));
//					gl.glColor3f(0.0f, 0, 0f);
					
				}
				else{
					BuildingBlocks[i][j].drawBlock(gl, LoadTexturesEditor.getTexture("editorWall"));
				}
			}
		}
	}
	
	/**
	 * wanneer het scherm reshaped, zullen ook alle blokken in de map gereshaped worden
	 * @param screenWidthFrame
	 * @param screenHeightFrame
	 */
	public void updateBlocks(int screenWidthFrame, int screenHeightFrame){
		for(int j=0; j < TotalBuildingBlockY; j++){
			for(int i=0; i < TotalBuildingBlockX; i++){
				BuildingBlocks[i][j].update(screenWidthFrame, screenHeightFrame);	
			}
		}
	}
		
	/**
	 * 
	 * @param xclick mouse click
	 * @param yclick mouse click
	 * @return het BuildingBlock waar op is geklikt. als er niet op een buildingblock is geklikt
	 * returnt deze functie null.
	 */
	public BuildingBlock getClickedBuildingBlock(int xclick, int yclick){
		for(int j=0; j < TotalBuildingBlockY; j++){
			for(int i=0; i < TotalBuildingBlockX; i++){
				if(BuildingBlocks[i][j].clickedOnIt(xclick, yclick)){
					return BuildingBlocks[i][j];
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param a een Point
	 * @return een BuildingBlock.
	 */
	public BuildingBlock getBuildingBlockByPosition(Point a){
		int x = (int)a.getX();
		int y = (int)a.getY();
		return BuildingBlocks[x][y];
	}
	
	/**
	 * 
	 * @return alle BuildingBlocks
	 */
	public BuildingBlock[][] getAllBuildingBlocks(){
		return BuildingBlocks;
	}
	
		
}
