// Author: Huy Huynh
// Date: 07/12/2024

package com.chess.engine.board;

public class BoardUtils {

    public static final boolean[] FIRST_COLUMN = null;
    public static final boolean[] SECOND_COLUMN = null;
    public static final boolean[] SEVENTH_COLUMN = null;
    public static final boolean[] EIGHTH_COLUMN = null;

    private BoardUtils() {
        throw new RuntimeException("You can't instantiate this class.");
    }

    // Behavior: checks if a coordinate is within the bounds of the game board
    // Return: confirms whether or not the coordinate is valid
    // Parameter:
    //      coordinate: the coordinate being checked
    public static boolean isValidTileCoordinate(int coordinate) {
        return coordinate >= 0 && coordinate < 64;
    }
}
