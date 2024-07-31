// Author: Huy Huynh
// Date: 07/12/2024

package com.chess.engine;

// This enum is of the Alliance object which is used to store whether a piece is white or black.
public enum Alliance {
    WHITE {
        @Override
        public int getDirection() {
            return -1;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public boolean isWhite() {
            return true;
        }
    },
    BLACK {
        @Override
        public int getDirection() {
            return 1;
        }

        @Override
        public boolean isBlack() {
            return true;
        }

        @Override
        public boolean isWhite() {
            return false;
        }
    };

    // Behavior: returns the direction the piece is facing depending on its alliance
    // Return: returns -1 is the piece is moving in the white direction and 1 if
    //         the piece is moving in the black direction
    public abstract int getDirection();

    // Behavior: returns whether a piece is black or not
    // Return: true or false
    public abstract boolean isBlack();

    // Behavior: returns whether a piece is white or not
    // Return: true or false
    public abstract boolean isWhite();
}
