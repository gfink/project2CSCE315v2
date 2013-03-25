package eighteen;

public class Tree {
	private TreeNode root;
	
	public Tree() {
		root = new TreeNode();
	}
	
	public TreeNode getRoot() {
		return this.root;
	}
	
	public void setRoot(TreeNode newRoot) {
		this.root = newRoot;
	}
}
