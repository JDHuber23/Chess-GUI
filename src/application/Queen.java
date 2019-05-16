package application;

import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.Icon;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Queen extends Piece {
	
	private ImageView image;
	private Label imageLabel;
	
	public Queen(String color, int x, int y, King myKing) {
		super(color, x, y);
		this.myKing = myKing;
		
		String path = "/images/pieces/" + color + " Queen.png";
		image = new ImageView(new Image(Icon.class.getResource(path).toExternalForm()));
		imageLabel = new Label();
		image.setFitHeight(100);
		image.setFitWidth(100);
		imageLabel.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		imageLabel.setGraphic(image);
		imageLabel.setTooltip(new Tooltip(color + " Queen"));
		
		this.getChildren().add(imageLabel);
	}

	@Override
	ArrayList<int[]> getMoves(Piece[][] board) {
		ArrayList<int[]> moves = new ArrayList<>();
		
		int north = 1;
		while ((yPos - north) >= 0) {
			int[] move = new int[3];
			int y = yPos - north;
			if (board[y][xPos] != null && board[y][xPos].getColor().equals(color)) break;
			
			move[0] = xPos;
			move[1] = y;
			if (board[y][xPos] != null && !board[y][xPos].getColor().equals(color)) {
				move[2] = 1;
				moves.add(move);
				break;
			}
			moves.add(move);
			north++;
		}
		
		int south = 1;
		while((yPos + south) < 8) {
			int[] move = new int[3];
			int y = yPos + south;
			if (board[y][xPos] != null && board[y][xPos].getColor().equals(color)) break;
			
			move[0] = xPos;
			move[1] = y;
			if (board[y][xPos] != null && !board[y][xPos].getColor().equals(color)) {
				move[2] = 1;
				moves.add(move);
				break;
			}
			moves.add(move);
			south++;
		}
		
		int east = 1;
		while ((xPos + east) < 8) {
			int[] move = new int[3];
			int x = xPos + east;
			if (board[yPos][x] != null && board[yPos][x].getColor().equals(color)) break;
			
			move[0] = x;
			move[1] = yPos;
			if (board[yPos][x] != null && !board[yPos][x].getColor().equals(color)) {
				move[2] = 1;
				moves.add(move);
				break;
			}
			moves.add(move);
			east++;
		}
		
		int west = 1;
		while ((xPos - west) >= 0) {
			int[] move = new int[3];
			int x = xPos - west;
			if (board[yPos][x] != null && board[yPos][x].getColor().equals(color)) break;
			
			move[0] = x;
			move[1] = yPos;
			if (board[yPos][x] != null && !board[yPos][x].getColor().equals(color)) {
				move[2] = 1;
				moves.add(move);
				break;
			}
			moves.add(move);
			west++;
		}
		
		int northEast = 1;
		while((xPos + northEast) < 8 && (yPos - northEast) >= 0) {
			int[] move = new int[3];
			int x = xPos + northEast;
			int y = yPos - northEast;
			
			if (board[y][x] != null && board[y][x].getColor().equals(color)) break;
			
			move[0] = x;
			move[1] = y;
			if (board[y][x] != null && !board[y][x].getColor().equals(color)) {
				move[2] = 1;
				moves.add(move);
				break;
			}
			moves.add(move);
			northEast++;
		}
		
		int northWest = 1;
		while((xPos - northWest) >= 0 && (yPos - northWest) >= 0) {
			int[] move = new int[3];
			int x = xPos - northWest;
			int y = yPos - northWest;
			
			if (board[y][x] != null && board[y][x].getColor().equals(color)) break;
			
			move[0] = x;
			move[1] = y;
			if (board[y][x] != null && !board[y][x].getColor().equals(color)) {
				move[2] = 1;
				moves.add(move);
				break;
			}
			moves.add(move);
			northWest++;
		}
		
		int southWest = 1;
		while((xPos - southWest) >= 0 && (yPos + southWest) < 8) {
			int[] move = new int[3];
			int x = xPos - southWest;
			int y = yPos + southWest;
			
			if (board[y][x] != null && board[y][x].getColor().equals(color)) break;
			
			move[0] = x;
			move[1] = y;
			if (board[y][x] != null && !board[y][x].getColor().equals(color)) {
				move[2] = 1;
				moves.add(move);
				break;
			}
			moves.add(move);
			southWest++;
		}
		
		int southEast = 1;
		while((xPos + southEast) < 8 && (yPos + southEast) < 8) {
			int[] move = new int[3];
			int x = xPos + southEast;
			int y = yPos + southEast;
			
			if (board[y][x] != null && board[y][x].getColor().equals(color)) break;
			
			move[0] = x;
			move[1] = y;
			if (board[y][x] != null && !board[y][x].getColor().equals(color)) {
				move[2] = 1;
				moves.add(move);
				break;
			}
			moves.add(move);
			southEast++;
		}
		
		ListIterator<int[]> it = moves.listIterator();
		while(it.hasNext()) {
			int[] move = it.next();
			if (!myKing.isLegalMove(this, move, board)) it.remove();
		}
		
		return moves;
	}

}
