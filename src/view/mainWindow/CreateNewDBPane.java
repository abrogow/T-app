package view.mainWindow;

import java.util.Arrays;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class CreateNewDBPane extends GridPane {

	private Button saveDBButton;
	private ChoiceBox DBTypeChoiceBox;
	private Label nameDBLabel, DBTypeLabel;
	private TextField nameDBTextField;
	private RadioButton alternateRecords, nonalternateRecords;
	private ToggleGroup savingTypeToggleGroup;

	private final List<String> types = Arrays.asList("", "Losowe sekwencje");

	public ToggleGroup getSavingTypeToggleGroup() {
		return savingTypeToggleGroup;
	}

	public ChoiceBox getDBTypeChoiceBox() {
		return DBTypeChoiceBox;
	}

	public Button getSaveDBButton() {
		return saveDBButton;
	}

	public TextField getNameDBTextField() {
		return nameDBTextField;
	}

	public RadioButton getAlternateRecords() {
		return alternateRecords;
	}

	public RadioButton getNonalternateRecords() {
		return nonalternateRecords;
	}

	public CreateNewDBPane() {

		createControls();
		setProperties();
		addControls();
		configureGrid();
	}

	public void createControls() {

		nameDBLabel = new Label("Nazwa nowej bazy: ");
		DBTypeLabel = new Label("Typ bazy: ");

		alternateRecords = new RadioButton("Tylko rekordy z nowej bazy");
		nonalternateRecords = new RadioButton("Naprzemian- rekordy z nowej bazy, redkordy ze starej bazy");

		savingTypeToggleGroup = new ToggleGroup();
		alternateRecords.setToggleGroup(savingTypeToggleGroup);
		nonalternateRecords.setToggleGroup(savingTypeToggleGroup);

		saveDBButton = new Button("Zapisz");
		nameDBTextField = new TextField();
		DBTypeChoiceBox = new ChoiceBox();
		for (String type : types) {
			DBTypeChoiceBox.getItems().add(type);
		}
	}

	public void addControls() {

		Text sceneTitle = new Text("Torzenie nowej bazy:");
		this.add(sceneTitle, 0, 0, 2, 1);

		this.add(nameDBLabel, 0, 1);
		this.add(nameDBTextField, 1, 1);
		this.add(DBTypeLabel, 0, 2);
		this.add(DBTypeChoiceBox, 1, 2);
		this.add(alternateRecords, 0, 4);
		this.add(nonalternateRecords, 0, 3);

		this.add(saveDBButton, 0, 5);

	}

	public void setProperties() {

		this.setStyle("-fx-padding: 5;" + "-fx-border-style: solid inside;" + "-fx-border-width: 0.5;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: grey;");

		if (!alternateRecords.isSelected())
			nonalternateRecords.setSelected(true);
		if (!nonalternateRecords.isSelected())
			alternateRecords.setSelected(true);
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
}
