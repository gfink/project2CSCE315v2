package eighteen;

import eighteen.Board.BadMoveException;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class AI {
	// Will need to implement Thread at some point in the future
	
	private Tree minMaxTree;
	private Color myColor;
	private int levels;
	
	public AI(Color color) {
		minMaxTree = new Tree();
		myColor = color;
		levels = 1;
	}
	
	public void getNewLevel() throws BadMoveException {
		addLevel(minMaxTree.getRoot());
		levels++;
	}
	
	public void addLevel(TreeNode node) throws BadMoveException {
		if(!node.hasChildren()) {
			Color color;
			if(node.getMove().getColor() == Color.WHITE)
				color = Color.BLACK;
			else
				color = Color.WHITE;
			List<Move> validMoves = node.board.getValidMoves(color);
			for(Move move: validMoves) {
				Board newBoard = Board.move(node.board, move);
				TreeNode newChild = new TreeNode(move, newBoard);
				node.addChild(newChild);
			}
		}
		else
			for(TreeNode child: node.getChildren())
				addLevel(child);
	}
	
	private Move alphaBetaSearch() throws BadMoveException {
		TreeNode root = minMaxTree.getRoot();
		if(!root.hasChildren())
			getNewLevel();
		int value;
		if (root.getMove().getColor() != myColor)
			value = maxValue(root, -999999, 999999);
		else
			value = minValue(root, -999999, 999999);
		for(TreeNode child: root.getChildren()) {
			if(child.traversalValue == value)
				return child.getMove();
		}
		return new Move();
	}
	
	private int maxValue(TreeNode state, int alpha, int beta) {
		if(!state.hasChildren())
			return state.value;
		
		for(TreeNode child: state.getChildren()) {
			alpha = Math.max(alpha,  minValue(child, alpha, beta));
			if(beta <= alpha) {
				// Beta cut-off
				break;
			}
		}
		return alpha;
	}
	
	private int minValue(TreeNode state, int alpha, int beta) {
		if(!state.hasChildren())
			return state.value;
		
		for(TreeNode child: state.getChildren()) {
			beta = Math.min(beta, maxValue(child, alpha, beta));
			if(beta <= alpha) {
				// Alpha cut-off
				return state.traversalValue;
			}
		}
		return beta;
	}
}