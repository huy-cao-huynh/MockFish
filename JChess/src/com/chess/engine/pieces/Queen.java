// Author: Huy Huynh
// Date: 7/27/2024

package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Queen extends Piece{
    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-9, 8, -7, -1, 1, 7, 8, 9};

    Queen(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    // Behavior: this method calculates a list of legal moves for the piece. Valid spaces must be on the board. If a space
    //           is occupied, the space is only valid if the alliance is opposite of the current piece
    // Return: returns a list of legal moves
    // Parameter:
    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES) { // loops through every possible direction
            int candidateDestinationCoordinate = this.piecePosition;

            while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) { // continues looping as long as the piece can keep moving in that direction
                candidateDestinationCoordinate += candidateCoordinateOffset;

                if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {

                    if (isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset) ||
                            isEighthColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)) {
                        break;
                    }

                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate); // the tile the piece would occupy

                    if (!candidateDestinationTile.isTileOccupied()) {
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate)); // adds a legal move to the list if the tile is unoccupied
                    } else {
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                        if (this.pieceAlliance != pieceAtDestination.getPieceAlliance()) {
                            legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination)); // adds a legal attacking move to the list if the tile is occupied by a piece of opposite alliance
                        }
                        break;
                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    // Behavior: checks whether the piece is in the first column of the board and if a legal move can be made
    // Return: whether or not a move can be made
    // Parameter:
    //      currentPosition: the current position of the piece
    //      candidateOffset: the position the piece could move to
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == 7 || candidateOffset == -1);
    }

    // Behavior: checks whether the piece is in the eighth column of the board and if a legal move can be made
    // Return: whether or not a move can be made
    // Parameter:
    //      currentPosition: the current position of the piece
    //      candidateOffset: the position the piece could move to
    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 9 || candidateOffset == 1);
    }
}
