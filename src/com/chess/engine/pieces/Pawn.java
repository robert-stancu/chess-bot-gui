package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.google.common.collect.ImmutableList;

//TO DO: EN-PASSANT

public class Pawn extends Piece {

    private static final int[] CANDIDATE_MOVE_COORDINATES = { 8, 16, 7, 9 };

    public Pawn(final int piecePosition, final Alliance pieseAlliance) {
        super(PieceType.PAWN, piecePosition, pieseAlliance, true);
    }

    public Pawn(final int piecePosition, final Alliance pieseAlliance, final boolean isFirstMove) {
        super(PieceType.PAWN, piecePosition, pieseAlliance, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
            final int candidateDestinationCoordinate = this.piecePosition
                    + (currentCandidateOffset * this.getPieceAlliance().getDirection());

            if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                continue;
            }

            if (currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate)); // change move type
            } else if (currentCandidateOffset == 16 && this.isFirstMove()
                    && ((BoardUtils.SEVENTH_RANK[this.piecePosition] && this.getPieceAlliance().isBlack())
                    || (BoardUtils.SECOND_RANK[this.piecePosition] && this.getPieceAlliance().isWhite()))) {

                final int behindCandidateDestinationCoordinate = this.piecePosition
                        + (this.getPieceAlliance().getDirection() * 8);

                if (!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied()
                        && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    legalMoves.add(new Move.PawnJump(board, this, candidateDestinationCoordinate));
                }
            } else if (currentCandidateOffset == 7
                    && !((BoardUtils.EIGHT_COLUMN[this.piecePosition] && this.getPieceAlliance().isWhite()
                    || (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.getPieceAlliance().isBlack())))) {
                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.getPieceAlliance() != pieceOnCandidate.getPieceAlliance()) {
                        legalMoves.add(
                                new Move.PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                    }
                }
            } else if (currentCandidateOffset == 9
                    && !((BoardUtils.EIGHT_COLUMN[this.piecePosition] && this.getPieceAlliance().isBlack()
                    || (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.getPieceAlliance().isWhite())))) {
                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.getPieceAlliance() != pieceOnCandidate.getPieceAlliance()) {
                        legalMoves.add(
                                new Move.PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }

    @Override
    public Pawn movePiece(Move move) {
        return new Pawn(move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance());
    }
}