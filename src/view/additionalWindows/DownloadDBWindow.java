package view.additionalWindows;

import java.util.Arrays;
import java.util.List;

import controller.additionalWindows.DownloadDBWindowController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DownloadDBWindow extends GridPane {

	private Label fileNameLabel, dbLabel, dstPathLabel;
	private TextField fileNameTextField, dstPathTextField;
	private ComboBox dbComboBox;
	private Button loadButton, cancelButton, downloadButton;
	private final List<String> dataBaseType = Arrays.asList("UniProt-SwissProt", "UniProt-TREMBL", "NCBI", "SGD",
			"TAIR");
	private Stage stage;

	public DownloadDBWindow() {
		createControls();
		addControls();
		setProperties();
		configureGrid();
	}

	public Stage getStage() {
		return stage;
	}

	private void createControls() {

		fileNameLabel = new Label("Nazwa pliku: ");
		dbLabel = new Label("Rodzaj bazy: ");
		dstPathLabel = new Label("Miejsce docelowe pliku: ");
		dbComboBox = new ComboBox();
		for (String db : dataBaseType) {
			dbComboBox.getItems().add(db);
		}
		fileNameTextField = new TextField();
		dstPathTextField = new TextField();
		loadButton = new Button("Przegl¹daj");
		cancelButton = new Button("Anuluj");
		downloadButton = new Button("Œci¹gnij");
	}

	private void addControls() {

		this.add(fileNameLabel, 0, 1);
		this.add(dstPathLabel, 0, 3);
		this.add(dbLabel, 0, 2);
		this.add(dbComboBox, 1, 2);
		this.add(fileNameTextField, 1, 1);
		this.add(dstPathTextField, 1, 3);
		this.add(loadButton, 2, 3);
		this.add(cancelButton, 0, 4);
		this.add(downloadButton, 1, 4);
	}

	private void setProperties() {

		this.setAlignment(Pos.CENTER);
		this.setHgap(50);
		this.setVgap(10);
		this.setPadding(new Insets(5, 5, 5, 5));
	}

	private void configureGrid() {

	}

	public void createAndShowStage() {
		// TODO Auto-generated method stub

		DownloadDBWindowController controller = new DownloadDBWindowController(this);
		stage = new Stage();
		stage.setTitle("Œci¹ganie bazy danych");
		stage.setScene(new Scene(this, 800, 500));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();
	}

	public Button getCancelButton() {
		return cancelButton;
	}

	public Button getLoadButton() {
		return loadButton;
	}

	public Button getDownloadButton() {
		return downloadButton;
	}

	public TextField getDstPathTextField() {
		return dstPathTextField;
	}

	public TextField getFileNameTextField() {
		return fileNameTextField;
	}

	public ComboBox getDBComboBox() {
		return dbComboBox;
	}

}
