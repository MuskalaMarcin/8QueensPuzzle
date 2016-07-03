package com.muskalanawrot.eightqueenspuzzle.implementation;

import java.util.LinkedList;
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
    private int populationSize;
    private Random random;

    public Population(int populationSize, int columnsNumber, int rowsNumber, int queensNumber)
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

    private static List<ChessBoard> generatePopulation(int rowsNumber, int columnsNumber, int queensNumber,
		    int populationSize)
    {
	return IntStream.range(0, populationSize).boxed().parallel()
			.map(m -> new ChessBoard(rowsNumber, columnsNumber, queensNumber)).collect(
					Collectors.toList());
    }

    private ChessBoard getRandomObject(List<ChessBoard> chessBoardList)
    {
	double maxFit = chessBoardList.stream().mapToDouble(ChessBoard::getFit).max().getAsDouble();
	int i = 0;
	int maxIndex = chessBoardList.size() - 1;
	int index;
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
	/*double fitSum = chessBoardList.stream().mapToDouble(ChessBoard::getFit).sum();

	double value = fitSum * random.nextDouble();

	for (int i = 0; i < chessBoardList.size(); i++)
	{
	    value -= chessBoardList.get(i).getFit();
	    if (value <= 0) return chessBoardList.get(i);
	}

	return chessBoardList.get(random.nextInt(chessBoardList.size()));*/
    }

    public void sortPopulation()
    {
	objectsList.sort((y, x) -> x.getFit().compareTo(y.getFit()));
    }

    public List<ChessBoard> performSelection(float selectionPercent)
    {
	int selectionNumber = Math.round(selectionPercent * populationSize / 100);

	sortPopulation();
	return objectsList.subList(0, populationSize - selectionNumber).stream().map(chessBoard -> chessBoard.getCopy())
			.collect(Collectors.toList());
    }

    public List<ChessBoard> getChildrens(float crossoverPercent)
    {
	int numberOfCrossovers = Math.round(crossoverPercent * populationSize / 100);
	List<ChessBoard> chessBoardList = new LinkedList<>(objectsList);
	List<ChessBoard> childrenList = new LinkedList<>();

	for (int i = 0; i < numberOfCrossovers; i++)
	{
	    ChessBoard firstBoard = getRandomObject(chessBoardList);
	    ChessBoard secondBoard = getRandomObject(chessBoardList);

	    childrenList.add(ChessBoard.crossover(firstBoard, secondBoard));
	}
	return childrenList;
    }

    public void mutatePopulation(float mutationPercent, double mutationRate)
    {
	int minMutationIndex = populationSize - Math.round(populationSize * mutationPercent / 100);
	System.out.println(minMutationIndex);
	sortPopulation();
	objectsList.subList(minMutationIndex, populationSize).forEach(board -> {
	    if ((random.nextDouble() * 100d) < mutationRate) board.mutate();
	});
	sortPopulation();
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
