// Author: Huy Huynh
// Date: 07/12/2024

package com.chess.engine.board;

public class BoardUtils {

    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);

    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8;

    private BoardUtils() {
        throw new RuntimeException("You can't instantiate this class.");
    }

    // Behavior: creates a boolean array that represents a single column being filled with true values
    // Return: an array with only true values int the given column
    // Parameter:
    //      columnNumber: the desired column
    private static boolean[] initColumn(int columnNumber) {
        final boolean[] column = new boolean[NUM_TILES];

        do {
            column[columnNumber] = true;
            columnNumber += NUM_TILES_PER_ROW;
        } while (columnNumber < NUM_TILES);
        return column;
    }

    // Behavior: checks if a coordinate is within the bounds of the game board
    // Return: confirms whether or not the coordinate is valid
    // Parameter:
    //      coordinate: the coordinate being checked
    public static boolean isValidTileCoordinate(final int coordinate) {
        return coordinate >= 0 && coordinate < NUM_TILES;
    }
}
