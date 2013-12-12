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
    
    /**
     * creert een Jframe met twee in te vullen tekst vakken voor de breedte en de hoogte.
     * dit Jframe is standaard niet zichtbaar. 
     */
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

    /**
     * Wordt bekeken of je op de 'set' of 'clear' knop hebt gedrukt.
     * indien op de set knop worden de breedte en hoogte naar integers vertaald en wordt het scherm
     * ontzichtbaar gemaakt.
     * indien op de clear knop wordt gedrukt worden alleen de breedte en hoogte vlakken leeg gemaakt
     * en blijft het scherm zichtbaar.
     */
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
    
    /**
     * zorgt dat het Jframe verschijnt
     */
    public void appear(){
    	setVisible(true);
    }
    
    /**
     * 
     * @return de breedte als integer.
     */
    public int getWidthField(){
    	return width;
    }
    
    /**
     * 
     * @return de hoogte als integer
     */
    public int getHeightField(){
    	return height;
    }
    
    /**
     * zorgt dat de boolean 'mapdrawCheck' weer op false wordt geset. deze is standaard false
     * en wordt op true geset wanneer een goede breedte en hoogte is geset.
     */
    public void resetMapdrawCheck(){
    	mapdrawCheck= false;
    }
    
    /**
     * 
     * @return true als er een goede breedte en hoogte is ingevuld.
     */
    public boolean getMapdrawCheck(){
    	return mapdrawCheck;
    }
    
}
