package eighteen;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
public class HelpGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public HelpGUI() {
		
		String helpTextString = 
				"This is where the help information will go " +
				"once we decide what that is.";
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
