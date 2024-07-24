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
            return false;
        }

        @Override
        public boolean isWhite() {
            return true;
        }
    };

    public abstract int getDirection();

    public abstract boolean isBlack();

    public abstract boolean isWhite();
}
