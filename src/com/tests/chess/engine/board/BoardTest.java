package com.tests.chess.engine.board;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MoveFactory;
import com.chess.engine.player.MoveTransition;
import com.chess.engine.player.ai.MinMax;
import com.chess.engine.player.ai.MoveStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    public void initialBoard() {

    }

    @Test
    public void foolsMateTest() {
        final Board board = Board.createStandardBoard();

        final MoveTransition t1 = board.currentPlayer().makeMove(MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("f2"),
                                    BoardUtils.getCoordinateAtPosition("f3")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getTransitionBoard().currentPlayer().makeMove(MoveFactory.createMove(t1.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                BoardUtils.getCoordinateAtPosition("e5")));

        System.out.println(t2.getMoveStatus().isDone());

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getTransitionBoard().currentPlayer().makeMove(MoveFactory.createMove(t2.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("g2"),
                BoardUtils.getCoordinateAtPosition("g4")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveStrategy strategy = new MinMax(4);

        final Move botMove = strategy.execute(t3.getTransitionBoard());

        final Move bestMove = MoveFactory.createMove(t3.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d8"),
                BoardUtils.getCoordinateAtPosition("h4"));

        System.out.println(bestMove.toString());
        System.out.println(botMove.toString());

        assertEquals(bestMove, botMove);
    }
}