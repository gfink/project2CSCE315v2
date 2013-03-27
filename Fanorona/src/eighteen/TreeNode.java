package eighteen;

import java.util.ArrayList;
import java.util.List;

import eighteen.Board.BadBoardException;

public class TreeNode {
	private List<TreeNode> children;
	private TreeNode parent;
	Board board;
	double value;
	double traversalValue;

	public TreeNode() {
		children = new ArrayList<TreeNode>();
		try {
			board = new Board();
		} catch (BadBoardException e) {
			//This should never happen
			e.printStackTrace();
		}
		value = 0;
		traversalValue = 0;
	}
	
	public TreeNode(TreeNode node) {
		this.children = node.children;
		this.parent = node.parent;
		this.board = new Board(node.board);
		this.value = node.value;
		this.traversalValue = node.traversalValue;
	}
	
	public TreeNode(Board board) {
		this();
		setBoard(board);
		value = board.Utility();
	}
	
	public ArrayList<Move> getMoves() {
		return board.chainMoves;
	}
	
	public void setBoard(Board board) {
		this.board = new Board(board);
	}
	
	public void addChild(TreeNode child) {
		children.add(child);
		child.setParent(this);
	}
	
	public List<TreeNode> getChildren() {
		return children;
	}
	
	public int getNumberOfChildren() {
		return children.size();
	}
	
	public boolean hasChildren() {
		return (getNumberOfChildren() > 0);
	}
	
	public void setParent(TreeNode parent) {
		this.parent = parent;
	}
	
	public TreeNode getParent() {
		return parent;
	}
}
