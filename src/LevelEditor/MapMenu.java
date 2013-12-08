package LevelEditor;

import java.awt.Point;
import java.util.ArrayList;

import javax.media.opengl.GL;

public class MapMenu extends Window {
	
	private BuildingBlock[][] BuildingBlocks; // = new BuildingBlock[10][10];
	private int TotalBuildingBlockX, TotalBuildingBlockY;
	private int width, height;
	

	public MapMenu(float[] sizes, int screenWidthFrame, int screenHeightFrame) {
		super(sizes, screenWidthFrame, screenHeightFrame);
		TotalBuildingBlockX = 0;
		TotalBuildingBlockY = 0;
		width = 0;
		height = 0;
		// TODO Auto-generated constructor stub
	}
	
	public void setWidth(int tempWidth){
		width = tempWidth;
	}
	
	public void setHeight(int tempHeight){
		height = tempHeight;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public boolean BuildingBlocksExists(){
		if(width>0 && height>0){
			return true;
		}
		else return false;
	}
	public void setTotalBuildingBlocks(){
		TotalBuildingBlockX = width;
		TotalBuildingBlockY = height;
		BuildingBlocks = new BuildingBlock[TotalBuildingBlockX][TotalBuildingBlockY];
		this.createTotalBuildingBlocks();
	}
	
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
	
	//alle sleutels worden nagelopen om te controlleren welke deuren een key nodig hebben.
	public void loadKeysAndDoors(ArrayList<Key> loadedKeys){
		for(Key k: loadedKeys){
			getBuildingBlockByPosition(k.getDoor()).setKeyRequired();	
		}		
	}
	
	
	public boolean hasHeightAndWidth(){
		if(TotalBuildingBlockX>0 && TotalBuildingBlockY>0 && TotalBuildingBlockX<101 & TotalBuildingBlockY<101){return true;}
		else if(TotalBuildingBlockX==0 && TotalBuildingBlockY==0){return false;}
		//System.out.println("De maze moet tussen de 0 en 100 groot zijn");
		return false;
	}

	//er worden blokjes in het vlak map getekend, waar op 
	//geklikt kan worden zodat ieder element uit de maze items kunnen worden toegekent
	public void drawBlocks(GL gl) {
		for(int j=0; j < TotalBuildingBlockY; j++){
			for(int i=0; i < TotalBuildingBlockX; i++){
				if(BuildingBlocks[i][j].getFloor()){
					BuildingBlocks[i][j].draw(gl);
				}
				else if(BuildingBlocks[i][j].getDoor()){
						if(BuildingBlocks[i][j].getKeyRequired()){
							gl.glColor3f(0.0f, 1f, 0f);
						}
						else{
							gl.glColor3f(0.5f, 0.1f, 0f);
						}
						BuildingBlocks[i][j].drawBlock(gl);
						gl.glColor3f(0.0f, 0, 0f);
				}
				else{
					BuildingBlocks[i][j].drawBlock(gl);
				}
			}
		}
	}
	
	//wanneer het scherm reshaped, zullen ook de elementen in de map gereshaped worden
	public void updateBlocks(int screenWidthFrame, int screenHeightFrame){
		for(int j=0; j < TotalBuildingBlockY; j++){
			for(int i=0; i < TotalBuildingBlockX; i++){
				BuildingBlocks[i][j].update(screenWidthFrame, screenHeightFrame);	
			}
		}
	}
			
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
	
	public BuildingBlock getBuildingBlockByPosition(Point a){
		int x = (int)a.getX();
		int y = (int)a.getY();
		return BuildingBlocks[x][y];
	}
	
	public BuildingBlock[][] getAllBuildingBlocks(){
		return BuildingBlocks;
	}
	
		
}
