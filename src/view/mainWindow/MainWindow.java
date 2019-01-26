package view.mainWindow;

import java.sql.DriverManager;
import java.sql.SQLException;

import controller.mainWindow.MainWindowController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.dataBase.DataBaseModel;
import view.additionalWindows.AddEditFileWindow;
import view.additionalWindows.DownloadDBWindow;

public class MainWindow {

	private MenuItem downloadDB;
	private MenuItem exit;

	public void initializeGUI(Stage primaryStage) {

		primaryStage.setTitle("FastaViewer");
		ButtonsPane buttons = new ButtonsPane();
		FilterPane filter = new FilterPane();
		FilesTable filesTable = new FilesTable();
		RecordsTable recordsTable = new RecordsTable();
		CreateNewDBPane createNewDB = new CreateNewDBPane();
		DownloadDBWindow donloadDB = new DownloadDBWindow();
		RecordPane recordPane = new RecordPane();

		AddEditFileWindow addEditFileWindow = new AddEditFileWindow();

		DataBaseModel db = new DataBaseModel();

		BorderPane root = initializeRoot(buttons, filesTable, recordsTable, filter, createNewDB, recordPane);
		MainWindowController controller = new MainWindowController(this, buttons, filesTable, addEditFileWindow, filter,
				createNewDB, recordsTable, recordPane, donloadDB);
		primaryStage.setScene(new Scene(root, 1600, 1000));
		primaryStage.setOnCloseRequest(event -> {

			System.out.println("Closing Stage");
			try {
				DriverManager.getConnection("jdbc:derby:Fasta_DB;shutdown=true");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			Platform.exit();
		});
		primaryStage.show();
	}

	public BorderPane initializeRoot(ButtonsPane buttons, FilesTable filesTable, RecordsTable recordsTable,
			FilterPane filter, CreateNewDBPane createNewDB, RecordPane recordPane) {

		BorderPane root = new BorderPane();
		initializeMenu(root);

		AnchorPane ap = new AnchorPane();
		root.setCenter(ap);
		SplitPane splitPane1 = new SplitPane();
		splitPane1.setOrientation(Orientation.HORIZONTAL);

		// for filestable
		AnchorPane anchorFilesTable = new AnchorPane();
		anchorFilesTable.getChildren().addAll(filesTable);
		anchorFilesTable.setTopAnchor(filesTable, 0.0);
		anchorFilesTable.setRightAnchor(filesTable, 0.0);
		anchorFilesTable.setLeftAnchor(filesTable, 0.0);
		anchorFilesTable.setBottomAnchor(filesTable, 0.0);
		anchorFilesTable.setPrefWidth(500);

		// for Recordstable
		AnchorPane anchorRecordsTable = new AnchorPane();
		anchorRecordsTable.getChildren().add(recordsTable);
		anchorRecordsTable.setTopAnchor(recordsTable, 0.0);
		anchorRecordsTable.setRightAnchor(recordsTable, 0.0);
		anchorRecordsTable.setLeftAnchor(recordsTable, 0.0);
		anchorRecordsTable.setBottomAnchor(recordsTable, 0.0);
		anchorRecordsTable.setPrefSize(500, 700);

		// for recordstable and recordPane
		VBox vBox2 = new VBox();
		vBox2.getChildren().add(anchorRecordsTable);
		vBox2.getChildren().add(recordPane);
		vBox2.setPadding(new Insets(0, 0, 0, 0));
		vBox2.setPrefWidth(500);

		// for newDBCreator and filter
		VBox vBox = new VBox();
		vBox.getChildren().add(filter);
		filter.setPrefWidth(600);
		vBox.getChildren().add(createNewDB);
		createNewDB.setPrefWidth(600);
		vBox.setPadding(new Insets(20, 5, 30, 5));
		vBox.setPrefWidth(600);

		// for buttons
		AnchorPane anchorButtons = new AnchorPane();
		anchorButtons.getChildren().add(buttons);
		anchorButtons.setTopAnchor(buttons, 0.0);
		anchorButtons.setRightAnchor(buttons, 0.0);
		anchorButtons.setLeftAnchor(buttons, 0.0);
		anchorButtons.setBottomAnchor(buttons, 0.0);

		splitPane1.getItems().addAll(anchorFilesTable, vBox2, vBox);

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
		downloadDB = new MenuItem("Pobierz bazê danych");
		exit = new MenuItem("Zamknij");

		menu.getItems().addAll(exit, downloadDB);
		mainMenu.getMenus().addAll(menu);

		topContainer.getChildren().add(mainMenu);
		root.setTop(topContainer);
	}

	public MenuItem getExitMenuItem() {
		return exit;
	}

	public MenuItem getDonloadDBMenuItem() {
		return downloadDB;
	}

}
