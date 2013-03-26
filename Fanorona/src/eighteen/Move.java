package eighteen;

import java.awt.Color;

import eighteen.Board.BadMoveException;

public class Move {
	private Piece start;
	private Piece end;
	private Direction direction;
	private boolean advancing;
	private boolean attacking;
	
	public Move() {}
	
	public Move(Piece start, Piece end, boolean isAdvancing, boolean isAttacking) throws BadMoveException {
		this.start = start;
		this.end = end;
		advancing = isAdvancing;
		attacking = isAttacking;
		updateDirection();
	}
	
	public void updateDirection() throws BadMoveException {
		if(start.equals(end)) {
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
	
	public void setEnd(Piece end) {
		this.end = end;
	}
	
	public Piece getEnd() {
		return end;
	}
	
	public Color getColor() {
		return start.getColor();
	}
	public Direction getDirection() {
		return direction;
	}
	
	public void setAdvancing(boolean adv) {
		advancing = adv;
	}
	
	public boolean getAdvancing() {
		return advancing;
	}
}
