package com.muskalanawrot.eightqueenspuzzle.test;

import com.muskalanawrot.eightqueenspuzzle.implementation.Queen;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * Test class for {@link Queen} class
 */
public class QueenTest
{
    private static final int QUEEN_ROW = 2;
    private static final int QUEEN_COLUMN = 2;

    private Queen queen;

    @Before
    public void setUp()
    {
	queen = new Queen(QUEEN_ROW, QUEEN_COLUMN);
    }

    @Test
    public void mutationTest()
    {
	final int MAX_ROW_NUMBER = 8;
	final int MAX_COLUMN_NUMBER = 8;

	Random random = new Random();

	//Given:
	assertEquals("Column before mutation doesn't match expected.", QUEEN_COLUMN, queen.getColumn().intValue());
	assertEquals("Row before mutation doesn't match expected.", QUEEN_ROW, queen.getRow().intValue());

	//When:
	queen.mutate(MAX_ROW_NUMBER, MAX_COLUMN_NUMBER, random);

	//Then:
	if (queen.getRow() != QUEEN_ROW)
	{
	    int newRowValue = queen.getRow();
	    assertTrue("Row value isn't within desired range", newRowValue >= 0 && newRowValue < MAX_ROW_NUMBER);
	    assertTrue("Value change is too big.", (newRowValue - QUEEN_ROW) % MAX_ROW_NUMBER < MAX_ROW_NUMBER / 2);
	}
	else
	{
	    int newColumnValue = queen.getColumn();
	    assertTrue("Column value isn't within desired range",
			    newColumnValue >= 0 && newColumnValue < MAX_COLUMN_NUMBER);
	    assertTrue("Value change is too big.",
			    (newColumnValue - QUEEN_COLUMN) % MAX_COLUMN_NUMBER < MAX_COLUMN_NUMBER / 2);
	}
    }

    @Test
    public void checkCollisionNumberTest()
    {
	// horizontal or vertical collision
	List<Queen> queenList = Stream.of(new Queen(2, 1), new Queen(2, 7), new Queen(7, 2), new Queen(3, 2))
			.collect(Collectors.toList());

	assertEquals("Number of horizontal or vertical collisions doesn't match expected. ", 4,
			queen.getNumberOfCollisions(queenList).intValue());

	// cross collision
	queenList = Stream.of(new Queen(1, 1), new Queen(3, 3), new Queen(3, 1), new Queen(1, 3))
			.collect(Collectors.toList());
	assertEquals("Number of cross collisions doesn't match expected. ", 4,
			queen.getNumberOfCollisions(queenList).intValue());

	// one collumn collision
	queenList = Stream.of(new Queen(2, 5), new Queen(2, 3), new Queen(2, 1), new Queen(2, 4), new Queen(2, 8),
			new Queen(2, 6), new Queen(2, 8), new Queen(2, 2)).collect(Collectors.toList());
	for (Queen q : queenList)
	{
	    assertEquals("Number of one collumn collisions doesn't match expected. ", 7,
			    q.getNumberOfCollisions(queenList).intValue());
	}

	// no collision
	queenList = Stream.of(new Queen(7, 3), new Queen(5, 4), new Queen(0, 1), new Queen(1, 7))
			.collect(Collectors.toList());
	assertEquals("Number of collisions doesn't match expected. ", 0,
			queen.getNumberOfCollisions(queenList).intValue());
    }

    @Test
    public void getQueenCopyTest()
    {
	//When:
	Queen queenCopy = queen.getCopy();

	//Then:
	assertFalse("Copy is not a copy but the same object.", queenCopy.equals(queen));
	assertEquals("Column number is not the same in copy and original object.", queen.getColumn(),
			queenCopy.getColumn());
	assertEquals("Row number is not the same in copy and original object.", queen.getRow(), queenCopy.getRow());
    }
}
