package com.muskalanawrot.eightqueenspuzzle.test;

import com.muskalanawrot.eightqueenspuzzle.implementation.ChessBoard;
import com.muskalanawrot.eightqueenspuzzle.implementation.Population;
import com.muskalanawrot.eightqueenspuzzle.implementation.Queen;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class for {@link Population} class.
 */
public class PopulationTest
{
    private static Integer POPULATION_SIZE = 500;
    private static Integer COLUMNS_NUMBER = 8;
    private static Integer ROWS_NUMBER = 8;
    private static Integer QUEENS_NUMBER = 8;

    private Population population;

    @Before
    public void setUp()
    {
	population = new Population(POPULATION_SIZE, COLUMNS_NUMBER, ROWS_NUMBER, QUEENS_NUMBER);
    }

    @Test
    public void selectionTest()
    {
	//Given:
	final Float selectionPercent = 50f;

	//When:
	List<ChessBoard> afterSelection = population.performSelection(selectionPercent);

	//Then:
	assertEquals("Number of boards after selection doesn't match expected.", 250, afterSelection.size());

	List<ChessBoard> sortedList = afterSelection.stream().sorted((y, x) -> (x.getFit().compareTo(y.getFit())))
			.collect(Collectors.toList());
	assertEquals("Results aren't sorted.", sortedList, afterSelection);
    }

    @Test
    public void crossoverTest()
    {
	//Given:
	final Float crossoverPercent = 50f;

	//When:
	List<ChessBoard> afterCrossover = population.getChildrens(crossoverPercent);

	//Then:
	assertEquals("Number of children after crossover doesn't match expected.", 250, afterCrossover.size());
    }
}
