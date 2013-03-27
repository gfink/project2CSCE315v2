package eighteen;

import eighteen.Board.BadMoveException;
import eighteen.Board.GameOverException;

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
			Color color = Board.oppositeColor(node.getMove().get(0).getColor());
			List<Move> validMoves = node.board.getValidMoves(color);
			for(Move move: validMoves) {
				Board tempBoard = new Board(node.board); 
				try {
					tempBoard.move(move);
				} catch (GameOverException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				TreeNode newChild = new TreeNode(tempBoard);
				node.addChild(newChild);
			}
		}
		else
			for(TreeNode child: node.getChildren())
				addLevel(child);
	}
	
	public void opponentMove(Move move) {
		TreeNode root = minMaxTree.getRoot();
		if(!root.hasChildren()) {
			Board newBoard = root.board;
			try {
				ArrayList<Piece> temp = newBoard.move(move);
				TreeNode newRoot = new TreeNode(newBoard);
			} catch (BadMoveException e) {
				e.printStackTrace();
			} catch (GameOverException e) {
				e.printStackTrace();
			}
		}
		else {
			for(TreeNode child: root.getChildren()) {
				if(child.getMove().equals(move)) {
					minMaxTree.setRoot(child);
					break;
				}
			}
		}
	}
	
	public ArrayList<Move> alphaBetaSearch() throws BadMoveException {
		TreeNode root = minMaxTree.getRoot();
		if(!root.hasChildren())
			getNewLevel();
		double value;
		if (root.getMove().get(0).getColor() != myColor)
			value = maxValue(root, -999999, 999999);
		else
			value = minValue(root, -999999, 999999);
		for(TreeNode child: root.getChildren()) {
			if(child.traversalValue == value)
				return child.getMove();
		}
		return new ArrayList<Move>();
	}
	
	public double maxValue(TreeNode state, double alpha, double beta) {
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
	
	public double minValue(TreeNode state, double alpha, double beta) {
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