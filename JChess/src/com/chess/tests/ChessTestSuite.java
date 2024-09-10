package com.chess.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestPieces.class,
                     TestBoard.class,
                     TestStaleMate.class,
                     TestPlayer.class,
                     TestCheckmate.class,
                     TestAlphaBeta.class, // TODO need to debug
                     TestCastling.class,
                     TestPawnStructure.class,
                     TestFENParser.class})
public class ChessTestSuite {
}
