package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.FastaRecord;
import model.File;
import model.dataBase.DataBaseModel;
import model.reader.FastaIndexBuilder;
import model.reader.FastaReader;
import model.reader.FastaUniprotRecordParser;
import model.writer.UniprotWriter;
import model.writer.Writer;
import view.additionalWindows.AddEditFileWindow;
import view.mainWindow.ButtonsGridPane;
import view.mainWindow.FilesTable;
import view.mainWindow.FilterPane;
import view.mainWindow.MainWindow;
import view.mainWindow.RecordsTable;

public class MainWindowController {

	private MainWindow mainWindow;
	private ButtonsGridPane buttonsGridPane;
	private RecordsTable recordsTable;
	private FilesTable filesTable;
	private AddEditFileWindow addEditFileWindow;
	private DataBaseModel dataBaseModel;
	private FilterPane filterPane;
	private Button okButton, cancelButton;
	private Stage stage;
	private String fileName;

	private File file;

	// do filtra
	private Map<String, Long> idHashMap;
	private Map<String, Long> nameHashMap;
	private Map<String, ArrayList<Long>> organismNameHashMap;
	private Map<String, String> params;
	List<FastaRecord> resultList;
	FastaReader reader;
	String srcPath;

	// do readera
	private static final String UNIPROT_READER = "UniProt";

