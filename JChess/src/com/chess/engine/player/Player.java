// Author: Huy Huynh
// Date: 7/30/2024

package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// This class is of the Player object. It represents a player in the game chess.
public abstract class Player {
    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    private final boolean isInCheck;

    // Behavior: constructs a new player object
    // Parameter:
    //      board: the state of the game board
    //      legalMoves: a collection of the players legal moves
    //      opponentMoves: a collection of the opponents legal moves
    Player(final Board board,
           final Collection<Move> legalMoves,
           final Collection<Move> opponentMoves) {

        this.board = board;
        this.playerKing = establishKing();
        this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
        this.legalMoves = ImmutableList.copyOf(Iterables.concat(legalMoves, calculateKingCastles(legalMoves, opponentMoves)));
    }


    // Behavior: returns the player king
    public King getPlayerKing() {
        return this.playerKing;
    }

    // Behavior: returns the list of legal moves
    public Collection<Move> getLegalMoves() {
        return this.legalMoves;
    }

    // Behavior: this method calculates a list of opponent attack moves at a given tile
    // Return: returns a list of attack moves
    // Parameter:
    //      piecePosition: the position being checked
    //      opponentMoves: a collection of opponent attack moves
    protected static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> opponentMoves) {
        final List<Move> attackMoves = new ArrayList<>();
        for (final Move move : opponentMoves) {
            if (piecePosition == move.getDestinationCoordinate()) {
                attackMoves.add(move);
            }
        }
        return ImmutableList.copyOf(attackMoves);
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

    // Behavior: checks if a given move is within the legal move set
    public boolean isMoveLegal(final Move move) {
        return this.legalMoves.contains(move);
    }

    // Behavior: returns if the player's king is in check
    public boolean isInCheck() {
        return this.isInCheck;
    }

    // Behavior: returns if the player's king has been checkmated
    public boolean isInCheckMate() {
        return this.isInCheck && hasEscapeMoves(); // the king is in check and there are no escape moves
    }

    // Behavior: returns false is there are escape moves and true if there are no escape moves
    protected boolean hasEscapeMoves() {
        for (final Move move : this.legalMoves) {
            final MoveTransition transition = makeMove(move);
            if (transition.getMoveStatus().isDone()) {
                return false;
            }
        }
        return true;
    }

    // Behavior: returns if the player's king is in a stalemate
    public boolean isInStaleMate() {
        return !this.isInCheck && hasEscapeMoves(); // the king is not in check but there are no moves
    }


    public boolean isCastled() {
        return this.playerKing.isCastled();
    }

    public boolean isKingSideCastleCapable() {
        return this.playerKing.isKingSideCastleCapable();
    }

    public boolean isQueenSideCastleCapable() {
        return this.playerKing.isQueenSideCastleCapable();
    }

    // Behavior: checks is a move is legal then transitions the board into the game state where the move has either
    //           happened or not happened
    // Return: returns the move transition after a player has moved/not moved
    // Parameter:
    //      move: the move being checked
    public MoveTransition makeMove(final Move move) {
        if (!isMoveLegal(move)) {
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
        }

        final Board transitionBoard = move.execute();
        final Collection<Move> kingAttacks = Player.calculateAttacksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
                transitionBoard.currentPlayer().getLegalMoves());
        if (!kingAttacks.isEmpty()) {
            return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }

        return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
    }

    public abstract Collection<Piece> getActivePieces();
    public abstract Alliance getAlliance();
    public abstract Player getOpponent();
    protected abstract Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentLegals);
}
