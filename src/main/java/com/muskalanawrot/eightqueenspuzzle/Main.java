package com.muskalanawrot.eightqueenspuzzle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class starting app and creating application window.
 */
public class Main extends Application
{
    public static void main(String[] args)
    {
	launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
	Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
	primaryStage.setTitle("Problem 8 hetmanów - Muskała, Nawrot");
	primaryStage.setScene(new Scene(root, 365, 600));
	primaryStage.setResizable(false);
	primaryStage.show();
    }
}
