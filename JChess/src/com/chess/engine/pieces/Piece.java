// Author: Huy Huynh
// Date: 07/12/2024

package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;

// This class if of the Piece object. It represents a chess piece.
public abstract class Piece {

    protected final int piecePosition;
    protected final Alliance pieceAlliance;
    protected final boolean isFirstMove;

    // Behavior: constructs a new chess piece
    // Parameter:
    //      piecePosition: the position of the piece on the board
    //      pieceAlliance: the alliance of the piece, i.e. white or black
    Piece(final int piecePosition, final Alliance pieceAlliance) {
        this.piecePosition = piecePosition;
        this.pieceAlliance = pieceAlliance;
        this.isFirstMove = false; // todo more work here
    }

    public int getPiecePosition() {
        return this.piecePosition;
    }

    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }

    public boolean isFirstMove() {
        return this.isFirstMove;
    }

    // Behavior: calculates a list of the legal moves for the given piece
    public abstract Collection<Move> calculateLegalMoves(final Board board);

    public enum PieceType {
        PAWN("P"),
        KNIGHT("N"),
        BISHOP("B"),
        ROOK("R"),
        QUEEN("Q"),
        KING("K");

        private String pieceName;

        PieceType(final String pieceName) {
            this.pieceName = pieceName;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }
    }
}
