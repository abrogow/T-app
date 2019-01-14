package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.FastaRecord;
import model.File;
import model.Record;
import model.reader.FastaIndexBuilder;
import model.reader.FastaReader;
import model.reader.FastaRecordParser;
import model.writer.Writer;
import view.mainWindow.FilterPane;
import view.mainWindow.RecordsTable;

public class FilterWindowController {

	private FilterPane filterPane;
	RecordsTable recordsTable;
	private Map<String, Long> idHashMap;
	private Map<String, Long> nameHashMap;
	private Map<String, ArrayList<Long>> organismNameHashMap;
	private Map<String, String> params;
	private List<FastaRecord> resultList;
	private FastaReader reader;
	private String srcPath;
	private Stage stage;
	private File file;
	private String fileName;
	private Record tmpRec;
	private ArrayList<Record> recordsList;
	private ObservableList<Record> data = FXCollections.observableArrayList();
	private Stage progressStage;
	private Thread th;

	public FilterWindowController(FilterPane filterPane, RecordsTable recordsTable) {

		this.recordsTable = recordsTable;
		this.filterPane = filterPane;
		initializeHandlers();
	}

	private void initializeHandlers() {

		initializeSearchButton();
		initializeSaveButton();
	}

	private void initializeSearchButton() {

		filterPane.getSearchButton().setOnAction((event) -> {

			recordsList = recordsTable.getItemsAsArrayList();
			file = recordsTable.getFile();

			if (!recordsList.isEmpty()) {

				data.clear();
				setParams();
				try {
					filterRecords();
					recordsTable.updateTableView(data, file);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Szukanie skonczone");

			} else {
				showAlertInfoForNoFile();
				return;
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
				Writer writer = Writer.getInstance(file.getId_DB());
				;
				try {

					writer.saveRecordsToFile(resultList, fileName, srcPath, file.getId_DB());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				showResultInfo();

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

		resultList = new ArrayList<FastaRecord>();
		FastaRecord record = null;

		// ustawienie positionsList i posiitonsMap
		reader.setPositionsListFromIdHashMap(idHashMap);
		reader.setPositionsMapFromPositionsList();

		// otwarcie pliku
		reader.openFile();

		// przeszukanie po gatunkach
		reader.setOrganism(params.get("species"));

		reader.setIdFilter(params.get("id"));
		reader.setNameFilter(params.get("name"));

		while ((record = reader.getNextRecord()) != null)
			resultList.add(record);

		reader.close();
		String key = "";

		for (FastaRecord rec : resultList) {

			key = rec.getIdentyfier();
			tmpRec = new Record(key);
			data.add(tmpRec);
		}

	}

	private void setParams() {

		// get params
		getParamsFromFields();
		srcPath = file.getDstPath();

		// load hashmaps
		FastaRecordParser parser = FastaRecordParser.getInstance(file.getId_DB());
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
		alert.setContentText("Nie wybrano pliku!");
		alert.showAndWait();
	}

	Task<Void> task = new Task<Void>() {
		@Override
		public Void call() {
			try {
				filterRecords();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

	public List<FastaRecord> getResultList() {
		return resultList;
	}

	public void showResultInfo() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Zakoñczono sukcesem!");
		alert.setHeaderText(null);
		alert.setContentText("Zapisano rekordy!");
		alert.showAndWait();
	}

}
