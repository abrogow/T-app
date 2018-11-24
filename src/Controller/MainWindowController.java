package Controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.File;
import model.dataBase.DataBaseModel;
import model.reader.Reader;
import model.writer.UniprotWriter;
import model.writer.Writer;
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
	private Button okButton, cancelButton;
	private Stage stage;
	private String fileName;

	private File file;

	// do filtra
	private LinkedHashMap<Long, String> idHashMap;
	private LinkedHashMap<Long, String> nameHashMap;
	private LinkedHashMap<Long, String> organismNameHashMap;

	// do readera
	private Reader reader;
	private static final String UNIPROT_READER = "UniProt";

	// do filtra
	private Scanner in;

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

				// getSpecifiedReader();

				reader.setHashMaps();
				// setHashMapsFromFile();
				// getSpecyficationsFromFilter();
				// findFiles();
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

			Stage stage = filterGridPane.showStageWithFileName();
			stage.show();
			// dalsze zapisywanie w przycisku OK
			initializeOkButton();
			initializeCancelButton();

		});
	}

	private void initializeOkButton() {

		filterGridPane.getOkButton().setOnAction((event) -> {

			if (!("").equals(filterGridPane.getNewFileTextField().getText())) {
				fileName = filterGridPane.getNewFileTextField().getText();
				stage = filterGridPane.getStage();
				stage.close();

				// TODO:szukanie rekordów (get srcFile)

				// TODO:zapisywanie pliku narazie wybieram plik i po prostu przekopiowuje
				// rekord po rekordzie do nowego pliku
				String path = "C:\\Users\\BROGO\\Desktop\\INZYNIERKA\\test-positionsList.txt";
				String srcPath = "C:\\Users\\BROGO\\Desktop\\INZYNIERKA\\test.txt";
				// utworznie listy z pozycjami pozniej wstawic ArrayList z filtrowania
				ArrayList<Long> resultPositions = new ArrayList<Long>();
				String line;
				try {
					BufferedReader bufReader = new BufferedReader(new FileReader(path));
					line = bufReader.readLine();
					while (line != null) {
						resultPositions.add(Long.parseLong(line));
						line = bufReader.readLine();
					}
					bufReader.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// TODO:trzeba zrobic rozpoznawanie writera
				Writer writer = new UniprotWriter();
				try {

					writer.saveRecordsToFile(resultPositions, fileName, srcPath);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				in.close();

			} else {
				showAlertInfoForNewName();
				return;
			}
		});
	}

	private void initializeCancelButton() {

		filterGridPane.getCancelButton().setOnAction((event) -> {

			stage = filterGridPane.getStage();
			stage.close();

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

}
