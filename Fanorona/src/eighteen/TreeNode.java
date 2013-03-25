package eighteen;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
	private Move move;
	private List<TreeNode> children;
	private TreeNode parent;
	Board board;
	int value;
	int traversalValue;

	public TreeNode() {
		children = new ArrayList<TreeNode>();
		board = new Board();
		value = 0;
		traversalValue = 0;
	}
	
	public TreeNode(TreeNode node) {
		this.move = node.move;
		this.children = node.children;
		this.parent = node.parent;
		this.board = node.board;
		this.value = node.value;
		this.traversalValue = node.traversalValue;
	}
	
	public TreeNode(Move move, Board board) {
		this();
		setMove(move);
		setBoard(board);
		value = board.Utility();
	}
	
	public void setMove(Move move) {
		this.move = move;
	}
	
	public Move getMove() {
		return move;
	}
	
	public void setBoard(Board board) {
		this.board = board;
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
