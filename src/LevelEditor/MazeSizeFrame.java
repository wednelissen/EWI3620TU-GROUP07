package LevelEditor;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

public class MazeSizeFrame extends JFrame implements ActionListener{

 
	private static final long serialVersionUID = 1L;
	private JButton setSize   = new JButton("set Sizes");
    private JButton removeSize = new JButton("clear all");  

    private JLabel heightLabel     = new JLabel("height:");
    private JTextField heightField  = new JTextField();
    
    private JLabel widthLabel     = new JLabel("width:");
    private JTextField widthField  = new JTextField();
    
    
    private JLabel Melding     = new JLabel("give a size between 0 and 100");   
    private JPanel panel          = new JPanel(new GridLayout(3,2));

    private int height = 0, width = 0;
    private boolean mapdrawCheck = false; //Wanneer er een goede waarde is ingevuld en opgeslagen zal deze true worden
    
    public MazeSizeFrame(){
        setTitle("Maze Sizes");
        setBounds(100,100,400,200);
        Container c = getContentPane();
        setLayout(null);

        panel.setBounds(40,20,300,100);
        panel.add(setSize);
        panel.add(removeSize);
        panel.add(heightLabel);
        panel.add(heightField);
        panel.add(widthLabel);
        panel.add(widthField);
        
        c.add(panel);

        Melding.setBounds(40,120,300,50);
        c.add(Melding);

        setSize.addActionListener(this);
        removeSize.addActionListener(this);

        setVisible(false);
    }

    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
        if (source == setSize){
        	String heightString = heightField.getText();
        	String widthString = widthField.getText();
        	try {
        		height = Integer.parseInt(heightString);
        		width = Integer.parseInt(widthString);
	        	heightField.setText("");
	        	widthField.setText("");
	        	Melding.setText("give a size between 0 and 100");
	        	mapdrawCheck = true;
	        	setVisible(false);
	        	
	      	} catch (NumberFormatException error) {
	      		Melding.setText("no valid height or width");
	      		height = 0;
	      		width = 0;
	      		mapdrawCheck = false;
	      	}        	
        }
        else if(source == removeSize){
        	heightField.setText("");
        	widthField.setText("");
        	Melding.setText("give a size between 0 and 100");
        }
    }
    
    public void appear(){
    	setVisible(true);
    }
    
    
    public int getWidthField(){
    	return width;
    }
    
    public int getHeightField(){
    	return height;
    }
    
    public void resetMapdrawCheck(){
    	mapdrawCheck= false;
    }
    
    public boolean getMapdrawCheck(){
    	return mapdrawCheck;
    }
    
}
