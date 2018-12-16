package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.FastaRecord;
import model.File;
import model.reader.FastaIndexBuilder;
import model.reader.FastaReader;
import model.reader.FastaUniprotRecordParser;
import model.tools.RandomizationTools;
import model.writer.UniprotWriter;
import model.writer.Writer;
import view.mainWindow.CreateNewDBPane;
import view.mainWindow.FilesTable;

public class CreateDBWindowController {

	private CreateNewDBPane createNewDB;
	private FilesTable filesTable;
	private File file;
	private String fileName;
	private String DBType;
	private String srcPath;
	private List<FastaRecord> resultList;
	private Map<String, Long> idHashMap;
	private FastaReader reader;
	private String seqType;
	private RandomizationTools randomization;
	private String saveType;
	private Writer writer;
	private List<FastaRecord> filterList;
	private Stage progressStage;
	private Thread th;
	private FilterWindowController fwc;

	private final static String RANDOM_SEQUENCE = "Losowe sekwencje";
	private final static String REVERSED_SEQUENCE = "Odwrócone sekwencje";

	private final static String ALTERNATE_SAVING = "Tylko rekordy z nowej bazy";
	private static final String NONALTERNATE_SAVING = "Naprzemian- rekordy z nowej bazy, redkordy ze starej bazy";

	public CreateDBWindowController(CreateNewDBPane createNewDB, FilesTable filesTable,
			FilterWindowController filterController) {

		this.createNewDB = createNewDB;
		this.filesTable = filesTable;
		this.fwc = filterController;
		initializeHandlers();
	}

	private void initializeHandlers() {

		initializeSaveDBButton();
	}

	private void initializeSaveDBButton() {

		createNewDB.getSaveDBButton().setOnAction((event) -> {

			if (ifValuesSelected()) {

				// setProgressIndicator();
				try {
					createNewDB();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				showResultInfo();
				reloadFileds();

			} else {
				showAlertInfoForNoValues();
			}

		});
	}

	private void createNewDB() throws IOException {

		srcPath = file.getDstPath();
		resultList = new ArrayList<FastaRecord>();
		FastaRecord record = null;

		String newRecString = new String();
		FastaRecord newRec = null;
		StringBuilder lineSeparator = new StringBuilder();

		String dscLine;

		// set idHashMap
		FastaUniprotRecordParser parser = new FastaUniprotRecordParser();
		FastaIndexBuilder indexBuilder = new FastaIndexBuilder(srcPath, parser);
		reader = new FastaReader(srcPath, parser);

		writer = new UniprotWriter();
		randomization = new RandomizationTools();

		String idHMPath = indexBuilder.getResultFilesPath(srcPath, "idHashMap");

		idHashMap = reader.readIdIndex(idHMPath);
		idHashMap.keySet();

		// set id hash map in reader
		reader.setMaps(null, idHashMap, null);

		// ustawienie positionsList i posiitonsMap
		reader.setPositionsListFromIdHashMap(idHashMap);
		reader.setPositionsMapFromPositionsList();

		// otwarcie pliku
		reader.openFile();

		// dla filtowanych rekordów

		if (filterList.size() > 0 && fwc.isCreateNewDBSelected()) {

			for (FastaRecord rec : filterList) {

				newRecString = randomization.getRandomRecord(rec, srcPath, writer, DBType);
				newRec = parser.parse(newRecString);
				resultList.add(newRec);

				// jezeli zapisywanie naprzemian ze starym rekordem
				if (NONALTERNATE_SAVING.equals(saveType))
					resultList.add(record);
			}
			writer.saveRecordsToFile(resultList, fileName, srcPath);
			reader.close();
		} else {
			// dla pliku
			for (String key : idHashMap.keySet()) {

				record = reader.getRecord(key);
				newRecString = randomization.getRandomRecord(record, srcPath, writer, DBType);
				newRec = parser.parse(newRecString);
				resultList.add(newRec);

				// jezeli zapisywanie naprzemian ze starym rekordem
				if (NONALTERNATE_SAVING.equals(saveType))
					resultList.add(record);
			}

			writer.saveRecordsToFile(resultList, fileName, srcPath);
			reader.close();
		}

	}

	private boolean ifValuesSelected() {

		file = filesTable.getFilesTable().getSelectionModel().getSelectedItem();
		fileName = createNewDB.getNameDBTextField().getText();
		RadioButton selectedRadioButton = (RadioButton) createNewDB.getSavingTypeToggleGroup().getSelectedToggle();
		saveType = selectedRadioButton.getText();

		filterList = fwc.getResultList();

		if (!createNewDB.getDBTypeChoiceBox().getSelectionModel().isEmpty())
			DBType = createNewDB.getDBTypeChoiceBox().getValue().toString();

		if (file != null | filterList.size() > 0) {
			if (!("").equals(fileName) & !createNewDB.getDBTypeChoiceBox().getSelectionModel().isEmpty()
					& !("").equals(saveType))
				return true;

		}

		return false;
	}

	public void showAlertInfoForNoValues() {

		String info = "";
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("B³¹d!");
		alert.setHeaderText(null);

		if (createNewDB.getDBTypeChoiceBox().getSelectionModel().isEmpty())
			info = "Nie zaznaczono typu bazy!";
		if (("").equals(fileName))
			info = "Nie wpisano nazwy nowego pliku!";
		if (file == null)
			info = "Nie zaznaczono pliku!";
		alert.setContentText(info);
		alert.showAndWait();
	}

	public void reloadFileds() {

		createNewDB.getDBTypeChoiceBox().setValue(null);
		createNewDB.getNameDBTextField().setText("");
	}

	public void showResultInfo() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Zakoñczono sukcesem!");
		alert.setHeaderText(null);
		alert.setContentText("Nowa baza zosta³a utworzona!");
		alert.showAndWait();
	}

	Task<Void> task = new Task<Void>() {
		@Override
		public Void call() throws IOException {
			createNewDB();
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
