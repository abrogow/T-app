package Controller;

import javafx.beans.property.SimpleLongProperty;
import model.Record;
import model.dataBase.DataBaseModel;
import view.additionalWindows.RecordGridPane;
import view.mainWindow.ButtonsGridPane;
import view.mainWindow.MainWindow;
import view.mainWindow.RecordsTable;

public class MainWindowController {

	private MainWindow mainWindow;
	private ButtonsGridPane buttonsGridPane;
	private RecordsTable recordsTable;
	private RecordGridPane recordGridPane;
	private DataBaseModel dataBaseModel;

	public MainWindowController(ButtonsGridPane buttonsGridPane, RecordsTable recordsTable,
			RecordGridPane recordsGridPane) {

		this.buttonsGridPane = buttonsGridPane;
		this.recordsTable = recordsTable;
		this.recordGridPane = recordsGridPane;

		initializeHandlers();

	}

	// metoda inicjalizuje handlery przyciskow
	private void initializeHandlers() {

		initializeAddButton();
		initializeRemoveButton();
		initializeEditButton();
	}

	private void initializeAddButton() {

		buttonsGridPane.getAddButton().setOnAction((event) -> {
			showRecordWindow();
		});
	}

	private void initializeEditButton() {

		buttonsGridPane.getEditButton().setOnAction((event) -> showRecordEditWindow());
	}

	private void initializeRemoveButton() {

		buttonsGridPane.getRemoveButton().setOnAction((event) -> {
			Record record = recordsTable.getRecordsTable().getSelectionModel().getSelectedItem();
			SimpleLongProperty idx = record.getRecordId();
			String sidx = idx.toString();
			// long idx = Long.valueOf(record.getRecordId().toString());
			dataBaseModel.getInstance().removeRecord(idx.getValue());
			recordsTable.updateTableView();

		});
	}

	private void showRecordWindow() {

		RecordGridPane recordPane = new RecordGridPane();
		recordPane.createAndShowStage();
	}

	private void showRecordEditWindow() {

		recordsTable.getRecordsTable().getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showRecordData(newValue));
		showRecordWindow();

	}

	private void disableMainWindow() {

	}

	// laduje dane rekordu do formularzy
	private void showRecordData(Record record) {

		if (record != null) {
			reloadFiledsRecordAdditionalWindow();
			recordGridPane.getIdTextField().setText(record.getRecordIdentifier());
			recordGridPane.getNameTextField().setText(record.getRecordName());
			recordGridPane.getInfoTextField().setText(record.getRecordInfo());
			recordGridPane.getSequenceTextField().setText(record.getRecordSequence());
		}
	}

	// przeladowuje pola w RecordAdditionalWindow
	private void reloadFiledsRecordAdditionalWindow() {

		recordGridPane.getIdTextField().setStyle(null);
		recordGridPane.getNameTextField().setStyle(null);
		recordGridPane.getInfoTextField().setStyle(null);
		recordGridPane.getSequenceTextField().setStyle(null);
	}

}
