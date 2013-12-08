package LevelEditor;

import java.io.File;

import javax.swing.*;

public class SaveLevelFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	private static JFileChooser fileChooser;
	private String saveFile;
	private String pathLastOpened;
	
	public SaveLevelFrame(){
		fileChooser = new JFileChooser();
	}
	
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
	
	public String getFilePath(){
		this.runSaveFile();
		return saveFile;
	}

}


