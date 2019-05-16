package application;

import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.Icon;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Knight extends Piece {
	
	private ImageView image;
	private Label imageLabel;
	
	public Knight(String color, int x, int y, King myKing) {
		super(color, x, y);
		this.myKing = myKing;
		
		String path = "/images/pieces/" + color + " Knight.png";
		image = new ImageView(new Image(Icon.class.getResource(path).toExternalForm()));
		imageLabel = new Label();
		image.setFitHeight(100);
		image.setFitWidth(100);
		imageLabel.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		imageLabel.setGraphic(image);
		imageLabel.setTooltip(new Tooltip(color + " Knight"));
		
		this.getChildren().add(imageLabel);
	}

	@Override
	ArrayList<int[]> getMoves(Piece[][] board) {
		ArrayList<int[]> moves = new ArrayList<>();
		
		int x = 0;
		int y = 0;
		
		x = xPos + 1;
		y = yPos - 2;
		if (x < 8 && y >= 0) {
			if (board[y][x] == null || !board[y][x].color.equals(color)) {
				int[] move1 = new int[3];
				move1[0] = x;
				move1[1] = y;
				if (board[y][x] != null) move1[2] = 1;
				moves.add(move1);
			}
		}
		
		x = xPos + 2;
		y = yPos - 1;
		if (x < 8 && y >= 0) {
			if (board[y][x] == null || !board[y][x].color.equals(color)) {
				int[] move2 = new int[3];
				move2[0] = x;
				move2[1] = y;
				if (board[y][x] != null) move2[2] = 1;
				moves.add(move2);
			}
		}
		
		x = xPos + 2;
		y = yPos + 1;
		if (x < 8 && y < 8) {
			if (board[y][x] == null || !board[y][x].color.equals(color)) {
				int[] move3 = new int[3];
				move3[0] = x;
				move3[1] = y;
				if (board[y][x] != null) move3[2] = 1;
				moves.add(move3);
			}
		}
		
		x = xPos + 1;
		y = yPos + 2;
		if (x < 8 && y < 8) {
			if (board[y][x] == null || !board[y][x].color.equals(color)) {
				int[] move4 = new int[3];
				move4[0] = x;
				move4[1] = y;
				if (board[y][x] != null) move4[2] = 1;
				moves.add(move4);
			}
		}
		
		x = xPos - 1;
		y = yPos + 2;
		if (x >= 0 && y < 8) {
			if (board[y][x] == null || !board[y][x].color.equals(color)) {
				int[] move5 = new int[3];
				move5[0] = x;
				move5[1] = y;
				if (board[y][x] != null) move5[2] = 1;
				moves.add(move5);
			}
		}
		
		x = xPos - 2;
		y = yPos + 1;
		if (x >= 0 && y < 8) {
			if (board[y][x] == null || !board[y][x].color.equals(color)) {
				int[] move6 = new int[3];
				move6[0] = x;
				move6[1] = y;
				if (board[y][x] != null) move6[2] = 1;
				moves.add(move6);
			}
		}
		
		x = xPos - 2;
		y = yPos - 1;
		if (x >= 0 && y >= 0) {
			if (board[y][x] == null || !board[y][x].color.equals(color)) {
				int[] move7 = new int[3];
				move7[0] = x;
				move7[1] = y;
				if (board[y][x] != null) move7[2] = 1;
				moves.add(move7);
			}
		}
		
		x = xPos - 1;
		y = yPos - 2;
		if (x >= 0 && y >= 0) {
			if (board[y][x] == null || !board[y][x].color.equals(color)) {
				int[] move8 = new int[3];
				move8[0] = x;
				move8[1] = y;
				if (board[y][x] != null) move8[2] = 1;
				moves.add(move8);
			}
		}
			
		ListIterator<int[]> it = moves.listIterator();
		while(it.hasNext()) {
			int[] move = it.next();
			if (!myKing.isLegalMove(this, move, board)) it.remove();
		}
		
		return moves;
	}

}
