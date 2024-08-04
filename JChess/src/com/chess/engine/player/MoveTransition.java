// Author: Huy Huynh
// Date: 7/30/2024

package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

// This class if of the MoveTransition class. It represents the process of the board changing after a player has made a move.
public class MoveTransition {
    private final Board transitionBoard;
    private final Move move;
    private final MoveStatus moveStatus;

    public MoveTransition(final Board transitionBoard,
                          final Move move,
                          final MoveStatus moveStatus) {

        this.transitionBoard = transitionBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }

    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }
}
