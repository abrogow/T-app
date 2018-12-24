package Controller;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import view.additionalWindows.DownloadDBWindow;

public class DownloadDBWindowController {

	private DownloadDBWindow downloadDB;
	private String dstPath;

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

			if (isDBSet()) {

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

		Window stage = downloadDB.getScene().getWindow();
		stage.hide();
	}

	private void getPathFromFileDialog() {

		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Wybierz folder docelowy pliku:");
		java.io.File selectedDirectory = fileChooser.showOpenDialog(stage);
		dstPath = selectedDirectory.getAbsolutePath();
	}

	private boolean isDBSet() {
		if (downloadDB.getDBComboBox().getSelectionModel().isEmpty())
			return false;

		else
			return true;

	}

	private void showAlertInfo() {

		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("B³¹d!");
		alert.setHeaderText(null);
		alert.setContentText("Nie zaznaczono bazy danych!");
		alert.showAndWait();
	}
}
