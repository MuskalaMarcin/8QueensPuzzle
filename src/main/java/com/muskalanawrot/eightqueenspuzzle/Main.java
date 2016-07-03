package com.muskalanawrot.eightqueenspuzzle;

import com.muskalanawrot.eightqueenspuzzle.implementation.ChessBoard;
import com.muskalanawrot.eightqueenspuzzle.implementation.GeneticAlgorithmMain;

import java.util.concurrent.*;

/**
 * Created by Marcin on 02.07.2016.
 */
public class Main
{
    public static void main(String args[])
    {
	int populationSize = 1000;
	int columnsNumber = 8;
	int rowsNumber = 8;
	int queensNumber = 8;
	int maxGenNumber = 600;
	float mutationPercent = 90f;
	float mutationRate = 10f;
	float crossoverPercent = 50f;

	GeneticAlgorithmMain geneticAlgorithmMain = new GeneticAlgorithmMain(populationSize, columnsNumber, rowsNumber,
			queensNumber, maxGenNumber, mutationPercent, mutationRate, crossoverPercent);
	ExecutorService executorService = Executors.newSingleThreadExecutor();
	Future<ChessBoard> finishedBoard = executorService.submit(geneticAlgorithmMain);

	try
	{
	    executorService.shutdown();
	    executorService.awaitTermination(1000, TimeUnit.DAYS);
	    ChessBoard chessBoard = finishedBoard.get();
	    chessBoard.getGenotype().forEach(f -> System.out.println(f));
	}
	catch (ExecutionException | InterruptedException e)
	{
	    e.printStackTrace();
	}
    }
}
