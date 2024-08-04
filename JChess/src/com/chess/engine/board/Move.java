// Author: Huy Huynh
// Date: 07/12/2024

package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.chess.engine.board.Board.Builder;

// This class is of the move object. It contains the behaviors of a chess move.
public abstract class Move {
    final Board board;
    final Piece movedPiece;
    final int destinationCoordinate;

    public static final Move NULL_MOVE = new nullMove();

    // Behavior: this constructor constructs a new Move object.
    // Parameter:
    //      board: the game board
    //      movedPiece: the piece being moved
    //      destinationCoordinate: the coordinate of where the piece is being moved to
    private Move(final Board board,
                 final Piece movedPiece,
                 final int destinationCoordinate) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
    }

    // Behavior: returns the coordinate of the current moved piece
    public int getCurrentCoordinate(){
        return this.getMovedPiece().getPiecePosition();
    }

    // Behavior: this method is a getter for the destination coordinate
    public int getDestinationCoordinate() {
        return this.destinationCoordinate;
    }

    // Behavior: returns the moved piece
    public Piece getMovedPiece() {
        return this.movedPiece;
    }

    // Behavior: this method executes a move and creates a new game board based on the move made
    // Return: returns the new move
    // Parameter: this method accepts no parameters
    public Board execute() {
        final Builder builder = new Builder();

        // places all the current players pieces that are not the moved piece
        for (final Piece piece : this.board.currentPlayer().getActivePieces()) {
            // TODO hashcode and equals for pieces
            if (!this.movedPiece.equals(piece)) {
                builder.setPiece(piece);
            }
        }

        // places all the opposing players pieces
        for (final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
            builder.setPiece(piece);
        }

        // moves the moved piece
        builder.setPiece(this.movedPiece.movePiece(this));
        builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());

        return builder.build();
    }

    // This class is of the MajorMove object. It represents a transitional move for a chess piece.
    public static final class MajorMove extends Move {

        // Behavior: this constructor constructs a new MajorMove object
        // Parameter:
        //      board: the game board
        //      movedPiece: the piece being moved
        //      destinationCoordinate: the coordinate of where the piece is being moved to
        public MajorMove(final Board board,
                         final Piece movedPiece,
                         final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    }

    // This class if of the AttackMove object. It represents an attack move for a chess piece.
    public static class AttackMove extends Move {
        final Piece attackedPiece;

        // Behavior: this constructor constructs a new AttackMove object
        // Parameter:
        //      board: the game board
        //      movedPiece: the piece being moved
        //      destinationCoordinate: the coordinate of where the piece is being moved to
        //      attackedPiece: the piece being captured
        public AttackMove(final Board board,
                          final Piece movedPiece,
                          final int destinationCoordinate,
                          final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public Board execute() {
            return null;
        }
    }

    // This class is of the PawnMove object. It represents a move for the pawn piece.
    public static final class PawnMove extends Move {

        // Behavior: this constructor constructs a new MajorMove object
        // Parameter:
        //      board: the game board
        //      movedPiece: the piece being moved
        //      destinationCoordinate: the coordinate of where the piece is being moved to
        public PawnMove(final Board board,
                        final Piece movedPiece,
                        final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    }

    // This class is of the PawnMove object. It represents an attack move for the pawn piece.
    public static class PawnAttackMove extends AttackMove {

        // Behavior: this constructor constructs a new MajorMove object
        // Parameter:
        //      board: the game board
        //      movedPiece: the piece being moved
        //      destinationCoordinate: the coordinate of where the piece is being moved to
        public PawnAttackMove(final Board board,
                              final Piece movedPiece,
                              final int destinationCoordinate,
                              final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }
    }

    // This class is of the PawnMove object. It represents an En Passant attack move for the pawn piece.
    public static final class PawnEnPassantAttack extends PawnAttackMove {

        // Behavior: this constructor constructs a new MajorMove object
        // Parameter:
        //      board: the game board
        //      movedPiece: the piece being moved
        //      destinationCoordinate: the coordinate of where the piece is being moved to
        public PawnEnPassantAttack(final Board board,
                                   final Piece movedPiece,
                                   final int destinationCoordinate,
                                   final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }
    }

    // This class is of the PawnMove object. It represents a jump move for the pawn piece.
    public static final class PawnJump extends Move {

        // Behavior: this constructor constructs a new MajorMove object
        // Parameter:
        //      board: the game board
        //      movedPiece: the piece being moved
        //      destinationCoordinate: the coordinate of where the piece is being moved to
        public PawnJump(final Board board,
                        final Piece movedPiece,
                        final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    }

    // This class is of the CastleMove object. It represents a the castling move for a rook and king.
    static abstract class CastleMove extends Move {

        // Behavior: this constructor constructs a new MajorMove object
        // Parameter:
        //      board: the game board
        //      movedPiece: the piece being moved
        //      destinationCoordinate: the coordinate of where the piece is being moved to
        public CastleMove(final Board board,
                          final Piece movedPiece,
                          final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    }

    // This class is of the KingSideCastleMove object. It represents castling towards the king side of the board.
    public static final class KingSideCastleMove extends CastleMove {

        // Behavior: this constructor constructs a new MajorMove object
        // Parameter:
        //      board: the game board
        //      movedPiece: the piece being moved
        //      destinationCoordinate: the coordinate of where the piece is being moved to
        public KingSideCastleMove(final Board board,
                                  final Piece movedPiece,
                                  final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    }

    // This class is of the QueenSideCastleMove object. It represents castling towards the queen side of the board.
    public static final class QueenSideCastleMove extends CastleMove {

        // Behavior: this constructor constructs a new MajorMove object
        // Parameter:
        //      board: the game board
        //      movedPiece: the piece being moved
        //      destinationCoordinate: the coordinate of where the piece is being moved to
        public QueenSideCastleMove(final Board board,
                                  final Piece movedPiece,
                                  final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    }

    // This class is of the nullMove object. It represents an invalid move.
    public static final class nullMove extends Move {

        // Behavior: this constructor constructs a new NullMove object
        public nullMove() {
            super(null, null,-1);
        }

        @Override
        public Board execute() {
            throw new RuntimeException("Cannot execute the null move!");
        }
    }

    // This class is of the MoveFactory object. It is used to help our superclass instantiate our subclasses.
    public static class MoveFactory {
        private MoveFactory() {
            throw new RuntimeException("Not instantiable");
        }

        public static Move createMove(final Board board,
                                      final int currentCoordinate,
                                      final int destinationCoordinate) {
            for (final Move move : board.getAllLegalMoves()) {
                if(move.getCurrentCoordinate() == currentCoordinate && move.getDestinationCoordinate() == destinationCoordinate) {
                    return move;
                }
            }
            return NULL_MOVE;
        }
    }
}
