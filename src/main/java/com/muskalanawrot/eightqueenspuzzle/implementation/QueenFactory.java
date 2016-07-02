package com.muskalanawrot.eightqueenspuzzle.implementation;

import java.util.Random;

/**
 * Created by Marcin on 02.07.2016.
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

    public static Queen getNewQueen(int rowsNumber, int columnsNumber)
    {
	return new Queen(random.nextInt(rowsNumber), random.nextInt(columnsNumber));
    }
}
