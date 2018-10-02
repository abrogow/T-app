package Controller;

import javafx.stage.Window;
import model.Record;
import model.dataBase.DataBaseModel;
import view.additionalWindows.RecordGridPane;
import view.mainWindow.RecordsTable;

public class AdditionalWindowController {

	private RecordsTable recordsTable;
	private RecordGridPane recordGridPane;
	private Record record;
	private DataBaseModel dataBaseModel;

	public AdditionalWindowController(RecordsTable recordsTable, RecordGridPane recordGridPane) {

		this.recordsTable = recordsTable;
		this.recordGridPane = recordGridPane;

		initializeHandlers();
	}

	// metoda inicjalizuje handlery przyciskow
	private void initializeHandlers() {

		initializeSaveButton();
		initializeCancelButton();
		initializeWebButton();
		initializeLoadButton();
	}

	private void initializeSaveButton() {

		recordGridPane.getSaveButton().setOnAction((event) -> {
			// Record selected =
			// recordsTable.getRecordsTable().getSelectionModel().getSelectedItem();
			// przy edycji
			// if (selected != null) {

			// } else {
			String identifier = recordGridPane.getIdTextField().getText();
			String name = recordGridPane.getNameTextField().getText();
			String info = recordGridPane.getInfoTextField().getText();
			String sequence = recordGridPane.getSequenceTextField().getText();

			record = new Record(identifier, name, info, sequence);
			if (record != null) {
				DataBaseModel.getInstance().addRecord(record);
				// recordsTable update table
				// update table !?
				// recordsTable.
				recordsTable.updateTableView();
			}
			hideRecordAdditionalWidnow();

			// }

		});
	}

	private void initializeCancelButton() {

		recordGridPane.getCancelButton().setOnAction((event) -> hideRecordAdditionalWidnow());
	}

	private void initializeWebButton() {

	}

	private void initializeLoadButton() {

	}

	private void hideRecordAdditionalWidnow() {

		Window stage = recordGridPane.getScene().getWindow();
		stage.hide();
	}

	private void clearFieldsAdditionalWindow() {

		recordGridPane.getIdTextField().setText("");
		recordGridPane.getNameTextField().setText("");
		recordGridPane.getInfoTextField().setText("");
		recordGridPane.getSequenceTextField().setText("");
	}

	// przeladowuje pola w RecordAdditionalWindow (mozna bedzie uzyc do kolorow)
	private void reloadFiledsRecordAdditionalWindow() {

		recordGridPane.getIdTextField().setStyle(null);
		recordGridPane.getNameTextField().setStyle(null);
		recordGridPane.getInfoTextField().setStyle(null);
		recordGridPane.getSequenceTextField().setStyle(null);
	}

}
