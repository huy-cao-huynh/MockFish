package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
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

    // Behavior: returns a list of all castle moves for the black player
    // Return: returns the list of castle moves
    // Parameter:
    //      playerLegals: the current players current possible moves
    //      opponentLegals: the opponent players current possible moves
    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals,
                                                    final Collection<Move> opponentLegals) {

        final List<Move> kingCastles = new ArrayList<Move>();

        if (this.playerKing.isFirstMove() && !this.isInCheck()) {
            // blacks king side castle
            if (!this.board.getTile(5).isTileOccupied() && !this.board.getTile(6).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(7);

                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (Player.calculateAttacksOnTile(5, opponentLegals).isEmpty() &&
                            Player.calculateAttacksOnTile(6, opponentLegals).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()) {
                        if(!BoardUtils.isKingPawnTrap(this.board, this.playerKing, 12)) {
                            kingCastles.add(new Move.KingSideCastleMove(this.board, this.playerKing, 6, (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 5));

                        }
                    }
                }
            }

            // blacks queen side castle
            if (!this.board.getTile(1).isTileOccupied() &&
                    !this.board.getTile(2).isTileOccupied() &&
                    !this.board.getTile(3).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(0);

                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() &&
                        Player.calculateAttacksOnTile(2, opponentLegals).isEmpty() &&
                        Player.calculateAttacksOnTile(3, opponentLegals).isEmpty() &&
                        rookTile.getPiece().getPieceType().isRook()) {
                    if(!BoardUtils.isKingPawnTrap(this.board, this.playerKing, 12)) {
                        kingCastles.add(new Move.QueenSideCastleMove(this.board, this.playerKing, 2, (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 3));
                    }
                }
            }
        }
        return ImmutableList.copyOf(kingCastles);
    }

    @Override
    public String toString() {
        return Alliance.BLACK.toString();
    }

}
