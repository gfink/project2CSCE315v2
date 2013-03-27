package eighteen;
import java.awt.*;

import javax.swing.*;
//using model code from here http://harryjoy.com/2011/08/21/different-button-shapes-in-swing/
public class GameGrid extends JComponent{
	public int rows;
	public int columns;
	Shape shape;
	public GameGrid(int x, int y)
	{
		
		super();
		rows = x;
		columns = y;
		Dimension size = getPreferredSize();
		//size.width = size.height = Math.max(size.width, size.height);
		setPreferredSize(size);
		//setContentAreaFilled(false);
		
	}
	protected void paintComponent(Graphics g)
	{
		g.setColor(Color.BLACK);
    	if(rows>1)//draw vertical lines
    	{
    		for(int i = 0; i < columns; i++)
    		{
    			g.drawLine(FanoronaGUI.GUI.gamePieces[0][i].getX(),FanoronaGUI.GUI.gamePieces[0][i].getY(),
    					FanoronaGUI.GUI.gamePieces[rows-1][i].getX(),FanoronaGUI.GUI.gamePieces[rows-1][i].getY());
    		}
    	}
    	if(columns>1)//draw horizontal lines
    	{
    		for(int i = 0; i < rows; i++)
    		{
    			g.drawLine(FanoronaGUI.GUI.gamePieces[i][0].getX(),FanoronaGUI.GUI.gamePieces[i][0].getY(),
    					FanoronaGUI.GUI.gamePieces[i][columns-1].getX(),FanoronaGUI.GUI.gamePieces[i][columns-1].getY());
    		}
    	}
		super.paintComponent(g);
	}

}
