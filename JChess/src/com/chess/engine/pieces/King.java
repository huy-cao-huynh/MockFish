// Author: Huy Huynh
// Date: 7/27/2024

package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MajorAttackMove;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// This class is of the King object. It contains the behavior of the king chess piece.
public class King extends Piece {

    private final static int[] CANDIDATE_MOVE_CANDIDATE = {-9, -8, -7, - 1, 1, 7, 8, 9};

    // Behavior: this constructor constructs a new king object
    // Parameter:
    //      piecePosition: the position of the piece on the board
    //      pieceAlliance: the alliance of the piece, i.e. white or black
    public King(final Alliance pieceAlliance, final int piecePosition) {
        super(PieceType.KING, piecePosition, pieceAlliance, true);
    }

    public King(final Alliance pieceAlliance,
                final int piecePosition,
                final boolean isFirstMove) {
        super(PieceType.KING, piecePosition, pieceAlliance, isFirstMove);
    }

    // Behavior: this method calculates a list of legal moves for the piece. Valid spaces must be on the board. If a space
    //           is occupied, the space is only valid if the alliance is opposite of the current piece
    // Return: returns a list of legal moves
    // Parameter:
    //      board: the game board
    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : CANDIDATE_MOVE_CANDIDATE) {
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;

            if (isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                    isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
                continue;
            }


            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

                if (!candidateDestinationTile.isTileOccupied()) {
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate)); // adds a legal move to the list if the tile is unoccupied
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                    if (this.pieceAlliance != pieceAtDestination.getPieceAlliance()) {
                        legalMoves.add(new MajorAttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination)); // adds a legal attacking move to the list if the tile is occupied by a piece of opposite alliance
                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    // Behavior: creates a new piece in the location where the piece has been moved
    // Return: returns the new piece created
    // Parameter:
    //      move: the move on the piece
    @Override
    public King movePiece(final Move move) {
        return new King(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public String toString() {
        return PieceType.KING.toString();
    }

    // Behavior: checks whether the piece is in the first column of the board and if a legal move can be made
    // Return: whether or not a move can be made
    // Parameter:
    //      currentPosition: the current position of the piece
    //      candidateOffset: the position the piece could move to
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == -1 ||
                candidateOffset == 7);
    }

    // Behavior: checks whether the piece is in the eighth column of the board and if a legal move can be made
    // Return: whether or not a move can be made
    // Parameter:
    //      currentPosition: the current position of the piece
    //      candidateOffset: the position the piece could move to
    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 9 || candidateOffset == 1 ||
                candidateOffset == -7);
    }
}
