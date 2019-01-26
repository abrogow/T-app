package view.mainWindow;

import java.util.Arrays;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FilterPane extends GridPane {

	private Label idLabel, nameLabel, speciesLabel, newFileLabel;
	private ChoiceBox speciesChoiceBox;
	private Button saveButton, searchButton, okButton, cancelButton;
	private TextField newFileTextField, idTextField, nameTextField;
	private Stage stage;

	private String fileName;

	private final List<String> species = Arrays.asList("", "Homo sapiens ");

	public Button getSearchButton() {
		return searchButton;
	}

	public Button getSaveButton() {
		return saveButton;
	}

	public Button getOkButton() {
		return okButton;
	}

	public Button getCancelButton() {
		return cancelButton;
	}

	public TextField getNewFileTextField() {
		return newFileTextField;
	}

	public String getFileName() {
		return fileName;
	}

	public ChoiceBox getSpeciesChoiceBox() {
		return speciesChoiceBox;
	}

	public TextField getNameTextField() {
		return nameTextField;
	}

	public TextField getIdTextField() {
		return idTextField;
	}

	public Stage getStage() {
		return stage;
	}

	public FilterPane() {

		createControls();
		setProperties();
		addControls();
		configureGrid();
	}

	public void createControls() {

		idLabel = new Label("Id: ");
		nameLabel = new Label("Nazwa: ");
		speciesLabel = new Label("Gatunek: ");

		idTextField = new TextField();
		nameTextField = new TextField();
		speciesChoiceBox = new ChoiceBox();
		for (String spec : species) {
			speciesChoiceBox.getItems().add(spec);
		}

		searchButton = new Button("Wyszukaj");
		saveButton = new Button("Zapisz rekordy do pliku");

	}

	public void addControls() {

		Text sceneTitle = new Text("Filtry wyszukiwania:");
		this.add(sceneTitle, 0, 0, 2, 1);

		this.add(idLabel, 0, 1);
		this.add(nameLabel, 0, 2);
		this.add(speciesLabel, 0, 3);

		this.add(idTextField, 1, 1);
		this.add(nameTextField, 1, 2);
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
		this.setPrefSize(600, 100);
	}

	public void setProperties() {

		this.saveButton.setDisable(true);
		this.setStyle("-fx-padding: 5;" + "-fx-border-style: solid inside;" + "-fx-border-width: 0.5;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: grey;");
	}

	public Stage showStageWithFileName() {

		stage = new Stage();
		stage.setTitle("Nazwa nowego pliku: ");
		GridPane gridPane = new GridPane();
		Scene scene = new Scene(gridPane, 600, 400);
		newFileLabel = new Label("Nazwa nowego pliku: ");
		newFileLabel.setAlignment(Pos.CENTER);
		newFileTextField = new TextField();
		newFileTextField.setPrefWidth(300);
		okButton = new Button("Ok");
		okButton.setAlignment(Pos.CENTER);
		cancelButton = new Button("Anuluj");
		cancelButton.setAlignment(Pos.CENTER);

		gridPane.add(newFileLabel, 1, 2);
		gridPane.add(newFileTextField, 2, 2);
		gridPane.add(okButton, 1, 3);
		gridPane.add(cancelButton, 2, 3);

		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(50);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(5, 5, 5, 5));

		stage.setScene(scene);
		return stage;

	}

}
