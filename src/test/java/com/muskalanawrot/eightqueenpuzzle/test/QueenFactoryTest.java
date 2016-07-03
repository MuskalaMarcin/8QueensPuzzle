package com.muskalanawrot.eightqueenpuzzle.test;

import com.muskalanawrot.eightqueenspuzzle.implementation.Queen;
import com.muskalanawrot.eightqueenspuzzle.implementation.QueenFactory;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Marcin on 03.07.2016.
 */
public class QueenFactoryTest
{
    @Test
    public void queenFactoryTest()
    {
	//Given:
	final int maxRowNumber = 10;
	final int maxColumnNumber = 10;

	//When:
	Queen queenFromFactory = QueenFactory.getNewQueen(maxRowNumber, maxColumnNumber);

	//Then:
	assertTrue("Wrong type of returned object.", queenFromFactory instanceof Queen);
	int row = queenFromFactory.getRow();
	assertTrue("Row value isn't inside requried boundaries.", row >= 0 && row < maxRowNumber);
	int column = queenFromFactory.getColumn();
	assertTrue("Column value isn't inside requried boundaries.", column >= 0 && row < maxColumnNumber);
    }
}
