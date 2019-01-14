package Controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCodeCombination;
import javafx.stage.Stage;
import model.File;
import model.dataBase.DataBaseModel;
import view.additionalWindows.AddEditFileWindow;
import view.additionalWindows.DownloadDBWindow;
import view.mainWindow.ButtonsPane;
import view.mainWindow.CreateNewDBPane;
import view.mainWindow.FilesTable;
import view.mainWindow.FilterPane;
import view.mainWindow.MainWindow;
import view.mainWindow.RecordPane;
import view.mainWindow.RecordsTable;

public class MainWindowController {

	private MainWindow mainWindow;
	private DownloadDBWindow downloadDB;
	private ButtonsPane buttonsPane;
	private RecordsTable recordsTable;
	private FilesTable filesTable;
	private AddEditFileWindow addEditFileWindow;
	private DataBaseModel dataBaseModel;
	private FilterPane filterPane;
	private CreateNewDBPane createNewDB;
	private Button okButton, cancelButton;
	private Stage stage;
	private String fileName;
	RecordPane recordPane;

	private File file;

	// do filtra

	// do readera
	private static final String UNIPROT_READER = "UniProt";

	public MainWindowController(MainWindow mainWindow, ButtonsPane buttonsPane, FilesTable filesTable,
			AddEditFileWindow addEditFileWindow, FilterPane filterPane, CreateNewDBPane createNewDB,
			RecordsTable recordsTable, RecordPane recordPane, DownloadDBWindow downloadDB) {

		this.buttonsPane = buttonsPane;
		this.filesTable = filesTable;
		this.addEditFileWindow = addEditFileWindow;
		this.filterPane = filterPane;
		this.createNewDB = createNewDB;
		this.recordsTable = recordsTable;
		this.recordPane = recordPane;
		this.mainWindow = mainWindow;
		this.downloadDB = downloadDB;

		initializeHandlers();

	}

	// metoda inicjalizuje handlery przyciskow
	private void initializeHandlers() {

		initializeAddButton();
		initializeRemoveButton();
		initializeEditButton();
		initializeExitMenuItem();
		initializeDonloadDBMenuItem();

		// tabela
		FilesTableController filesTableController = new FilesTableController(filesTable, recordsTable);

		RecordsTableController recordsTableController = new RecordsTableController(recordsTable, filesTable,
				recordPane);

		RecordPaneController recordPaneController = new RecordPaneController(recordsTable, recordPane,
				recordsTableController);

		// przyciski do filtrowania
		FilterWindowController filterController = new FilterWindowController(filterPane, filesTable, recordsTable);

		// przyciski do tworzenia nowej bazy
		CreateDBWindowController createNewDBController = new CreateDBWindowController(createNewDB, recordsTable);
	}

	private void initializeAddButton() {

		buttonsPane.getAddButton().setOnAction((event) -> {
			showFileWindow(null);
		});
	}

	private void initializeEditButton() {

		buttonsPane.getEditButton().setOnAction((event) -> {
			file = filesTable.getFilesTable().getSelectionModel().getSelectedItem();
			if (file != null) {

				Long idx = file.getFileId();
				File f = DataBaseModel.getInstance().getFile(idx.longValue());
				showFileWindow(f);

			} else {
				showAlertInfo();
			}
		});
	}

	private void initializeRemoveButton() {

		buttonsPane.getRemoveButton().setOnAction((event) -> {
			file = filesTable.getFilesTable().getSelectionModel().getSelectedItem();
			if (file != null) {

				Long idx = file.getFileId();
				DataBaseModel.getInstance().removeFile((int) (long) idx);
				filesTable.updateTableView();

			} else {
				showAlertInfo();
			}
		});
	}

	private void initializeExitMenuItem() {
		mainWindow.getExitMenuItem().setAccelerator(KeyCodeCombination.keyCombination("ALT+F4"));
		mainWindow.getExitMenuItem().setOnAction((event) -> {

			System.exit(0);
		});
	}

	private void initializeDonloadDBMenuItem() {

		mainWindow.getDonloadDBMenuItem().setOnAction((event) -> {

			showDonloadDBWindow();
		});

	}

	private void showFileWindow(File file) {
		addEditFileWindow.createAndShowStage(filesTable, file);
	}

	private void showDonloadDBWindow() {
		downloadDB.createAndShowStage();
	}

	// przeladowuje pola w AddEditFileWindow
	private void reloadFiledsAddEditFileWindow() {

		addEditFileWindow.getIdDBComboBox().setStyle(null);
		addEditFileWindow.getNameTextField().setStyle(null);
		addEditFileWindow.getDescriptionTextField().setStyle(null);
		addEditFileWindow.getSequence_idTextField().setStyle(null);
		addEditFileWindow.getVersion_DBTextField().setStyle(null);
		addEditFileWindow.getSequence_nameTextField().setStyle(null);
		addEditFileWindow.getRand_sequenceTextField().setStyle(null);
		addEditFileWindow.getPrefixTextField().setStyle(null);
		addEditFileWindow.getRand_typeTextField().setStyle(null);
		addEditFileWindow.getPositions_PathTextField().setStyle(null);

	}

	public void showAlertInfo() {

		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("B³¹d!");
		alert.setHeaderText(null);
		alert.setContentText("Nie zaznaczono rekordu!");
		alert.showAndWait();
	}

}
