package com.muskalanawrot.eightqueenspuzzle.implementation;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class representing one chess board which in our case is one genotype.
 */
public class ChessBoard
{
    private Integer rowsNumber;
    private Integer columnsNumber;
    private Integer queensNumber;
    private Integer fit;
    private Random random;
    private List<Queen> genotype;

    private ChessBoard(Integer rowsNumber, Integer columnsNumber, List<Queen> genotype)
    {
	this.rowsNumber = rowsNumber;
	this.columnsNumber = columnsNumber;
	this.queensNumber = genotype.size();
	this.genotype = genotype;
	this.random = new Random();
	calculateFit();
    }

    public ChessBoard(Integer rowsNumber, Integer columnsNumber, Integer queensNumber)
    {
	this(rowsNumber, columnsNumber, getRandomGenotype(rowsNumber, columnsNumber, queensNumber));
    }

    private Integer getRowsNumber()
    {
	return rowsNumber;
    }

    private Integer getColumnsNumber()
    {
	return columnsNumber;
    }

    private Integer getQueensNumber()
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

    /**
     * Method calculating max possible fit value for current situation.
     *
     * @return max fit value
     */
    private Integer calculateMaxFit()
    {
	return queensNumber * (queensNumber - 1);
    }

    /**
     * Calculates and sets current fit value for this object.
     */
    private void calculateFit()
    {
	this.fit = calculateMaxFit() - genotype.stream().mapToInt(i -> i.getNumberOfCollisions(genotype)).sum();
    }

    /**
     * Mutates one random {@link Queen} object from genotype and recalculates fit.
     */
    public void mutate()
    {
	genotype.get(random.nextInt(queensNumber)).mutate(rowsNumber, columnsNumber, random);
	calculateFit();
    }

    /**
     * Returns true if this object's fit is equal to max possible fit
     *
     * @return true if 8 queens puzzle is solved by this object
     */
    public boolean isFinished()
    {
	return getFit().compareTo(calculateMaxFit()) == 0;
    }

    /**
     * Method used to initialize genotype of this class with random {@link Queen} objects from {@link QueenFactory}
     *
     * @param rowsNumber    max rows number
     * @param columnsNumber max columns number
     * @param queensNumber  number of queens in genotype
     * @return new genotyp
     */
    private static List<Queen> getRandomGenotype(Integer rowsNumber, Integer columnsNumber, Integer queensNumber)
    {
	return IntStream.range(0, queensNumber).boxed()
			.map(i -> QueenFactory.getNewQueen(rowsNumber, columnsNumber)).collect(
					Collectors.toList());
    }

    /**
     * Method performing crossover operation on two chess boards (parents).
     *
     * @param firstBoard  first parent
     * @param secondBoard second parent
     * @return generated child
     */
    public static ChessBoard crossover(ChessBoard firstBoard, ChessBoard secondBoard)
    {
	Random random = new Random();
	List<Queen> secondGenotype = secondBoard.getGenotype();
	List<Queen> firstGenotype = getDistinctQueensList(firstBoard.getGenotype(), secondGenotype);

	Integer rowsNumber = firstBoard.getRowsNumber();
	Integer columnsNumber = firstBoard.getColumnsNumber();
	Integer queensNumber = firstBoard.getQueensNumber();
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

    /**
     * Method removes from first genotype repeated queen values.
     *
     * @param firstGenotype  first genotype
     * @param secondGenotype second genotype
     * @return first genotype without repeated values
     */
    private static List<Queen> getDistinctQueensList(List<Queen> firstGenotype, List<Queen> secondGenotype)
    {
	List<Queen> distinctList = new LinkedList<>(firstGenotype);
	distinctList.removeIf(f -> secondGenotype.stream()
			.filter(s -> s.getColumn() == f.getColumn() && s.getRow() == f.getRow()).findAny().isPresent());
	return distinctList;
    }

    /**
     * Method returns copy of this object.
     *
     * @return copy of this object
     */
    public ChessBoard getCopy()
    {
	List<Queen> genotypeCopy = genotype.stream().map(queen -> queen.getCopy()).collect(Collectors.toList());

	return new ChessBoard(getRowsNumber(), getColumnsNumber(), genotypeCopy);
    }
}
