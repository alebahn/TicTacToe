package lebahn.aaron;

import java.util.ArrayList;
//testing git
public class GameTree {
	private int [] board;
	private ArrayList<GameTree> children;
	public GameTree() {
		board = new int[9];
		children = new ArrayList<GameTree>();
	}
	public GameTree(int [] board) {
		this.board = board;
		children = new ArrayList<GameTree>();
	}
	public GameTree(int [] board, ArrayList<GameTree> children) {
		this.board = board;
		this.children = new ArrayList<GameTree>(children);
	}
	public int[] getBoard() {
		return board;
	}
	public void setBoard(int[] board) {
		this.board = board;
	}
	public ArrayList<GameTree> getChildren() {
		return children;
	}
	public void addChild(GameTree child) {
		children.add(child);
	}
}
