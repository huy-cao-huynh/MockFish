// Author: Huy Huynh
// Date: 07/12/2024
// Tutorial Followed: https://www.youtube.com/watch?v=GKJ3yYFCJO4&list=PLOJzCFLZdG4zk5d-1_ah2B4kqZSeIlWtt&index=3

package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.List;

public abstract class Piece {

    protected final int piecePosition;
    protected final Alliance pieceAlliance;

    private Piece(final int piecePosition, final Alliance pieceAlliance) {
        this.piecePosition = piecePosition;
        this.pieceAlliance = pieceAlliance;
    }

    public abstract List<Move> calculateLegalMoves(final Board board);
}
