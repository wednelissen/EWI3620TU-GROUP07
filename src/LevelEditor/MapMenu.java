package LevelEditor;

import javax.media.opengl.GL;

public class MapMenu extends Window {
	
	private BuildingBlock[][] BuildingBlocks; // = new BuildingBlock[10][10];
	private int TotalBuildingBlockX, TotalBuildingBlockY;

	public MapMenu(float[] sizes, int screenWidthFrame, int screenHeightFrame) {
		super(sizes, screenWidthFrame, screenHeightFrame);
		TotalBuildingBlockX = 0;
		TotalBuildingBlockY = 0;
		// TODO Auto-generated constructor stub
	}
	
	public void drawLines(GL gl) {
		gl.glBegin(GL.GL_LINE_LOOP);
		gl.glVertex2f(x, y);
		gl.glVertex2f(x + sizeX, y);
		gl.glVertex2f(x + sizeX, y - sizeY);
		gl.glVertex2f(x, y - sizeY);
		gl.glEnd();
	}
	
	public void setTotalBuildingBlocks(int x, int y){
		TotalBuildingBlockX = x;
		TotalBuildingBlockY = y;
		BuildingBlocks = new BuildingBlock[TotalBuildingBlockX][TotalBuildingBlockY];
		this.createTotalBuildingBlocks();
	}
	
	public void createTotalBuildingBlocks(){
		float BuildingBlockSizeX = originalSizes[2]/TotalBuildingBlockX;
		float BuildingBlockSizeY = originalSizes[3]/TotalBuildingBlockY;
						
		for(int i=0; i < TotalBuildingBlockX; i++){
			float BuildingBlock_LinksBovenX = originalSizes[0] + (BuildingBlockSizeX * (i));		
			for(int j=0; j < TotalBuildingBlockY; j++){
				float BuildingBlock_LinksBovenY = originalSizes[1] + (BuildingBlockSizeY * (j));
				
				float[] BuildingBlockSizes = new float[] {BuildingBlock_LinksBovenX,BuildingBlock_LinksBovenY,BuildingBlockSizeX,BuildingBlockSizeY};
				
				//System.out.println(BuildingBlock_LinksBovenX+" , "+BuildingBlock_LinksBovenY+" , "+BuildingBlockSizeX+" , " + BuildingBlockSizeY);
				BuildingBlocks[i][j] = new BuildingBlock(BuildingBlockSizes,screenWidth, screenHeight);	
			}
		}
		
		
	}
	
	public boolean hasHeightAndWidth(){
		if(TotalBuildingBlockX>0 && TotalBuildingBlockY>0 && TotalBuildingBlockX<101 & TotalBuildingBlockY<101){return true;}
		else if(TotalBuildingBlockX==0 && TotalBuildingBlockY==0){return false;}
		System.out.println("De maze moet tussen de 0 en 100 groot zijn");
		return false;
	}

	//er worden blokjes in het vlak map getekend, waar op 
	//geklikt kan worden zodat ieder element uit de maze items kunnen worden toegekent
	public void drawBlocks(GL gl) {
		for(int i=0; i < TotalBuildingBlockX; i++){
			for(int j=0; j < TotalBuildingBlockY; j++){
				BuildingBlocks[i][j].drawBlock(gl);
			}
		}
	}
	
	//wanneer het scherm reshaped, zullen ook de elementen in de map gereshaped worden
	public void updateBlocks(int screenWidthFrame, int screenHeightFrame){
		for(int i=0; i < TotalBuildingBlockX; i++){
			for(int j=0; j < TotalBuildingBlockY; j++){
				BuildingBlocks[i][j].update(screenWidthFrame, screenHeightFrame);	
			}
		}
	}
			
/*public void update(int screenWidthFrame, int screenHeightFrame) {

		float x_linksOnder = originalSizes[0];
		float y_rechtsOnder = originalSizes[1];
		float buttonSizeX = originalSizes[2];
		float buttonSizeY = originalSizes[3];

		screenWidth = screenWidthFrame;
		screenHeight = screenHeightFrame;

		x = x_linksOnder / 800 * screenWidth;
		y = (600 - y_rechtsOnder) / 600 * screenHeight;
		
		sizeX = buttonSizeX / 800 * screenWidth;
		sizeY = buttonSizeY / 600 * screenHeight;
	}*/			
			
}
