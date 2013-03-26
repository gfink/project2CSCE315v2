package eighteen;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Board {

	public static class BadMoveException extends Exception {
		public BadMoveException(String message){
			super(message);
		}
	}
	Piece[][] theBoard;
	int blacks;
	int whites;
	int moves;
	boolean chain;
	Color chainColor;
	Piece previousSpot;
	Direction previousDirection;
	ArrayList<Piece> previousLocations;
	
	public static final int ROWS = 13;
	public static final int COLUMNS = 13;
	
	public Board()
	{ 
		theBoard = new Piece[ROWS][COLUMNS];
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLUMNS; j++) {
				if(i < ROWS/2) {
					theBoard[i][j].setColor(Color.BLACK);
				}
				else {
					theBoard[i][j].setColor(Color.WHITE);
				}
			}
		}
		theBoard[ROWS/2][0].setColor(Color.BLACK);
		theBoard[ROWS/2][1].setColor(Color.WHITE);
		theBoard[ROWS/2][2].setColor(Color.BLACK);
		theBoard[ROWS/2][3].setColor(Color.WHITE);
		theBoard[ROWS/2][4].setColor(Color.GRAY);
		theBoard[ROWS/2][5].setColor(Color.BLACK);
		theBoard[ROWS/2][6].setColor(Color.WHITE);
		theBoard[ROWS/2][7].setColor(Color.BLACK);
		theBoard[ROWS/2][8].setColor(Color.WHITE);
		whites = 22;
		blacks = 22;
	}
	
	public Board(Board b) {
		theBoard = b.theBoard;
		blacks = b.blacks;
		whites = b.whites;
	}
	
	public Piece getPiece(int x, int y) {
		return theBoard[x][y];
	}
	//public void setPieceColor(int x, int y, Color color) {
	//	(theBoard[x][y]).setColor(color);
	//}
	
	public int Utility() {
		return whites - blacks;
	}
	private void resetBoard() {
		Board board = new Board();
		moves = 0;
		
		chain = false;
		//The first mover is white
		chainColor = Color.WHITE;
		//0,0 will never be a problem for a first move in the middle, which will reset it
		previousSpot = new Piece(0,0,Color.GRAY);
		//As white moves first, this will be reset and not be a problem
		previousDirection = Direction.LEFT;
		previousLocations = new ArrayList<Piece>();
	}
	
	//Returns whether the game is over
	public boolean move(Move mov) throws BadMoveException {
		if(!isValidMove(mov)) {
			throw new BadMoveException("Bad move at [" + mov.getStart().row + ", " + mov.getStart().column + "] to [" + mov.getEnd().row + ", " + mov.getEnd().column + "]");
		}
		
		boolean isChain = true;
		
		if(chain) {
			if(mov.getStart().getColor() != chainColor) {
				isChain = false;
				previousLocations = new ArrayList<Piece>();
				chainColor = get(mov.getStart());
			}
			else if(mov.getStart() != previousSpot) {
				throw new BadMoveException("Bad move at [" + mov.getStart().row + ", " + mov.getStart().column + "] -> Wrong starting spot");
			}
			else if(previousLocations.contains(mov.getEnd())) {
				throw new BadMoveException("Bad move at [" + mov.getStart().row + ", " + mov.getStart().column + "] -> That space has already been moved to in this chain");
			}
			else if(previousDirection == mov.getDirection()) {
				throw new BadMoveException("Bad move at [" + mov.getStart().row + ", " + mov.getStart().column + "] -> Same direction as previous move in chain");
			}
		}
		chain = isChain;
		
		
		board.set(mov.getEnd(), board.get(mov.getStart()));
		board.set(mov.getStart(), Pieces.EMPTY);
		
		int iterateVertical = 0;
		int iterateHorizontal = 0;
		switch(mov.getDirection()) {
		case UP:
			iterateVertical = -1;
			break;
		case UPRIGHT:
			iterateVertical = -1;
			iterateHorizontal = 1;
			break;
		case UPLEFT:
			iterateVertical = -1;
			iterateHorizontal = -1;
			break;
		case DOWN:
			iterateVertical = 1;
			break;
		case DOWNRIGHT:
			iterateVertical = 1;
			iterateHorizontal = 1;
			break;
		case DOWNLEFT:
			iterateVertical = 1;
			iterateHorizontal = -1;
			break;
		case LEFT:
			iterateHorizontal = -1;
			break;
		case RIGHT:
			iterateHorizontal = 1;
		}
		
		if(!mov.getAdvancing()) {
			iterateVertical *= -1;
			iterateHorizontal *= -1;
		}
		
		Points nextPoint;
		if(mov.getAdvancing()) {
			nextPoint = new Points(mov.getEnd());
		}
		else {
			nextPoint = new Points(mov.getStart());
		}
		
		try {
			while(true) {
				nextPoint = new Points(nextPoint.row + iterateHorizontal, nextPoint.column + iterateVertical);
				if(board.get(nextPoint) == chainColor || board.get(nextPoint) == Pieces.EMPTY) {
					break;
				}
				board.set(nextPoint, Pieces.EMPTY);
				if(chainColor == Pieces.WHITE) {
					blacks--;
				}
				else {
					whites--;
				}
			}
		}
		finally {}
		chainColor = board.get(mov.getEnd());
		previousSpot = mov.getStart();
		previousDirection = mov.getDirection();
		previousLocations.add(mov.getStart());
		
		if(!chain) {
			moves++;
		}
		if(moves >= 50 || whites == 0 || blacks == 0) {
			return true;
		}
		return false;
		
	}
	
	public static Board move(Board b, Move mov) throws BadMoveException {
		if(!isValidMove(b, mov))
			throw new BoardManager.BadMoveException("Bad move at [" + mov.getStart().row + ", " + mov.getStart().column + "] to [" + mov.getEnd().row + ", " + mov.getEnd().column + "]");
		
		Board ret = new Board(b);
		
		ret.set(mov.getEnd(), ret.get(mov.getStart()));
		ret.set(mov.getStart(), Pieces.EMPTY);
		
		return ret;
	}
	
	private boolean isValidMove(Move mov) {
		//Space is taken
		if(mov.getEnd()) != Pieces.EMPTY) {
			return false;
		}
		if(Points.isValidSpace(mov.getStart().row, mov.getStart().column) && Points.isValidSpace(mov.getEnd().row, mov.getEnd().column) && !mov.getStart().equals(mov.getEnd())) {
			//Checks for Diagonal moves
			if(mov.getStart().adjacentLocations().contains(mov.getEnd())) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isValidMove(Move mov) {
		//Space is taken
		if(mov.getEnd() != Pieces.EMPTY) {
			return false;
		}
		if(Points.isValidSpace(mov.getStart().row, mov.getStart().column) && Points.isValidSpace(mov.getEnd().row, mov.getEnd().column) && !mov.getStart().equals(mov.getEnd())) {
			//Checks for Diagonal moves
			if(mov.getStart().adjacentLocations().contains(mov.getEnd())) {
				return true;
			}
		}
		return false;
	}
	/* Gets all valid moves for a specific point.
	 * Doesn't take into account the state of the entire board.
	 * (e.g. If a capture is possible at another location on the board,
	 *       this can still return a paika move)
	 */
	public List<Move> getValidMoves(x,y) throws BadMoveException {
		ArrayList<Move> capture = new ArrayList<Move>();
		ArrayList<Move> paika = new ArrayList<Move>();
		Piece piece = get(x,y);
		
		// In case the point doesn't have a piece there
		if(piece.isEmpty())
			return capture;
		
		// Local move to get the direction of each potential move
		Move move = new Move();
		move.setStart(start);
		for(Piece end: start.adjacentLocations()) {
			// Updates the direction
			move.setEnd(end);
			move.updateDirection();
			int rowAdv = 0;
			int rowWd = 0;
			int colAdv = 0;
			int colWd = 0;
			if(Board.isValidMove(this, move)) {
				Piece advance;
				Piece withdraw;
				// Sets the locations of the pieces for an advance and a withdraw
				switch(move.getDirection()) {
				case UP:
					rowAdv = -2;
					colAdv = 0;
					rowWd = 1;
					colWd = 0;
					break;
				case UPRIGHT:
					rowAdv = -2;
					colAdv = 2;
					rowWd = 1;
					colWd = -1;
					break;
				case UPLEFT:
					rowAdv = -2;
					colAdv = -2;
					rowWd = 1;
					colWd = 1;
					break;
				case DOWN:
					rowAdv = 2;
					colAdv = 0;
					rowWd = -1;
					colWd = 0;
					break;
				case DOWNRIGHT:
					rowAdv = 2;
					colAdv = 2;
					rowWd = -1;
					colWd = -1;
					break;
				case DOWNLEFT:
					rowAdv = 2;
					colAdv = -2;
					rowWd = -1;
					colWd = 1;
					break;
				case LEFT:
					rowAdv = 0;
					colAdv = -2;
					rowWd = 0;
					colWd = 1;
					break;
				case RIGHT:
					rowAdv = 0;
					colAdv = 2;
					rowWd = 0;
					colWd = -1;
					break;
				}
				// Accounts for if an advance AND a withdraw are possible
				if(Piece.isValidSpace(start.row + rowAdv, start.column + colAdv) && Piece.isValidSpace(start.row + rowWd, start.column + colWd)) {
					advance = theBoard[start.row + rowAdv][start.column + colAdv];
					withdraw = theBoard[start.row + rowWd][start.column + colWd];
					if(advance.getColor() != move.getColor() && !advance.isEmpty()) {
						Move newMove = new Move(start, end, true, true);
						capture.add(newMove);
					}
					if(withdraw.getColor() != move.getColor() && !withdraw.isEmpty()) {
						Move newMove = new Move(start, end, false, true);
						capture.add(newMove);
					}
				}
				// Just an advance is possible
				else if(Piece.isValidSpace(start.row + rowAdv, start.column + colAdv)) {
					advance = theBoard[start.row + rowAdv][start.column + colAdv];
					if(advance.getColor() != move.getColor() && !advance.isEmpty()) {
						Move newMove = new Move(start, end, false, true);
						capture.add(newMove);
					}
				}
				// Just a withdraw is possible
				else if(Piece.isValidSpace(start.row + rowWd, start.column + colWd)) {
					withdraw = theBoard[start.row + rowWd][start.column + colWd];
					if(withdraw.getColor() != move.getColor() && !withdraw.isEmpty()) {
						Move newMove = new Move(start, end, true, true);
						capture.add(newMove);
					}
				}
				// If no captures have been found, a paika is possible
				else if(capture.isEmpty()) {
						Move newMove = new Move(start, end, false, false);
						paika.add(newMove);
				}
			}
		}
		if(!capture.isEmpty())
			return capture;
		else
			return paika;
	}
	
	// Gets valid moves for a specific color on the entire board
	public List<Move> getValidMoves(Color color) throws BadMoveException {
		ArrayList<Move> capture = new ArrayList<Move>();
		ArrayList<Move> paika = new ArrayList<Move>();
		Piece curLocation = new Piece(0,0);
		
		for(int x=0; x < Board.ROWS; x++) {
			for(int y=0; y < Board.COLUMNS; y++) {
				curLocation.row = x;
				curLocation.column = y;
				Move move = new Move();
				if(get(curLocation) == color) {
					move.setStart(curLocation);
					move.setColor(color);
					for(Piece end: curLocation.adjacentLocations()) {
						move.setEnd(end);
						move.updateDirection();
						int rowAdv = 0;
						int rowWd = 0;
						int colAdv = 0;
						int colWd = 0;
						if(Board.isValidMove(this, move)) {
							Piece advance;
							Piece withdraw;
							switch(move.getDirection()) {
							case UP:
								rowAdv = -2;
								colAdv = 0;
								rowWd = 1;
								colWd = 0;
								break;
							case UPRIGHT:
								rowAdv = -2;
								colAdv = 2;
								rowWd = 1;
								colWd = -1;
								break;
							case UPLEFT:
								rowAdv = -2;
								colAdv = -2;
								rowWd = 1;
								colWd = 1;
								break;
							case DOWN:
								rowAdv = 2;
								colAdv = 0;
								rowWd = -1;
								colWd = 0;
								break;
							case DOWNRIGHT:
								rowAdv = 2;
								colAdv = 2;
								rowWd = -1;
								colWd = -1;
								break;
							case DOWNLEFT:
								rowAdv = 2;
								colAdv = -2;
								rowWd = -1;
								colWd = 1;
								break;
							case LEFT:
								rowAdv = 0;
								colAdv = -2;
								rowWd = 0;
								colWd = 1;
								break;
							case RIGHT:
								rowAdv = 0;
								colAdv = 2;
								rowWd = 0;
								colWd = -1;
								break;
							}
							if(Piece.isValidSpace(curLocation.row + rowAdv, curLocation.column + colAdv) && Piece.isValidSpace(curLocation.row + rowWd, curLocation.column + colWd)) {
								advance = theBoard[curLocation.row + rowAdv][curLocation.column + colAdv];
								withdraw = theBoard[curLocation.row + rowWd][curLocation.column + colWd];
								if(advance.getColor() != move.getColor() && !advance.isEmpty()) {
									Move newMove = new Move(curLocation, end, true, true);
									capture.add(newMove);
								}
								if(withdraw.getColor() != move.getColor() && !withdraw.isEmpty()) {
									Move newMove = new Move(curLocation, end, false, true);
									capture.add(newMove);
								}
							}
							else if(Piece.isValidSpace(curLocation.row + rowAdv, curLocation.column + colAdv)) {
								advance = theBoard[curLocation.row + rowAdv][curLocation.column + colAdv];
								if(advance.getColor() != move.getColor() && !advance.isEmpty()) {
									Move newMove = new Move(curLocation, end, false, true);
									capture.add(newMove);
								}
							}
							else if(Piece.isValidSpace(curLocation.row + rowWd, curLocation.column + colWd)) {
								withdraw = theBoard[curLocation.row + rowWd][curLocation.column + colWd];
								if(withdraw.getColor() != move.getColor() && !withdraw.isEmpty()) {
									Move newMove = new Move(curLocation, end, true, true);
									capture.add(newMove);
								}
							}
							else if(capture.isEmpty()) {
									Move newMove = new Move(curLocation, end, false, false);
									paika.add(newMove);
							}
						}
					}
				}
			}
		}
		if(!capture.isEmpty())
			return capture;
		else
			return paika;
	}
}
