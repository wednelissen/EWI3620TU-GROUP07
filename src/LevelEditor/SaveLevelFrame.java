package LevelEditor;

import java.io.File;

import javax.swing.*;

public class SaveLevelFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	private static JFileChooser fileChooser;
	private String saveFile;
	private String pathLastOpened;
	
	/**
	 * maakt een JfileChooser waarmee een bestand kan worden opgeslagen.
	 * dit frame is niet standaard zichtbaar.
	 */
	public SaveLevelFrame(){
		fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("C:\\Java Workspace\\Level database"));
	}
	
	/**
	 * zorgt dat het 'save' frame verschijnt. wanneer de file wordt opgeslagen wordt het pad naar 
	 * deze plek onthouden in de variable 'saveFile'.
	 */
	public void runSaveFile(){
		
		int returnVal = fileChooser.showSaveDialog(rootPane);
		

	    if (returnVal == JFileChooser.APPROVE_OPTION) {
	    	System.out.println("File "+fileChooser.getSelectedFile().getName()+" has saved");
	    	saveFile = fileChooser.getSelectedFile().getAbsolutePath();
	    	pathLastOpened = fileChooser.getSelectedFile().getParent();
	    	fileChooser.setCurrentDirectory(new File(pathLastOpened));
	    	
	    } else {
	    	System.out.println("saver has been closed");
	        saveFile = null;
	    }		
	}
	
	/**
	 * 
	 * @return de String naar het pad incl naam van het bestand waarin de data moet worden opgeslagen.
	 */
	public String getFilePath(){
		this.runSaveFile();
		return saveFile;
	}

}


