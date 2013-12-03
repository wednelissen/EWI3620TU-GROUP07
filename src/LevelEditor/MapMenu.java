package LevelEditor;

import java.awt.Point;

import javax.media.opengl.GL;

public class MapMenu extends Window {
	
	private BuildingBlock[][] BuildingBlocks; // = new BuildingBlock[10][10];
	private int TotalBuildingBlockX, TotalBuildingBlockY;
	private int width, height;
	private String printWidth, printHeight;
	
	

	public MapMenu(float[] sizes, int screenWidthFrame, int screenHeightFrame) {
		super(sizes, screenWidthFrame, screenHeightFrame);
		TotalBuildingBlockX = 0;
		TotalBuildingBlockY = 0;
		width = 0;
		height = 0;
		// TODO Auto-generated constructor stub
	}
	
	public void setWidth(char key){
		if(printWidth == null){	
			printWidth = ""+key;
		}
		else{
			printWidth = printWidth + key;
		}
		//de input van type String wordt omgezet naar een integer
		width = Integer.parseInt(printWidth);
		
	}
	
	public void setHeight(char key){
		if(printHeight == null){	
			printHeight = ""+key;
		}
		else{
			printHeight = printHeight + key;
		}
		
		//de input van type String wordt omgezet naar een integer
		height = Integer.parseInt(printHeight);
	}
	
	public void removeWidth(){
		if(printWidth != null){
			if(printWidth.length()>1){
				printWidth = printWidth.substring(0,printWidth.length()-1);
				width = Integer.parseInt(printWidth);
			}
			else{
				printWidth = null;
				width = 0;
			}
		}		
	}
	
	public void removeHeight(){
		if(printHeight != null){
			if(printHeight.length()>1){
				printHeight = printHeight.substring(0,printHeight.length()-1);
				height = Integer.parseInt(printHeight);
			}
			else{
				printHeight = null;
				height = 0;
			}
		}		
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
					gl.glColor3f(0.5f, 0.1f, 0f);
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
