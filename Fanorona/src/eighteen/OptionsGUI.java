package eighteen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
public class OptionsGUI extends JFrame{
	//TODO connect to server option
	//TODO host server option, choose to host or connect or AI
	//TODO cancel button
	private static final long serialVersionUID = 1L;
	public static OptionState currentOptions;
	public static OptionsGUI OGUI;
	public static int colNdx = 6;
	public static int rowNdx = 2;
	public boolean gameRunning=false;
	private String[] rcOptions = {"1","3","5","7","9","11","13"};
	public OptionsGUI() {
		
		currentOptions = new OptionState();
		setTitle("Fanorona Options");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		JButton okButton = new JButton("OK");
		JRadioButton white = new JRadioButton("White");
		JRadioButton black = new JRadioButton("Black");
		JLabel colorLabel = new JLabel("Player Color");
		JLabel sizeLabel = new JLabel("Size of Board");
		JLabel rowLabel = new JLabel("Rows");
		JLabel colLabel = new JLabel("Column");
		JLabel oWarning = new JLabel("Warning, changing any game options mid-game will automatically reset the game!");
		JPanel radioPanel = new JPanel(new GridLayout(0,1));
		JPanel sizePanel = new JPanel(new GridLayout(3,2));
		JComboBox rowList = new JComboBox(rcOptions);
		JComboBox colList = new JComboBox(rcOptions);
		if(currentOptions.startColor==Color.WHITE)
			white.setSelected(true);
		else
			black.setSelected(true);
		rowList.setSelectedIndex(rowNdx);
		colList.setSelectedIndex(colNdx);
		ButtonGroup colorOptions = new ButtonGroup();
		colorOptions.add(white);
		colorOptions.add(black);
		radioPanel.add(colorLabel);
		radioPanel.add(white);
		radioPanel.add(black);
		sizePanel.add(sizeLabel);
		sizePanel.add(new JLabel(""));
		sizePanel.add(rowLabel);
		sizePanel.add(rowList);
		sizePanel.add(colLabel);
		sizePanel.add(colList);
		add(oWarning,BorderLayout.NORTH);//warning only shows up after you start playing the game
		add(sizePanel,BorderLayout.LINE_END);
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
		rowList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JComboBox cb = (JComboBox)event.getSource();
                String sRow = (String)cb.getSelectedItem();
                OptionsGUI.currentOptions.oRow = Integer.parseInt(sRow);
            }
        });
		colList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JComboBox cb = (JComboBox)event.getSource();
                String sCol = (String)cb.getSelectedItem();
                OptionsGUI.currentOptions.oCol = Integer.parseInt(sCol);
            }
        });
		okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	//on program start, will make sure valid settings
            	//are entered, while program is running, if clicked
            	//will see if the old settings are different than the new, if so
            	//will restart game with new settings
            	
            	//before creating game, check if option is valid
            	if(!gameRunning || currentOptions.isEqual(currentOptions.prevOption) == false)
            	{
            		//gameRunning variable prevents isEqual running on null pointer
            		currentOptions.prevOption =  new OptionState(currentOptions);
            		if(gameRunning){
            		FanoronaGUI.GUI.setVisible(false);
            		}
            		FanoronaGUI.GUI =  new FanoronaGUI(currentOptions);
            		gameRunning = true;
            		
            	}
            	OGUI.setVisible(false);
            }
        });
		/*
		 * User should be able to start over here, change the board size, change which color they are, connect to a server, or host a game
		 * any other options we need or want to add should be here also
		 */
		setSize(500,150);
		setLocationRelativeTo(null);
	}
	public class OptionState {
		//values the game starts at
		Color startColor = Color.WHITE;
		Color AIColor = Color.BLACK;
		OptionState prevOption = null;
		int oRow = 5;
		int oCol = 13;
		public OptionState()
		{
			//default values above
		}
		public OptionState(OptionState o)
		{
			this.startColor = o.startColor;
			this.AIColor = o.AIColor;
			this.oRow = o.oRow;
			this.oCol = o.oCol;
		}

		public boolean isEqual(OptionState option) {
			//add each option state here
			if(	this.startColor == option.startColor &&
				this.AIColor == option.AIColor &&
				this.oCol == option.oCol &&
				this.oRow == option.oRow)
				return true;
			else
				return false;
		}
	}
	
}
