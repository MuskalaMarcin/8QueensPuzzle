package com.muskalanawrot.eightqueenspuzzle.gui;

import com.muskalanawrot.eightqueenspuzzle.implementation.ChessBoard;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.swing.border.Border;
import java.awt.*;

/**
 * Class for window showing application results.
 */
public class ResultWindow extends Scene
{
    private ChessBoard chessBoard;
    private int rowsNumber;
    private int columnsNumber;
    private Stage stage;
    private GridPane gridPane;
    private static Group root = new Group();
    private static final String LIGHT_COLOR = "#FFE4C4";
    private static final String DARK_COLOR = "#CD853F";

    public void showWindow()
    {
	stage.show();
    }

    ResultWindow(ChessBoard chessBoard, int rowsNumber, int columnsNumber)
    {
	super(root, 40 * (rowsNumber + 2), 40 * (columnsNumber + 2));
	this.chessBoard = chessBoard;
	this.rowsNumber = rowsNumber;
	this.columnsNumber = columnsNumber;
	this.stage = new Stage();
	this.gridPane = new GridPane();

	initializeResultWindow();
	setRoot(gridPane);
    }

    private void initializeResultWindow()
    {
	stage.setTitle("Wynik algorytmu");
	stage.setMinHeight(40 * (rowsNumber + 2));
	stage.setMinWidth(40 * (columnsNumber + 2));
	stage.setMaxHeight(40 * (rowsNumber + 2));
	stage.setMaxWidth(40 * (columnsNumber + 2));
	stage.setScene(this);
	gridPane.setVgap(0);
	gridPane.setHgap(0);
	gridPane.setMinHeight(40 * (rowsNumber + 2));
	gridPane.setMinWidth(40 * (columnsNumber + 2));
	gridPane.setMaxHeight(40 * (rowsNumber + 2));
	gridPane.setMaxWidth(40 * (columnsNumber + 2));
	stage.setResizable(false);
	for (int row = 0; row <= rowsNumber; row++)
	{
	    if (row != rowsNumber)
	    {
		Label rowNumber = new Label("" + (rowsNumber - row));
		rowNumber.setFont(Font.font("Verdana", 14));
		GridPane innerPane = new GridPane();
		innerPane.setMinHeight(40);
		innerPane.setMinWidth(40);
		innerPane.setMaxHeight(40);
		innerPane.setMaxWidth(40);
		innerPane.setAlignment(Pos.CENTER);
		GridPane.setConstraints(rowNumber, 0, 0);
		innerPane.getChildren().add(rowNumber);
		GridPane.setConstraints(innerPane, 0, row);
		innerPane.setStyle("-fx-background-color:" + DARK_COLOR + ";");
		gridPane.getChildren().add(innerPane);
	    }
	    int rowFinal = row;
	    for (int column = 0; column <= columnsNumber; column++)
	    {
		if (column != 0)
		{
		    String color;
		    if ((row + column) % 2 == 0)
		    {
			color = DARK_COLOR;
		    }
		    else
		    {
			color = LIGHT_COLOR;
		    }
		    int columnFinal = column;
		    if (row == rowsNumber)
		    {
			Label columnLetter = new Label("" + (char) ('A' + column - 1));
			columnLetter.setFont(Font.font("Verdana", 14));
			GridPane innerPane = new GridPane();
			innerPane.setMinHeight(40);
			innerPane.setMinWidth(40);
			innerPane.setMaxHeight(40);
			innerPane.setMaxWidth(40);
			innerPane.setAlignment(Pos.CENTER);
			GridPane.setConstraints(columnLetter, 0, 0);
			innerPane.getChildren().add(columnLetter);
			GridPane.setConstraints(innerPane, column, row);
			innerPane.setStyle("-fx-background-color:" + DARK_COLOR + ";");
			gridPane.getChildren().add(innerPane);
		    }
		    else if (chessBoard.getGenotype().stream().filter(g -> g.getColumn().intValue() + 1 == columnFinal
				    && rowsNumber - g.getRow().intValue() - 1 == rowFinal).findAny().isPresent())
		    {
			javafx.scene.image.Image image = new Image("queen.png");
			ImageView imageView = new ImageView(image);
			GridPane innerPane = new GridPane();
			innerPane.setMinHeight(40);
			innerPane.setMinWidth(40);
			innerPane.setMaxHeight(40);
			innerPane.setMaxWidth(40);
			innerPane.setAlignment(Pos.CENTER);
			GridPane.setConstraints(imageView, 0, 0);
			innerPane.getChildren().add(imageView);
			GridPane.setConstraints(innerPane, column, row);
			innerPane.setStyle("-fx-background-color: " + color + ";");
			gridPane.getChildren().add(innerPane);
		    }
		    else
		    {
			Label columnLetter = new Label("");
			columnLetter.setFont(Font.font("Verdana", 14));
			GridPane innerPane = new GridPane();
			innerPane.setMinHeight(40);
			innerPane.setMinWidth(40);
			innerPane.setMaxHeight(40);
			innerPane.setMaxWidth(40);
			innerPane.setAlignment(Pos.CENTER);
			GridPane.setConstraints(columnLetter, 0, 0);
			innerPane.getChildren().add(columnLetter);
			GridPane.setConstraints(innerPane, column, row);
			innerPane.setStyle("-fx-background-color: " + color + ";");
			gridPane.getChildren().add(innerPane);
		    }
		}
	    }
	}
    }
}
