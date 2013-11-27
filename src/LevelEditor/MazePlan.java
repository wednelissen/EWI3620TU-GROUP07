package LevelEditor;

public class MazePlan {
	private int width, height;
	private String printWidth, printHeight;
	
	
	public MazePlan(){
		width = 0;
		height = 0;
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

	

}



/*
  
 	//wordt gebruikt voor een vierkant maze van grote MAZE_SIZE
  	public final double MAZE_SIZE = 10;
	public final double SQUARE_SIZE = 5;

	private int[][] maze = 
	{	{  1,  1,  1,  1,  1,  1,  1,  1,  1,  1 },
		{  1,  0,  0,  0,  0,  0,  0,  0,  0,  1 },
		{  1,  0,  0,  0,  0,  0,  1,  1,  1,  1 },
		{  1,  0,  1,  0,  0,  0,  1,  0,  0,  1 },
		{  1,  0,  1,  0,  1,  0,  1,  0,  0,  1 },
		{  1,  0,  1,  0,  1,  0,  1,  0,  0,  1 },
		{  1,  0,  0,  0,  1,  0,  1,  0,  0,  1 },
		{  1,  0,  0,  0,  1,  1,  1,  0,  0,  1 },
		{  1,  0,  0,  0,  0,  0,  0,  0,  0,  1 },
		{  1,  1,  1,  1,  1,  1,  1,  1,  1,  1 }	};
*/
