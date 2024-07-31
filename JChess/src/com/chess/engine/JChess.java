// Author: Huy Huynh
// Date: 7/20/2024

package com.chess.engine;

import com.chess.engine.board.Board;

public class JChess {

    public static void main(String[] args) {
        Board board = Board.createStandardBoard();

        System.out.println(board);
    }
}
