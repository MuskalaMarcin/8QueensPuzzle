package com.muskalanawrot.eightqueenspuzzle.implementation;

import com.muskalanawrot.eightqueenspuzzle.gui.MainController;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Main class for genetic algorithm.
 */
public class GeneticAlgorithmMain implements Callable<ChessBoard>
{
    private Integer populationSize;
    private Integer columnsNumber;
    private Integer rowsNumber;
    private Integer queensNumber;
    private Integer maxGenNumber;
    private Float crossoverPercent;
    private Float mutationPercent;
    private Double mutationRate;
    private MainController mainController;

    /**
     * Constructor for main genetic algorithm class.
     *
     * @param populationSize   number of objects in population
     * @param columnsNumber    number of columns on chess board
     * @param rowsNumber       number of rows on chess board
     * @param queensNumber     number of queens to place on chess board
     * @param maxGenNumber     number of generation after which program will end even without solution
     * @param mutationPercent  percentage of objects from population to undergo mutation
     * @param mutationRate     chances for object to mutate
     * @param crossoverPercent percentage of objects in next population that will be created by crossover
     * @param mainController   reference to main controller
     */
    public GeneticAlgorithmMain(Integer populationSize, Integer columnsNumber, Integer rowsNumber, Integer queensNumber,
		    Integer maxGenNumber, Float mutationPercent, Double mutationRate, Float crossoverPercent,
		    MainController mainController)
    {
	this.populationSize = populationSize;
	this.columnsNumber = columnsNumber;
	this.rowsNumber = rowsNumber;
	this.queensNumber = queensNumber;
	this.maxGenNumber = maxGenNumber;
	this.crossoverPercent = crossoverPercent;
	this.mutationPercent = mutationPercent;
	this.mutationRate = mutationRate;
	this.mainController = mainController;
    }

    /**
     * Main thread method, ends after selected number of generations or after finding
     * {@link ChessBoard} that is solution to problem.
     *
     * @return best {@link ChessBoard} generated through this algorithm
     */
    @Override
    public ChessBoard call()
    {
	Population population = new Population(populationSize, columnsNumber, rowsNumber, queensNumber);

	for (Integer i = 0; i < maxGenNumber; i++)
	{
	    mainController.getProgressBar().setProgress((double) i / maxGenNumber);
	    if (population.isFinished())
	    {
		mainController.getStatusField().setText("SUKCES");
		mainController.setFinished();
		return  population.getBestChessBoard();
	    }
	    else
	    {
		population = generateNewPopulation(population);
	    }
	    if (Thread.interrupted())
	    {
		return population.getBestChessBoard();
	    }
	}
	mainController.getStatusField().setText("BRAK OPTYMALNEGO");
	mainController.setFinished();
	return  population.getBestChessBoard();
    }

    /**
     * Method generating new generation based on previous. Performs crossover, selection and mutation.
     *
     * @param oldPopulation previous {@link Population}
     * @return new {@link Population} with next generation
     */
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
