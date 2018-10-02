package view.additionalWindows;

import Controller.AdditionalWindowController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Record;
import view.mainWindow.RecordsTable;
import view.mainWindow.TemplateGridPane;

public class RecordGridPane extends TemplateGridPane {

	private Label idLabel;
	private Label nameLabel;
	private Label infoLabel;
	private Label sequenceLanel;

	private TextField idTextField;
	private TextField nameTextField;
	private TextField infoTextField;
	private TextField sequenceTextField;

	private Button loadButton;
	private Button webButton;
	private Button saveButton;
	private Button cancelButton;

	private Stage stage;
	private Record record;

	public RecordGridPane() {

		createControls();
		addControls();
		setProperties();
		configureGrid();

	}

	@Override
	public void createControls() {

		idLabel = new Label("Id: ");
		nameLabel = new Label("Nazwa: ");
		infoLabel = new Label("Opis: ");
		sequenceLanel = new Label("Sekwencja: ");

		idTextField = new TextField();
		nameTextField = new TextField();
		infoTextField = new TextField();
		sequenceTextField = new TextField();

		loadButton = new Button("Wczytaj plik");
		webButton = new Button("Icon");
		saveButton = new Button("Zapisz");
		cancelButton = new Button("Anuluj");
	}

	@Override
	public void addControls() {

		this.add(idLabel, 0, 1);
		this.add(nameLabel, 0, 2);
		this.add(infoLabel, 0, 3);
		this.add(sequenceLanel, 0, 4);
		this.add(idTextField, 1, 1);
		this.add(nameTextField, 1, 2);
		this.add(infoTextField, 1, 3);
		this.add(sequenceTextField, 1, 4);
		this.add(loadButton, 2, 1);
		this.add(webButton, 2, 2);
		this.add(saveButton, 0, 5);
		this.add(cancelButton, 1, 5);

	}

	@Override
	public void configureGrid() {
		// TODO Auto-generated method stub
		this.setAlignment(Pos.CENTER);
		this.setHgap(50);
		this.setVgap(10);
		this.setPadding(new Insets(5, 5, 5, 5));
	}

	@Override
	public void setProperties() {
		// TODO Auto-generated method stub

	}

	public Button getLoadButton() {
		return loadButton;
	}

	public Button getWebButton() {
		return webButton;
	}

	public Button getSaveButton() {
		return saveButton;
	}

	public Button getCancelButton() {
		return cancelButton;
	}

	public TextField getIdTextField() {
		return idTextField;
	}

	public TextField getNameTextField() {
		return nameTextField;
	}

	public TextField getInfoTextField() {
		return infoTextField;
	}

	public TextField getSequenceTextField() {
		return sequenceTextField;
	}

	@Override
	public void createAndShowStage(RecordsTable recordsTable) {
		// TODO Auto-generated method stub

		RecordGridPane recordGridPane = new RecordGridPane();

		AdditionalWindowController controller = new AdditionalWindowController(recordsTable, recordGridPane);
		stage = new Stage();
		stage.setScene(new Scene(recordGridPane, 500, 300));
		stage.show();
	}

	public Record getRecord() {
		return record;
	}

}
