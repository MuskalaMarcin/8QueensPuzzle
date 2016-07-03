package com.muskalanawrot.eightqueenspuzzle.test;

import com.muskalanawrot.eightqueenspuzzle.implementation.ChessBoard;
import com.muskalanawrot.eightqueenspuzzle.implementation.Queen;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class for {@link ChessBoard} class.
 */
public class ChessBoardTest
{
    private final static Integer ROWS_NUMBER = 8;
    private final static Integer COLUMNS_NUMBER = 8;
    private final static Integer QUEENS_NUMBER = 8;

    private ChessBoard chessBoard;

    @Before
    public void setUp()
    {
	chessBoard = new ChessBoard(ROWS_NUMBER, COLUMNS_NUMBER, QUEENS_NUMBER);
    }

    @Test
    public void crossoverTest()
    {
	//Given:
	ChessBoard firstParent = new ChessBoard(ROWS_NUMBER, COLUMNS_NUMBER, QUEENS_NUMBER);
	ChessBoard secondParent = new ChessBoard(ROWS_NUMBER, COLUMNS_NUMBER, QUEENS_NUMBER);

	//When:
	ChessBoard child = ChessBoard.crossover(firstParent, secondParent);

	//Then:
	List<Queen> firstParentGenotype = firstParent.getGenotype();
	List<Queen> secondParentGenotype = secondParent.getGenotype();

	for (Queen queen : child.getGenotype())
	{
	    assertTrue("Queen isn't found in any of parents", firstParentGenotype.stream()
			    .filter(q -> q.getRow().compareTo(queen.getRow()) == 0
					    && q.getColumn().compareTo(queen.getColumn()) == 0).findAny().isPresent()
			    || secondParentGenotype.stream()
			    .filter(q -> q.getRow().compareTo(queen.getRow()) == 0
					    && q.getColumn().compareTo(queen.getColumn()) == 0).findAny().isPresent());
	}
    }

    @Test
    public void mutationTest()
    {
	//Given:
	ChessBoard copy = chessBoard.getCopy();
	List<Queen> copyGenotype = copy.getGenotype();
	List<Queen> originalGenotype = chessBoard.getGenotype();

	//When:
	chessBoard.mutate();

	//Then:
	assertEquals("Number of not mutated objects doesn't match expected.", QUEENS_NUMBER.intValue() - 1,
			copyGenotype.stream().filter(q -> originalGenotype.stream()
					.filter(queen -> q.getRow().compareTo(queen.getRow()) == 0
							&& q.getColumn().compareTo(queen.getColumn())
							== 0).findAny().isPresent()).count());
    }

    @Test
    public void isFinishedTest()
    {
	if (chessBoard.getFit().intValue() == 56)
	{
	    assertTrue("Chess board isn't marked as finished.", chessBoard.isFinished());
	}
	else
	{
	    assertFalse("Chess board isn marked as finished before it should be.", chessBoard.isFinished());
	}

    }

    @Test
    public void getCopyTest()
    {
	//Given:
	List<Queen> previousGenotype = chessBoard.getGenotype();
	assertEquals("Genotype size doesn't match expected.", QUEENS_NUMBER.intValue(), previousGenotype.size());

	//When:
	ChessBoard chessBoardCopy = chessBoard.getCopy();
	List<Queen> copiedGenotype = chessBoardCopy.getGenotype();

	//Then:
	assertEquals("Genotype size doesn't match expected.", QUEENS_NUMBER.intValue(), copiedGenotype.size());
	assertEquals("Fit value doesn't match.", chessBoard.getFit().intValue(), chessBoardCopy.getFit().intValue());
	for (Queen queen : previousGenotype)
	{
	    assertTrue("No matching object in genotype copy.", copiedGenotype.stream().filter(q ->
			    q.getColumn().compareTo(queen.getColumn()) == 0
					    && q.getRow().compareTo(queen.getRow()) == 0).findAny().isPresent());
	    assertFalse("Element is not a copy.", copiedGenotype.stream().filter(q ->
			    q.getColumn().compareTo(queen.getColumn()) == 0
					    && q.getRow().compareTo(queen.getRow()) == 0).findAny().get()
			    .equals(queen));
	}
    }
}
