package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.FastaRecord;
import model.File;
import model.reader.FastaIndexBuilder;
import model.reader.FastaReader;
import model.reader.FastaUniprotRecordParser;
import model.writer.UniprotWriter;
import model.writer.Writer;
import view.mainWindow.FilesTable;
import view.mainWindow.FilterPane;

public class FilterWindowController {

	private FilterPane filterPane;
	private Map<String, Long> idHashMap;
	private Map<String, Long> nameHashMap;
	private Map<String, ArrayList<Long>> organismNameHashMap;
	private Map<String, String> params;
	private List<FastaRecord> resultList;
	private FastaReader reader;
	private String srcPath;
	private Stage stage;
	private File file;
	private FilesTable filesTable;
	private String fileName;

	public FilterWindowController(FilterPane filterPane, FilesTable filesTable) {

		this.filterPane = filterPane;
		this.filesTable = filesTable;
	}

	public void initializeHandlers() {

		initializeSearchButton();
		initializeSaveButton();
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

		// szukanie po nazwie je¿eli wypelniony agtunek zawezamy liste
		String name = params.get("name");

		if (name != null) {
			if (recordsList.size() > 0 && resultList.size() == 0) {

				for (FastaRecord rec : recordsList) {

					pos = reader.getStartPos(rec);
					record = reader.getRecordContainsName(name, pos);
					if (record != null)
						resultList.add(record);
				}
			} else if (resultList.size() > 0) { // je¿eli wyeplnione id

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

		if (resultList.size() == 0 && recordsList.size() > 0)
			resultList = recordsList;
		showResultInfo(resultList.size());
		reader.close();
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

	public void showResultInfo(int i) {

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Zakoñczono sukcesem!");
		alert.setHeaderText(null);
		alert.setContentText("Znaleziono " + i + " rekordów spe³niaj¹cych kryteria!");
		alert.showAndWait();
	}

	public void showAlertInfoForNewName() {

		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("B³¹d!");
		alert.setHeaderText(null);
		alert.setContentText("Nie podano nowej nazwy pliku!");
		alert.showAndWait();
	}

	public void showAlertInfoForNoFile() {

		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("B³¹d!");
		alert.setHeaderText(null);
		alert.setContentText("Nie zaznaczono pliku!");
		alert.showAndWait();
	}
}
