package chess.engine;

import java.util.ArrayList;
import java.util.Random;

import application.Bishop;
import application.King;
import application.Knight;
import application.Pawn;
import application.Piece;
import application.Queen;
import application.Rook;


class Node {
	int[] move;
	int[] position;
	ChessBoard board;
	int moveScore;
	ArrayList<Node> children;
	boolean isWhite;

	public Node(int[] move, int[] position, ChessBoard board, boolean isWhite) {
		this.move = move;
		this.position = position;
		this.board = board;
		this.isWhite = isWhite; //isWhite is true if children are only white moves
		this.moveScore = 0;
		children = new ArrayList<Node>();
	}
	
	public Node(ArrayList<Piece> pieces, boolean isWhite) {
		board = new ChessBoard(pieces);
		this.isWhite = isWhite;
		children = new ArrayList<Node>();
	}

	@Override
	public String toString() {
		return "Piece to move x and y: [" + position[0] + ", " + position[1] + "] to [" + move[0] + ", " + move[1] + "] Score: " + moveScore;
	}
}

public class ChessAI {
	Node root;
	ArrayList<Piece> pieces;
	static int MAX = 10000000; 
	static int MIN = -10000000;
	final boolean isWhite;
	int DEPTH;

	/*TO DO
	 * OPTIMIZE NODES AND ALGORITHM TO REDUCE MEMORY USAGE AND INCREASE EFFICIENCY ALLOWING
	 * 4 MOVES AHEAD CALCULATIONS.
	 * SETUP GAME TO HAVE AN OPTION IN THE START MENU AS TO WHETHER PLAYING AGAINST BOT
	 * OR ANOTHER PLAYER, MAYBE ROTATE BOARD FOR SECOND PLAYER.
	 * REVAMP EVALUATION ALGORITHM TO INCENTIVIZE CHECKMATING AND DECENTIVIZE STALEMATING?
	 * FIX A BUNCH OF UI ISSUES, MAKE THE GAME FEEL BETTER AND FIGURE OUT HOW TO FIX
	 * THE LAYOUT FOR THE GETBESTMOVE BUTTONS!!!
	 * 
	 */
	public ChessAI(boolean isWhite, int depth, ArrayList<Piece> pieces) {
		DEPTH = depth;
		root = new Node(pieces, isWhite);
		this.pieces = pieces;
		this.isWhite = isWhite; 

	}
/*
	public Node getRandomMove(boolean isWhite) {
		ArrayList<Node> list = getAvailableMoveNodes(root.board, isWhite);
		Random rand = new Random();
		if (list.size() == 0) {
			System.out.println("List is empty");
			int[] alreadyLost = {0, 0, -1};
			return new Node(alreadyLost,new int[2], null, false);
		}
		int rNum = rand.nextInt(list.size());
		System.out.println("Getting random move");
		return list.get(rNum);
	}*/

