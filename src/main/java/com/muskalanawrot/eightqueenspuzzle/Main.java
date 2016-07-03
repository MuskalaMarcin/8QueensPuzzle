package com.muskalanawrot.eightqueenspuzzle;

import com.muskalanawrot.eightqueenspuzzle.implementation.ChessBoard;
import com.muskalanawrot.eightqueenspuzzle.implementation.GeneticAlgorithmMain;

import java.util.concurrent.*;

/**
 * Created by Marcin on 02.07.2016.
 */
public class Main
{
    /*
    public class Main extends Application
{

    public static void main(String[] args)
    {
	launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }
}
     */
    public static void main(String args[])
    {
	Integer populationSize = 1000;
	Integer columnsNumber = 8;
	Integer rowsNumber = 8;
	Integer queensNumber = 8;
	Integer maxGenNumber = 600;
	Float  mutationPercent = 90f;
	Double  mutationRate = 10d;
	Float  crossoverPercent = 50f;

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
