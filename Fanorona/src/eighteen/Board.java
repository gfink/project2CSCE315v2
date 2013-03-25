package eighteen;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import eighteen.BoardManager.BadMoveException;

public class Board {

	Piece[][] theBoard;
	int blacks;
	int whites;
	
	public Board()
	{
		theBoard = new Piece[BoardManager.ROWS][BoardManager.COLUMNS];
		for(int i = 0; i < BoardManager.ROWS; i++) {
			for(int j = 0; j < BoardManager.COLUMNS; j++) {
				if(i < BoardManager.ROWS/2) {
					theBoard[i][j].pieceColor=Color.BLACK;
				}
				else {
					theBoard[i][j].pieceColor=Color.WHITE;
				}
			}
		}
		theBoard[BoardManager.ROWS/2][0].pieceColor=Color.BLACK;
		theBoard[BoardManager.ROWS/2][1].pieceColor=Color.WHITE;
		theBoard[BoardManager.ROWS/2][2].pieceColor=Color.BLACK;
		theBoard[BoardManager.ROWS/2][3].pieceColor=Color.WHITE;
		theBoard[BoardManager.ROWS/2][4].pieceColor=Color.GRAY;
		theBoard[BoardManager.ROWS/2][5].pieceColor=Color.BLACK;
		theBoard[BoardManager.ROWS/2][6].pieceColor=Color.WHITE;
		theBoard[BoardManager.ROWS/2][7].pieceColor=Color.BLACK;
		theBoard[BoardManager.ROWS/2][8].pieceColor=Color.WHITE;
		whites = 22;
		blacks = 22;
	}
	
	public Board(Board b) {
		theBoard = b.theBoard;
		blacks = b.blacks;
		whites = b.whites;
	}
	
	public Piece get(int x, int y) {
		return theBoard[x][y];
	}
	
	public void set(int x, int y, Color color) {
		theBoard[x][y].pieceColor=color;
	}
	
	public int Utility() {
		return whites - blacks;
	}
	
	/* Gets all valid moves for a specific point.
	 * Doesn't take into account the state of the entire board.
	 * (e.g. If a capture is possible at another location on the board,
	 *       this can still return a paika move)
	 */
	/*public List<Move> getValidMoves(x,y) throws BadMoveException {
		ArrayList<Move> capture = new ArrayList<Move>();
		ArrayList<Move> paika = new ArrayList<Move>();
		Pieces color = get(x,y);
		
		// In case the point doesn't have a piece there
		if(color == Pieces.EMPTY)
			return capture;
		
		// Local move to get the direction of each potential move
		Move move = new Move();
		move.setStart(start);
		move.setColor(color);
		for(Points end: start.adjacentLocations()) {
			// Updates the direction
			move.setEnd(end);
			move.updateDirection();
			int rowAdv = 0;
			int rowWd = 0;
			int colAdv = 0;
			int colWd = 0;
			if(BoardManager.isValidMove(this, move)) {
				Pieces advance;
				Pieces withdraw;
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
				if(Points.isValidSpace(start.row + rowAdv, start.column + colAdv) && Points.isValidSpace(start.row + rowWd, start.column + colWd)) {
					advance = theBoard[start.row + rowAdv][start.column + colAdv];
					withdraw = theBoard[start.row + rowWd][start.column + colWd];
					if(advance != move.getColor() && advance != Pieces.EMPTY) {
						Move newMove = new Move(start, end, color, true, true);
						capture.add(newMove);
					}
					if(withdraw != move.getColor() && withdraw != Pieces.EMPTY) {
						Move newMove = new Move(start, end, color, false, true);
						capture.add(newMove);
					}
				}
				// Just an advance is possible
				else if(Points.isValidSpace(start.row + rowAdv, start.column + colAdv)) {
					advance = theBoard[start.row + rowAdv][start.column + colAdv];
					if(advance != move.getColor() && advance != Pieces.EMPTY) {
						Move newMove = new Move(start, end, color, false, true);
						capture.add(newMove);
					}
				}
				// Just a withdraw is possible
				else if(Points.isValidSpace(start.row + rowWd, start.column + colWd)) {
					withdraw = theBoard[start.row + rowWd][start.column + colWd];
					if(withdraw != move.getColor() && withdraw != Pieces.EMPTY) {
						Move newMove = new Move(start, end, color, true, true);
						capture.add(newMove);
					}
				}
				// If no captures have been found, a paika is possible
				else if(capture.isEmpty()) {
						Move newMove = new Move(start, end, color, false, false);
						paika.add(newMove);
				}
			}
		}
		if(!capture.isEmpty())
			return capture;
		else
			return paika;
	}
	*/
	// Gets valid moves for a specific color on the entire board
	/*public List<Move> getValidMoves(Color color) throws BadMoveException {
		ArrayList<Move> capture = new ArrayList<Move>();
		ArrayList<Move> paika = new ArrayList<Move>();
		Points curLocation = new Points(0,0);
		
		for(int x=0; x < BoardManager.ROWS; x++) {
			for(int y=0; y < BoardManager.COLUMNS; y++) {
				curLocation.row = x;
				curLocation.column = y;
				Move move = new Move();
				if(get(curLocation) == color) {
					move.setStart(curLocation);
					move.setColor(color);
					for(Points end: curLocation.adjacentLocations()) {
						move.setEnd(end);
						move.updateDirection();
						int rowAdv = 0;
						int rowWd = 0;
						int colAdv = 0;
						int colWd = 0;
						if(BoardManager.isValidMove(this, move)) {
							Pieces advance;
							Pieces withdraw;
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
							if(Points.isValidSpace(curLocation.row + rowAdv, curLocation.column + colAdv) && Points.isValidSpace(curLocation.row + rowWd, curLocation.column + colWd)) {
								advance = theBoard[curLocation.row + rowAdv][curLocation.column + colAdv];
								withdraw = theBoard[curLocation.row + rowWd][curLocation.column + colWd];
								if(advance != move.getColor() && advance != Pieces.EMPTY) {
									Move newMove = new Move(curLocation, end, color, true, true);
									capture.add(newMove);
								}
								if(withdraw != move.getColor() && withdraw != Pieces.EMPTY) {
									Move newMove = new Move(curLocation, end, color, false, true);
									capture.add(newMove);
								}
							}
							else if(Points.isValidSpace(curLocation.row + rowAdv, curLocation.column + colAdv)) {
								advance = theBoard[curLocation.row + rowAdv][curLocation.column + colAdv];
								if(advance != move.getColor() && advance != Pieces.EMPTY) {
									Move newMove = new Move(curLocation, end, color, false, true);
									capture.add(newMove);
								}
							}
							else if(Points.isValidSpace(curLocation.row + rowWd, curLocation.column + colWd)) {
								withdraw = theBoard[curLocation.row + rowWd][curLocation.column + colWd];
								if(withdraw != move.getColor() && withdraw != Pieces.EMPTY) {
									Move newMove = new Move(curLocation, end, color, true, true);
									capture.add(newMove);
								}
							}
							else if(capture.isEmpty()) {
									Move newMove = new Move(curLocation, end, color, false, false);
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
	}*/
}
