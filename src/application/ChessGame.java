package application;

import java.io.File;
import java.util.ArrayList;

import javax.swing.Icon;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ChessGame extends Application {

	private GridPane board;
	private BorderPane root;
	private ArrayList<Piece> pieces;
	private ArrayList<Piece> removedPieces;
	private ArrayList<StackPane> availableMoves;
	private boolean whiteTurn = true;
	private boolean whiteInCheck = false;
	private boolean blackInCheck = false;
	private King whiteKing;
	private King blackKing;

	@Override
	public void start(Stage primaryStage) throws Exception {
		//initialize chess board
		board = initBoard();
		pieces = initPieces();
		availableMoves = new ArrayList<>();
		removedPieces = new ArrayList<>();

		// setting up the board
		// initialize background
		root = new BorderPane(board);
		root.setBackground(new Background(new BackgroundFill(Color.BURLYWOOD, CornerRadii.EMPTY, Insets.EMPTY)));

		board.setAlignment(Pos.CENTER);
		
		Text text = new Text("HIGH QUALITY");
		text.setFont(Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 100));
		
		root.getChildren().add(text);
		
		TranslateTransition translTrans = new TranslateTransition(Duration.millis(1000));
		translTrans.setNode(text);
		translTrans.setFromX(0);
		translTrans.setFromY(900);
		translTrans.setToX(1000);
		translTrans.setToY(0);
		
		
		//root.getChildren().remove(text);
		
		// add all the pieces to the board
		for (Piece p: pieces) {
			board.add(p, p.getX(), p.getY());
		}

		Scene game = new Scene(root, 1000, 900);

		// main menu screen
		BorderPane menu = new BorderPane();
		menu.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		StackPane sp = new StackPane();
		String path = "/images/misc/Every Piece.png";
		ImageView image = new ImageView(new Image(Icon.class.getResource(path).toExternalForm()));
		image.setFitHeight(315);
		image.setFitWidth(900);
		Button start = new Button("Start Game");
		start.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				String musicFile = "Setting up pieces.mp3"; 

				Media sound = new Media(new File(musicFile).toURI().toString());
				MediaPlayer mediaPlayer = new MediaPlayer(sound);
				mediaPlayer.play();
				primaryStage.setScene(game);
				translTrans.play();
			}
		});
		sp.getChildren().addAll(image, start);
		Text title = new Text("Quality Chess");
		title.setFont(Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 100));
		menu.setTop(title);
		Insets topM = new Insets(250, 0 , 0 ,0);
		BorderPane.setMargin(title, topM);
		BorderPane.setAlignment(title, Pos.TOP_CENTER);
		menu.setCenter(sp);
		Scene scene = new Scene(menu , 1000, 900);

		primaryStage.setTitle("Quality Chess");
		primaryStage.getIcons().add(new Image(Icon.class.getResourceAsStream("/images/pieces/White King.png")));
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private ArrayList<Piece> initPieces() {
		ArrayList<Piece> pieces = new ArrayList<>();


		String black = "Black";
		String white = "White";

		//kings
		blackKing = new King(black, 4, 0);
		whiteKing = new King(white, 4, 7);

		blackKing.setOnMouseClicked(clickMouse(blackKing, board));
		whiteKing.setOnMouseClicked(clickMouse(whiteKing, board));

		pieces.add(blackKing);
		pieces.add(whiteKing);

		for (int i = 0; i < 8; i++) {
			Pawn bp = new Pawn(black, i, 1, blackKing);
			Pawn wp = new Pawn(white, i, 6, whiteKing);

			bp.setOnMouseClicked(clickMouse(bp, board));
			wp.setOnMouseClicked(clickMouse(wp, board));

			pieces.add(bp);
			pieces.add(wp);
		}

		//knights
		Knight bK = new Knight(black, 1, 0, blackKing);
		Knight bK2 = new Knight(black, 6, 0, blackKing);
		Knight wK = new Knight(white, 1, 7, whiteKing);
		Knight wK2 = new Knight(white, 6, 7, whiteKing);

		bK.setOnMouseClicked(clickMouse(bK, board));
		bK2.setOnMouseClicked(clickMouse(bK2, board));
		wK.setOnMouseClicked(clickMouse(wK, board));
		wK2.setOnMouseClicked(clickMouse(wK2, board));

		pieces.add(bK);
		pieces.add(bK2);
		pieces.add(wK);
		pieces.add(wK2);

		//rooks
		Rook bR = new Rook(black, 0, 0, blackKing);
		Rook bR2 = new Rook(black, 7, 0, blackKing);
		Rook wR = new Rook(white, 0, 7, whiteKing);
		Rook wR2 = new Rook(white, 7, 7, whiteKing);

		bR.setOnMouseClicked(clickMouse(bR, board));
		bR2.setOnMouseClicked(clickMouse(bR2, board));
		wR.setOnMouseClicked(clickMouse(wR, board));
		wR2.setOnMouseClicked(clickMouse(wR2, board));

		pieces.add(bR);
		pieces.add(bR2);
		pieces.add(wR);
		pieces.add(wR2);


		//bishops
		Bishop bB = new Bishop(black, 2, 0, blackKing);
		Bishop bB2 = new Bishop(black, 5, 0, blackKing);
		Bishop wB = new Bishop(white, 2, 7, whiteKing);
		Bishop wB2 = new Bishop(white, 5, 7, whiteKing);

		bB.setOnMouseClicked(clickMouse(bB, board));
		bB2.setOnMouseClicked(clickMouse(bB2, board));
		wB.setOnMouseClicked(clickMouse(wB, board));
		wB2.setOnMouseClicked(clickMouse(wB2, board));

		pieces.add(bB);
		pieces.add(bB2);
		pieces.add(wB);
		pieces.add(wB2);

		//queens
		Queen bq = new Queen(black, 3, 0, blackKing);
		Queen wq = new Queen(white, 3, 7, whiteKing);

		bq.setOnMouseClicked(clickMouse(bq, board));
		wq.setOnMouseClicked(clickMouse(wq, board));

		pieces.add(bq);
		pieces.add(wq);

		return pieces;
	}

	private GridPane initBoard() {
		GridPane board = new GridPane();


		for (int i = 0; i < 8; i++) {
			board.getColumnConstraints().add(new ColumnConstraints(100));
			board.getRowConstraints().add(new RowConstraints(100));
		}


		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Color color = Color.WHITESMOKE;
				if ((i + j) % 2 == 1) color = Color.LIGHTGRAY;

				StackPane sp = new StackPane();
				sp.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent mouseEvent) {
						if (availableMoves.size() > 0) {
							board.getChildren().removeAll(availableMoves);
						}
						for (Piece p: pieces) {
							p.isSelected(false);
						}
					}
				});
				sp.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
				board.add(sp, j, i);
				GridPane.setFillHeight(sp, true);
				GridPane.setFillWidth(sp, true);
			}
		}
		//board.setGridLinesVisible(true);

		return board;
	}

	private EventHandler<MouseEvent> clickMouse(Piece piece, GridPane board) {
		EventHandler<MouseEvent> mousePressHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {
					for (Piece p: pieces) {
						p.isSelected(false);
					}
					
					if (availableMoves.size() > 0) {
						board.getChildren().removeAll(availableMoves);
						availableMoves.clear();
					}

					if ((piece.color.equals("White") && whiteTurn) ||
							piece.color.equals("Black") && !whiteTurn) {

						piece.isSelected(true);
						Piece[][] boardArray = Piece.readBoard(pieces);

						// get the moves list for this piece
						ArrayList<int[]> moves = piece.getMoves(boardArray);

						for (int i = 0; i < moves.size(); i++) {
							int xPos = moves.get(i)[0];
							int yPos = moves.get(i)[1];

							StackPane available = new StackPane();
							Circle move = new Circle(25);
							move.setFill(Color.GREEN);
							available.getChildren().add(move);
							availableMoves.add(available);

							available.setOnMouseClicked(initAvailableMove(piece, available, moves.get(i)[2]));

							if (moves.get(i)[2] == 1 || moves.get(i)[2] == 2) move.setFill(Color.RED);

							board.add(available, xPos, yPos);
						}
					}
				}	
			}
		};


		return mousePressHandler;
	}

	private EventHandler<MouseEvent> initAvailableMove(Piece piece, StackPane available, int moveType) {
		EventHandler<MouseEvent> click = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton() == MouseButton.PRIMARY) {
					board.getChildren().removeAll(availableMoves);
					availableMoves.clear();

					for (Piece p: pieces) {
						p.isSelected(false);
						if (p instanceof Pawn) {
							((Pawn) p).canEnPassant = false;
						}
					}

					Piece[][] boardArray = Piece.readBoard(pieces);

					int newX = GridPane.getColumnIndex(available);
					int newY = GridPane.getRowIndex(available);
					int deltaY = 0;

					String soundFile = "";

					switch (moveType) {
					case 1: // simple attack
						soundFile = "Taking a piece.mp3";
						removedPieces.add(boardArray[newY][newX]);
						board.getChildren().remove(boardArray[newY][newX]);
						pieces.remove(boardArray[newY][newX]);

						break;
					case 2: // en passant
						soundFile = "Taking a piece.mp3";

						if (piece.getColor().equals("Black")) deltaY = -1;
						else deltaY = 1;

						removedPieces.add(boardArray[newY + deltaY][newX]);
						board.getChildren().remove(boardArray[newY + deltaY][newX]);
						pieces.remove(boardArray[newY + deltaY][newX]);

						break;
					case 3: // castling kingside
						soundFile = "Castling.mp3";

						Rook rook = (Rook)boardArray[newY][7];
						GridPane.setColumnIndex(rook, 5);
						GridPane.setRowIndex(rook, newY);
						rook.setX(5);
						rook.setY(newY);

						break;
					case 4: // castling queenside
						soundFile = "Castling.mp3";

						Rook rook2 = (Rook)boardArray[newY][0];
						GridPane.setColumnIndex(rook2, 3);
						GridPane.setRowIndex(rook2, newY);
						rook2.setX(3);
						rook2.setY(newY);

						break;
					default: //normal move
						soundFile = "Moving a piece better.mp3";

					}

					Media sound = new Media(new File(soundFile).toURI().toString());
					MediaPlayer mediaPlayer = new MediaPlayer(sound);
					mediaPlayer.play();

					GridPane.setColumnIndex(piece, newX);
					GridPane.setRowIndex(piece, newY);
					piece.setX(newX);
					piece.setY(newY);

					boardArray = Piece.readBoard(pieces);
					
					whiteInCheck = whiteKing.check(boardArray);
					blackInCheck = blackKing.check(boardArray);
					
					if (whiteInCheck || blackInCheck) {
						System.out.println("CHECK");
						String soundFile1 = "Check.mp3";
						Media sound1 = new Media(new File(soundFile1).toURI().toString());
						MediaPlayer mediaPlayer1 = new MediaPlayer(sound1);
						mediaPlayer1.play();
					} else {
						root.setBackground(new Background(new BackgroundFill(Color.BURLYWOOD, CornerRadii.EMPTY, Insets.EMPTY)));
					}

					if (!whiteTurn) { // black just moved\
						boolean noWhiteMoves = true;		
						for (Piece p : pieces) {
							if (p.color.equals("White") && p.getMoves(boardArray).size() > 0) {
								noWhiteMoves = false;
								break;
							}
						}
						if (whiteInCheck) {
							root.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
						}
						if (noWhiteMoves && whiteInCheck) {
							restart("MATE! Black wins");
						}
						if (noWhiteMoves && !whiteInCheck) {
							restart("STALEMATE :(");
							
						}
					} else { // white just moved
						boolean noBlackMoves = true;

						for (Piece p: pieces) {
							if (p.color.equals("Black") && p.getMoves(boardArray).size() > 0) {
								noBlackMoves = false;
								break;
							}
						}
						if (blackInCheck) {
							root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
						}
						if (noBlackMoves && blackInCheck) {
							restart("MATE! White wins");
						}
						if (noBlackMoves && !blackInCheck) {
							restart("STALEMATE :(");
						}
					}
					

					whiteTurn = !whiteTurn;

				}
			}
		};
		return click;
	}
	
	public void restart(String ending) {
		root.getChildren().remove(board);
		root.setBackground(new Background(new BackgroundFill(Color.BURLYWOOD, CornerRadii.EMPTY, Insets.EMPTY)));
		Text text = new Text(ending);
		text.setFont(Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 100));
		root.setTop(text);
		Insets margin = new Insets(200);
		BorderPane.setMargin(text, margin);
		
		Button playAgain = new Button("Play again?");
		playAgain.setOnMouseClicked(new EventHandler<MouseEvent> () {
			@Override
			public void handle(MouseEvent mouseEvent) {				
				root.getChildren().clear();
				
				//initialize chess board
				board = initBoard();
				pieces = initPieces();
				availableMoves = new ArrayList<>();
				removedPieces = new ArrayList<>();
				
				// add all the pieces to the board
				for (Piece p: pieces) {
					board.add(p, p.getX(), p.getY());
				}
				
				root.setCenter(board);
				board.setAlignment(Pos.CENTER);
				
				whiteTurn = true;
				whiteInCheck = false;
				blackInCheck = false;
				
			}
		});
		
		Button quit = new Button("Exit");
		quit.setOnMouseClicked(new EventHandler<MouseEvent> () {
			@Override
			public void handle(MouseEvent mouseEvent) {
				System.exit(0);
			}
		});
		
		root.setLeft(playAgain);
		root.setRight(quit);
		BorderPane.setMargin(playAgain, margin);
		BorderPane.setMargin(quit, margin);

	}


	public static void main(String[] args) {
		launch(args);

	}

}
