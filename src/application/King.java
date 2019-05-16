package application;

import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.Icon;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class King extends Piece {

	private ImageView image;
	private Label imageLabel;
	private boolean hasMoved = false;
	
	public King(String color, int x, int y) {
		super(color, x, y);
		
		String path = "/images/pieces/" + color + " King.png";
		image = new ImageView(new Image(Icon.class.getResource(path).toExternalForm()));
		imageLabel = new Label();
		image.setFitHeight(100);
		image.setFitWidth(100);
		imageLabel.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		imageLabel.setGraphic(image);
		imageLabel.setTooltip(new Tooltip(color + " King"));
		
		this.getChildren().add(imageLabel);
	}
	/*
	 * REWRITE CHECK FUNCTION TO TEST ALL THE SQUARES FROM THE KING'S POSITION TO SEE IF
	 * THERE IS AN INSTANCE OF THAT PIECE THAT WOULD CHECK THE KING
	 * E.G. 
	 * BISHOPS AND QUEENS ON DIAGONAL
	 * ROOKS AND QUEENS ON CROSS
	 * PAWNS ON 1 SPACE DIAGONAL
	 * KNIGHTS FROM 2*1 L
	 * KINGS <<< ONLY TO TEST LEGAL MOVES
	 * 
	 * ALSO TO TEST LEGAL MOVES JUST READ THE BOARD ARRAY AND THEN ADJUST THE COORDS ACCORDINGLY
	 * IN CASE A PIECE WOULD BE TAKEN
	 */
	
	//checks if the king is in check
	public boolean check(Piece[][] board) {
		boolean inCheck = false;
		
		// horizontal and vertical axes (check if contains a queen or a rook)
		boolean north = false;
		boolean south = false;
		boolean east = false;
		boolean west = false;
		int s = 1; //straight horizontal and vertical
		while ((xPos - s >= 0 && !west) || (xPos + s < 8 && !east) || (yPos + s < 8 && !south) || (yPos - s >= 0 && !north)) {
			if (xPos - s >= 0 && !west) {
				inCheck = (board[yPos][xPos - s] instanceof Queen && !board[yPos][xPos - s].color.equals(color)) ||
							(board[yPos][xPos - s] instanceof Rook && !board[yPos][xPos - s].color.equals(color) ||
							(board[yPos][xPos - s] instanceof Pawn && !board[yPos][xPos - s].color.equals(color) &&
							((Pawn)board[yPos][xPos - s]).isQueen));
				if (inCheck) return true;
				west = board[yPos][xPos - s] != null; 
			}	
			if (xPos + s < 8 && !east) {
				inCheck = (board[yPos][xPos + s] instanceof Queen && !board[yPos][xPos + s].color.equals(color)) ||
							(board[yPos][xPos + s] instanceof Rook && !board[yPos][xPos + s].color.equals(color) ||
							(board[yPos][xPos + s] instanceof Pawn && !board[yPos][xPos + s].color.equals(color) &&
							((Pawn)board[yPos][xPos + s]).isQueen));
				if (inCheck) return true;
				east = board[yPos][xPos + s] != null;
			}
			if (yPos + s < 8 && !south) {
				inCheck = (board[yPos + s][xPos] instanceof Queen && !board[yPos + s][xPos].color.equals(color)) ||
							(board[yPos + s][xPos] instanceof Rook && !board[yPos + s][xPos].color.equals(color) ||
							(board[yPos + s][xPos] instanceof Pawn && !board[yPos + s][xPos].color.equals(color) &&
							((Pawn)board[yPos + s][xPos]).isQueen));
				if (inCheck) return true;
				south = board[yPos + s][xPos] != null;
			}
			if (yPos - s >= 0 && !north) {
				inCheck = (board[yPos - s][xPos] instanceof Queen && !board[yPos - s][xPos].color.equals(color)) ||
							(board[yPos - s][xPos] instanceof Rook && !board[yPos - s][xPos].color.equals(color) ||
							(board[yPos - s][xPos] instanceof Pawn && !board[yPos - s][xPos].color.equals(color) &&
							((Pawn)board[yPos - s][xPos]).isQueen));
				if (inCheck) return true;
				north = board[yPos - s][xPos] != null;
			}
			s++;
		}
		
		// check diagonals for queen or bishop
		boolean northEast = false;
		boolean southEast = false;
		boolean southWest = false;
		boolean northWest = false;
		int d = 1; //diagonals
		while ((xPos - d >= 0 && yPos - d >= 0 && !northWest) || (xPos - d >= 0 && yPos + d < 8 && !southWest) ||
				(xPos + d < 8 && yPos - d >= 0 && !northEast) || (xPos + d < 8 && yPos + d < 8 && !southEast)) {
			if (xPos - d >= 0 && yPos - d >= 0 && !northWest) {
				inCheck = (board[yPos - d][xPos - d] instanceof Queen && !board[yPos - d][xPos - d].color.equals(color) ||
							board[yPos - d][xPos - d] instanceof Bishop && !board[yPos - d][xPos - d].color.equals(color) ||
							(board[yPos - d][xPos - d] instanceof Pawn && !board[yPos - d][xPos - d].color.equals(color) &&
							((Pawn)board[yPos - d][xPos - d]).isQueen));
				if (inCheck) return true;
				northWest = board[yPos - d][xPos - d] != null;
			}
			if (xPos - d >= 0 && yPos + d < 8 && !southWest) {
				inCheck = (board[yPos + d][xPos - d] instanceof Queen && !board[yPos + d][xPos - d].color.equals(color) ||
						board[yPos + d][xPos - d] instanceof Bishop && !board[yPos + d][xPos - d].color.equals(color) ||
						(board[yPos + d][xPos - d] instanceof Pawn && !board[yPos + d][xPos - d].color.equals(color) &&
						((Pawn)board[yPos + d][xPos - d]).isQueen));
				if (inCheck) return true;
				southWest = board[yPos + d][xPos - d] != null;
			}
			if (xPos + d < 8 && yPos - d >= 0 && !northEast) {
				inCheck = (board[yPos - d][xPos + d] instanceof Queen && !board[yPos - d][xPos + d].color.equals(color) ||
						board[yPos - d][xPos + d] instanceof Bishop && !board[yPos - d][xPos + d].color.equals(color) ||
						(board[yPos - d][xPos + d] instanceof Pawn && !board[yPos - d][xPos + d].color.equals(color) &&
						((Pawn)board[yPos - d][xPos + d]).isQueen));
				if (inCheck) return true;
				northEast = board[yPos - d][xPos + d] != null;
			}
			if (xPos + d < 8 && yPos + d < 8 && !southEast) {
				inCheck = (board[yPos + d][xPos + d] instanceof Queen && !board[yPos + d][xPos + d].color.equals(color) ||
						board[yPos + d][xPos + d] instanceof Bishop && !board[yPos + d][xPos + d].color.equals(color) ||
						(board[yPos + d][xPos + d] instanceof Pawn && !board[yPos + d][xPos + d].color.equals(color) &&
						((Pawn)board[yPos + d][xPos + d]).isQueen));
				if (inCheck) return true;
				southEast = board[yPos + d][xPos + d] != null;
			}
			d++;
		}
		
		//PAWNS
		int deltaY = 0;
		if (color.equals("Black")) deltaY = 1;
		else deltaY = -1; //color is white
		
		if (xPos - 1 >= 0 && (yPos + deltaY < 8 && yPos + deltaY >= 0)) inCheck = (board[yPos + deltaY][xPos - 1] instanceof Pawn) && 
										(!board[yPos + deltaY][xPos - 1].color.equals(color));
		if (inCheck) return true;
		if (xPos + 1 < 8 && (yPos + deltaY < 8 && yPos + deltaY >= 0)) inCheck = (board[yPos + deltaY][xPos + 1] instanceof Pawn) && 
				(!board[yPos + deltaY][xPos + 1].color.equals(color));
		if (inCheck) return true;
		
		
		//KNIGHTS
		int x = 0;
		int y = 0;
		
		x = xPos - 1;
		y = yPos - 2;
		if (x >= 0 && y >= 0) {
			inCheck = (board[y][x] instanceof Knight) && !board[y][x].color.equals(color);
			if (inCheck) return true;
		}
		
		x = xPos - 2;
		y = yPos - 1;
		if (x >= 0 && y >= 0) {
			inCheck = (board[y][x] instanceof Knight && !board[y][x].color.equals(color));
			if (inCheck) return true;
		}
		
		x = xPos - 2;
		y = yPos + 1;
		if (x >= 0 && y < 8) {
			inCheck = (board[y][x] instanceof Knight && !board[y][x].color.equals(color));
			if (inCheck) return true;
		}
		
		x = xPos - 1;
		y = yPos + 2;
		if (x >= 0 && y < 8) {
			inCheck = (board[y][x] instanceof Knight && !board[y][x].color.equals(color));
			if (inCheck) return true;
		}
		
		x = xPos + 1;
		y = yPos + 2;
		if (x < 8 && y < 8) {
			inCheck = (board[y][x] instanceof Knight && !board[y][x].color.equals(color));
			if (inCheck) return true;
		}
		
		x = xPos + 2;
		y = yPos + 1; 
		if (x < 8 && y < 8) {
			inCheck = (board[y][x] instanceof Knight && !board[y][x].color.equals(color));
			if (inCheck) return true;
		}
		
		x = xPos + 2;
		y = yPos - 1;
		if (x < 8 && y >= 0) {
			inCheck = (board[y][x] instanceof Knight && !board[y][x].color.equals(color));
			if (inCheck) return true;
		}
		
		x = xPos + 1;
		y = yPos - 2;
		if (x < 8 && y >= 0) {
			inCheck = (board[y][x] instanceof Knight && !board[y][x].color.equals(color));
			if (inCheck) return true;
		}
		
		
		
		//KINGS
		x = 0;
		y = 0;
		
		x = xPos;
		y = yPos - 1;
		if (y >= 0) {
			inCheck = (board[y][x] instanceof King && !board[y][x].color.equals(color));
			if (inCheck) return true;
		}
		
		x = xPos + 1;
		y = yPos - 1;
		if (x < 8 && y >= 0) {
			inCheck = (board[y][x] instanceof King && !board[y][x].color.equals(color));
			if (inCheck) return true;
		}
		
		x = xPos + 1;
		y = yPos;
		if (x < 8) {
			inCheck = (board[y][x] instanceof King && !board[y][x].color.equals(color));
			if (inCheck) return true;
		}
		
		x = xPos + 1;
		y = yPos + 1;
		if (x < 8 && y < 8) {
			inCheck = (board[y][x] instanceof King && !board[y][x].color.equals(color));
			if (inCheck) return true;
		}
		
		x = xPos;
		y = yPos + 1;
		if (y < 8) {
			inCheck = (board[y][x] instanceof King && !board[y][x].color.equals(color));
			if (inCheck) return true;
		}
		
		x = xPos - 1;
		y = yPos + 1;
		if (x >= 0 && y < 8) {
			inCheck = (board[y][x] instanceof King && !board[y][x].color.equals(color));
			if (inCheck) return true;
		}
		
		x = xPos - 1;
		y = yPos;
		if (x >= 0) {
			inCheck = (board[y][x] instanceof King && !board[y][x].color.equals(color));
			if (inCheck) return true;
		}
		
		x = xPos - 1;
		y = yPos - 1;
		if (x >= 0 && y >= 0) {
			inCheck = (board[y][x] instanceof King && !board[y][x].color.equals(color));
			if (inCheck) return true;
		}
		
		
		return inCheck;
	}
	
	// this method only checks if move puts own king in check
	public boolean isLegalMove(Piece piece , int[] move, Piece[][] board) {		
		boolean isLegal = true;
		Piece[] removedPiece = new Piece[1];
		
		int oldX = 0; //just for kings
		int oldY = 0; 
		
		int x = move[0];
		int y = move[1];
		
		if (piece instanceof King) {
			oldX = piece.getX();
			oldY = piece.getY();
			
			piece.xPos = x;
			piece.yPos = y;
		}
		
		
		
		if (board[y][x] != null) removedPiece[0] = board[y][x];
		
		board[y][x] = piece;
		board[piece.getY()][piece.getX()] = null;
		
		isLegal = !check(board);
		
		if (piece instanceof King) {
			piece.xPos = oldX;
			piece.yPos = oldY;
		}
		
		board[y][x] = null;
		board[piece.getY()][piece.getX()] = piece;
		if (removedPiece.length > 0) board[y][x] = removedPiece[0];
		
		return isLegal;
	}
	
	@Override
	public void setY(int y) {
		hasMoved = true;
		yPos = y;
	}
	
	@Override
	public void setX(int x) {
		hasMoved = true;
		xPos = x;
	}

	@Override
	ArrayList<int[]> getMoves(Piece[][] board) {
		ArrayList<int[]> moves = new ArrayList<>();

		// castling
		// kingside
		if (!hasMoved && !check(board)) {
			if	(board[yPos][5] == null && board[yPos][6] == null && (board[yPos][7] instanceof Rook)) {
				Rook rook = (Rook) board[yPos][7];
				if (!rook.hasMoved) {
					int[] kingsideCastle = new int[3];
					kingsideCastle[0] = 6;
					kingsideCastle[1] = yPos;
					kingsideCastle[2] = 3;
					int[] move1 = {5, yPos};
					int[] move2 = {6, yPos};
					if (isLegalMove(this, move1, board) && isLegalMove(this, move2, board)) moves.add(kingsideCastle);
				}
			}
		}
		
		// queenside
		if (!hasMoved && !check(board)) {
			if (board[yPos][3] == null && board[yPos][2] == null && board[yPos][1] == null
				&& (board[yPos][0] instanceof Rook)) {
				Rook rook = (Rook) board[yPos][0];
				if (!rook.hasMoved) {
					int[] queensideCastle = new int[3];
					queensideCastle[0] = 2;
					queensideCastle[1] = yPos;
					queensideCastle[2] = 4;
					int[] move1 = {3, yPos};
					int[] move2 = {2, yPos};
					int[] move3 = {1, yPos};
					if (isLegalMove(this, move1, board) && isLegalMove(this, move2, board) &&
						isLegalMove(this, move3, board)) moves.add(queensideCastle);
				}
			}
		}
		
		
		//all 8 directions
		int x = 0;
		int y = 0;
		
		x = xPos;
		y = yPos - 1;
		if (y >= 0) {
			if (board[y][x] == null || !board[y][x].color.equals(color)) {
				int[] move1 = new int[3];
				move1[0] = x;
				move1[1] = y;
				if (board[y][x] != null) move1[2] = 1;
				moves.add(move1);
			}
		}
		
		x = xPos + 1;
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
		
		x = xPos + 1;
		y = yPos;
		if (x < 8) {
			if (board[y][x] == null || !board[y][x].color.equals(color)) {
				int[] move3 = new int[3];
				move3[0] = x;
				move3[1] = y;
				if (board[y][x] != null) move3[2] = 1;
				moves.add(move3);
			}
		}
		
		x = xPos + 1;
		y = yPos + 1;
		if (x < 8 && y < 8) {
			if (board[y][x] == null || !board[y][x].color.equals(color)) {
				int[] move4 = new int[3];
				move4[0] = x;
				move4[1] = y;
				if (board[y][x] != null) move4[2] = 1;
				moves.add(move4);
			}
		}
		
		x = xPos;
		y = yPos + 1;
		if (y < 8) {
			if (board[y][x] == null || !board[y][x].color.equals(color)) {
				int[] move5 = new int[3];
				move5[0] = x;
				move5[1] = y;
				if (board[y][x] != null) move5[2] = 1;
				moves.add(move5);
			}
		}
		
		x = xPos - 1;
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
		
		x = xPos - 1;
		y = yPos;
		if (x >= 0) {
			if (board[y][x] == null || !board[y][x].color.equals(color)) {
				int[] move7 = new int[3];
				move7[0] = x;
				move7[1] = y;
				if (board[y][x] != null) move7[2] = 1;
				moves.add(move7);
			}
		}
		
		x = xPos - 1;
		y = yPos - 1;
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
			if (!isLegalMove(this, move, board)) it.remove();
		}
		
		return moves;
	}

}
