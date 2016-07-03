package com.muskalanawrot.eightqueenspuzzle.implementation;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class representing one generation/population from genetic algorithm.
 */
public class Population
{
    private List<ChessBoard> objectsList;
    private Integer populationSize;
    private Random random;

    public Population(Integer populationSize, Integer columnsNumber, Integer rowsNumber, Integer queensNumber)
    {
	this(generatePopulation(rowsNumber, columnsNumber, queensNumber, populationSize));
    }

    public Population(List<ChessBoard> objectsList)
    {
	this.populationSize = objectsList.size();
	this.random = new Random();
	this.objectsList = objectsList;
	sortPopulation();
    }

    /**
     * Method generating new random population.
     *
     * @param rowsNumber     max number of rows
     * @param columnsNumber  max number of columns
     * @param queensNumber   number of queens in single genotype
     * @param populationSize size of population
     * @return list of {@link ChessBoard} population
     */
    private static List<ChessBoard> generatePopulation(Integer rowsNumber, Integer columnsNumber, Integer queensNumber,
		    Integer populationSize)
    {
	return IntStream.range(0, populationSize).boxed().parallel()
			.map(m -> new ChessBoard(rowsNumber, columnsNumber, queensNumber)).collect(
					Collectors.toList());
    }

    /**
     * Method returning random object from population by performing stochastic roulette wheel selection.
     *
     * @param chessBoardList all {@link ChessBoard} to choose
     * @return random {@link ChessBoard}
     */
    private ChessBoard getRandomObject(List<ChessBoard> chessBoardList)
    {
	Double maxFit = chessBoardList.stream().mapToDouble(ChessBoard::getFit).max().getAsDouble();
	Integer i = 0;
	Integer maxIndex = chessBoardList.size() - 1;
	Integer index;
	while (i < 1000)
	{
	    index = (int) Math.round(maxIndex * random.nextDouble());
	    ChessBoard chessBoard = chessBoardList.get(index);

	    if (random.nextDouble() < chessBoard.getFit() / maxFit)
	    {
		return chessBoard;
	    }
	    i++;
	}
	return chessBoardList.get(random.nextInt(chessBoardList.size()));
    }

    private void sortPopulation()
    {
	objectsList.sort((y, x) -> x.getFit().compareTo(y.getFit()));
    }

    /**
     * Method performing selection on population, leaving worst ones behind.
     *
     * @param selectionPercent percentage of object to be passed to next population
     * @return copy of best {@link ChessBoard} objects
     */
    public List<ChessBoard> performSelection(Float selectionPercent)
    {
	Integer selectionNumber = Math.round(selectionPercent * populationSize / 100);

	sortPopulation();
	return objectsList.subList(0, populationSize - selectionNumber).stream().map(chessBoard -> chessBoard.getCopy())
			.collect(Collectors.toList());
    }

    /**
     * Method returning list of childrens.
     *
     * @param crossoverPercent percentage of population that will be childrens of objects from previous
     * @return list of childrens
     */
    public List<ChessBoard> getChildrens(Float crossoverPercent)
    {
	Integer numberOfCrossovers = Math.round(crossoverPercent * populationSize / 100);
	List<ChessBoard> chessBoardList = new LinkedList<>(objectsList);
	List<ChessBoard> childrenList = new LinkedList<>();

	for (Integer i = 0; i < numberOfCrossovers; i++)
	{
	    ChessBoard firstBoard = getRandomObject(chessBoardList);
	    ChessBoard secondBoard = getRandomObject(chessBoardList);

	    int counter = 0;
	    while (secondBoard.equals(firstBoard) && counter < 1000)
	    {
		secondBoard = getRandomObject(chessBoardList);
		counter++;
	    }

	    childrenList.add(ChessBoard.crossover(firstBoard, secondBoard));
	}
	return childrenList;
    }

    /**
     * Method performing mutations in population.
     *
     * @param mutationPercent percentage of objects to take part in mutation (starting from the worst ones)
     * @param mutationRate    chance rate of single mutation
     */
    public void mutatePopulation(Float mutationPercent, Double mutationRate)
    {
	Integer minMutationIndex = populationSize - Math.round(populationSize * mutationPercent / 100);

	sortPopulation();
	objectsList.subList(minMutationIndex, populationSize).forEach(board -> {
	    if ((random.nextDouble() * 100d) < mutationRate) board.mutate();
	});
	sortPopulation();
    }

    /**
     * Method returning {@link ChessBoard} object with biggest fit value from population.
     *
     * @return best {@link ChessBoard}
     */
    public ChessBoard getBestChessBoard()
    {
	sortPopulation();
	return objectsList.get(0);
    }

    /**
     * Method checking if any of objects in population passed exit conditions.
     *
     * @return true if finished
     */
    public boolean isFinished()
    {
	return objectsList.stream().filter(p -> p.isFinished()).findAny().isPresent();
    }
}