	public int[] getBestMove() {
		System.out.println(root.board.toString());
		
		return null;
		// build tree out to depth level
		// Get every legal move of your color's piece, and add each move to the children list
		// For each child node, reread the board as if that move had been played,
		// and get the new list of legal moves and add it to the children list for that node
		// Repeat depth times

		/*root.children = getAvailableMoveNodes(root.board, isWhite);

		for (int i = 0; i < DEPTH; i++) {
			// go until you get to nodes that don't have a child
			buildTree(root);

		}

		// traverse tree and calculate highest scoring path (max or min depending on color) assuming opponent plays optimally

		int bestMoveScore = minimax(0, root, MIN, MAX);
		if (root.children.size() == 0) {
			int[] checkMated = {0, 0, 0, 0, 0};
			return checkMated;
		}
		System.out.println("Best move score: " + bestMoveScore);
		Node bestMove = root.children.get(0);
		boolean allNodesEqual = true;
		for (Node n: root.children) {

			if (n.moveScore != bestMove.moveScore) allNodesEqual = false;
			if (n.moveScore > bestMove.moveScore && isWhite) bestMove = n;
			if (n.moveScore < bestMove.moveScore && !isWhite) bestMove = n;
		}
		if (allNodesEqual) bestMove = getRandomMove(isWhite);
		System.out.println(bestMove.toString());
		int[] bestMoveArray = {bestMove.position[0], bestMove.position[1], bestMove.move[0], bestMove.move[1], bestMove.move[2]};
		// return the coords of the piece and its best move as int[]: [oldX, oldY, newX, newY]
		root = null;
		return bestMoveArray; */
		
		
		
	}
	/*
	// upon new node creation: create node, set board to node position, 
	public ArrayList<Node> getAvailableMoveNodes(Piece[][] board, boolean isWhite) {
		// TODO if given a board where either king is in check, the get moves algorithm
		// will return moves that take the opposing king and so there will be nodes that have
		// board positions that don't have a king. so fuck.
		// TODO for these boards, the pieces don't realize that theyve been moved so
		// p.getX() and p.getY() is never accurate, neither is getMoves.....
		ArrayList<Node> moves = new ArrayList<>();
		String color = "";
		if (isWhite) color = "White";
		else color = "Black";
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == null) continue;
				Piece p = board[i][j];				
				
				if (p.getColor().equals(color)) {
					int originalX = p.getX();
					int originalY = p.getY();
				
					for (int[] m : p.getMoves(board)) {
						if (board[m[1]][m[0]] instanceof King && !board[m[1]][m[0]].getColor().equals(color)) {
							//System.out.println("Condition met");
							continue;
						}
						int[] pos = {j, i};
						//move piece to new position
						Piece[][] thisBoard = movePiece(board, j, i, m[0], m[1], m[2]);
						Node n = new Node(m, pos, thisBoard, !isWhite);
						moves.add(n);
					}
					

				}
			}
		}

		return moves;

	}

	public void buildTree(Node node) {
		// TODO combine building the tree and the alpha beta pruning evaluation algorithm.
		// the minimax method is only more efficient at traversing the tree, but if it could see that
		// it didn't need to create more nodes because it was traversing while it was adding nodes
		// then it would save a lost of space and could make the tree go deeper
		if (node.children.size() == 0) {
			node.children = getAvailableMoveNodes(node.board, node.isWhite);
		} else {
			for (Node childNode : node.children) {
				buildTree(childNode);
			}
		}

	}
	
	public void traverseTree(Node node) {

		if (node.children.size() != 0) {
			for (Node childNode: node.children) {
				traverseTree(childNode);
			}
		}

		for (Node childNode: node.children) {
			if (childNode.moveScore > node.moveScore && node.isWhite) 
				node.moveScore = childNode.moveScore;
			else if (childNode.moveScore < node.moveScore && !node.isWhite)
				node.moveScore = childNode.moveScore;
		}

	}

	public int minimax(int depth, Node node, int alpha, int beta) { 
		// TODO combine with build tree method somehow 
		// POTENTIALLY MIGHT HAVE TO COMBINE getAvailableMoveNodes
		// as well as build tree, because getAvailableMoveNodes creates the whole list of nodes at once.
		// Maybe not though because one list isn't that big for any given node. Not sure.
		// Ideally each node added would be evaluated and then if the minimax algorithm found it didn't 
		// need any more nodes it would immediately stop creating more nodes.


		// leaf node is reached 
		if (depth == DEPTH) {
			node.moveScore = evaluateBoard(node.board);
			return node.moveScore; 
		}

		if (node.isWhite) 
		{ 
			int best = MIN; 

			// Recur for all children 
			for (Node childNode: node.children) 
			{ 
				childNode.moveScore = minimax(depth + 1, childNode, alpha, beta); 
				best = Math.max(best, childNode.moveScore); 
				alpha = Math.max(alpha, best); 

				// Alpha Beta Pruning 
				if (beta <= alpha) 
					break; 
			} 
			return best; 
		} 
		else
		{ 
			int best = MAX; 

			// Recur for all children
			for (Node childNode: node.children) 
			{ 

				childNode.moveScore = minimax(depth + 1, childNode, alpha, beta); 
				best = Math.min(best, childNode.moveScore); 
				beta = Math.min(beta, best); 

				// Alpha Beta Pruning 
				if (beta <= alpha) 
					break; 
			} 
			return best; 
		} 
	}

	public int evaluateBoard(Piece[][] board) {
		// TODO GETMOVES AND CHECK METHODS ARE NOT CALCULATING CORRECTLY
		// BECAUSE THE PIECES REFERENCE DO NOT HAVE THE SAME X AND Y
		// AS THEY SHOULD ON THE BOARD
		//System.out.println("current pieces size: " + Piece.readPieces(board).size());

		int score = 0;
		King whiteKing = null;
		King blackKing = null;
		boolean whiteCheckMated = true;
		boolean blackCheckMated = true;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == null) continue;
				Piece p = board[i][j];
				if (p.color.equals("White")) {
					score += getValue(p);
					if (p instanceof King) whiteKing = new King("White", j, i);
					if (whiteCheckMated) {
						if (p.getMoves(board).size() > 0) whiteCheckMated = false;
					}
				} else {
					score -= getValue(p);
					if (p instanceof King) blackKing = new King("Black", j, i);
					if (blackCheckMated) {
						if (p.getMoves(board).size() > 0) blackCheckMated = false;
					}
				}
			}
		}

		if (whiteCheckMated && whiteKing.check(board)) {
			score -= 10000000;
		}
		if (blackCheckMated && blackKing.check(board)) {
			score += 10000000;
		}

		if (score > 9000 && score < 15000) {
			System.out.println("U wot");
			
		}


		return score;
	}*/

