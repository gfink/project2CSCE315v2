package eighteen;
import java.awt.Color;
import java.util.ArrayList;

public class Piece {
	public class adjLoc{//adjacent location
		int row;
		int column;
		public adjLoc(int x, int y)
		{
			row = x;
			column = y;
		}
	}
	
	public int row;
	public int column;
	public Color pieceColor;
	ArrayList<adjLoc> adjacentLocations = new ArrayList<adjLoc>();
	public Piece(int x, int y) {
		row = x;
		column = y;
		if(!isValidSpace()) {
			throw new IndexOutOfBoundsException();
		}
		adjacentLocations();
	}
	
	public Piece(Piece a) {
		this.row = a.row;
		this.column = a.column;
	}
	
	public boolean isValidSpace() {
		if(row < BoardManager.ROWS && row >= 0 && column < BoardManager.COLUMNS && column >= 0)
			return true;
		return false;
	}
	
	public static boolean isValidSpace(int s_row, int s_column) {
		if(s_row < BoardManager.ROWS && s_row >= 0 && s_column < BoardManager.COLUMNS && s_column >= 0)
			return true;
		return false;
	}
	
	public void adjacentLocations() {
		if(row + column % 2 == 0) {
			if(isValidSpace(row - 1, column - 1))
				adjacentLocations.add(new adjLoc(row - 1, column - 1));
			if(isValidSpace(row - 1, column + 1))
				adjacentLocations.add(new adjLoc(row - 1, column + 1));
			if(isValidSpace(row + 1, column - 1))
				adjacentLocations.add(new adjLoc(row + 1, column - 1));
			if(isValidSpace(row + 1, column + 1))
				adjacentLocations.add(new adjLoc(row + 1, column + 1));
		}
		if(isValidSpace(row - 1, column))
			adjacentLocations.add(new adjLoc(row - 1, column));
		if(isValidSpace(row + 1, column))
			adjacentLocations.add(new adjLoc(row + 1, column));
		if(isValidSpace(row, column - 1))
			adjacentLocations.add(new adjLoc(row, column - 1));
		if(isValidSpace(row, column + 1))
			adjacentLocations.add(new adjLoc(row, column + 1));
	}
	
	public boolean equals(Points a)
	{
		if(a.row == row && a.column == column)
			return true;
		return false;
	}
}
