package eighteen;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import eighteen.Board.BadBoardException;
import eighteen.Board.BadMoveException;
import eighteen.Board.GameOverException;
import eighteen.Piece.adjLoc;

public class FanoronaGUI extends JFrame {
	//TODO if choice between pieces, pick attacking or retreating
	//TODO add to button bar buttons for, sacrifice piece, reset game
	//Class members for GUI
	JButton helpb;
	JPanel topRow = new JPanel();	
	JLabel blackPieces,whitePieces,movesMade,utilityVal,currentTurn;
	PieceListener clicked;//given to every piece in a loop
	Container game = getContentPane();
	private String playerTurn = "WHITE";
	DrawnPiece[][] gamePieces;
	DrawnPiece prevMoveDrawn;
    public static FanoronaGUI GUI;
	//Class members for operation of game
	Board board; //contains the array of pieces and board management functions
	Piece prevMove;
	AttackState userPickState;
	AI opponent;
	Color playerColor;
	Boolean isMoveState2 = false;
	/*
	 * MAIN IS HERE
	 */
    public static void main(String[] args) {
    	//the options GUI will create an instance of this object
    	OptionsGUI.OGUI = new OptionsGUI();
    	OptionsGUI.OGUI.setVisible(true);
    	//GUI =  new FanoronaGUI();
    	//start a thread waiting for the user to pick server or client, something like that
    }
    
    public void changeTurn() {
    	if (playerTurn=="WHITE")
    		playerTurn = "BLACK";
    	else
    		playerTurn = "WHITE";
    }
    
    public void makeInfoPanel() {
    	JPanel InfoPanel = new JPanel();
    	GridLayout Lay = new GridLayout();
    	InfoPanel.setLayout(Lay);
    	InfoPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    	blackPieces = new JLabel("Black: "+board.blacks);
    	whitePieces = new JLabel("White: "+board.whites);
    	movesMade = new JLabel("Move: "+board.moves);
    	utilityVal = new JLabel("Utility: "+board.Utility());
    	currentTurn = new JLabel("Turn: " + playerTurn);
    	InfoPanel.add(blackPieces);
    	InfoPanel.add(whitePieces);
    	InfoPanel.add(movesMade);
    	InfoPanel.add(utilityVal);
    	InfoPanel.add(currentTurn);
    	game.add(InfoPanel,BorderLayout.SOUTH);
    }
    
    public void updateInfoPanel() {
    	blackPieces.setText("Black: "+board.blacks);
    	whitePieces.setText("White: "+board.whites);
    	movesMade.setText("Move: "+board.moves);
    	utilityVal.setText("Utility: "+board.Utility());
    	currentTurn.setText("Turn: " + playerTurn);
    }
    
    public void makePieces() {
    	BoardPanel jBoard = new BoardPanel(Board.ROWS, Board.COLUMNS);
    	jBoard.setLayout(new GridLayout(Board.ROWS,Board.COLUMNS,10,10));
        for(int x= 0;x<Board.ROWS;x++) {
        	for(int y = 0; y<Board.COLUMNS; y++) {
        		if(board.theBoard[x][y].getColor() == Color.BLACK) {
        			gamePieces[x][y] = new DrawnPiece(x,y,Color.BLACK);
        		}
        		else if(board.theBoard[x][y].getColor() == Color.WHITE) {
        			gamePieces[x][y] = new DrawnPiece(x,y,Color.WHITE);
        		}
        		else {
        			gamePieces[x][y] = new DrawnPiece(x,y,Color.GRAY);
        		}
        		jBoard.add(gamePieces[x][y]);
        	}
        }
        game.add(jBoard,BorderLayout.CENTER);
    }
    
