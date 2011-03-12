package lebahn.aaron;

import java.util.ArrayList;
import java.util.TreeMap;

public class TttAi
{
	private TreeMap<int[], ArrayList<int[]>> boardMap;
	public TttAi() {
		boardMap = new TreeMap<int[], ArrayList<int[]>>();
	}
	public void generateTree() {
		int [] board = new int[9];
		for(int i=0; i<9; i++) {
			board[i] = TicTacToe.EMPTY;
		}
		addNext(board, TicTacToe.X);
	}
	private void addNext(int [] board, int turn) {
		for(int i=0; i<9; i++) {
			if(board[i]==TicTacToe.EMPTY) {
				int [] temp = board;
				temp[i]=turn;
				temp=transform(temp);
				if(boardMap.containsKey(temp)) {
					return;	//map
				}
				boardMap.put(temp, new ArrayList<int[]>());
				boardMap.get(board).add(temp);
				if(TicTacToe.evaluateState(board)==TicTacToe.EMPTY) {
					addNext(temp,-turn);
				}
			}
		}
	}
	private void trimTree() {
		int [] board = new int[9];
		for(int i=0; i<9; i++) {
			board[i] = TicTacToe.EMPTY;
		}
		trimTree(board, TicTacToe.X);
	}
	private int [] trimTree(int [] board, int turn) {
		int evaluation=TicTacToe.evaluateState(board);
		if(evaluation!=TicTacToe.EMPTY) {
			if(evaluation==TicTacToe.TIE) {
				int [] result = new int[2];
				result[0] = result[1] = 0;
				return result;
			}
		}
		return new int[2];
	}
	private int [] transform(int [] board) {
		if(boardMap.containsKey(board)) {
			return board;
		}
		int [] transformation=new int[9];
		//flip horizontally
		for(int i=0;i<9;i++) {
			transformation[i]=board[2-(i%3)+i/3];
		}
		if(boardMap.containsKey(transformation)) {
			return transformation;
		}
		//flip vertically
		for(int i=0;i<9;i++) {
			transformation[i]=board[3*(2-(i/3))+i%3];
		}
		if(boardMap.containsKey(transformation)) {
			return transformation;
		}
		//rotate right
		for(int i=0;i<9;i++) {
			transformation[i]=board[3*(i%3)+2-i/3];
		}
		if(boardMap.containsKey(transformation)) {
			return transformation;
		}
		//rotate left
		for(int i=0;i<9;i++) {
			transformation[i]=board[3*(2-(i%3))+i/3];
		}
		if(boardMap.containsKey(transformation)) {
			return transformation;
		}
		//rotate 180
		for(int i=0;i<9;i++) {
			transformation[i]=board[8-i];
		}
		if(boardMap.containsKey(transformation)) {
			return transformation;
		}
		//no transformations in map, return first instance
		return board;
	}
}
