package Controller;

import java.util.LinkedHashMap;

import javafx.scene.control.Alert;
import model.File;
import model.dataBase.DataBaseModel;
import model.reader.Reader;
import model.reader.UniprotReader;
import view.additionalWindows.AddEditFileWindow;
import view.mainWindow.ButtonsGridPane;
import view.mainWindow.FilesTable;
import view.mainWindow.FilterGridPane;
import view.mainWindow.MainWindow;
import view.mainWindow.RecordsTable;

public class MainWindowController {

	private MainWindow mainWindow;
	private ButtonsGridPane buttonsGridPane;
	private RecordsTable recordsTable;
	private FilesTable filesTable;
	private AddEditFileWindow addEditFileWindow;
	private DataBaseModel dataBaseModel;
	private FilterGridPane filterGridPane;

	private File file;

	// do filtra
	private LinkedHashMap<Long, String> idHashMap;
	private LinkedHashMap<Long, String> nameHashMap;
	private LinkedHashMap<Long, String> organismNameHashMap;

	// do readera
	private Reader reader;
	private static final String UNIPROT_READER = "UniProt";

	public MainWindowController(ButtonsGridPane buttonsGridPane, FilesTable filesTable,
			AddEditFileWindow addEditFileWindow, FilterGridPane filterGridPane) {

		this.buttonsGridPane = buttonsGridPane;
		this.filesTable = filesTable;
		this.addEditFileWindow = addEditFileWindow;
		this.filterGridPane = filterGridPane;

		initializeHandlers();

	}

	// metoda inicjalizuje handlery przyciskow
	private void initializeHandlers() {

		initializeAddButton();
		initializeRemoveButton();
		initializeEditButton();
		initializeSearchButton();
		initializeSaveButton();
	}

	private void initializeAddButton() {

		buttonsGridPane.getAddButton().setOnAction((event) -> {
			showFileWindow(null);
		});
	}

	private void initializeEditButton() {

		buttonsGridPane.getEditButton().setOnAction((event) -> {
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

		buttonsGridPane.getRemoveButton().setOnAction((event) -> {
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

	private void initializeSearchButton() {

		filterGridPane.getSearchButton().setOnAction((event) -> {

			file = filesTable.getFilesTable().getSelectionModel().getSelectedItem();

			if (file != null) {

				getSpecyfiedReader();

				reader.setHashMaps();
				// getSpecyficationsFromFilter();
				// saveNewRecordsListIntoFile();

			} else {
				// to samo dla wszytskich plikow po kolei
			}

			filterGridPane.getSaveButton().setDisable(false);

		});
		;
	}

	private void initializeSaveButton() {

		filterGridPane.getSaveButton().setOnAction((event) -> {

			filterGridPane.showStageWithFileName();
			if (filterGridPane.getNewFileTextField().getText().isEmpty())
				showAlertInfoForNewName();
			else {
				// zapisywanie rekordow do nowego pliku
			}

		});
	}

	private void setHashMaps(LinkedHashMap<Long, String> idHashMap, LinkedHashMap<Long, String> nameHashMap,
			LinkedHashMap<Long, String> organismNameHashMap) {

		this.idHashMap = idHashMap;
		this.nameHashMap = nameHashMap;
		this.organismNameHashMap = organismNameHashMap;
	}

	private void getSpecyficationsFromFilter() {

	}

	private void showFileWindow(File file) {
		addEditFileWindow.createAndShowStage(filesTable, file);
	}

	// dla rekordow
	// private void showRecordWindow(Record record) {
	//
	// AddEditFileWindow recordPane = new AddEditFileWindow();
	// recordPane.createAndShowStage(recordsTable, record);
	// }

	private void disableMainWindow() {

	}

	// laduje dane rekordu do formularzy
	private void showRecordData(File file) {

		if (file != null) {
			reloadFiledsAddEditFileWindow();
			addEditFileWindow.getIdDBComboBox().setValue(file.getId_DB().toString());
			addEditFileWindow.getNameTextField().setText(file.getName());
			addEditFileWindow.getDescriptionTextField().setText(file.getDescription());
			addEditFileWindow.getSequence_idTextField().setText(file.getSequence_id());
			addEditFileWindow.getVersion_DBTextField().setText(file.getVersion_DB());
			addEditFileWindow.getSequence_nameTextField().setText(file.getSequence_name());
			addEditFileWindow.getRand_sequenceTextField().setText(file.getRand_sequence().toString());
			addEditFileWindow.getPrefixTextField().setText(file.getPrefix());
			addEditFileWindow.getRand_typeTextField().setText(file.getRand_type().toString());
			addEditFileWindow.getPositions_PathTextField().setText(file.getPositions_path());

		}
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

	public void showAlertInfoForNewName() {

		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("B³¹d!");
		alert.setHeaderText(null);
		alert.setContentText("Nie podano nowej nazwy pliku!");
		alert.showAndWait();
	}
	// TODO: dopisac inne readery jezeli zostana zrobione

	public void getSpecyfiedReader() {

		String id_DB = file.getId_DB();

		if (UNIPROT_READER.equals(id_DB))
			reader = new UniprotReader(addEditFileWindow);

	}

}
