// Author: Huy Huynh
// Date: 7/30/2024

package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// This class if of the WhitePlayer object. It represents the behavior of the white player in chess.
public class WhitePlayer extends Player {

    // Behavior: constructs a new WhitePlayer object
    // Parameter:
    //      board: the state of the game board
    //      whiteStandardLegalMoves: the white pieces legal moves
    //      blackStandardLegalMoves: the black pieces legal moves
    public WhitePlayer(final Board board,
                       final Collection<Move> whiteStandardLegalMoves,
                       final Collection<Move> blackStandardLegalMoves) {
        super(board, whiteStandardLegalMoves, blackStandardLegalMoves);
    }

    // Behavior: returns a list of all the active white pieces on the board
    // Return: returns a list of all the active white pieces
    // Parameter: this method accepts no parameters
    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    // Behavior: returns the alliance of the player
    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    // Behavior: returns the opponent of the player
    @Override
    public Player getOpponent() {
        return this.board.blackPlayer();
    }

    // Behavior: returns a list of all castle moves for the white player
    // Return: returns the list of castle moves
    // Parameter:
    //      playerLegals: the current players current possible moves
    //      opponentLegals: the opponent players current possible moves
    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals,
                                                    final Collection<Move> opponentLegals) {

        final List<Move> kingCastles = new ArrayList<>();

        if (this.playerKing.isFirstMove() && !this.isInCheck()) {
            // whites king side castle
            if (!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(63);

                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (Player.calculateAttacksOnTile(61, opponentLegals).isEmpty() &&
                            Player.calculateAttacksOnTile(62, opponentLegals).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new Move.KingSideCastleMove(this.board, this.playerKing, 62, (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 61));
                    }
                }
            }

            // whites queen side castle
            if (!this.board.getTile(59).isTileOccupied() &&
                    !this.board.getTile(58).isTileOccupied() &&
                    !this.board.getTile(57).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(56);

                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() &&
                        Player.calculateAttacksOnTile(58, opponentLegals).isEmpty() &&
                        Player.calculateAttacksOnTile(59, opponentLegals).isEmpty() &&
                        rookTile.getPiece().getPieceType().isRook()) {
                    kingCastles.add(new Move.QueenSideCastleMove(this.board, this.playerKing, 58, (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 59));
                }
            }
        }

        return ImmutableList.copyOf(kingCastles);
    }

    @Override
    public String toString() {
        return Alliance.WHITE.toString();
    }

}
