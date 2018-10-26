package Controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import view.mainWindow.MainWindow;

public class Main extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {

		MainWindow mainWindow = new MainWindow();
		mainWindow.initializeGUI(primaryStage);

		///////////////// DO READERA

		// Scanner input = new Scanner(System.in);
		// UniprotReader uniprotReader = new UniprotReader();
		// uniprotReader.start();
		// System.out.println("Nr rekordu :");
		//
		// int recordNr = 1;
		//
		// uniprotReader.parseRecord(recordNr);
		// System.out.println("Nr rekordu :");

	}

}
