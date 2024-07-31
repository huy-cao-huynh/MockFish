// Author: Huy Huynh
// Date: 07/12/2024

package com.chess.engine.board;

import com.chess.engine.Alliance;
import com.chess.engine.pieces.*;
import com.google.common.collect.ImmutableList;

import java.util.*;

// This class is of the Board object. It represents the game board.
public class Board {
    private final List<Tile> gameBoard;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;

    // Behavior: constructs a Board object.
    // Parameter:
    //      builder: a builder used to construct the board
    private Board(Builder builder) {
        this.gameBoard = createGameBoard(builder);
        this.whitePieces = calculateActivePieces(this.gameBoard, Alliance.WHITE);
        this.blackPieces = calculateActivePieces(this.gameBoard, Alliance.BLACK);

        final Collection<Move> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces);
        final Collection<Move> blackStandardLegalMoves = calculateLegalMoves(this.blackPieces);
    }

    // Behavior: prints out the board
    // Return: returns the state of the board
    // Parameter: this method accepts no parameters
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            final String tileText = gameBoard.get(i).toString();
            builder.append(String.format("%3s", tileText));
            if ((i + 1) % BoardUtils.NUM_TILES_PER_ROW == 0) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    // Behavior: builds the list of all the legal moves given a a collection of pieces
    // Return: returns a list of all the legal moves
    // Parameter:
    //      pieces: the set of pieces being checked
    private Collection<Move> calculateLegalMoves(final Collection<Piece> pieces) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final Piece piece : pieces) {
            legalMoves.addAll(piece.calculateLegalMoves(this));
        }
        return ImmutableList.copyOf(legalMoves);
    }

    // Behavior: creates a list of the active pieces for the given alliance
    // Return: returns a list of the active pieces
    // Parameter:
    //      gameBoard: the current state of the game board
    //      alliance: the alliance being checked
    private static Collection<Piece> calculateActivePieces(final List<Tile> gameBoard, final Alliance alliance) {
        final List<Piece> activePieces = new ArrayList<>();

        for (final Tile tile : gameBoard) {
            if (tile.isTileOccupied()) {
                final Piece piece = tile.getPiece();

                if (piece.getPieceAlliance() == alliance) {
                    activePieces.add(piece);
                }
            }
        }

        return ImmutableList.copyOf(activePieces);
    }

    // Behavior: returns the tile at the given coordinate
    // Return: the tile at the given coordinate
    // Parameter:
    //      tileCoordinate: the coordinate of the tile
    public Tile getTile(final int tileCoordinate) {
        return gameBoard.get(tileCoordinate);
    }

    // Behavior: constructs a new game board with a new Tile from 0-63, representing
    //           the spaces on a chess board
    // Return: a list containing the 64 tiles on a chess board
    // Parameter:
    //      builder: a builder used to construct the board
    private static List<Tile> createGameBoard(final Builder builder) {
        final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
        }
        return ImmutableList.copyOf(tiles);
    }

    // Behavior: constructs a standard game board with all pieces in their designated
    //           starting positions.
    // Return: the builder that built this game board
    // Parameter: this method accepts no parameters
    public static Board createStandardBoard() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Knight(Alliance.BLACK, 1));
        builder.setPiece(new Bishop(Alliance.BLACK, 2));
        builder.setPiece(new Queen(Alliance.BLACK, 3));
        builder.setPiece(new King(Alliance.BLACK, 4));
        builder.setPiece(new Bishop(Alliance.BLACK, 5));
        builder.setPiece(new Knight(Alliance.BLACK, 6));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setPiece(new Pawn(Alliance.BLACK, 8));
        builder.setPiece(new Pawn(Alliance.BLACK, 9));
        builder.setPiece(new Pawn(Alliance.BLACK, 10));
        builder.setPiece(new Pawn(Alliance.BLACK, 11));
        builder.setPiece(new Pawn(Alliance.BLACK, 12));
        builder.setPiece(new Pawn(Alliance.BLACK, 13));
        builder.setPiece(new Pawn(Alliance.BLACK, 14));
        builder.setPiece(new Pawn(Alliance.BLACK, 15));

        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 48));
        builder.setPiece(new Pawn(Alliance.WHITE, 49));
        builder.setPiece(new Pawn(Alliance.WHITE, 50));
        builder.setPiece(new Pawn(Alliance.WHITE, 51));
        builder.setPiece(new Pawn(Alliance.WHITE, 52));
        builder.setPiece(new Pawn(Alliance.WHITE, 53));
        builder.setPiece(new Pawn(Alliance.WHITE, 54));
        builder.setPiece(new Pawn(Alliance.WHITE, 55));
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new Knight(Alliance.WHITE, 57));
        builder.setPiece(new Bishop(Alliance.WHITE, 58));
        builder.setPiece(new Queen(Alliance.WHITE, 59));
        builder.setPiece(new King(Alliance.WHITE, 60));
        builder.setPiece(new Bishop(Alliance.WHITE, 61));
        builder.setPiece(new Knight(Alliance.WHITE, 62));
        builder.setPiece(new Rook(Alliance.WHITE, 63));

        builder.setMoveMaker(Alliance.WHITE); // white to move
        return builder.build();
    }

    // This class is of the Builder object. It is used to help build the game board.
    public static class Builder {
        Map<Integer, Piece> boardConfig;
        Alliance nextMoveMaker;

        // Behavior: constructs a Builder object.
        public Builder() {
            this.boardConfig = new HashMap<>();
        }

        // Behavior: assigns a piece to it's designated place on the board
        // Return: returns the state of the builder object
        // Parameter:
        //      piece: the given piece
        public Builder setPiece(final Piece piece) {
            this.boardConfig.put(piece.getPiecePosition(), piece);
            return this;
        }

        // Behavior: changes/sets the alliance to match the correct players turn
        // Return: returns the state of the builder object
        // Parameter:
        //      nextMoveMaker: the alliance of the next player's move
        public Builder setMoveMaker(final Alliance nextMoveMaker) {
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }

        public Board build() {
            return new Board(this);
        }
    }
}
