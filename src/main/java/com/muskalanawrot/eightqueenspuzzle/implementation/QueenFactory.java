package com.muskalanawrot.eightqueenspuzzle.implementation;

import java.util.Random;

/**
 * Factory class for {@link Queen} objects.
 */
public class QueenFactory
{
    private static Random random = new Random();

    /**
     * Static class do not instantiate.
     */
    private QueenFactory()
    {
    }

    /**
     * Returns new {@link Queen} object with row and columns inside specified boundaries.
     *
     * @param rowsNumber    max number of row
     * @param columnsNumber max number of column
     * @return new {@link Queen} object
     */
    public static Queen getNewQueen(int rowsNumber, int columnsNumber)
    {
	return new Queen(random.nextInt(rowsNumber), random.nextInt(columnsNumber));
    }
}