    public void makeHelpButton() {
    	 helpb = new JButton("Help");
         helpb.setBounds(50,60,80,30);
         helpb.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent event) {
                 HelpGUI helpgui = new HelpGUI();
                 helpgui.setVisible(true);
             }
         });
         topRow.add(helpb);
    }
    public void makeResetButton() {
	   	JButton options = new JButton("Reset Game");
	   	options.setBounds(50,60,80,30);
	   	options.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent event) {
	        	FanoronaGUI.GUI = new FanoronaGUI(OptionsGUI.currentOptions);
	        }
	    });
	    topRow.add(options);
    }
    public void makeGameOptionsButton() {
	   	JButton options = new JButton("Game Options");
	   	options.setBounds(50,60,80,30);
	   	options.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent event) {
	        	OptionsGUI.OGUI.setVisible(true);
	        }
	    });
	    topRow.add(options);
    }
    public void makeAttackWithdrawButton() {
    	 JButton attack = new JButton("Attack");
    	 JButton withdraw = new JButton("Withdraw");
    	 attack.setBounds(50,60,80,30);
    	 withdraw.setBounds(50,60,80,30);
         attack.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent event) {
            	 //if(userPickState != )
            	 //{
            		 
            	 //}
             }
         });
         withdraw.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent event) {
            	 
             }
         });
         topRow.add(attack);
         topRow.add(withdraw);
    }
    
    public void makeMoveCancelButton() {
    	 JButton cancelMove = new JButton("Cancel Move");
    	 cancelMove.setBounds(50,60,80,30);
    	 cancelMove.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent event) {
            	 isMoveState2 = false;
            	 resetPrevMove();
             }
         });
         topRow.add(cancelMove);
    }
    
    public void makeEndTurnButton() {
    	 JButton EndTurn = new JButton("End Turn");
    	 EndTurn.setBounds(50,60,80,30);
    	 EndTurn.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent event) {
            	 if(!isMoveState2 && board.chain) {
            		System.out.print("End turn pressed\n");
            		//RUN AI
            		try {
        	    		ArrayList<Move> AIMoves;
        	    		opponent.opponentMove(board);
        				AIMoves = opponent.alphaBetaSearch();
        				
        				System.out.println("Number of AI moves to make: " + AIMoves.size());
        				
        	    		for (Move m : AIMoves) {
        	    			System.out.println(m);
        	  //  			for(Piece.adjLoc loc: board.previousLocations) {
        	  // 				System.out.print(loc);
        	  // 			}
        	  //  			System.out.println();
        					gamePieces[m.end.row][m.end.column].pColor = opponent.getColor();
        					gamePieces[m.end.row][m.end.column].repaint();
        					//make the previous point turn gray
        					gamePieces[m.start.row][m.start.column].pColor = Color.GRAY;
        					gamePieces[m.start.row][m.start.column].repaint();
        					
        					ArrayList<Piece> piecesToColor;
        					try {
        						piecesToColor = board.move(m);
        		    			/*for(Piece.adjLoc prev: board.previousLocations) {
        			    			System.out.println(prev);
        		    			}*/
        						for(Piece p : piecesToColor) {
        		    					gamePieces[p.row][p.column].pColor = Color.GRAY;
        		    					gamePieces[p.row][p.column].repaint();
        		    			}
        					} catch (GameOverException e) {
        						// TODO Auto-generated catch block
        						e.printStackTrace();
        					}
        					prevMoveDrawn = null;
        	    	    	prevMove = null;
        	    			if(!board.chain)
        	    				changeTurn();//the board will automatically change the turn, the gui needs to be updated manually though
        	    		}
            		} catch (BadMoveException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
            		//RUN AI END
            		changeTurn();
            		board.switchTurn();//has to be called manually since it is normally called in move(), which isn't called here
            		updateInfoPanel();
            	 }
             }
         });
         topRow.add(EndTurn);
    }
    
    public void resetPrevMove() {
    	//eventually display that the move was reset visually
    	System.out.print("ResetPrevMove\n");
    	if(prevMove != null) {
	    	for(adjLoc p : prevMove.adjacentLocations) {
				if (gamePieces[p.row][p.column].pColor == Color.YELLOW)
				{
					gamePieces[p.row][p.column].pColor = Color.GRAY;
					gamePieces[p.row][p.column].repaint();
				}
			}
	    	prevMoveDrawn.selected = false;
	    	prevMoveDrawn.repaint();
	    	prevMoveDrawn = null;
	    	prevMove = null;
	    	isMoveState2 = false;
    	}
    }
    
    public void makePieceListeners() {
    	clicked = new PieceListener(); 
		for(int x= 0;x<Board.ROWS;x++) {
        	for(int y = 0; y<Board.COLUMNS; y++)
        	{
        		gamePieces[x][y].addActionListener(clicked);
        	}
		}
    }
    public FanoronaGUI()
    {
    	//DO NOT CALL THIS EVER
    }
    public FanoronaGUI(OptionsGUI.OptionState options) {
    	game.setLayout(new BorderLayout());
    	topRow.setLayout(new FlowLayout());
    	try {
			board = new Board(options.oRow,options.oCol);
		} catch (BadBoardException e) {
			e.printStackTrace();
		}
    	opponent = new AI(options.AIColor, 5000);
    	playerColor = options.startColor;
    	try {
			opponent.getNewLevel();
		} catch (BadMoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	gamePieces = new DrawnPiece[Board.ROWS][Board.COLUMNS];
    	prevMoveDrawn = null;
    	userPickState = null;
    	makePieces();
    	makeAttackWithdrawButton();
    	makeInfoPanel();
    	makeMoveCancelButton();
    	makeEndTurnButton();
    	makeHelpButton();
    	makeResetButton();
    	makeGameOptionsButton();
    	makePieceListeners();
    	game.add(topRow, BorderLayout.NORTH);
        setTitle("Fanorona");
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        //if the user is black, run the AI first
        if(playerColor == Color.BLACK)
        {
        	//NEED BETTER SUPPORT FOR THIS
        }
    }
    
    class PieceListener implements ActionListener {
    	public void runAIMove(Board board) {
    		try {
	    		ArrayList<Move> AIMoves;
	    		opponent.opponentMove(board);
				AIMoves = opponent.alphaBetaSearch();
				
				System.out.println("Number of AI moves to make: " + AIMoves.size());
				
	    		for (Move m : AIMoves) {
	    			System.out.println(m);
	  //  			for(Piece.adjLoc loc: board.previousLocations) {
	  // 				System.out.print(loc);
	  // 			}
	  //  			System.out.println();
					gamePieces[m.end.row][m.end.column].pColor = opponent.getColor();
					gamePieces[m.end.row][m.end.column].repaint();
					//make the previous point turn gray
					gamePieces[m.start.row][m.start.column].pColor = Color.GRAY;
					gamePieces[m.start.row][m.start.column].repaint();
					
					ArrayList<Piece> piecesToColor;
					try {
						piecesToColor = board.move(m);
		    			/*for(Piece.adjLoc prev: board.previousLocations) {
			    			System.out.println(prev);
		    			}*/
						for(Piece p : piecesToColor) {
		    					gamePieces[p.row][p.column].pColor = Color.GRAY;
		    					gamePieces[p.row][p.column].repaint();
		    			}
					} catch (GameOverException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					prevMoveDrawn = null;
	    	    	prevMove = null;
	    			isMoveState2 = false;
	    			if(!board.chain)
	    				changeTurn();//the board will automatically change the turn, the gui needs to be updated manually though
	    	    	//re-color captured pieces
		    		//give ai/opponent move we ran
		    		//TODO ask for ai/opponent move
		    		//draw ai/opponent move
	    		}
    		} catch (BadMoveException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	public void actionPerformed(ActionEvent e) {
    		DrawnPiece gamePiece = (DrawnPiece) e.getSource();
    		Piece pieceClicked = board.theBoard[gamePiece.xLoc][gamePiece.yLoc];
    		//first check if the piece clicked is an available piece
    		if (gamePiece.pColor == playerColor || gamePiece.pColor == Color.YELLOW) {
	    		if (gamePiece.pColor == Color.YELLOW && isMoveState2) { //already selected
	    			try {
		    			Move moveToPlay = null;
						moveToPlay = new Move(prevMove, new Piece.adjLoc(pieceClicked), AttackState.NEITHER);
						moveToPlay.state = board.isAdvancing(moveToPlay); //now constructed, check move if attacking or withdrawing
						//prompt user if needed
						if (moveToPlay.state == AttackState.BOTH) {
							//TODO ask user for one, right now just set to attacking
							System.out.print("USER REACHED CHOICE OF ADVANCING OR WITHDRAWING\n");
							moveToPlay.state = AttackState.ADVANCING;
						}
						//then set the value externally
						if (board.isValidMove(moveToPlay)) {
		    				System.out.print("Move was valid\n");
			    			//make the point clicked the color of the previous point clicked
			    			gamePieces[pieceClicked.row][pieceClicked.column].pColor = playerColor;
			    			gamePieces[pieceClicked.row][pieceClicked.column].repaint();
			    			//make the previous point turn gray and removes the green selection color
			    			gamePieces[prevMoveDrawn.xLoc][prevMoveDrawn.yLoc].pColor = Color.GRAY;
			    			gamePieces[prevMoveDrawn.xLoc][prevMoveDrawn.yLoc].selected = false;
			    			gamePieces[prevMoveDrawn.xLoc][prevMoveDrawn.yLoc].repaint();
			    			//modify potential moves back to gray
			    			if(prevMove.getColor() == playerColor)
				    	    	for(adjLoc p : prevMove.adjacentLocations) {
				    				if (gamePieces[p.row][p.column].pColor == Color.YELLOW) {
				    					gamePieces[p.row][p.column].pColor = Color.GRAY;
				    					gamePieces[p.row][p.column].repaint();
				    				}
				    			}
			    	    	//returns the list of pieces that need to be painted
			    	    	ArrayList<Piece> piecesToColor = board.move(moveToPlay);
			    	    	//re-color captured pieces
			    	    	for(Piece p : piecesToColor) {
			    					gamePieces[p.row][p.column].pColor = Color.GRAY;
			    					gamePieces[p.row][p.column].repaint();
			    			}
			    			
			    	    	System.out.print("Moved to x:" + gamePiece.xLoc + " y:" + gamePiece.yLoc + 
			    	    					 " from x:" +prevMove.row + " y:" + prevMove.column + "\n");
			    	    	//set up for next turn
			    	    	prevMoveDrawn = null;
			    	    	prevMove = null;
			    	    	isMoveState2 = false;
			    			if(!board.chain) {
			    				changeTurn();//the board will automatically change the turn, the gui needs to be updated manually though
			    				runAIMove(board);
			    			}
			    			updateInfoPanel();
		    			}
					} 
	    			catch (BadMoveException e1) {
						e1.printStackTrace();
						//TODO something about the GameOverException
					}
	    			catch (GameOverException e2) {
		    			//TODO check if game over by win
		    			//TODO check if game over by win or by too many moves
					}
	    		}
	    		else if (gamePiece.pColor != Color.YELLOW && gamePiece.pColor != Color.GRAY && !isMoveState2) {
	    			try {
		    			//highlight all available moves, if not making a move, and if the piece clicked isn't gray
		    			//getValidMoves(Color), returns a list of moves, List<Move>
		    			//using the start location given by the click, iterate through List<Move> to find all available moves
		    			//using the clicked piece to start
		    			//then color those
		    			ArrayList<Move> validMoves = board.getValidMoves(board.turn);
		    			boolean ColorChanged = false;
		    			if(validMoves.isEmpty()) {
		    				System.out.println("No valid moves...");
		    			}
			    		for(Move m : validMoves) {
			    			if (m.start.equals(pieceClicked)) {
			    				gamePieces[m.start.row][m.start.column].selected = true;
			    				ColorChanged = true;
			    				gamePieces[m.end.row][m.end.column].pColor = Color.YELLOW;
			    				gamePieces[m.end.row][m.end.column].repaint();
			    			}
			    		}
			    		if(ColorChanged) {
			    			prevMoveDrawn = gamePiece;
			    			prevMove = pieceClicked;
			    			isMoveState2 = true;
			    		}
			    		System.out.print("Clicked x:" + gamePiece.xLoc + " y:" + gamePiece.yLoc + "\n");
	    			}
	    			catch (BadMoveException e1)  {
						e1.printStackTrace();
						//TODO something about the GameOverException
					}
	    		}
    		}		
    	}
    }
}