	public MainWindowController(ButtonsGridPane buttonsGridPane, FilesTable filesTable,
			AddEditFileWindow addEditFileWindow, FilterPane filterPane) {

		this.buttonsGridPane = buttonsGridPane;
		this.filesTable = filesTable;
		this.addEditFileWindow = addEditFileWindow;
		this.filterPane = filterPane;

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

		filterPane.getSearchButton().setOnAction((event) -> {

			file = filesTable.getFilesTable().getSelectionModel().getSelectedItem();

			if (file != null) {

				// get params
				getParamsFromFields();
				srcPath = file.getDstPath();

				// load hashmaps
				FastaUniprotRecordParser parser = new FastaUniprotRecordParser();
				FastaIndexBuilder indexBuilder = new FastaIndexBuilder(srcPath, parser);
				reader = new FastaReader(srcPath, parser);
				reader.setFileSize();

				String idHMPath = indexBuilder.getResultFilesPath(srcPath, "idHashMap");
				String nameHMPath = indexBuilder.getResultFilesPath(srcPath, "nameHashMap");
				String organismHMPath = indexBuilder.getResultFilesPath(srcPath, "organismNameHashMap");

				try {
					try {
						idHashMap = reader.readIdIndex(idHMPath);
						idHashMap.keySet();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					organismNameHashMap = reader.readOrganismIndex(organismHMPath);
					organismNameHashMap.keySet();
					nameHashMap = reader.readIdIndex(nameHMPath);
					nameHashMap.keySet();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// set hash maps in reader
				reader.setMaps(organismNameHashMap, idHashMap, nameHashMap);
				// filtering
				try {
					filterRecords();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				showAlertInfoForNoFile();
			}

			filterPane.getSaveButton().setDisable(false);

		});
		;
	}

	private void initializeSaveButton() {

		filterPane.getSaveButton().setOnAction((event) -> {

			Stage stage = filterPane.showStageWithFileName();
			stage.show();
			// dalsze zapisywanie w przycisku OK
			initializeOkButton();
			initializeCancelButton();
			reloadFieldsFilter();

		});
	}

	private void initializeOkButton() {

		filterPane.getOkButton().setOnAction((event) -> {

			if (!("").equals(filterPane.getNewFileTextField().getText())) {
				fileName = filterPane.getNewFileTextField().getText();
				stage = filterPane.getStage();
				stage.close();

				// TODO:trzeba zrobic rozpoznawanie writera
				Writer writer = new UniprotWriter();
				try {

					writer.saveRecordsToFile(resultList, fileName, srcPath);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				showAlertInfoForNewName();
				return;
			}
		});
	}

	private void initializeCancelButton() {

		filterPane.getCancelButton().setOnAction((event) -> {

			stage = filterPane.getStage();
			stage.close();

		});
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
			addEditFileWindow.getPositions_PathTextField().setText(file.getDstPath());

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

	private void reloadFieldsFilter() {

		filterPane.getNameTextField().setText(null);
		filterPane.getIdTextField().setText(null);
		filterPane.getSpeciesChoiceBox().setValue(null);
	}

	private void getParamsFromFields() {

		params = new HashMap<String, String>();

		if (!("").equals(filterPane.getIdTextField().getText()))
			params.put("id", filterPane.getIdTextField().getText());
		if (!("").equals(filterPane.getNameTextField().getText()))
			params.put("name", filterPane.getNameTextField().getText());
		if (!filterPane.getSpeciesChoiceBox().getSelectionModel().isEmpty())
			params.put("species", filterPane.getSpeciesChoiceBox().getValue().toString());

	}

	// :TODO przeniesc funkcje do readera
	private void filterRecords() throws IOException {

		params.keySet();

		resultList = new ArrayList<FastaRecord>();

		List<FastaRecord> recordsList = new ArrayList<FastaRecord>();
		FastaRecord record = null;

		// ustawienie positionsList i posiitonsMap
		reader.setPositionsListFromIdHashMap(idHashMap);
		reader.setPositionsMapFromPositionsList();

		// otwarcie pliku
		reader.openFile();

		// przeszukanie po gatunkach
		if (reader.setOrganism(params.get("species")) == true) {

			for (int i = 0; i < organismNameHashMap.get(params.get("species")).size(); i++) {
				record = reader.getNextRecord();
				recordsList.add(record);
			}
		}

		// przeszukanie po ID jezeli wypelniony gatunek oraniczamy liste przeszukania
		String id = params.get("id");
		Long pos;
		if (id != null) {
			if (recordsList.size() > 0) {

				for (FastaRecord rec : recordsList) {

					pos = reader.getStartPos(rec);
					record = reader.getRecordContainsId(id, pos);
					if (record != null)
						resultList.add(record);

				}
			} else {// jezeli niewypelniony gatunek:
				for (String key : idHashMap.keySet()) {

					pos = idHashMap.get(key);
					record = reader.getRecordContainsId(id, pos);
					if (record != null)
						resultList.add(record);
				}
			}
		}

		// szukanie po nazwie je�eli wypelniony agtunek zawezamy liste
		String name = params.get("name");

		if (name != null) {
			if (recordsList.size() > 0) {

				for (FastaRecord rec : recordsList) {

					pos = reader.getStartPos(rec);
					record = reader.getRecordContainsName(name, pos);
					if (record != null)
						resultList.add(record);
				}
			} else if (resultList.size() > 0) { // je�eli wyeplnione id

				recordsList.clear();

				for (FastaRecord rec : resultList) {

					pos = reader.getStartPos(rec);
					record = reader.getRecordContainsName(name, pos);
					if (record != null)
						recordsList.add(record);
				}

			} else { // jezeli niewypelniony gatunek i id
				for (String key : nameHashMap.keySet()) {

					pos = nameHashMap.get(key);
					record = reader.getRecordContainsName(name, pos);
					if (record != null)
						resultList.add(record);
				}
			}

		}

		showResultInfo(resultList.size());
		reader.close();
	}

	public void showResultInfo(int i) {

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Zako�czono sukcesem!");
		alert.setHeaderText(null);
		alert.setContentText("Znaleziono " + i + " rekord�w spe�niaj�cych kryteria!");
		alert.showAndWait();
	}

	public void showAlertInfo() {

		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("B��d!");
		alert.setHeaderText(null);
		alert.setContentText("Nie zaznaczono rekordu!");
		alert.showAndWait();
	}

	public void showAlertInfoForNewName() {

		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("B��d!");
		alert.setHeaderText(null);
		alert.setContentText("Nie podano nowej nazwy pliku!");
		alert.showAndWait();
	}

	public void showAlertInfoForNoFile() {

		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("B��d!");
		alert.setHeaderText(null);
		alert.setContentText("Nie zaznaczono pliku!");
		alert.showAndWait();
	}

}
