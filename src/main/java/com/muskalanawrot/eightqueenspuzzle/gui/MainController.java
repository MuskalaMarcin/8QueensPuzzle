package com.muskalanawrot.eightqueenspuzzle.gui;

import com.muskalanawrot.eightqueenspuzzle.implementation.ChessBoard;
import com.muskalanawrot.eightqueenspuzzle.implementation.GeneticAlgorithmMain;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.*;

/**
 * Controller class for main application window.
 */
public class MainController implements Initializable
{
    @FXML
    private Pane mainPanel;

    @FXML
    private Button startButton;
    @FXML
    private Button resultButton;

    @FXML
    private Slider crossoverPercent;
    @FXML
    private Slider mutationPercent;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private TextField columnsNumber;
    @FXML
    private TextField rowsNumber;
    @FXML
    private TextField queensNumber;
    @FXML
    private TextField mutationRate;
    @FXML
    private TextField maxGenNumber;
    @FXML
    private TextField populationSize;
    @FXML
    private TextField statusField;

    private ExecutorService executorService;
    private Future<ChessBoard> chessBoardFuture;
    private ChessBoard chessBoard;
    private int rowsNumberValue;
    private int columnsNumberValue;

    public void setFinished()
    {
	Platform.runLater(() -> {
	    try
	    {
		executorService.shutdown();
		executorService.awaitTermination(100, TimeUnit.SECONDS);
		this.chessBoard = chessBoardFuture.get();
		progressBar.setProgress(1d);
		resultButton.setDisable(false);
	    }
	    catch (ExecutionException | InterruptedException e)
	    {
		statusField.setText("BŁĄD PROGRAMU");
		progressBar.setProgress(0d);
		resultButton.setDisable(true);
		e.printStackTrace();
	    }
	    startButton.setText("Start");
	    crossoverPercent.setDisable(false);
	    mutationPercent.setDisable(false);
	    columnsNumber.setDisable(false);
	    rowsNumber.setDisable(false);
	    queensNumber.setDisable(false);
	    queensNumber.setDisable(false);
	    mutationRate.setDisable(false);
	    resultButton.setDisable(false);
	    maxGenNumber.setDisable(false);
	    populationSize.setDisable(false);
	});

    }

    public ProgressBar getProgressBar()
    {
	return progressBar;
    }

    public TextField getStatusField()
    {
	return statusField;
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources)
    {
	mainPanel.setFocusTraversable(true);
	resultButton.setDisable(true);

	crossoverPercent.setMin(0);
	crossoverPercent.setMax(100);
	crossoverPercent.setBlockIncrement(1d);
	crossoverPercent.setValue(50);
	crossoverPercent.setShowTickLabels(true);
	crossoverPercent.setShowTickMarks(true);
	crossoverPercent.setMajorTickUnit(25);
	crossoverPercent.setMinorTickCount(5);

	mutationPercent.setMin(0);
	mutationPercent.setMax(100);
	mutationPercent.setBlockIncrement(1d);
	mutationPercent.setValue(90);
	mutationPercent.setShowTickLabels(true);
	mutationPercent.setShowTickMarks(true);
	mutationPercent.setMajorTickUnit(25);
	mutationPercent.setMinorTickCount(5);

	mainPanel.setOnMouseClicked(handle -> progressBar.requestFocus());

	startButton.setOnMouseClicked(handle -> {
	    if (startButton.getText().equals("Start"))
	    {
		handleStartButtonClicked();
	    }
	    else
	    {
		handleStopButtonClicked();
	    }
	});

	resultButton.setOnMouseClicked(handle -> handleResultButton());
    }

    private void handleResultButton()
    {
	ResultWindow resultWindow = new ResultWindow(chessBoard, rowsNumberValue, columnsNumberValue);
	resultWindow.showWindow();
    }

    private Integer getIntegerFromTextField(String message, TextField textField) throws UnsupportedOperationException
    {
	try
	{
	    Integer value = Integer.parseInt(textField.getText());
	    if (value < 0) throw new NumberFormatException();
	    return value;
	}
	catch (NumberFormatException e)
	{
	    Alert alert = new Alert(Alert.AlertType.WARNING);
	    alert.setTitle("Błąd danych wejściowych");
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();

	    throw new UnsupportedOperationException();
	}
    }

