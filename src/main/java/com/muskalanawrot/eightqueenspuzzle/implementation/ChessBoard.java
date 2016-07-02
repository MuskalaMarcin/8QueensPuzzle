package com.muskalanawrot.eightqueenspuzzle.implementation;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Marcin on 02.07.2016.
 */
public class ChessBoard
{
    private int rowsNumber;
    private int columnsNumber;
    private int queensNumber;
    private int fit;
    private Random random;
    private List<Queen> genotype;

    private ChessBoard(int rowsNumber, int columnsNumber, List<Queen> genotype)
    {
	this.rowsNumber = rowsNumber;
	this.columnsNumber = columnsNumber;
	this.queensNumber = genotype.size();
	this.genotype = genotype;
	this.random = new Random();
	this.fit = calculateFit();
    }

    public ChessBoard(int rowsNumber, int columnsNumber, int queensNumber)
    {
	this(rowsNumber, columnsNumber, getRandomGenotype(rowsNumber, columnsNumber, queensNumber));
    }

    private int getRowsNumber()
    {
	return rowsNumber;
    }

    private int getColumnsNumber()
    {
	return columnsNumber;
    }

    private int getQueensNumber()
    {
	return queensNumber;
    }

    public List<Queen> getGenotype()
    {
	return genotype;
    }

    public Integer getFit()
    {
	return fit;
    }

    public Integer calculateMaxFit()
    {
	return (int) Math.pow(queensNumber - 1, 2);
    }

    public int calculateFit()
    {
	int maxFit = calculateMaxFit();

	return (maxFit + genotype.stream().mapToInt(i -> i.getNumberOfCollisions(genotype)).sum()) % (maxFit + 1);
    }

    public void mutate()
    {
	genotype.get(random.nextInt(queensNumber)).mutate(rowsNumber, columnsNumber, random);
	fit = calculateFit();
    }

    public boolean isFinished()
    {
	return getFit().compareTo(calculateMaxFit()) == 0;
    }

    private static List<Queen> getRandomGenotype(int rowsNumber, int columnsNumber, int queensNumber)
    {
	return IntStream.range(0, queensNumber).boxed()
			.map(i -> QueenFactory.getNewQueen(rowsNumber, columnsNumber)).collect(
					Collectors.toList());
    }

    public static ChessBoard crossover(ChessBoard firstBoard, ChessBoard secondBoard)
    {
	Random random = new Random();
	List<Queen> secondGenotype = secondBoard.getGenotype();
	List<Queen> firstGenotype = getDistinctQueensList(firstBoard.getGenotype(), secondGenotype);

	int rowsNumber = firstBoard.getRowsNumber();
	int columnsNumber = firstBoard.getColumnsNumber();
	int queensNumber = firstBoard.getQueensNumber();
	List<Queen> newGenotype = IntStream.range(0, queensNumber).boxed().map(i ->
	{
	    if (i < firstGenotype.size() && random.nextBoolean())
	    {
		return firstGenotype.get(i);
	    }
	    else
	    {
		return secondGenotype.get(i);
	    }
	}).collect(Collectors.toList());

	return new ChessBoard(rowsNumber, columnsNumber, newGenotype);
    }

    private static List<Queen> getDistinctQueensList(List<Queen> firstGenotype, List<Queen> secondGenotype)
    {
	List<Queen> distinctList = new LinkedList<>(firstGenotype);
	distinctList.removeIf(f -> secondGenotype.stream()
			.filter(s -> s.getColumn() == f.getColumn() && s.getRow() == f.getRow()).findAny().isPresent());
	return distinctList;
    }

    public ChessBoard getCopy()
    {
	List<Queen> genotypeCopy = genotype.stream().map(queen -> queen.getCopy()).collect(Collectors.toList());

	return new ChessBoard(getRowsNumber(), getColumnsNumber(), genotypeCopy);
    }
}
