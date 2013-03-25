package eighteen;

import eighteen.BoardManager.BadMoveException;

public class Move {
	private Points start;
	private Points end;
	private Pieces color;
	private Direction direction;
	private boolean advancing;
	private boolean attacking;
	
	public Move() {}
	
	public Move(Points start, Points end, Pieces color, boolean isAdvancing, boolean isAttacking) throws BadMoveException {
		this.start = start;
		this.end = end;
		this.color = color;
		advancing = isAdvancing;
		attacking = isAttacking;
		updateDirection();
	}
	
	public void updateDirection() throws BadMoveException {
		if(start.equals(end)) {
			throw new BoardManager.BadMoveException("Bad move initialized");
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
	
	public void setStart(Points start) {
		this.start = start;
	}
	
	public Points getStart() {
		return start;
	}
	
	public void setEnd(Points end) {
		this.end = end;
	}
	
	public Points getEnd() {
		return end;
	}
	
	public void setColor(Pieces color) {
		this.color = color;
	}
	
	public Pieces getColor() {
		return color;
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
