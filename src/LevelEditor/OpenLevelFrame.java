package LevelEditor;

import java.io.File;

import javax.swing.*;

public class OpenLevelFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	private static JFileChooser fileChooser;
	private String openFile;
	private String pathLastOpened;
	
	public OpenLevelFrame(){
		fileChooser = new JFileChooser();
	}
	
	public void runLoadFile(){
		int returnVal = fileChooser.showOpenDialog(rootPane);
		
		//int returnVal = fileChooser.showSaveDialog(rootPane);
		

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
	
	public String getFilePath(){
		this.runLoadFile();
		return openFile;
	}

}


