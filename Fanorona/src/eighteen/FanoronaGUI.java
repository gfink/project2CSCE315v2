package eighteen;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import eighteen.Board.BadMoveException;
import eighteen.Piece.adjLoc;

public class FanoronaGUI extends JFrame {
	//TODO if choice between pieces, pick attacking or retreating
	//TODO end move button
	//TODO show whose turn it is
	//TODO show how many pieces each side has
	//TODO show how many moves have been made
	//TODO show the utility value of the board (for the user)
	JButton helpb; //TODO add to a tool bar
	Board board;
	DrawnPiece[][] gamePieces;
	Container game = getContentPane();
	PieceListener clicked;
	DrawnPiece prevMove;
	Boolean isMoveState2 = false;
    public static FanoronaGUI GUI;
    
    public static void main(String[] args) {
       GUI =  new FanoronaGUI();
    }
    public void makePieces()
    {
    	JPanel jBoard = new JPanel();
    	jBoard.setLayout(new GridLayout(board.ROWS,board.COLUMNS,15,15));
        for(int x= 0;x<board.ROWS;x++)
        {
        	for(int y = 0; y<board.COLUMNS; y++)
        	{
        		if(board.theBoard[x][y].getColor() == Color.BLACK)
        		{
        			gamePieces[x][y] = new DrawnPiece(x,y,Color.BLACK);
        			gamePieces[x][y].setBackground(Color.BLACK);
        		}
        		else if(board.theBoard[x][y].getColor() == Color.WHITE)
        		{
        			gamePieces[x][y] = new DrawnPiece(x,y,Color.WHITE);
        			gamePieces[x][y].setBackground(Color.WHITE);
        		}
        		else
        		{
        			gamePieces[x][y] = new DrawnPiece(x,y,Color.GRAY);
        			gamePieces[x][y].setBackground(Color.GRAY);
        		}
        		jBoard.add(gamePieces[x][y]);
        	}
        }
        game.add(jBoard,BorderLayout.CENTER);
    }
    public void makeHelpButton()
    {
    	 helpb = new JButton("Help");
         helpb.setBounds(50,60,80,30);
         helpb.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent event) {
                 HelpGUI helpgui = new HelpGUI();
                 helpgui.setVisible(true);
             }
         });
         game.add(helpb,BorderLayout.NORTH);
    }
    public void makePieceListeners()
    {
    	clicked = new PieceListener(); 
		for(int x= 0;x<board.ROWS;x++)
        {
        	for(int y = 0; y<board.COLUMNS; y++)
        	{
			gamePieces[x][y].addActionListener(clicked);
        	}
		}
    }
    public void makeBoard()
    {
    	
    }
    public FanoronaGUI() {
    	game.setLayout(new BorderLayout());
    	board = new Board(5,13);
    	gamePieces = new DrawnPiece[Board.ROWS][Board.COLUMNS];
    	prevMove = new DrawnPiece(0,0,Color.GRAY);
    	
    	makePieces();
    	//makeBoard();
    	makeHelpButton();
    	makePieceListeners();
    	setSize(1000,700);
        setTitle("Fanorona");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    class PieceListener implements ActionListener
    {
    	/*
			isMoveState2 tells us if the user is in MoveState1 or MoveState2
			if isMoveState2 is false, or in MoveState1, the user is picking a piece
			if isMoveState2 is true, or in MoveState2, the user is picking a location for the piece to move
		
    	 */
    	public void actionPerformed(ActionEvent e)
    	{
    		//needs to call some sort of isValidMove function before trying to run the move
    		DrawnPiece gamePiece = (DrawnPiece) e.getSource();
    		Piece pointsClicked = board.theBoard[gamePiece.xLoc][gamePiece.yLoc];
    		//first check if the piece clicked is an available piece
    		if (gamePiece.pColor == Color.YELLOW)//already selected
    		{
    			//make the point clicked the color of the previous point clicked
    			gamePieces[pointsClicked.row][pointsClicked.column].pColor = prevMove.pColor;
    			gamePieces[pointsClicked.row][pointsClicked.column].repaint();
    			//make the previous point turn gray
    			gamePieces[prevMove.xLoc][prevMove.yLoc].pColor = Color.GRAY;
    			gamePieces[prevMove.xLoc][prevMove.yLoc].repaint();
    			//THIS IS THE POINT WHERE A MOVE SHOULD BE MADE
    			//ALL PIECES INVOLVED IN A MOVE NEED TO BE REPAINTED AT THIS POINT
    		}
    		else
    		{
    			//highlight all available moves, if not making a move, and if the piece clicked isn't gray
	    		for(adjLoc p : pointsClicked.adjacentLocations)
	    		{
	    			if (gamePieces[p.row][p.column].pColor == Color.GRAY)
	    			{
	    				gamePieces[p.row][p.column].pColor = Color.YELLOW;
	    				gamePieces[p.row][p.column].repaint();
	    			}
	    			gamePieces[prevMove.xLoc][prevMove.yLoc].pColor = Color.GRAY;
	    		}
	    	prevMove=gamePiece;
    		}
    		//after click is done, set the previous move to the last piece clicked, records x, y, and color
    		
    		System.out.print("Clicked x:" + gamePiece.xLoc+" y: "+gamePiece.yLoc+"\n");
    		
    	}
    }
    
}