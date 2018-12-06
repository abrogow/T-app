package Controller;

import javafx.scene.control.Alert;
import model.File;
import view.mainWindow.CreateNewDBPane;
import view.mainWindow.FilesTable;

public class CreateDBWindowController {

	private CreateNewDBPane createNewDB;
	private FilesTable filesTable;
	private File file;
	private String fileName;
	private String DBType;

	public CreateDBWindowController(CreateNewDBPane createNewDB, FilesTable filesTable) {

		this.createNewDB = createNewDB;
		this.filesTable = filesTable;
	}

	void initializeHandlers() {

		initializeSaveDBButton();
	}

	private void initializeSaveDBButton() {

		createNewDB.getSaveDBButton().setOnAction((event) -> {

			if (ifValuesSelected()) {

			} else {
				showAlertInfoForNoValues();
			}

		});
	}

	private boolean ifValuesSelected() {

		file = filesTable.getFilesTable().getSelectionModel().getSelectedItem();
		fileName = createNewDB.getNameDBTextField().getText();
		if (!createNewDB.getDBTypeChoiceBox().getSelectionModel().isEmpty())
			DBType = createNewDB.getDBTypeChoiceBox().getValue().toString();

		if (file != null & !("").equals(fileName) & !createNewDB.getDBTypeChoiceBox().getSelectionModel().isEmpty())
			return true;
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

}
