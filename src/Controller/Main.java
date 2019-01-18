package controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import view.mainWindow.MainWindow;

public class Main extends Application {

	private Application app;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {

		MainWindow mainWindow = new MainWindow();
		mainWindow.initializeGUI(primaryStage);

	}

}
