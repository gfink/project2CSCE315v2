package eighteen;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
public class HelpGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public HelpGUI() {
		
		String helpTextString = 
				"To play, click a piece you want to move. Moves you can make are highlighted in yellow, and the selected piece is highlighted in green.\n" +
				"When you click a yellow move, your move will be made plus the move of the opponent. Clicking the second location to move to commits that\n"+
				"move. To de-select the move, click ' Cancel Move ', and to end a turn mid chain, click 'End Turn'. You cannot end your turn if a potential\n"+
				"move has been selected. First cancel the move, then end the turn.\n\n"+
				"Team 18\n";
		JLabel helpText = new JLabel(helpTextString);
		this.add(helpText,BorderLayout.NORTH);
		setTitle("Help");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//Right now width needs to be hard coded
		//don't know how to get text to wrap, will need
		//to hardcode line breaks in prompt also
		setSize(800,200);
		setLocationRelativeTo(null);
	}
}
