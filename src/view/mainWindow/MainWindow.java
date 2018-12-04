package view.mainWindow;

import java.sql.DriverManager;
import java.sql.SQLException;

import Controller.MainWindowController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.dataBase.DataBaseModel;
import view.additionalWindows.AddEditFileWindow;
import view.additionalWindows.DownloadDBWindow;

public class MainWindow {

	public void initializeGUI(Stage primaryStage) {

		primaryStage.setTitle("Thesis");
		ButtonsGridPane buttons = new ButtonsGridPane();
		FilterPane filter = new FilterPane();
		FilesTable filesTable = new FilesTable();
		RecordsTable recordsTable = new RecordsTable();
		DownloadDBWindow download = new DownloadDBWindow();

		AddEditFileWindow addEditFileWindow = new AddEditFileWindow();
		MainWindowController controller = new MainWindowController(buttons, filesTable, addEditFileWindow, filter);

		DataBaseModel db = new DataBaseModel();

		BorderPane root = initializeRoot(buttons, filesTable, recordsTable, filter);
		primaryStage.setScene(new Scene(root, 1000, 600));
		primaryStage.setOnCloseRequest(event -> {

			System.out.println("Closing Stage");
			try {
				DriverManager.getConnection("jdbc:derby:Fasta_DB;shutdown=true");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		});
		primaryStage.show();
	}

	public BorderPane initializeRoot(ButtonsGridPane buttons, FilesTable filesTable, RecordsTable recordsTable,
			FilterPane filter) {

		BorderPane root = new BorderPane();
		initializeMenu(root);

		AnchorPane ap = new AnchorPane();
		root.setCenter(ap);
		SplitPane splitPane1 = new SplitPane();
		splitPane1.setOrientation(Orientation.HORIZONTAL);
		AnchorPane apl = new AnchorPane();

		// for filestable
		AnchorPane anchorFilesTable = new AnchorPane();
		anchorFilesTable.getChildren().add(filesTable);
		anchorFilesTable.setTopAnchor(filesTable, 0.0);
		anchorFilesTable.setRightAnchor(filesTable, 0.0);
		anchorFilesTable.setLeftAnchor(filesTable, 0.0);
		anchorFilesTable.setBottomAnchor(filesTable, 0.0);

		// for Recordstable
		AnchorPane anchorRecordsTable = new AnchorPane();
		anchorRecordsTable.getChildren().add(recordsTable);
		anchorRecordsTable.setTopAnchor(recordsTable, 0.0);
		anchorRecordsTable.setRightAnchor(recordsTable, 0.0);
		anchorRecordsTable.setLeftAnchor(recordsTable, 0.0);
		anchorRecordsTable.setBottomAnchor(recordsTable, 0.0);

		// for filter
		AnchorPane anchorFilter = new AnchorPane();
		anchorFilter.getChildren().add(filter);
		anchorFilter.setTopAnchor(filter, 0.0);
		anchorFilter.setRightAnchor(filter, 0.0);
		anchorFilter.setLeftAnchor(filter, 0.0);
		// anchorFilter.setBottomAnchor(filter, 0.0);

		// for buttons
		AnchorPane anchorButtons = new AnchorPane();
		anchorButtons.getChildren().add(buttons);
		anchorButtons.setTopAnchor(buttons, 0.0);
		anchorButtons.setRightAnchor(buttons, 0.0);
		anchorButtons.setLeftAnchor(buttons, 0.0);
		anchorButtons.setBottomAnchor(buttons, 0.0);

		splitPane1.getItems().addAll(anchorFilesTable, anchorRecordsTable, anchorFilter);

		ap.getChildren().add(splitPane1);
		ap.setTopAnchor(splitPane1, 0.0);
		ap.setRightAnchor(splitPane1, 0.0);
		ap.setLeftAnchor(splitPane1, 0.0);
		ap.setBottomAnchor(splitPane1, 0.0);
		splitPane1.setDividerPosition(0, 0.4);
		splitPane1.setDividerPosition(1, 0.8);

		root.setBottom(anchorButtons);

		return root;
	}

	public void initializeMenu(BorderPane root) {

		VBox topContainer = new VBox();
		MenuBar mainMenu = new MenuBar();

		Menu menu = new Menu("Menu");
		MenuItem createDB = new MenuItem("Stw�rz now� baz� danych");
		MenuItem downloadDB = new MenuItem("�ci�gnij baz� danych");
		MenuItem exit = new MenuItem("Zamknij");
		exit.setAccelerator(KeyCodeCombination.keyCombination("ALT+F4"));
		exit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.exit(0);
			}
		});
		menu.getItems().addAll(exit, createDB, downloadDB);
		mainMenu.getMenus().addAll(menu);

		topContainer.getChildren().add(mainMenu);
		root.setTop(topContainer);
	}

}
