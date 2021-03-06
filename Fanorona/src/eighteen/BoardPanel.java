package eighteen;

import java.awt.*;
import javax.swing.*;

public class BoardPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4611523655863094386L;
	public int rows;
	public int cols;
	
	public BoardPanel(int x, int y) {
		rows = x;
		cols = y;
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(rows > 1) {
			// Draws vertical lines
    		for(int i = 0; i < cols; i++) {
    			DrawnPiece start = FanoronaGUI.GUI.gamePieces[0][i];
    			DrawnPiece end = FanoronaGUI.GUI.gamePieces[rows-1][i];
    			g2.drawLine(start.getX() + start.getWidth() / 2,
    					start.getY() + start.getHeight() / 2,
    					end.getX() + end.getWidth() / 2,
    					end.getY() + end.getHeight() / 2);
    		}
    		
    		// Draws diagonals not fully from top to bottom (Far left/right)
    		for(int i=2; i < rows; i += 2) {
    			if(i == 2 && (rows + cols) % 4 == 0)
    				i--;
    			DrawnPiece start = FanoronaGUI.GUI.gamePieces[i][0];
    			
    			// For \ diagonals
    			int endRow = i + rows - 1;
    			int endCol = rows - 1;
    			while(endRow >= rows || endCol >= cols) {
    				endCol--;
    				endRow--;
    			}
    			DrawnPiece end = FanoronaGUI.GUI.gamePieces[endRow][endCol];
    			g2.drawLine(start.getX() + start.getWidth() / 2,
	    					start.getY() + start.getHeight() / 2,
	    					end.getX() + end.getWidth() / 2,
	    					end.getY() + end.getHeight() / 2);
    			
    			// For / diagonals
    			endRow = i + rows - 1;
    			endCol = cols - rows;
    			while(endRow >= rows || endCol < 0) {
    				endCol++;
    				endRow--;
    			}
    			start = FanoronaGUI.GUI.gamePieces[i][cols - 1];
    			end = FanoronaGUI.GUI.gamePieces[endRow][endCol];
    			g2.drawLine(start.getX() + start.getWidth() / 2,
	    					start.getY() + start.getHeight() / 2,
	    					end.getX() + end.getWidth() / 2,
	    					end.getY() + end.getHeight() / 2);
    		}
    		
    		// Draws diagonals fully extending from top to bottom
    		if((cols + rows) % 4 == 0) {
    			for(int i=1; i < cols; i += 2) {
	    			DrawnPiece start = FanoronaGUI.GUI.gamePieces[0][i];
	    			
	    			// For \ Diagonals
	    			int endRow = rows - 1;
	    			int endCol = i + rows - 1;
	    			while(endCol >= cols || endRow >= rows) {
	    				endCol--;
	    				endRow--;
	    			}
	    			DrawnPiece end = FanoronaGUI.GUI.gamePieces[endRow][endCol];
	    			g2.drawLine(start.getX() + start.getWidth() / 2,
		    					start.getY() + start.getHeight() / 2,
		    					end.getX() + end.getWidth() / 2,
		    					end.getY() + end.getHeight() / 2);
	    			
	    			// For / Diagonals
	    			endRow = rows - 1;
	    			endCol = i - rows + 1;
	    			while(endCol < 0 || endRow >= rows) {
	    				endCol++;
	    				endRow--;
	    			}
	    			end = FanoronaGUI.GUI.gamePieces[endRow][endCol];
	    			g2.drawLine(start.getX() + start.getWidth() / 2,
		    					start.getY() + start.getHeight() / 2,
		    					end.getX() + end.getWidth() / 2,
		    					end.getY() + end.getHeight() / 2);
	    		}
    		}
    		else {
	    		for(int i=0; i < cols; i += 2) {
	    			DrawnPiece start = FanoronaGUI.GUI.gamePieces[0][i];
	    			
	    			// For \ Diagonals
	    			int endRow = rows - 1;
	    			int endCol = i + rows - 1;
	    			while(endCol >= cols || endRow >= rows) {
	    				endCol--;
	    				endRow--;
	    			}
	    			DrawnPiece end = FanoronaGUI.GUI.gamePieces[endRow][endCol];
	    			g2.drawLine(start.getX() + start.getWidth() / 2,
		    					start.getY() + start.getHeight() / 2,
		    					end.getX() + end.getWidth() / 2,
		    					end.getY() + end.getHeight() / 2);
	    			
	    			// For / Diagonals
	    			endRow = rows - 1;
	    			endCol = i - rows + 1;
	    			while(endCol < 0 || endRow >= rows) {
	    				endCol++;
	    				endRow--;
	    			}
	    			end = FanoronaGUI.GUI.gamePieces[endRow][endCol];
	    			g2.drawLine(start.getX() + start.getWidth() / 2,
		    					start.getY() + start.getHeight() / 2,
		    					end.getX() + end.getWidth() / 2,
		    					end.getY() + end.getHeight() / 2);
	    		}
    		}
    	}
		
    	if(cols > 1) {
    		// Draws horizontal lines
    		for(int i = 0; i < rows; i++)
    		{
    			DrawnPiece start = FanoronaGUI.GUI.gamePieces[i][0];
    			DrawnPiece end = FanoronaGUI.GUI.gamePieces[i][cols-1];
    			g2.drawLine(start.getX() + start.getWidth() / 2,
    						start.getY() + start.getHeight() / 2,
    						end.getX() + end.getWidth() / 2,
    						end.getY() + end.getHeight() / 2);
    		}
    	}
	}
}
