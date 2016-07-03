package com.muskalanawrot.eightqueenpuzzle.test;

import com.muskalanawrot.eightqueenspuzzle.implementation.Population;
import org.junit.Before;
import org.junit.Test;

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

    }

    @Test
    public void crossoverTest()
    {

    }

    @Test
    public void mutationTest()
    {

    }
}