    private Integer getPopulationSize() throws UnsupportedOperationException
    {
	return getIntegerFromTextField("Wielkość populacji musi być liczbą całkowitą większą od 0!", populationSize);
    }

    private Integer getColumnsNumber() throws UnsupportedOperationException
    {
	return getIntegerFromTextField("Ilość kolumn musi być liczbą całkowitą większą od 0!", columnsNumber);
    }

    private Integer getRowsNumber() throws UnsupportedOperationException
    {
	return getIntegerFromTextField("Ilość wierszy musi być liczbą całkowitą większą od 0!", rowsNumber);
    }

    private Integer getMaxGenNumber() throws UnsupportedOperationException
    {
	return getIntegerFromTextField("Maksymalna ilość generacji musi być liczbą całkowitą większą od 0!",
			maxGenNumber);
    }

    private Integer getQueensNumber() throws UnsupportedOperationException
    {
	return getIntegerFromTextField("Ilość hetmanów musi być większa od 0!", queensNumber);
    }

    private Float getMutationPercent()
    {
	return new Float(mutationPercent.getValue());
    }

    private Double getMutationRate() throws UnsupportedOperationException
    {
	try
	{
	    Double value = Double.parseDouble(mutationRate.getText().replace(',', '.'));
	    if (value < 0 || value > 100) throw new NumberFormatException();
	    return value;
	}
	catch (NumberFormatException e)
	{
	    Alert alert = new Alert(Alert.AlertType.WARNING);
	    alert.setTitle("Błąd danych wejściowych");
	    alert.setHeaderText(null);
	    alert.setContentText("Prawdopodobieństwo mutacji musi być liczbą rzeczywistą z przedziału 0 do 100!");
	    alert.showAndWait();

	    throw new UnsupportedOperationException();
	}
    }

    private Float getCrossoverPercent()
    {
	return new Float(crossoverPercent.getValue());
    }

    private void handleStartButtonClicked()
    {
	try
	{
	    Integer populationSizeV = getPopulationSize();
	    Integer columnsNumberV = getColumnsNumber();
	    Integer rowsNumberV = getRowsNumber();
	    Integer queensNumberV = getQueensNumber();
	    Integer maxGenNumberV = getMaxGenNumber();
	    Float mutationPercentV = getMutationPercent();
	    Double mutationRateV = getMutationRate();
	    Float crossoverPercentV = getCrossoverPercent();

	    this.executorService = Executors.newSingleThreadExecutor();
	    GeneticAlgorithmMain geneticAlgorithmMain = new GeneticAlgorithmMain(populationSizeV, columnsNumberV,
			    rowsNumberV, queensNumberV, maxGenNumberV, mutationPercentV, mutationRateV,
			    crossoverPercentV, this);
	    columnsNumberValue = columnsNumberV;
	    rowsNumberValue = rowsNumberV;
	    chessBoardFuture = executorService.submit(geneticAlgorithmMain);

	    resultButton.setDisable(true);
	    crossoverPercent.setDisable(true);
	    progressBar.setProgress(0d);
	    mutationPercent.setDisable(true);
	    columnsNumber.setDisable(true);
	    rowsNumber.setDisable(true);
	    resultButton.setDisable(true);
	    queensNumber.setDisable(true);
	    queensNumber.setDisable(true);
	    mutationRate.setDisable(true);
	    maxGenNumber.setDisable(true);
	    populationSize.setDisable(true);
	    startButton.setText("Stop");
	    statusField.setText("URUCHOMIONO");
	}
	catch (UnsupportedOperationException e)
	{
	    System.out.println("Błędne dane wejściowe");
	}
    }

    private void handleStopButtonClicked()
    {
	startButton.setText("Start");
	statusField.setText("ZAKOŃCZONO");
	progressBar.setProgress(0d);
	resultButton.setDisable(true);
	crossoverPercent.setDisable(false);
	mutationPercent.setDisable(false);
	columnsNumber.setDisable(false);
	rowsNumber.setDisable(false);
	queensNumber.setDisable(false);
	queensNumber.setDisable(false);
	mutationRate.setDisable(false);
	maxGenNumber.setDisable(false);
	populationSize.setDisable(false);
	chessBoardFuture.cancel(true);
	executorService.shutdownNow();
    }
}
