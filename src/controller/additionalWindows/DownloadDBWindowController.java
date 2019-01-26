package controller.additionalWindows;

import java.io.IOException;

import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.web.DatabaseDownloader;
import view.additionalWindows.DownloadDBWindow;

public class DownloadDBWindowController {

	private DownloadDBWindow downloadDB;
	private String dstPath;
	private String dbType;
	private String fileName;

	public DownloadDBWindowController(DownloadDBWindow downloadDB) {

		this.downloadDB = downloadDB;
		initializeHandlers();
	}

	private void initializeHandlers() {

		initializeDownloadButton();
		initializeCancelButton();
		initializeLoadButton();
	}

	private void initializeDownloadButton() {

		downloadDB.getDownloadButton().setOnAction((event) -> {

			if (isParamsSet()) {
				try {
					DatabaseDownloader.downloadDatabase(dbType, dstPath, fileName);
					showSuccessMessage();
					hideRecordWindow();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				showAlertInfo();
			}
		});
	}

	private void initializeCancelButton() {

		downloadDB.getCancelButton().setOnAction((event) -> hideRecordWindow());
	}

	private void initializeLoadButton() {

		downloadDB.getLoadButton().setOnAction((event) -> {

			getPathFromFileDialog();
			downloadDB.getDstPathTextField().setText(dstPath);

		});

	}

	private void hideRecordWindow() {

		Stage stage = downloadDB.getStage();
		stage.close();
	}

	private void getPathFromFileDialog() {

		Stage stage = new Stage();
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Wybierz folder docelowy pliku:");
		java.io.File selectedDirectory = directoryChooser.showDialog(stage);
		dstPath = selectedDirectory.getAbsolutePath();
	}

	private boolean isParamsSet() {

		dbType = downloadDB.getDBComboBox().getValue().toString();
		dstPath = downloadDB.getDstPathTextField().getText();
		fileName = downloadDB.getFileNameTextField().getText();

		if (("").equals(dbType) || ("").equals(dstPath) || ("").equals(fileName))
			return false;

		else
			return true;

	}

	private void showAlertInfo() {

		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("B³¹d!");
		alert.setHeaderText(null);
		String message = "";
		if (downloadDB.getDBComboBox().getSelectionModel().isEmpty())
			message = "Nie zaznaczono bazy danych!";
		else if (("").equals(downloadDB.getDstPathTextField().getText()))
			message = "Nie wybrano œcie¿ki!";
		else if (("").equals(downloadDB.getFileNameTextField().getText()))
			message = "Nie podano nazwy pliku!";
		alert.setContentText(message);
		alert.showAndWait();
	}

	private void showSuccessMessage() {

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Sukces!");
		alert.setHeaderText(null);
		alert.setContentText("Baza danych zosta³a pobrana!");
		alert.showAndWait();
	}
}
