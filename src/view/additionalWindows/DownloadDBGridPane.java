package view.additionalWindows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import view.mainWindow.RecordsTable;
import view.mainWindow.TemplateGridPane;

public class DownloadDBGridPane extends TemplateGridPane {

	private Button saveButton, cancelButton;
	private Label dbLabel, adressLabel;
	private ChoiceBox dbChoiceBox;
	private TextField adressTextField;
	private CheckBox adressCheckBox;

	public DownloadDBGridPane() {

		createControls();
		addControls();
		setProperties();
		configureGrid();

	}

	public void createControls() {
		// TODO Auto-generated method stub
		dbLabel = new Label("Baza danych");
		adressLabel = new Label("Adres: ");
		dbChoiceBox = new ChoiceBox();
		adressCheckBox = new CheckBox("U¿yj adresu www");
		adressTextField = new TextField();
		saveButton = new Button("Zapisz");
		cancelButton = new Button("Anuluj");

	}

	public void addControls() {
		// TODO Auto-generated method stub
		this.add(dbLabel, 0, 1);
		this.add(adressLabel, 0, 3);
		this.add(dbChoiceBox, 1, 1);
		this.add(adressCheckBox, 0, 2, 2, 1);
		this.add(adressTextField, 1, 3);
		this.add(saveButton, 0, 4);
		this.add(cancelButton, 1, 4);

	}

	public void configureGrid() {
		// TODO Auto-generated method stub
		this.setAlignment(Pos.CENTER);
		this.setHgap(50);
		this.setVgap(10);
		this.setPadding(new Insets(5, 5, 5, 5));

	}

	@Override
	public void setProperties() {
		adressTextField.setDisable(true);

	}

	@Override
	public void createAndShowStage(RecordsTable recordsTable) {
		// TODO Auto-generated method stub

	}

}
