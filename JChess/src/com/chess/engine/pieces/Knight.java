// Author: Huy Huynh
// Date: 07/12/2024

package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.*;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// This class is of the Knight object. It contains the behavior of the knight chess piece.
public class Knight extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = {- 17, -15, -10, -6, 6, 10, 15, 17}; // knight movement

    // Behavior: this constructor constructs a new knight object
    // Parameter:
    //      piecePosition: the position of the piece on the board
    //      pieceAlliance: the alliance of the piece, i.e. white or black
    Knight(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    // Behavior: this method calculates a list of legal moves for the piece. Valid spaces must be on the board. If a space
    //           is occupied, the space is only valid if the alliance is opposite of the current piece
    // Return: returns a list of legal moves
    // Parameter:
    //      board: the game board
    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        List<Move> legalMoves = new ArrayList<>(); // list of legal moves

        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
            int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset; // the current move coordinate being checked
            final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate); // the tile the piece would occupy

            // checks if the tile is out of bounds
            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {

                // skips past current tile if an edge case is present
                if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                        isSecondColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                        isSeventhColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                        isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
                    continue;
                }

                if (!candidateDestinationTile.isTileOccupied()) {
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate)); // adds a legal move to the list if the tile is unoccupied
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                    if (this.pieceAlliance != pieceAtDestination.getPieceAlliance()) {
                        legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination)); // adds a legal attacking move to the list if the tile is occupied by a piece of opposite alliance
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
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset == -10 ||
                candidateOffset == 6 || candidateOffset == 15);
    }

    // Behavior: checks whether the piece is in the second column of the board and if a legal move can be made
    // Return: whether or not a move can be made
    // Parameter:
    //      currentPosition: the current position of the piece
    //      candidateOffset: the position the piece could move to
    private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset == -10 || candidateOffset == 6);
    }

    // Behavior: checks whether the piece is in the seventh column of the board and if a legal move can be made
    // Return: whether or not a move can be made
    // Parameter:
    //      currentPosition: the current position of the piece
    //      candidateOffset: the position the piece could move to
    private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffset == -6 || candidateOffset == 10);
    }

    // Behavior: checks whether the piece is in the eighth column of the board and if a legal move can be made
    // Return: whether or not a move can be made
    // Parameter:
    //      currentPosition: the current position of the piece
    //      candidateOffset: the position the piece could move to
    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -15 || candidateOffset == -6 ||
                candidateOffset == 10 || candidateOffset == 17);
    }
}
