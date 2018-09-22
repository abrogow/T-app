package Controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.additionalWindows.DownloadDBGridPane;
import view.additionalWindows.RecordGridPane;
import view.mainWindow.ButtonsGridPane;
import view.mainWindow.FilesTable;
import view.mainWindow.FilterGridPane;
import view.mainWindow.RecordsTable;

public class Main extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Thesis");

		ButtonsGridPane buttons = new ButtonsGridPane();
		FilterGridPane filter = new FilterGridPane();
		FilesTable filesTable = new FilesTable();
		RecordsTable recordsTable = new RecordsTable();
		DownloadDBGridPane download = new DownloadDBGridPane();

		RecordGridPane recordGridPane = new RecordGridPane();

		// TODO
		// dodac controller !!

		BorderPane root = initializeGUI(buttons, filesTable, recordsTable, filter);
		primaryStage.setScene(new Scene(root, 1000, 600));
		primaryStage.show();

		// Stage filterStage = new Stage();
		// Pane pane = new Pane();
		// pane.getChildren().add(download);
		// Scene scene = new Scene(pane);
		// filterStage.setScene(scene);
		// filterStage.show();
	}

	public BorderPane initializeGUI(ButtonsGridPane buttons, FilesTable filesTable, RecordsTable recordsTable,
			FilterGridPane filter) {

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
		GridPane gridPane = new GridPane();

		Label idLabel = new Label("Id: ");
		Label nameLabel = new Label("Nazwa: ");
		Label speciesLabel = new Label("Gatunek: ");

		ChoiceBox idChoiceBox = new ChoiceBox();
		ChoiceBox nameChoiceBox = new ChoiceBox();
		ChoiceBox speciesChoiceBox = new ChoiceBox();

		Button searchButton = new Button("Wyszukaj");
		Button saveButton = new Button("Zapisz rekordy do pliku");

		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(50);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(5, 5, 5, 5));

		Text sceneTitle = new Text("Filtry wyszukiwania");
		gridPane.add(sceneTitle, 0, 0, 2, 1);

		gridPane.add(idLabel, 0, 1);
		gridPane.add(nameLabel, 0, 2);
		gridPane.add(speciesLabel, 0, 3);

		gridPane.add(idChoiceBox, 1, 1);
		gridPane.add(nameChoiceBox, 1, 2);
		gridPane.add(speciesChoiceBox, 1, 3);

		gridPane.add(searchButton, 0, 5, 3, 1);
		gridPane.add(saveButton, 0, 6, 2, 1);

		anchorFilter.getChildren().add(gridPane);
		// anchorFilter.setTopAnchor(filter, 0.0);
		// anchorFilter.setRightAnchor(filter, 0.0);
		// anchorFilter.setLeftAnchor(filter, 0.0);
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
		MenuItem createDB = new MenuItem("Stwórz now¹ bazê danych");
		MenuItem downloadDB = new MenuItem("Œci¹gnij bazê danych");
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
