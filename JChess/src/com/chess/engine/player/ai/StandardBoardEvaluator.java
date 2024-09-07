package com.chess.engine.player.ai;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.Player;

import static com.chess.engine.pieces.Piece.PieceType.BISHOP;

public final class StandardBoardEvaluator implements BoardEvaluator {

    private static final int CHECK_BONUS = 45;
    private static final int CHECK_MATE_BONUS = 10000;
    private static final int DEPTH_BONUS = 100;
    private static final int CASTLE_BONUS = 25;
    private static final int MOBILITY_MULTIPLIER = 5;
    private static final int ATTACK_MULTIPLIER = 1;
    private final static int TWO_BISHOPS_BONUS = 25;
    private static final StandardBoardEvaluator INSTANCE = new StandardBoardEvaluator();

    public StandardBoardEvaluator() {
    }

    public static StandardBoardEvaluator get() {
        return INSTANCE;
    }

    @Override
    public int evaluate(Board board,
                        int depth) {
        return scorePlayer(board.whitePlayer(), depth) - scorePlayer(board.blackPlayer(), depth);
    }

    public String evaluationDetails(final Board board, final int depth) {
        return
                ("White Mobility : " + mobility(board.whitePlayer()) + "\n") +
                        "White kingThreats : " + kingThreats(board.whitePlayer(), depth) + "\n" +
                        "White attacks : " + attacks(board.whitePlayer()) + "\n" +
                        "White castle : " + castle(board.whitePlayer()) + "\n" +
                        "White pieceEval : " + pieceEvaluations(board.whitePlayer()) + "\n" +
                        "White pawnStructure : " + pawnStructure(board.whitePlayer()) + "\n" +
                        "---------------------\n" +
                        "Black Mobility : " + mobility(board.blackPlayer()) + "\n" +
                        "Black kingThreats : " + kingThreats(board.blackPlayer(), depth) + "\n" +
                        "Black attacks : " + attacks(board.blackPlayer()) + "\n" +
                        "Black castle : " + castle(board.blackPlayer()) + "\n" +
                        "Black pieceEval : " + pieceEvaluations(board.blackPlayer()) + "\n" +
                        "Black pawnStructure : " + pawnStructure(board.blackPlayer()) + "\n\n" +
                        "Final Score = " + evaluate(board, depth);
    }

    private int scorePlayer(final Player player,
                            final int depth) {
        return mobility(player) +
                kingThreats(player, depth) +
                attacks(player) +
                castle(player) +
                pieceEvaluations(player) +
                pawnStructure(player);
    }

    private static int kingThreats(final Player player,
                                   final int depth) {
        return player.getOpponent().isInCheckMate() ? checkmate(player, depth) : check(player);
    }

    private static int attacks(final Player player) {
        int attackScore = 0;
        for(final Move move : player.getLegalMoves()) {
            if(move.isAttack()) {
                final Piece movedPiece = move.getMovedPiece();
                final Piece attackedPiece = move.getAttackedPiece();
                if(movedPiece.getPieceValue() <= attackedPiece.getPieceValue()) {
                    attackScore++;
                }
            }
        }
        return attackScore * ATTACK_MULTIPLIER;
    }


    private static int checkmate(final Player player, int depth) {
        return player.getOpponent().isInCheckMate() ? CHECK_MATE_BONUS * depthBonus(depth) : 0;
    }

    private static int depthBonus(int depth) {
        return depth == 0 ? 1 : DEPTH_BONUS * depth;
    }

    private static int check(final Player player) {
        return player.getOpponent().isInCheck() ? CHECK_BONUS : 0;
    }

    private static int mobility(final Player player) {
        return MOBILITY_MULTIPLIER * mobilityRatio(player);
    }

    private static int mobilityRatio(final Player player) {
        return (int)((player.getLegalMoves().size() * 10.0f) / player.getOpponent().getLegalMoves().size());
    }

    private static int castle(final Player player) {
        return player.isCastled() ? CASTLE_BONUS : 0;
    }

    private static int pawnStructure(final Player player) {
        return PawnStructureAnalyzer.get().pawnStructureScore(player);
    }

    private static int pieceEvaluations(final Player player) {
        int pieceValuationScore = 0;
        int numBishops = 0;
        for (final Piece piece : player.getActivePieces()) {
            pieceValuationScore += piece.getPieceValue() + piece.locationBonus();
            if(piece.getPieceType() == BISHOP) {
                numBishops++;
            }
        }
        return pieceValuationScore + (numBishops == 2 ? TWO_BISHOPS_BONUS : 0);
    }
}
