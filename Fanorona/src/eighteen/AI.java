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
	
	public Color getColor() {
		return myColor;
	}
	
	public void addLevel(TreeNode node) throws BadMoveException {
		if(!node.hasChildren()) {
			Color color = Board.oppositeColor(node.board.chainColor);
			ArrayList<Move> validMoves = node.board.getValidMoves(color);
			System.out.println("Found " + validMoves.size() + " moves.");
			for(Move move: validMoves) {
				Board newBoard = new Board(node.board); 
				try {
					newBoard.move(move);
				} catch (GameOverException e) {
					e.printStackTrace();
				}
				TreeNode newChild = new TreeNode(newBoard);
				node.addChild(newChild);
				if(move.getState() == AttackState.ADVANCING || move.getState() == AttackState.WITHDRAWING)
					chainCheck(newChild);
				
			}
		}
		else
			for(TreeNode child: node.getChildren())
				addLevel(child);
		displayChoices();
	}
	
	void displayChoices() {
		for(TreeNode child: minMaxTree.getRoot().getChildren()) {
		}
	}
	
	public void chainCheck(TreeNode child) throws BadMoveException {
		Piece.adjLoc previousSpot = child.board.previousSpot;
		ArrayList<Move> chainMoves = child.board.getValidChainMoves(previousSpot);
		System.out.println("Found " + chainMoves.size() + " chain possiblities.");
		for(Move move: chainMoves) {
			Board newBoard = new Board(child.board);
			try {
				newBoard.move(move);
			} catch (GameOverException e) {
				e.printStackTrace();
			}
			TreeNode newSibling = new TreeNode(newBoard);
			child.getParent().addChild(newSibling);
			chainCheck(newSibling);
		}
	}
	
	public void opponentMove(Board newBoard) {
		TreeNode root = minMaxTree.getRoot();
		if(!root.hasChildren()) {
			System.out.println("AI had no children in tree.");
			TreeNode newRoot = new TreeNode(newBoard);
			minMaxTree.setRoot(newRoot);
		}
		else {
			System.out.println("AI had children in tree");
			for(TreeNode child: root.getChildren()) {
				if(child.board.equals(newBoard)) {
					System.out.println("AI found the move made.");
					minMaxTree.setRoot(child);
					break;
				}
			}
		}
	}
	
	public ArrayList<Move> alphaBetaSearch() throws BadMoveException {
		TreeNode root = minMaxTree.getRoot();
		if(!root.hasChildren()) {
			System.out.println("Adding Children");
			getNewLevel();
		}
		double value = 0;
		if (root.board.chainColor != myColor)
			if(myColor == Color.WHITE)
				value = maxValue(root, -999999, 999999);
			else
				value = minValue(root, -999999, 999999);
		else
			if(myColor == Color.WHITE)
				value = minValue(root, -999999, 999999);
			else
				value = maxValue(root, -999999, 999999);
//		System.out.println("Value found: " + value);
		for(TreeNode child: root.getChildren()) {
//			System.out.println("Child traversal value: " + child.traversalValue);
			if(child.traversalValue == value) {
				minMaxTree.setRoot(child);
				return child.getMoves();
			}
		}
		System.out.println("No move found");
		return new ArrayList<Move>();
	}
	
	public double maxValue(TreeNode state, double alpha, double beta) {
		if(!state.hasChildren())
			return state.value;
		
		for(TreeNode child: state.getChildren()) {
			alpha = Math.max(alpha,  minValue(child, alpha, beta));
			child.traversalValue = alpha;
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
			child.traversalValue = beta;
			if(beta <= alpha) {
				// Alpha cut-off
				break;
			}
		}
		return beta;
	}
}