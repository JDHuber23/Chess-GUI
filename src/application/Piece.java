package application;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public abstract class Piece extends StackPane {
	int xPos; //in grid pane
	int yPos;
	String color;
	boolean pinned;
	King myKing;
	
	public Piece(String color, int x, int y) {
		this.color = color;
		xPos = x;
		yPos = y;
	}
	

	/* 
	 * @param board to determine available moves
	 * @return returns an array of coordinates for available moves
	 */
	abstract ArrayList<int[]> getMoves(Piece[][] board);
	
	public static Piece[][] readBoard(ArrayList<Piece> pieces) {
		Piece[][] board = new Piece[8][8];
		
		for (Piece p: pieces) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (p.getX() == j && p.getY() == i) {
						board[i][j] = p;
					}
				}
			}
		}
		return board;
	}
	
	public static ArrayList<Piece> readPieces(Piece[][] board) {
		ArrayList<Piece> pieces = new ArrayList<>();
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] instanceof Piece) pieces.add(board[i][j]);
			}
		}
		return pieces;
	}
	
	public void isSelected(boolean yes) {
		if (yes) setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
		else setBackground(null);
	}
	
	public void setX(int x) {
		xPos = x;
	}
	
	public void setY(int y) {
		yPos = y;
	}
	
	public int getX() {
		return xPos;
	}
	
	public int getY() {
		return yPos;
	}
	
	public String getColor() {
		return color;
	}

}
