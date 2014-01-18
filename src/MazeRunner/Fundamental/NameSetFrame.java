package MazeRunner.Fundamental;

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

    private String nameString;
	private boolean nameSet = false;
    
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
    }

    /**
     */
    public void actionPerformed(ActionEvent e){
		Object source = e.getSource();
		if (source == startGame) {
			nameString = nameField.getText();

			if (nameString.equals("")) {
				nameString = "AAA";
			}
			setNameSet(true);
		}
	}
    
	/**
	 * 
	 * @return De naam van de speler.
	 */
	public String getNameString(){

		return nameString;
	}
	
    /**
     * zorgt dat het Jframe verschijnt
     */
    public void appear(){
    	setVisible(true);
    }    
    
    public void disappear(){
    	setVisible(false);
    }
    
    public String getName(){
    	return nameString;
    }

	public boolean getNameSet() {
		return nameSet;
	}

	public void setNameSet(boolean nameSet) {
		this.nameSet = nameSet;
	}
}
