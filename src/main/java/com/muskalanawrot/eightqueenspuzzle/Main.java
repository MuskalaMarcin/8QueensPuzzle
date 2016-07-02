package com.muskalanawrot.eightqueenspuzzle;

import com.muskalanawrot.eightqueenspuzzle.implementation.ChessBoard;
import com.muskalanawrot.eightqueenspuzzle.implementation.GeneticAlgorithmMain;

import java.sql.Time;
import java.util.concurrent.*;

/**
 * Created by Marcin on 02.07.2016.
 */
public class Main
{
    public static void main(String args[])
    {
	int populationSize = 100;
	int columnsNumber = 8;
	int rowsNumber = 8;
	int queensNumber = 8;
	int maxGenNumber = 400;
	float mutationPercent = 20f;
	float mutationRate = 2.5f;
	float crossoverPercent = 40f;

	GeneticAlgorithmMain geneticAlgorithmMain = new GeneticAlgorithmMain(populationSize, columnsNumber, rowsNumber,
			queensNumber, maxGenNumber, mutationPercent, mutationRate, crossoverPercent);
	ExecutorService executorService = Executors.newSingleThreadExecutor();
	Future<ChessBoard> finishedBoard = executorService.submit(geneticAlgorithmMain);

	try
	{
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
