# Chess GUI + WIP Chess Engine
Made with Java and JavaFX

# *Chess GUI*

 I made this Chess GUI in JavaFX for my final project in my Java II class.
I got the images from Wikipedia and made the sounds using audacity and my
knuckle hitting the back of my phone.

 The GUI lays out individual piece objects on a gridpane, that when clicked
calculates its own legal moves based on the position of all of the other
objects and displays them as clickable green circles where they would move the 
piece(s) or red circles if they are attacking. 

The GUI handles nearly all the rules of Chess, including en-passant, 
castling, check, check-mate, stale-mate, pawns initial 2 moves, pawns 
queening, capturing... etc.

Rules that were left out on purpose for simplicity:
  1. Pawns can also become rooks, knights, or bishops when they reach the 
  other side of the board
    It's usually best to become a queen
  2. 3 move repetition draw
  3. 50 moves without a pawn move or a capture draw
  4. Move timer

Some of the parts I struggled with were:
  1. Checking if the King is in check, because I was orginally checking by
  getting the opponents' moves and seeing if they overlapped coordinates with
  the King.
  2. Checking if a move is legal before allowing it as an option,
  because a move is illegal if it puts the king in check.
  3. 1 and 2 combined resulted in a stack overflow exception because it created 
  an infinite loop of getting the opposing side's moves to check if a move 
  put the King in check
  4. Checking if a player had won or stalemated
  
To solve the above issues I:
  1. Instead of checking if other pieces could move towards the King, 
  check every possible position a King could be attacked
  from. 
    This solution took hundreds of more lines of codes, but was much faster.
  2. After the check system was reworked, I stored a reference to the ally
  King for every piece, and then had the King verify if a move put it in check
  before returning the list of moves for that piece.
    A more efficient solution might've been to check if moves were legal after
    they had been returned to the chess game caller, as I wouldn't need to store
    the King as a reference and I wouldn't have to copy the check code a bunch of 
    times, BUT if I ever asked a piece for it's legal moves for any other reason
    the piece would return illegal moves.
  3. Fixed 1 and 2, no more problem.
  4. Check if the King is in check and/or if the size of the list of 
  total legal moves for a player equals 0
    If both are true, checkmate! 
    If just check, then check
    If the player has no moves but not in check, then stalemate.
    
  Looking back, I could have designed this GUI with looser coupling between the
interface and the mechanics of the game. I created an object for each type of
piece, and that object had to meet the functionality of the interface and the
underlying mechanics and rules at the same time. If I were to redo it, I'd have
an interface that takes in a much simpler board representation input, like a 
bitboard, then reflects what that input says. When a piece is clicked,  the game
would calculate what piece it is and what valid moves should be shown and when 
a move is clicked, the game sends a new input.

# *WIP Chess ENGINE*
  After learning about data structures, I decided to take a whack at creating
an engine.

My initial plan was to take a board position and create a node. Then create child
nodes representing each possible move from the position. For each of the child nodes,
create child nodes based on the opponent's possible moves. Then evaluate the board
by assuming the opponent would play the move that would give themselves the best possible
score, and alternating which score was logically the best depending on which player's
move it was, moving up the tree until we find the best possible move assuming the
opponent plays ideally. (Recalculating after each move)

Then I added an improvement to the algorithm called alpha-beta pruning,
which logically deduces branches of moves that don't need to be checked for
the min or max value.

Unfortunately I've run into huge design flaws. I initially created a tree of nodes 
that contained a copy of a board given a set of moves, the move that got to this 
position in the tree, the score of that position, and whose turn it is to move.

Here are the reasons that doesn't work:
  1. Too much data is stored and the tree will never see enough moves out to be good
  2. The "board" is a 2-dimensional array of references to pieces that do not agree
  with where the copy of the board says they should be on the board.
  3. Since the pieces don't think they are where the copy of the board says they are,
  the next set of moves that is acquired to create the next branch creates impossible
  positions
  4. The solution to this problem is to create copies of each piece that correctly 
  exhibits the behavior and data you'd expect at a given node, but this amplifies
  problem 1. enormously.
  
It was at this point I decided to see how other people have approached this problem.

I turned to https://www.chessprogramming.org/ 

 The real solution is for the engine to represent the board better, using bitboards.
Bitboards can use sets of 64 bits to represent different parts of the board using
much less memory. The hard part is figuring out how to set up the bitboards to get
each possible move and to translate from bitboard to something that my GUI can understand.

This is where the project has stalled after I ripped out hours of broken code and
commented out good code that had to be reimplemented. I'm considering investigating
creating an actual AI instead.

