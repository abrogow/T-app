package Controller;

import javafx.beans.property.SimpleLongProperty;
import javafx.scene.control.Alert;
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
			showRecordWindow(null);
		});
	}

	private void initializeEditButton() {

		buttonsGridPane.getEditButton().setOnAction((event) -> {
			Record record = recordsTable.getRecordsTable().getSelectionModel().getSelectedItem();
			if (record != null) {

				SimpleLongProperty idx = record.getRecordId();
				Record rec = DataBaseModel.getInstance().getRecord(idx.getValue());
				showRecordWindow(rec);

			} else {
				showAlertInfo();
			}
		});
	}

	private void initializeRemoveButton() {

		buttonsGridPane.getRemoveButton().setOnAction((event) -> {
			Record record = recordsTable.getRecordsTable().getSelectionModel().getSelectedItem();
			if (record != null) {

				SimpleLongProperty idx = record.getRecordId();
				DataBaseModel.getInstance().removeRecord(idx.getValue());
				recordsTable.updateTableView();

			} else {
				showAlertInfo();
			}
		});
	}

	private void showRecordWindow(Record record) {

		RecordGridPane recordPane = new RecordGridPane();
		recordPane.createAndShowStage(recordsTable, record);
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

	public void showAlertInfo() {

		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("B³¹d!");
		alert.setHeaderText(null);
		alert.setContentText("Nie zaznaczono rekordu!");
		alert.showAndWait();
	}

}
