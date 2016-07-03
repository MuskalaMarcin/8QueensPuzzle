package com.muskalanawrot.eightqueenspuzzle.implementation;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Marcin on 02.07.2016.
 */
public class GeneticAlgorithmMain implements Callable<ChessBoard>
{
    private int populationSize;
    private int columnsNumber;
    private int rowsNumber;
    private int queensNumber;
    private int maxGenNumber;
    private float crossoverPercent;
    private float mutationPercent;
    private float mutationRate;

    public GeneticAlgorithmMain(int populationSize, int columnsNumber, int rowsNumber, int queensNumber,
		    int maxGenNumber, float mutationPercent, float mutationRate, float crossoverPercent)
    {
	this.populationSize = populationSize;
	this.columnsNumber = columnsNumber;
	this.rowsNumber = rowsNumber;
	this.queensNumber = queensNumber;
	this.maxGenNumber = maxGenNumber;
	this.crossoverPercent = crossoverPercent;
	this.mutationPercent = mutationPercent;
	this.mutationRate = mutationRate;
    }

    @Override
    public ChessBoard call()
    {
	Population population = new Population(populationSize, columnsNumber, rowsNumber, queensNumber);

	for (int i = 0; i < maxGenNumber; i++)
	{
	    System.out.println(i);
	    if (population.isFinished())
	    {
		System.out.println(population.getBestChessBoard().getFit());
		return population.getBestChessBoard();
	    }
	    else
	    {
		population = generateNewPopulation(population);
	    }
	}
	System.out.println(population.getBestChessBoard().getFit());
	return population.getBestChessBoard();
    }

    private Population generateNewPopulation(Population oldPopulation)
    {
	List<ChessBoard> objectsList = new LinkedList<>();
	objectsList.addAll(oldPopulation.getChildrens(crossoverPercent));
	objectsList.addAll(oldPopulation.performSelection(crossoverPercent));

	Population newPopulation = new Population(objectsList);
	newPopulation.mutatePopulation(mutationPercent, mutationRate);

	return newPopulation;
    }
}
