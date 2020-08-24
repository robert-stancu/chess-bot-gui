package com.chess;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.gui.Table;

public class Main {

    public static void main(String[] args) {
        Board board = Board.createStandardBoard();

        System.out.println(board);
        Table.get().show();
    }
}
