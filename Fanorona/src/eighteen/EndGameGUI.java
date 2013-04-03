package eighteen;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class EndGameGUI extends JFrame{
	public static EndGameGUI EGGUI;
	EndGameGUI()
	{
		setTitle("GAME OVER");
		setSize(400,200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		JButton play = new JButton("Play Again");
		JButton quit= new JButton("Quit");
		JLabel winloose = new JLabel(FanoronaGUI.GUI.winLossTieString);
		play.setBounds(50,60,80,30);
		quit.setBounds(50,60,80,30);
		play.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent event) {
        	   FanoronaGUI.GUI.setVisible(false);
        	   FanoronaGUI.GUI = new FanoronaGUI(OptionsGUI.currentOptions);
        	   EGGUI.setVisible(false);
           }
		});
		quit.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent event) {
        	   FanoronaGUI.GUI.setVisible(false);
        	   EGGUI.setVisible(false);
           }
		});
		JPanel buttonPanel = new JPanel(new GridLayout(1,0));
		buttonPanel.add(play);
		buttonPanel.add(quit);
		add(winloose,BorderLayout.NORTH);
		add(buttonPanel, BorderLayout.SOUTH);
		pack();
	}
}
