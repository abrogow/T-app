package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.File;
import model.dataBase.DataBaseModel;
import model.reader.UniprotReader;
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
	private UniprotReader uniprotReader;
	private String path;
	private LinkedHashMap<Long, String> idHashMap;
	private LinkedHashMap<Long, String> nameHashMap;
	private LinkedHashMap<Long, String> organismNameHashMap;

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
			// Record selected =
			// recordsTable.getRecordsTable().getSelectionModel().getSelectedItem();
			// przy edycji
			// if (selected != null) {

			// } else {
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

			File file = new File(name, description, id_DB, version_DB, sequence_id, sequence_name, rand_sequence,
					prefix, rand_type, positions_path);

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

				// recordsTable update table
				// update table !?
				// recordsTable.
				filesTable.updateTableView();
			}
			hideRecordAdditionalWidnow();

			// }

		});
	}

	private void initializeCancelButton() {

		addEditFileWindow.getCancelButton().setOnAction((event) -> hideRecordAdditionalWidnow());
	}

	private void initializeWebButton() {

	}

	private void initializeLoadButton() {

		addEditFileWindow.getLoadButton().setOnAction((event) -> {

			Stage stage = new Stage();

			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Wybierz plik FASTA:");
			java.io.File selectedDirectory = fileChooser.showOpenDialog(stage);

			// create progressBar

			path = selectedDirectory.getAbsolutePath();
			uniprotReader = new UniprotReader(addEditFileWindow);

			try {
				if (this.ifReaderSet()) {
					if (path != null) {
						addEditFileWindow.createProgressBar();
						ArrayList<Long> positionsList = uniprotReader.readPositions(path);
						uniprotReader.savePositionsToFile(positionsList);

						uniprotReader.savePositionsAndIdToMap(positionsList);

					}
					;
				} else {
					showAlertInfo();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// parsowanie
			Scanner input = new Scanner(System.in);
			System.out.println("Nr rekordu :");
			int recordNr = Integer.parseInt(input.nextLine());
			uniprotReader.parseRecord(recordNr);

			// addEditFileWindow.getProgressBar().setVisible(false);
			// addEditFileWindow.getIleProcent().setVisible(false);
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

	private boolean ifReaderSet() {
		if (("").equals(addEditFileWindow.getIdDBComboBox().getValue()))
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
		addEditFileWindow.getIdDBComboBox().setStyle("red");
	}

}
