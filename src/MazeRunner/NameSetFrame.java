package MazeRunner;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

public class NameSetFrame extends JFrame implements ActionListener{

 
	private static final long serialVersionUID = 1L;
	private JButton startGame   = new JButton("Start Game");  

    private JLabel nameLabel     = new JLabel("Name:");
    private JTextField nameField  = new JTextField();
         
    private JLabel Melding     = new JLabel("If no name is entered, 'AAA' will be used.");   
    private JPanel panel          = new JPanel(new GridLayout(3,1));

    private int height = 0, width = 0;
    private boolean mapdrawCheck = false; //Wanneer er een goede waarde is ingevuld en opgeslagen zal deze true worden
    
    /**
     * creert een Jframe met twee in te vullen tekst vakken voor de breedte en de hoogte.
     * dit Jframe is standaard niet zichtbaar. 
     */
    public NameSetFrame(){
        setTitle("Name");
        setBounds(100,100,400,200);
        Container c = getContentPane();
        setLayout(null);

        panel.setBounds(40,20,300,100);
        
        
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(startGame);
        c.add(panel);

        Melding.setBounds(40,120,300,50);
        c.add(Melding);

        startGame.addActionListener(this);

        setVisible(true);
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
		if (source == startGame) {
			String nameString = nameField.getText();

			if (nameString.equals("")) {
				nameString = "AAA";
			}
			System.out.println(nameString);
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
