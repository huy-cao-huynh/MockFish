# MockFish
My exploration of the game of chess.

Overview:
-
This project is a fully functioning chess engine implemented in Java. The engine uses a Mini-Max/Alpha-Beta pruning algorithm to evaluate board states and determine the best move for each player. The engine is built to focus on immutability: every time a move is made, a new board is created, with updated piece objects at the respective positions. This ensures that the state of the game can be traced back without side effects, making debugging and testing easier.

Features:
-
* Mini-Max Algorithm: Implements the Mini-Max algorithm with Alpha-Beta pruning for efficient move evaluation.
* Immutability: Each move generates a new immutable board, ensuring the previous game state remains unchanged.
* Object-Oriented Design: Designed with clean and well-structured classes for Pieces, Tiles, the Board, and Players.
* Move Generation: The engine generates all legal moves for a given board state, including special moves like castling, en passant, and pawn promotion.
* Check, Checkmate, and Stalemate Detection: Automatically detects when a player is in check, checkmate, or stalemate.
* Performance Optimization: Alpha-Beta pruning drastically reduces the number of nodes that need to be evaluated, improving search efficiency.
* Customizability: The design allows easy extensions and modifications, such as editing piece appearances or support for chess variants.

Project Structure:
-
The project follows an object-oriented approach. Some of the key components are:
* Piece: A superclass representing individual chess pieces (King, Queen, Rook, etc.).
* Tile: Represents a tile on the chessboard and stores the piece (if any) that occupies it.
* Board: Represents the chessboard and contains the methods for legal move generation and game state updates.
* Player: Represents the players (white and black), handling their respective pieces and moves.
* Move: Represents a move made by a player, including start and destination tiles.

Algorithms:
-
Mini-Max with Alpha-Beta Pruning: This engine evaluates all possible moves for both players, aiming to minimize the loss potential for the player in control while maximizing the potential gain. I chose this algorithm because chess is a zero-sum game. The alpha-beta pruning technique eliminates unnecessary branches (when the outcome is already decided) to improve performance.
* Depth Limit: the depth of the algorithm can be adjusted for difficulty tuning
* Heuristic Evaluation: the engine uses heuristic evaluation functions to assess the strength of the current board position based on several factors (number of pieces, pawn positions, castling availability, etc.).

Future Plans:
-
There are a few improvements I'm planning/additions I would like to make some time in the future:
* Make an integrated database with openings/book moves to speed up the beginning phase of the game.
* Add endgame tablebases for perfect play in simplified positions
* Edit the heuristic evaluation with more in-depth and nuanced factors that greatly improve the ELO of the engine.



