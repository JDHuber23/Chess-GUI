package chess.engine;

import java.util.ArrayList;
import application.Bishop;
import application.Knight;
import application.Pawn;
import application.Piece;
import application.Queen;
import application.Rook;

public class ChessBoard {

	enum ChessPieces {
		WPawn, WKnight, WBishop, WRook, WQueen, WKing,
		BPawn, BKnight, BBishop, BRook, BQueen, BKing
	}
	// [0,0] is bottom left corner
	private ChessPieces[][] boardArray;
	private ChessBitSet allPieces;
	private ChessBitSet whitePieces;
	private ChessBitSet blackPieces;
	private ChessBitSet whitePawns;
	private ChessBitSet blackPawns;
	private ChessBitSet whiteKnights;
	private ChessBitSet blackKnights;
	private ChessBitSet whiteBishops;
	private ChessBitSet blackBishops;
	private ChessBitSet blackRooks;
	private ChessBitSet whiteRooks;
	private ChessBitSet whiteQueens;
	private ChessBitSet blackQueens;
	private ChessBitSet whiteKing;
	private ChessBitSet blackKing;

	public ChessBoard() {
		boardArray = new ChessPieces[8][8];
		for (int i = 0; i < 8; i++) {
			boardArray[i][1] = ChessPieces.WPawn;
			boardArray[i][6] = ChessPieces.BPawn;
		}
		
				
		whitePieces = initWhitePieces();
		blackPieces = initBlackPieces();
		allPieces = initAllPieces();

		whitePawns = initWhitePawns();
		blackPawns = initBlackPawns();

		whiteKnights = initWhiteKnights();
		blackKnights = initBlackKnights();

		whiteBishops = initWhiteBishops();
		blackBishops = initBlackBishops();

		whiteRooks = initWhiteRooks();
		blackRooks = initBlackRooks();

		whiteQueens = initWhiteQueen();
		blackQueens = initBlackQueen();

		whiteKing = initWhiteKing();
		blackKing = initBlackKing();

	}
	
	public ChessBoard(ArrayList<Piece> pieces) {
		boardArray = new ChessPieces[8][8];
		whitePieces = new ChessBitSet();
		blackPieces = new ChessBitSet();
		allPieces = new ChessBitSet();
		whitePawns = new ChessBitSet();
		blackPawns = new ChessBitSet();
		whiteKnights = new ChessBitSet();
		blackKnights = new ChessBitSet();
		whiteBishops = new ChessBitSet();
		blackBishops = new ChessBitSet();
		whiteRooks = new ChessBitSet();
		blackRooks = new ChessBitSet();
		whiteQueens = new ChessBitSet();
		blackQueens = new ChessBitSet();
		whiteKing = new ChessBitSet();
		blackKing = new ChessBitSet();
		
		for (Piece p: pieces) {
			int bitLoc = (p.getY() * 8) + p.getX();
			int x = p.getX();
			int y = p.getY();
			boolean isWhite = p.getColor().equals("White");
			allPieces.set(bitLoc);
			if (isWhite) {
				whitePieces.set(bitLoc);
			} else {
				blackPieces.set(bitLoc);
			}
			
			if (p instanceof Pawn) {
				if (isWhite) {
					whitePawns.set(bitLoc);
					boardArray[x][y] = ChessPieces.WPawn;
				} else {
					blackPawns.set(bitLoc);
					boardArray[x][y] = ChessPieces.BPawn;
				}
			} else if (p instanceof Knight) {
				if (isWhite) {
					whiteKnights.set(bitLoc);
					boardArray[x][y] = ChessPieces.WKnight;
				} else {
					blackKnights.set(bitLoc);
					boardArray[x][y] = ChessPieces.BKnight;
				}
			} else if (p instanceof Bishop) {
				if (isWhite) {
					whiteBishops.set(bitLoc);
					boardArray[x][y] = ChessPieces.WBishop;
				} else {
					blackBishops.set(bitLoc);
					boardArray[x][y] = ChessPieces.BBishop;
				}
			} else if (p instanceof Rook) {
				if (isWhite) {
					whiteRooks.set(bitLoc);
					boardArray[x][y] = ChessPieces.WRook;
				} else {
					blackRooks.set(bitLoc);
					boardArray[x][y] = ChessPieces.BRook;
				}
			} else if (p instanceof Queen) {
				if (isWhite) {
					whiteQueens.set(bitLoc);
					boardArray[x][y] = ChessPieces.WQueen;
				} else {
					blackQueens.set(bitLoc);
					boardArray[x][y] = ChessPieces.BQueen;
				}
			} else { // p instanceof King
				if (isWhite) {
					whiteKing.set(bitLoc);
					boardArray[x][y] = ChessPieces.WKing;
				} else {
					blackKing.set(bitLoc);
					boardArray[x][y] = ChessPieces.BKing;
				}
			}
			
		}
		
	}
	
