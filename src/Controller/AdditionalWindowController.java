package Controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.concurrent.Task;
import javafx.scene.control.ProgressIndicator;
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
			Long id_DB = Long.parseLong(addEditFileWindow.getIdDBTextField().getText());
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

			String path = selectedDirectory.getAbsolutePath();
			UniprotReader uniprotReader = new UniprotReader();
			ProgressIndicator pind = addEditFileWindow.createProgressIndicator();

			try {
				if (path != null) {
					// Create new Task and Thread - Bind Progress Property to Task Progress
					Task task = taskCreator(Integer.parseInt("30"));
					pind.progressProperty().unbind();
					pind.progressProperty().bind(task.progressProperty());
					new Thread(task).start();

					ArrayList<Long> positionsList = uniprotReader.readPositions(path);
					uniprotReader.savePositionsToFile(positionsList);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}

	private void hideRecordAdditionalWidnow() {

		Window stage = addEditFileWindow.getScene().getWindow();
		stage.hide();
	}

	private void clearFieldsAdditionalWindow() {

		addEditFileWindow.getIdDBTextField().setText("");
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

		addEditFileWindow.getIdDBTextField().setStyle(null);
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

	// Create a New Task
	private Task taskCreator(int seconds) {
		return new Task() {

			@Override
			protected Object call() throws Exception {
				for (int i = 0; i < seconds; i++) {
					Thread.sleep(1000);
					updateProgress(i + 1, seconds);

				}
				return true;
			}
		};
	}

}