	public int getValue(Piece p) {

		if (p instanceof Pawn) {
			return 1;
		} else if (p instanceof Knight || p instanceof Bishop) {
			return 3;
		} else if (p instanceof Rook) {
			return 5;
		} else if (p instanceof Queen) {
			return 9;
		} else if (p instanceof King) {
			return 10000;
		} else {
			System.out.println("HERE'S A BUG");
			return -1;
		}

	}

	public Piece[][] movePiece(Piece[][] originalBoard, int oldX, int oldY, int newX, int newY, int moveType) {
		// TODO IMPLEMENT CASE FUNCTIONALITY FOR DIFFERENT MOVE TYPES
		// ^^^^ COMPLETED ^^^^
		// I think the reason that sometimes the board can't find the king (and probably loses other pieces) is
		// because some of the moves in the getMoves method can affect other pieces differently than just a simple
		// move and remove in the same coordinates. At the very least it is evaluating incorrect board positions
		// and this problem must be fixed in order for more accurate evaluations when a better algorithm
		// comes along.
		Piece[][] board = new Piece[8][8];
		for (int i = 0; i < 8; i++) {
			board[i] = originalBoard[i].clone();
			
		}
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] instanceof Pawn) {
					((Pawn) board[i][j]).canEnPassant = false;
				}
			}
		}

		Piece[] movingPiece = {board[oldY][oldX]};
		int deltaY = 0;
		switch (moveType) {
		case 2: // en passant
			// TODO there's a bug here because a pawn becomes
			// un en passantable during actual gameplay
			// so getMoves will throw enpassantable moves
			// that aren't legal
			if (movingPiece[0].getColor().equals("Black")) deltaY = -1;
			else deltaY = 1;

			board[newY + deltaY][newX] = null;

			break;
		case 3: // castling kingside

			Rook rook = (Rook)board[newY][7];
			
			board[newY][5] = rook;
			board[newY][7] = null;

			break;
		case 4: // castling queenside

			Rook rook2 = (Rook)board[newY][0];
			
			board[newY][3] = rook2;
			board[newY][0] = null;

			break;
		}
		board[oldY][oldX] = null;	
		board[newY][newX] = movingPiece[0];
		

		return board;
	}

	/*
	public int evaluateStringBoard(String pieces) {
		int score = 0;

		for (int i = 0; i < pieces.length(); i+= 4) {
			int color = 0;
			int value = 0;
			if (pieces.charAt(i) == 'W') color = 1;
			else color = -1;
			value = getValue(pieces.charAt(i + 1));

			score += value * color;
		}

		return score;
	}

	public int getValue(char p) {
		if (p == 'P') {
			return 1;
		} else if (p == 'N' || p == 'B') {
			return 3;
		} else if (p == 'R') {
			return 5;
		} else if (p == 'Q') {
			return 9;
		} else {
			return 10000;
		}
	}
	public String listPiecesToString(ArrayList<Piece> pieces) {
		String piecesString = "";
		for (int i = 0; i < pieces.size(); i++) {
			Piece p = pieces.get(i);
			if (p.color.equals("White")) piecesString += "W";
			else piecesString += "B";
			if (p instanceof Pawn) piecesString += "P" + p.getX()+p.getY();
			else if (p instanceof Knight) piecesString += "N" + p.getX()+p.getY();
			else if (p instanceof Bishop) piecesString += "B" + p.getX()+p.getY();
			else if (p instanceof Rook) piecesString += "R" + p.getX()+p.getY();
			else if (p instanceof Queen) piecesString += "Q" + p.getX()+p.getY();
			else piecesString += "K" + p.getX()+p.getY();
		}
		return piecesString;
	}

	public void stringToBoard(String pieces, Piece[][] board) {

		int indexOfBlackKing = pieces.indexOf("BK");
		int indexOfWhiteKing = pieces.indexOf("WK");
		String blackKingString = pieces.substring(indexOfBlackKing, indexOfBlackKing + 4);
		String whiteKingString = pieces.substring(indexOfWhiteKing, indexOfWhiteKing + 4);
		King blackKing = new King("Black", Integer.valueOf(blackKingString.charAt(2)+ ""), Integer.valueOf(blackKingString.charAt(3)+ ""));
		King whiteKing = new King("White", Integer.valueOf(whiteKingString.charAt(2)+ ""), Integer.valueOf(whiteKingString.charAt(3)+ ""));
		board[blackKing.getY()][blackKing.getX()] = blackKing;
		board[whiteKing.getY()][whiteKing.getX()] = whiteKing;


		pieces.replace(blackKingString, "");
		pieces.replace(whiteKingString, "");
		for (int i = 0; i < pieces.length(); i += 4) {
			King myKing = null;
			Piece piece = null;
			String color;
			int x = Integer.valueOf(pieces.charAt(i + 2) + "");
			int y = Integer.valueOf(pieces.charAt(i + 3) + "");
			if (pieces.charAt(i) == 'W') {
				color = "White";
				myKing = whiteKing;
			} else {
				color = "Black";
				myKing = blackKing;
			}

			if (pieces.charAt(i + 1) == 'P') piece = new Pawn(color, x, y, myKing);
			else if (pieces.charAt(i + 1) == 'N') piece = new Knight(color, x, y, myKing);
			else if (pieces.charAt(i + 1) == 'B') piece = new Bishop(color, x, y, myKing);
			else if (pieces.charAt(i + 1) == 'R') piece = new Rook(color, x, y, myKing);
			else if (pieces.charAt(i + 1) == 'Q') piece = new Queen(color, x, y, myKing);
			board[y][x] = piece;
		}

	}

	public void stringToList(String pieces, ArrayList<Piece> piecesList) {

		int indexOfBlackKing = pieces.indexOf("BK");
		int indexOfWhiteKing = pieces.indexOf("WK");
		String blackKingString = pieces.substring(indexOfBlackKing, indexOfBlackKing + 4);
		String whiteKingString = pieces.substring(indexOfWhiteKing, indexOfWhiteKing + 4);
		King blackKing = new King("Black", Integer.valueOf(blackKingString.charAt(2)+ ""), Integer.valueOf(blackKingString.charAt(3)+ ""));
		King whiteKing = new King("White", Integer.valueOf(whiteKingString.charAt(2)+ ""), Integer.valueOf(whiteKingString.charAt(3)+ ""));
		piecesList.add(whiteKing);
		piecesList.add(blackKing);

		pieces.replace(blackKingString, "");
		pieces.replace(whiteKingString, "");

		for (int i = 0; i < pieces.length(); i += 4) {
			King myKing = null;
			Piece piece = null;
			String color = "";
			int x = Integer.valueOf(pieces.charAt(i + 2) + "");
			int y = Integer.valueOf(pieces.charAt(i + 3) + "");
			if (pieces.charAt(i) == 'W') {
				color = "White";
				myKing = whiteKing;
			} else {
				color = "Black";
				myKing = blackKing;
			}

			if (pieces.charAt(i + 1) == 'P') piece = new Pawn(color, x, y, myKing);
			else if (pieces.charAt(i + 1) == 'N') piece = new Knight(color, x, y, myKing);
			else if (pieces.charAt(i + 1) == 'B') piece = new Bishop(color, x, y, myKing);
			else if (pieces.charAt(i + 1) == 'R') piece = new Rook(color, x, y, myKing);
			else if (pieces.charAt(i + 1) == 'Q') piece = new Queen(color, x, y, myKing);
			else if (pieces.charAt(i + 1) == 'K') continue; //safety code because string isn't removing what I want it to remove

			piecesList.add(piece);
		}*/

}