	@Override
	public String toString() {
		return allPieces.toString();
	}
	
	private ChessPieces[][] initBoardArray() {
		ChessPieces[][] boardArray = new ChessPieces[8][8];
		for (int i = 0; i < 8; i++) {
			boardArray[i][1] = ChessPieces.WPawn;
			boardArray[i][6] = ChessPieces.BPawn;
		}
		
		boardArray[1][0] = ChessPieces.WKnight;
		boardArray[6][0] = ChessPieces.WKnight;
		boardArray[1][7] = ChessPieces.BKnight;
		boardArray[6][7] = ChessPieces.BKnight;
		
		boardArray[2][0] = ChessPieces.WBishop;
		boardArray[5][0] = ChessPieces.WBishop;
		boardArray[2][7] = ChessPieces.BBishop;
		boardArray[5][7] = ChessPieces.BBishop;
		
		boardArray[0][0] = ChessPieces.WRook;
		boardArray[7][0] = ChessPieces.WRook;
		boardArray[0][7] = ChessPieces.BRook;
		boardArray[7][7] = ChessPieces.BRook;
		
		boardArray[3][0] = ChessPieces.WQueen;
		boardArray[3][7] = ChessPieces.BQueen;
		
		boardArray[4][0] = ChessPieces.WKing;
		boardArray[4][7] = ChessPieces.BKing;
		
		return boardArray;
	}

	private ChessBitSet initBlackKing() {
		ChessBitSet blackKing = new ChessBitSet();
		blackKing.set(60);
		return blackKing;
	}

	private ChessBitSet initWhiteKing() {
		ChessBitSet whiteKing = new ChessBitSet();
		whiteKing.set(4);
		return whiteKing;
	}

	private ChessBitSet initBlackQueen() {
		ChessBitSet blackQueen = new ChessBitSet();
		blackQueen.set(59);
		return blackQueen;
	}

	private ChessBitSet initWhiteQueen() {
		ChessBitSet whiteQueen = new ChessBitSet();
		whiteQueen.set(3);
		return whiteQueen;
	}

	private ChessBitSet initBlackRooks() {
		ChessBitSet blackRooks = new ChessBitSet();
		blackRooks.set(56);
		blackRooks.set(63);
		return blackRooks;
	}

	private ChessBitSet initWhiteRooks() {
		ChessBitSet whiteRooks = new ChessBitSet();
		whiteRooks.set(0);
		whiteRooks.set(7);
		return whiteRooks;
	}

	private ChessBitSet initBlackBishops() {
		ChessBitSet blackBishops = new ChessBitSet();
		blackBishops.set(58);
		blackBishops.set(61);
		return blackBishops;
	}

	private ChessBitSet initWhiteBishops() {
		ChessBitSet whiteBishops = new ChessBitSet();
		whiteBishops.set(2, 5);
		return whiteBishops;
	}

	private ChessBitSet initBlackKnights() {
		ChessBitSet blackKnights = new ChessBitSet();
		blackKnights.set(57);
		blackKnights.set(62);
		return blackKnights;
	}

	private ChessBitSet initWhiteKnights() {
		ChessBitSet whiteKnights = new ChessBitSet();
		whiteKnights.set(1);
		whiteKnights.set(6);
		return whiteKnights;
	}

	private ChessBitSet initBlackPawns() {
		ChessBitSet blackPawns = new ChessBitSet();
		blackPawns.set(48, 56);
		return blackPawns;
	}

	private ChessBitSet initWhitePawns() {
		ChessBitSet whitePawns = new ChessBitSet();
		whitePawns.set(8, 16);
		return whitePawns;
	}

	private ChessBitSet initAllPieces() {
		ChessBitSet allPieces = new ChessBitSet();
		allPieces.set(0, 16);
		allPieces.set(48, 64);
		return allPieces;
	}

	private ChessBitSet initWhitePieces() {
		ChessBitSet whitePieces = new ChessBitSet();
		whitePieces.set(0, 17);
		return whitePieces;
	}

	private ChessBitSet initBlackPieces() {
		ChessBitSet blackPieces = new ChessBitSet();
		blackPieces.set(48, 64);
		return blackPieces;
	}

}
