package Controller;

import javafx.application.Application;
import javafx.stage.Stage;
import view.mainWindow.MainWindow;

public class Main extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		MainWindow mainWindow = new MainWindow();
		mainWindow.initializeGUI(primaryStage);
	}

}
