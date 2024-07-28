// Author: Huy Huynh
// Date: 07/12/2024

package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

// This class is of the move object. It contains the behaviors of a chess move.
public abstract class Move {
    final Board board;
    final Piece movedPiece;
    final int destinationCoordinate;

    // Behavhior: this constructor constructs a new Move object.
    // Parameter:
    //      board: the game board
    //      movedPiece: the piece being moved
    //      destinationCoordinate: the coordinate of where the piece is being moved to
    private Move(final Board board, final Piece movedPiece, final int destinationCoordinate) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
    }

    // This class is of the MajorMove object. It represents a transitional move for a chess piece.
    public static final class MajorMove extends Move {

        // Behavior: this constructor constructs a new MajorMove object
        // Parameter:
        //      board: the game board
        //      movedPiece: the piece being moved
        //      destinationCoordinate: the coordinate of where the piece is being moved to
        public MajorMove(final Board board, final Piece movedPiece, final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
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
        public AttackMove(final Board board, final Piece movedPiece, final int destinationCoordinate, final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }
    }
}
