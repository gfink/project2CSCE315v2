package eighteen;

import java.awt.Color;

import eighteen.Board.BadMoveException;

public class Move {
	public Piece start;
	public Piece.adjLoc end;
	private Direction direction;
	AttackState state;
	public Move() {}
	
	public Move(Piece start, Piece.adjLoc end, AttackState state) throws BadMoveException {
		this.start = start;
		this.end = end;
		this.state = state;
		updateDirection();
	}
	
	/*
	 *  For use in finding valid moves, updates the direction of a possible move
	 *  after changing the start/end position(s)
	 */
	public void updateDirection() throws BadMoveException {
		if(start.row == end.row && start.column == end.column) {
			throw new Board.BadMoveException("Bad move initialized");
		}
		if(start.column == end.column) {
			if(start.row > end.row) {
				direction = Direction.UP;
			}
			else {
				direction = Direction.DOWN;
			}
		}
		else if(start.row == end.row) {
			if(start.column > end.column) {
				direction = Direction.LEFT;
			}
			else {
				direction = Direction.RIGHT;
			}
		}
		else {
			if(start.row < end.row) {
				if(start.column < end.column) {
					direction = Direction.DOWNRIGHT;
				}
				else {
					direction = Direction.DOWNLEFT;
				}
			}
			else {
				if(start.column < end.column) {
					direction = Direction.UPRIGHT;
				}
				else {
					direction = Direction.UPLEFT;
				}
			}
		}
	}
	
	public void setStart(Piece start) {
		this.start = start;
	}
	
	public Piece getStart() {
		return start;
	}
	
	public void setEnd(Piece.adjLoc end) {
		this.end = end;
	}
	
	public Piece.adjLoc getEnd() {
		return end;
	}
	
	public Color getColor() {
		return start.getColor();
	}
	public Direction getDirection() {
		return direction;
	}
	
	public AttackState getState() {
		return state;
	}
	
	public void setState(AttackState state) {
		this.state = state;
	}
	
	public boolean equals(Move moveToCompare) {
		if(!start.equals(moveToCompare.getStart()))
			return false;
		if(!end.equals(moveToCompare.getEnd()))
			return false;
		if(state != moveToCompare.getState())
			return false;
		if(direction != moveToCompare.getDirection())
			return false;
		return true;
	}
	
	public String toString() {
		String type = "";
		switch(state) {
		case ADVANCING:
			type = "A";
			break;
		case WITHDRAWING:
			type = "W";
			break;
		case NEITHER:
			type = "P";
			break;
		case SACRIFICE:
			type = "S";
			break;
		}
		switch(state) {
		case ADVANCING: case WITHDRAWING: case NEITHER:
			return type + " " + (start.column + 1) + " " + (start.row + 1) + " " + (end.column + 1) + " " + (end.column + 1);
		default:
			return 	type + " " + (start.column + 1) + " " + (start.row + 1);
		}
	}
}
