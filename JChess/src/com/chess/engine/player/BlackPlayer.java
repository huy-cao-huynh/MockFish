package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;

import java.util.Collection;
import java.util.List;

// This class if of the BlackPlayer object. It represents the behavior of the black player in chess.
public class BlackPlayer extends Player {

    // Behavior: constructs a new BlackPlayer object
    // Parameter:
    //      board: the state of the game board
    //      whiteStandardLegalMoves: the white pieces legal moves
    //      blackStandardLegalMoves: the black pieces legal moves
    public BlackPlayer(final Board board,
                       final Collection<Move> whiteStandardLegalMoves,
                       final Collection<Move> blackStandardLegalMoves) {
        super(board, blackStandardLegalMoves, whiteStandardLegalMoves);
    }

    // Behavior: returns a list of all the active black pieces on the board
    // Return: returns a list of all the active black pieces
    // Parameter: this method accepts no parameters
    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    // Behavior: returns the alliance of the player
    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    // Behavior: returns the opponent of the player
    @Override
    public Player getOpponent() {
        return this.board.whitePlayer();
    }


}
