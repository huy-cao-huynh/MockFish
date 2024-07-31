// Author: Huy Huynh
// Date: 7/30/2024

package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;

import java.util.Collection;

// This class is of the Player object. It represents a player in the game chess.
public abstract class Player {
    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;

    // Behavior: constructs a new player object
    // Parameter:
    //      board: the state of the game board
    //      legalMoves: a collection of the players legal moves
    //      opponentMoves: a collection of the opponents legal moves
    Player(final Board board, final Collection<Move> legalMoves, final Collection<Move> opponentMoves) {
        this.board = board;
        this.playerKing = establishKing();
        this.legalMoves = legalMoves;

    }

    // Behavior: this method finds the king piece if there is one
    // Exception: this method throws a RuntimeException if no king piece is found
    // Return: returns the king piece found
    // Parameter: this method accepts no parameters
    private King establishKing() {
        for (final Piece piece : getActivePieces()) {
            if(piece.getPieceType().isKing()) {
                return (King) piece;
            }
        }
        throw new RuntimeException("There's no king! This is not a valid board.");
    }

    public boolean isMoveLegal(final Move move) {
        return this.legalMoves.contains(move);
    }

    public boolean isInCheck() {
        return false;
    }

    public boolean isInCheckMate() {
        return false;
    }

    public boolean isInStaleMate() {
        return false;
    }

    public boolean isCastled() {
        return false;
    }

    public MoveTransition makeMove(final Move move) {
        return null;
    }

    public abstract Collection<Piece> getActivePieces();
    public abstract Alliance getAlliance();
    public abstract Player getOpponent();
}
