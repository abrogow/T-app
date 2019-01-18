package controller.additionalWindows;

import java.io.IOException;
import java.util.LinkedHashMap;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.File;
import model.dataBase.DataBaseModel;
import model.reader.FastaIndexBuilder;
import model.reader.FastaReader;
import model.reader.FastaRecordParser;
import view.additionalWindows.AddEditFileWindow;
import view.mainWindow.FilesTable;

public class AdditionalWindowController {

	private FilesTable filesTable;
	private AddEditFileWindow addEditFileWindow;
	// private Record record;
	private DataBaseModel dataBaseModel;
	private Stage stage;
	private Stage progressWindow;
	private Thread thread;
	private String path;
	private LinkedHashMap<Long, String> idHashMap;
	private LinkedHashMap<Long, String> nameHashMap;
	private LinkedHashMap<Long, String> organismNameHashMap;
	private String fileName;
	private Stage progressStage;
	private Thread th;
	private FastaRecordParser parser;
	private String dbType;

	public AdditionalWindowController(FilesTable filesTable, AddEditFileWindow addEditFileWindow, File file) {

		this.filesTable = filesTable;
		this.addEditFileWindow = addEditFileWindow;

		initializeHandlers(file);
	}

	// metoda inicjalizuje handlery przyciskow
	private void initializeHandlers(File file) {

		initializeSaveButton(file);
		initializeCancelButton();
		initializeWebButton();
		initializeLoadButton();
	}

	private void initializeSaveButton(File f) {

		addEditFileWindow.getSaveButton().setOnAction((event) -> {

			if (isReaderSet()) {
				saveFile(f);
			} else {
				showAlertInfo();
				return;
			}
			hideRecordAdditionalWidnow();
		});
	}

	private void initializeCancelButton() {

		addEditFileWindow.getCancelButton().setOnAction((event) -> hideRecordAdditionalWidnow());
	}

	private void initializeWebButton() {

	}

	private void initializeLoadButton() {

		addEditFileWindow.getLoadButton().setOnAction((event) -> {

			if (isReaderSet()) {
				getPathFromFileDialog();
				// setProgressIndicator();
				readAndSavePositions();
				addEditFileWindow.getPositions_PathTextField().setText(path);
				addEditFileWindow.getNameTextField().setText(fileName);

			} else {
				showAlertInfo();
				return;
			}
		});

	}

	private void hideRecordAdditionalWidnow() {

		Window stage = addEditFileWindow.getScene().getWindow();
		stage.hide();

	}

	private void clearFieldsAdditionalWindow() {

		addEditFileWindow.getIdDBComboBox().setValue("");
		addEditFileWindow.getNameTextField().setText("");
		addEditFileWindow.getDescriptionTextField().setText("");
		addEditFileWindow.getSequence_idTextField().setText("");
		addEditFileWindow.getVersion_DBTextField().setText("");
		addEditFileWindow.getSequence_nameTextField().setText("");
		addEditFileWindow.getRand_sequenceTextField().setText("");
		addEditFileWindow.getPrefixTextField().setText("");
		addEditFileWindow.getRand_typeTextField().setText("");
		addEditFileWindow.getPositions_PathTextField().setText("");
	}

	// przeladowuje pola w RecordAdditionalWindow (mozna bedzie uzyc do kolorow)
	private void reloadFiledsRecordAdditionalWindow() {

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

	private boolean isReaderSet() {

		dbType = addEditFileWindow.getIdDBComboBox().getValue().toString();
		if (addEditFileWindow.getIdDBComboBox().getSelectionModel().isEmpty())
			return false;

		else
			return true;

	}

	private void showAlertInfo() {

		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("B³¹d!");
		alert.setHeaderText(null);
		alert.setContentText("Nie zaznaczono identyfikatora bazy danych!");
		alert.showAndWait();
	}

	// zapisuje nowy lub edytowany plik do bazy
	private void saveFile(File f) {

		String id_DB = addEditFileWindow.getIdDBComboBox().getValue().toString();
		String name = addEditFileWindow.getNameTextField().getText();
		String description = addEditFileWindow.getDescriptionTextField().getText();
		String sequence_id = addEditFileWindow.getSequence_idTextField().getText();
		String version_DB = addEditFileWindow.getVersion_DBTextField().getText();
		String sequence_name = addEditFileWindow.getSequence_nameTextField().getText();
		Long rand_sequence = Long.parseLong(addEditFileWindow.getRand_sequenceTextField().getText());
		String prefix = addEditFileWindow.getPrefixTextField().getText();
		Long rand_type = Long.parseLong(addEditFileWindow.getRand_typeTextField().getText());
		String positions_path = addEditFileWindow.getPositions_PathTextField().getText();

		File file = new File(name, description, id_DB, version_DB, sequence_id, sequence_name, rand_sequence, prefix,
				rand_type, positions_path);

		if (file != null) {
			if (f != null) {
				Long id = f.getFileId();
				file = new File(id, name, description, id_DB, version_DB, sequence_id, sequence_name, rand_sequence,
						prefix, rand_type, positions_path);
				DataBaseModel.getInstance().editFile(file);
				System.out.println("EDIT FILE");
			} else {

				DataBaseModel.getInstance().addFile(file);
				System.out.println("ADD FILE");

			}
			filesTable.updateTableView();
		}
	}

	private void getPathFromFileDialog() {

		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Wybierz plik FASTA:");
		java.io.File selectedDirectory = fileChooser.showOpenDialog(stage);
		path = selectedDirectory.getAbsolutePath();
		fileName = selectedDirectory.getName();
	}

	private void readAndSavePositions() {

		parser = FastaRecordParser.getInstance(dbType);
		FastaIndexBuilder indexBuilder = new FastaIndexBuilder(path, parser);
		try {
			indexBuilder.buildIndex();
			FastaReader reader = new FastaReader(path, parser);
			reader.open();
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	Task<Void> task = new Task<Void>() {
		@Override
		public Void call() {
			readAndSavePositions();
			addEditFileWindow.getPositions_PathTextField().setText(path);
			addEditFileWindow.getNameTextField().setText(fileName);
			return null;
		}
	};

	private void setProgressIndicator() {

		task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent t) {
				progressStage.close();
			}
		});

		th = new Thread(task);
		th.setDaemon(true);
		th.start();

		ProgressIndicator progressIndicator = new ProgressIndicator();

		StackPane root = new StackPane();
		root.getChildren().addAll(progressIndicator);

		Scene scene = new Scene(root, 400, 300);
		progressStage = new Stage();
		progressStage.setTitle("£adowanie...");

		progressStage.setScene(scene);
		progressStage.show();
	}
}
