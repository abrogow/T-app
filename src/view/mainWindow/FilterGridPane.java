package view.mainWindow;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FilterGridPane extends TemplateGridPane {

	private Label idLabel, nameLabel, speciesLabel, newFileLabel;
	private ChoiceBox idChoiceBox, nameChoiceBox, speciesChoiceBox;
	private Button saveButton, searchButton;
	private TextField newFileTextField;

	public Button getSearchButton() {
		return searchButton;
	}

	public Button getSaveButton() {
		return saveButton;
	}

	public TextField getNewFileTextField() {
		return newFileTextField;
	}

	public FilterGridPane() {

		createControls();
		setProperties();
		addControls();
		configureGrid();
	}

	public void createControls() {

		idLabel = new Label("Id: ");
		nameLabel = new Label("Nazwa: ");
		speciesLabel = new Label("Gatunek: ");

		idChoiceBox = new ChoiceBox();
		nameChoiceBox = new ChoiceBox();
		speciesChoiceBox = new ChoiceBox();

		searchButton = new Button("Wyszukaj");
		saveButton = new Button("Zapisz rekordy do pliku");

	}

	public void addControls() {

		Text sceneTitle = new Text("Filtry wyszukiwania");
		this.add(sceneTitle, 0, 0, 2, 1);

		this.add(idLabel, 0, 1);
		this.add(nameLabel, 0, 2);
		this.add(speciesLabel, 0, 3);

		this.add(idChoiceBox, 1, 1);
		this.add(nameChoiceBox, 1, 2);
		this.add(speciesChoiceBox, 1, 3);

		this.add(searchButton, 1, 5);
		this.add(saveButton, 1, 6);
	}

	public void configureGrid() {

		ColumnConstraints leftCol = new ColumnConstraints();
		leftCol.setPercentWidth(45);
		ColumnConstraints rightCol = new ColumnConstraints();
		rightCol.setPercentWidth(55);
		this.getColumnConstraints().addAll(leftCol, rightCol);

		this.setAlignment(Pos.CENTER);
		this.setHgap(50);
		this.setVgap(10);
		this.setPadding(new Insets(5, 5, 5, 5));
	}

	public void setProperties() {

		this.saveButton.setDisable(true);
	}

	public void showStageWithFileName() {

		Stage stage = new Stage();
		stage.setTitle("Nazwa nowego pliku:");
		Scene scene = new Scene(this);
		newFileLabel = new Label("Nazwa nowego pliku: ");
		newFileTextField = new TextField();

		this.add(newFileLabel, 0, 0);
		this.add(newFileTextField, 1, 0);
		stage.show();
	}

}
