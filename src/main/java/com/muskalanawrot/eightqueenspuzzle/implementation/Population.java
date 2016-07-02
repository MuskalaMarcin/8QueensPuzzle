package com.muskalanawrot.eightqueenspuzzle.implementation;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Marcin on 02.07.2016.
 */
public class Population
{
    private List<ChessBoard> objectsList;
    private int columnsNumber;
    private int rowsNumber;
    private int queensNumber;
    private Random random;

    public Population(int populationSize, int columnsNumber, int rowsNumber, int queensNumber)
    {
	this.columnsNumber = columnsNumber;
	this.rowsNumber = rowsNumber;
	this.queensNumber = queensNumber;
	this.random = new Random();
	this.objectsList = generatePopulation(populationSize);
	sortPopulation();
    }

    private List<ChessBoard> generatePopulation(int populationSize)
    {
	return IntStream.range(0, populationSize).boxed().parallel()
			.map(m -> new ChessBoard(rowsNumber, columnsNumber, queensNumber)).collect(
					Collectors.toList());
    }

    public Population generateNextPopulation()
    {
	return null;
    }

    public void mutatePopulation(double mutationRate)
    {
	objectsList.forEach(board -> {
	    if ((random.nextDouble() * 100d) < mutationRate) board.mutate();
	});
	sortPopulation();
    }

    private void sortPopulation()
    {
	objectsList.sort((x, y) -> x.getFit().compareTo(y.getFit()));
    }

    public ChessBoard getBestChessBoard()
    {
	sortPopulation();
	return objectsList.get(0);
    }

    public boolean isFinished()
    {
	return objectsList.stream().filter(p -> p.isFinished()).findAny().isPresent();
    }
}
