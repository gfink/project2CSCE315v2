package eighteen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
public class OptionsGUI extends JFrame{
	//TODO connect to server option
	//TODO host server option, choose to host or connect or AI
	//TODO change board size option, with dropdowns of valid col and row sizes
	//TODO tell user with jlabel that if any options are changed, the game will reset
	//TODO cancel button
	//TODO when ok is clicked , modify game type
	private static final long serialVersionUID = 1L;
	public static OptionState currentOptions;
	public static OptionsGUI OGUI;
	public OptionsGUI() {
		
		currentOptions = new OptionState();
		setTitle("Game Options");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		JButton okButton = new JButton("OK");
		JRadioButton white = new JRadioButton("White");
		JRadioButton black = new JRadioButton("Black");
		JPanel radioPanel = new JPanel(new GridLayout(0,1));
		ButtonGroup colorOptions = new ButtonGroup();
		colorOptions.add(white);
		colorOptions.add(black);
		radioPanel.add(white);
		radioPanel.add(black);
		add(radioPanel, BorderLayout.LINE_START);
		add(okButton, BorderLayout.SOUTH);
		white.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	currentOptions.startColor = Color.WHITE;
            	currentOptions.AIColor = Color.BLACK;
            }
        });
		black.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	currentOptions.startColor = Color.BLACK;
            	currentOptions.AIColor = Color.WHITE;
            }
        });
		okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	//on program start, will make sure valid settings
            	//are entered, while program is running, if clicked
            	//will see if the old settings are different than the new, if so
            	//will restart game with new settings
            	
            	//before creating game, check if option is valid
            	if (currentOptions.isOptionValid())
            	{
            		currentOptions.prevOption = currentOptions;
            		FanoronaGUI.GUI =  new FanoronaGUI(currentOptions);
            		OGUI.setVisible(false);
            	}
            }
        });
		/*
		 * User should be able to start over here, change the board size, change which color they are, connect to a server, or host a game
		 * any other options we need or want to add should be here also
		 */
		setSize(800,200);
		setLocationRelativeTo(null);
	}
	public class OptionState {
		Color startColor;
		Color AIColor;
		OptionState prevOption;
		
		public OptionState()
		{
			
		}
		public Boolean isOptionValid()
		{
			return true;
		}
	}
	
}
