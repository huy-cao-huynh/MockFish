// Author: Huy Huynh
// Date: 07/12/2024

package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.chess.engine.board.Board.Builder;

// This class is of the move object. It contains the behaviors of a chess move.
public abstract class Move {
    final Board board;
    final Piece movedPiece;
    final int destinationCoordinate;

    // Behavior: this constructor constructs a new Move object.
    // Parameter:
    //      board: the game board
    //      movedPiece: the piece being moved
    //      destinationCoordinate: the coordinate of where the piece is being moved to
    private Move(final Board board,
                 final Piece movedPiece,
                 final int destinationCoordinate) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
    }

    // Behavior: this method is a getter for the destination coordinate
    public int getDestinationCoordinate() {
        return this.destinationCoordinate;
    }

    public abstract Board execute();

    // This class is of the MajorMove object. It represents a transitional move for a chess piece.
    public static final class MajorMove extends Move {

        // Behavior: this constructor constructs a new MajorMove object
        // Parameter:
        //      board: the game board
        //      movedPiece: the piece being moved
        //      destinationCoordinate: the coordinate of where the piece is being moved to
        public MajorMove(final Board board,
                         final Piece movedPiece,
                         final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }


        // Behavior: this method executes a move and creates a new game board based on the move made
        // Return: returns the new move
        // Parameter: this method accepts no parameters
        @Override
        public Board execute() {
            final Builder builder = new Builder();

            // places all the current players pieces that are not the moved piece
            for (final Piece piece : this.board.currentPlayer().getActivePieces()) {
                // TODO hashcode and equals for pieces
                if (!this.movedPiece.equals(piece)) {
                    builder.setPiece(piece);
                }
            }

            // places all the opposing players pieces
            for (final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }

            // moves the moved piece
            builder.setPiece(null); // TODO
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());

            return builder.build();
        }
    }

    // This class if of the AttackMove object. It represents an attack move for a chess piece.
    public static final class AttackMove extends Move {
        final Piece attackedPiece;

        // Behavior: this constructor constructs a new AttackMove object
        // Parameter:
        //      board: the game board
        //      movedPiece: the piece being moved
        //      destinationCoordinate: the coordinate of where the piece is being moved to
        //      attackedPiece: the piece being captured
        public AttackMove(final Board board,
                          final Piece movedPiece,
                          final int destinationCoordinate,
                          final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public Board execute() {
            return null;
        }
    }
}
