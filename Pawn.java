package application;

import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.Icon;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Pawn extends Piece {

	private ImageView image;
	private Label imageLabel;
	private boolean hasMoved = false;
	protected boolean isQueen = false;
	public boolean canEnPassant = false;

	public Pawn(String color, int x, int y, King myKing) {
		super(color, x, y);
		this.myKing = myKing;

		String path = "/images/pieces/" + color + " Pawn.png";
		image = new ImageView(new Image(Icon.class.getResource(path).toExternalForm()));
		imageLabel = new Label();
		image.setFitHeight(100);
		image.setFitWidth(100);
		imageLabel.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		imageLabel.setGraphic(image);
		imageLabel.setTooltip(new Tooltip(color + " Pawn"));

		this.getChildren().add(imageLabel);

	}

	@Override
	ArrayList<int[]> getMoves(Piece[][] board) {
		ArrayList<int[]> moves = new ArrayList<>();

		if (isQueen) {
			Queen queen = new Queen(color, xPos, yPos, myKing);

			moves = queen.getMoves(board);
		} else {

			// move forward 1 unless something blocks me
			int[] move1 = new int[3];
			// move forward 2 if haven't moved and nothing blocks me
			int[] move2 = new int[3];
			// attack diagonally left
			int[] move3 = new int[3];
			// attack diagonally right
			int[] move4 = new int[3];

			int deltaY1 = 0;
			int deltaY2 = 0;

			if (color.equals("Black")) {
				deltaY1 = 1;
				deltaY2 = 2;
			} else {
				deltaY1 = -1;
				deltaY2 = -2;
			}

			// xpos unchanged ypos advance 1
			move1[0] = xPos;
			move1[1] = yPos + deltaY1;
			move1[2] = 0; //not an attack
			// add if nothing is blocking
			if (board[move1[1]][move1[0]] == null) moves.add(move1);

			// xpos unchanged ypos advance 2
			move2[0] = xPos;
			move2[1] = yPos + deltaY2;
			move2[2] = 0; //not an attack
			// add if no block and is first move
			if (!hasMoved && board[move2[1]][move2[0]] == null &&
					board[move1[1]][move1[0]] == null) moves.add(move2);

			// xpos move 1 left ypos advance 1
			if (xPos - 1 >= 0) {
				move3[0] = xPos - 1;
				move3[1] = yPos + deltaY1;
				move3[2] = 1; // is an attack
				// add if move3 is occupied by an opposite color piece

				if (board[move3[1]][move3[0]] != null && 
						!board[move3[1]][move3[0]].getColor().equals(color)) moves.add(move3);
			}

			// en passant
			if (board[yPos][move3[0]] instanceof Pawn &&
					!board[yPos][move3[0]].getColor().equals(color)) {
				Pawn pawn = (Pawn) board[yPos][move3[0]];
				if (pawn.canEnPassant == true) {
					move3[2] = 2;
					moves.add(move3);
				}

			}

			// xpos move 1 right ypos advance 1
			if (xPos + 1 < 8) {
				move4[0] = xPos + 1;
				move4[1] = yPos + deltaY1;
				move4[2] = 1;
				// add if move4 is occupied by an opposite color piece
				if (board[move4[1]][move4[0]] != null && 
						!board[move4[1]][move4[0]].getColor().equals(color)) moves.add(move4);
			}

			// en passant other side
			if (board[yPos][move4[0]] instanceof Pawn &&
					!board[yPos][move4[0]].getColor().equals(color)) {
				Pawn pawn = (Pawn) board[yPos][move4[0]];
				if (pawn.canEnPassant == true) {
					move4[2] = 2;
					moves.add(move4);
				}
			}
			
			ListIterator<int[]> it = moves.listIterator();
			while(it.hasNext()) {
				int[] move = it.next();
				if (!myKing.isLegalMove(this, move, board)) it.remove();
			}
		}

		return moves;
	}

	@Override
	public void setY(int y) {
		hasMoved = true;
		if (yPos == 1 && y == 3 || yPos == 6 && y == 4) {
			canEnPassant = true;
			//System.out.println("This pawn is en passantable!!!");
		} else {
			canEnPassant = false;
		}
		if (y == 0 || y == 7) {
			queen();
		}
		yPos = y;
	}

	private void queen() {
		isQueen = true;

		String path = "/images/pieces/" + color + " Queen.png";
		image = new ImageView(new Image(Icon.class.getResource(path).toExternalForm()));
		image.setFitHeight(100);
		image.setFitWidth(100);

		imageLabel.setGraphic(image);
	}

}
