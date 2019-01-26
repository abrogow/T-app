package controller.mainWindow;

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
import model.FastaFile;
import model.FastaRecord;
import model.reader.FastaIndexBuilder;
import model.reader.FastaReader;
import model.reader.FastaRecordParser;
import model.tools.RandomizationTools;
import model.writer.FastaWriter;
import view.mainWindow.CreateNewDBPane;
import view.mainWindow.FilesTable;
import view.mainWindow.Record;
import view.mainWindow.RecordsTable;

public class CreateDBWindowController {

	private CreateNewDBPane createNewDB;
	private FilesTable filesTable;
	private FastaFile fastaFile;
	private String fileName;
	private String DBType;
	private String srcPath;
	private List<FastaRecord> resultList;
	private Map<String, Long> idHashMap;
	private FastaReader reader;
	private String seqType;
	private RandomizationTools randomization;
	private String saveType;
	private FastaWriter fastaWriter;
	private Stage progressStage;
	private Thread th;
	private RecordsTable recordsTable;
	private ArrayList<Record> recordsList;

	private final static String RANDOM_SEQUENCE = "Losowe sekwencje";
	private final static String REVERSED_SEQUENCE = "Odwrócone sekwencje";

	private final static String ALTERNATE_SAVING = "Tylko rekordy z nowej bazy";
	private static final String NONALTERNATE_SAVING = "Na przemian- rekordy z nowej bazy, redkordy ze starej bazy";

	public CreateDBWindowController(CreateNewDBPane createNewDB, RecordsTable recordsTable) {

		this.createNewDB = createNewDB;
		this.recordsTable = recordsTable;
		initializeHandlers();
	}

	private void initializeHandlers() {

		initializeSaveDBButton();
	}

	private void initializeSaveDBButton() {

		createNewDB.getSaveDBButton().setOnAction((event) -> {

			recordsList = recordsTable.getItemsAsArrayList();

			if (ifValuesSelected() && !recordsList.isEmpty()) {

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

		fastaFile = recordsTable.getFile();
		srcPath = fastaFile.getDstPath();
		resultList = new ArrayList<FastaRecord>();
		FastaRecord record = null;

		String newRecString = new String();
		FastaRecord newRec = null;
		StringBuilder lineSeparator = new StringBuilder();

		String dscLine;

		// set idHashMap
		FastaRecordParser parser = FastaRecordParser.getInstance(fastaFile.getId_DB());
		FastaIndexBuilder indexBuilder = new FastaIndexBuilder(srcPath, parser);
		reader = new FastaReader(srcPath, parser);

		fastaWriter = FastaWriter.getInstance(fastaFile.getId_DB());
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

		if (recordsList.size() > 0) {

			for (Record rec : recordsList) {

				record = reader.getRecord(rec.getFileId());

				newRecString = randomization.getNewRecord(record, srcPath, fastaWriter, DBType);
				newRec = parser.parse(newRecString);
				resultList.add(newRec);

				// jezeli zapisywanie naprzemian ze starym rekordem
				if (NONALTERNATE_SAVING.equals(saveType))
					resultList.add(record);
			}
			fastaWriter.saveRecordsToFile(resultList, fileName, srcPath, fastaFile.getId_DB());
			reader.close();
		}
	}

	private boolean ifValuesSelected() {

		fileName = createNewDB.getNameDBTextField().getText();
		RadioButton selectedRadioButton = (RadioButton) createNewDB.getSavingTypeToggleGroup().getSelectedToggle();
		saveType = selectedRadioButton.getText();

		if (!createNewDB.getDBTypeChoiceBox().getSelectionModel().isEmpty())
			DBType = createNewDB.getDBTypeChoiceBox().getValue().toString();

		if (!recordsList.isEmpty()) {
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
		if (recordsList.isEmpty())
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
