package LevelEditor;

import java.io.File;

import javax.swing.*;

public class OpenLevelFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	private static JFileChooser fileChooser;
	private String openFile;
	private String pathLastOpened;
	
	/**
	 * maakt een JfileChooser waarmee een bestand kan worden geopent.
	 * dit frame is niet standaard zichtbaar.
	 */
	public OpenLevelFrame(){
		fileChooser = new JFileChooser();
	}
	
	/**
	 * zorgt dat het 'open' frame verschijnt. wanneer de file wordt geopent wordt het pad naar dit 
	 * bestand opgeslagen in de variable 'openFile'.
	 */
	public void runLoadFile(){
		int returnVal = fileChooser.showOpenDialog(rootPane);
	    if (returnVal == JFileChooser.APPROVE_OPTION) {
	    	System.out.println("File "+fileChooser.getSelectedFile().getName()+" has openend");
	    	openFile = fileChooser.getSelectedFile().getAbsolutePath();
	    	pathLastOpened = fileChooser.getSelectedFile().getParent();
	    	fileChooser.setCurrentDirectory(new File(pathLastOpened));
	    	
	    } else {
	    	System.out.println("opener has been closed");
	        openFile = null;
	    }		
	}
	
	/**
	 * 
	 * @return een String met het pad naar het zojuist geopende bestand.
	 */
	public String getFilePath(){
		this.runLoadFile();
		return openFile;
	}

}


