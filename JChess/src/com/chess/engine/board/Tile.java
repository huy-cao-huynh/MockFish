// Author: Huy Huynh
// Date: 07/11/2024
// Tutorial Followed: https://www.youtube.com/watch?v=h8fSdSUKttk&list=PLOJzCFLZdG4zk5d-1_ah2B4kqZSeIlWtt&index=1

package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

// This class is of the Tile object. It represents a single tile on a chess board.
public abstract class Tile {

    protected final int tileCoordinate; // the coordinate of the tile
    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTile(); // a cache of all possible empty tiles

    // Behavior: generates a map of all 64 possible empty tiles
    // Return: returns a map of all tiles
    // Parameter: takes no parameters
    private static Map<Integer, EmptyTile> createAllPossibleEmptyTile() {

        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<Integer, EmptyTile>();

        for (int i = 0; i < 64; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }

        return ImmutableMap.copyOf(emptyTileMap);
    }

    // Behavior: creates a new occupied tile if there is a piece present or returns a cached empty tile
    // Return: returns the new/cached tile
    // Parameter:
    //      tileCoordinate: coordinate of the tile
    //      piece: the piece on the tile
    public static Tile createTile(final int tileCoordinate, final Piece piece) {
        return piece != null ? new OccupiedTile(piece, tileCoordinate) : EMPTY_TILES_CACHE.get(tileCoordinate);
    }

    // Behavior: this is the constructor for the Tile object
    // Parameter:
    //      tileCoordinate: the coordinate of the given tile
    private Tile (int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }

    public abstract boolean isTileOccupied();

    public abstract Piece getPiece();

    // This class if of the EmptyTile object. It represents an empty tile on a chess board.
    public static final class EmptyTile extends Tile {

        // Behavior: constructs a new empty tile at a given coordinate
        // Parameter:
        //      tileCoordinate: the coordinate of the given tile
        private EmptyTile(final int tileCoordinate) {
            super(tileCoordinate);
        }

        // Behavior: confirms that the tile is empty
        @Override
        public boolean isTileOccupied() {
            return false;
        }

        // Behavior: confirms there is no piece occupying the tile
        @Override
        public Piece getPiece() {
            return null;
        }
    }

    // This class is of the OccupiedTile object. It represents an occupied tile on a chess board.
    public static final class OccupiedTile extends Tile {

        private final Piece pieceOnTile;

        // Behavior: constructs a new occupied tile object
        // Parameter:
        //      pieceOnTile: the piece occupying the tile
        //      tileCoordinate: the coordinate of the tile
        private OccupiedTile(Piece pieceOnTile, final int tileCoordinate) {
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }

        // Behavior: confirms the tile is occupied
        @Override
        public boolean isTileOccupied() {
            return true;
        }

        // Behavior: returns the piece that is occupying the tile
        @Override
        public Piece getPiece() {
            return this.pieceOnTile;
        }
    }
}
