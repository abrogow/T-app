package view.mainWindow;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class RecordPane extends GridPane {

	private Label idLabel, nameLabel, sequenceLabel;
	private TextField idTextField, nameTextField, sequenceTextField;
	private Button saveButton, cancelButton, webButton;

	public RecordPane() {

		createControls();
		setProperties();
		addControls();
		configureGrid();
		setDefValue();

	}

	public void createControls() {

		idLabel = new Label("ID: ");
		nameLabel = new Label("Nazwa: ");
		sequenceLabel = new Label("Sekwencja: ");

		idTextField = new TextField();
		nameTextField = new TextField();
		sequenceTextField = new TextField();

		saveButton = new Button("Zapisz");
		cancelButton = new Button("Anuluj");
		webButton = new Button("Web");

	}

	public void addControls() {

		Text sceneTitle = new Text("Rekord: ");
		this.add(sceneTitle, 0, 0, 2, 1);

		this.add(idLabel, 0, 1);
		this.add(nameLabel, 0, 2);
		this.add(sequenceLabel, 0, 3);

		this.add(saveButton, 0, 5);
		this.add(cancelButton, 1, 5);
		this.add(webButton, 1, 0);

		this.add(idTextField, 1, 1);
		this.add(nameTextField, 1, 2);
		this.add(sequenceTextField, 1, 3);
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
		this.setPrefSize(500, 100);
	}

	public void setDefValue() {

		this.setDisable(true);
	}

	public void setProperties() {

		this.setStyle("-fx-padding: 5;" + "-fx-border-style: solid inside;" + "-fx-border-width: 0.5;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: grey;");
		// Image image = new
		// Image(getClass().getResourceAsStream("C:\\Users\\BROGO\\Desktop\\INZYNIERKA\\webButton.png"));
		// ImageView iv = new ImageView();
		// iv.setImage(image);
		// webButton.setGraphic(iv);
	}

	public Button getSaveButton() {
		return saveButton;
	}

	public Button getCancelButton() {
		return cancelButton;
	}

	public Button getWebButton() {
		return webButton;
	}

	public TextField getIdTextField() {
		return idTextField;
	}

	public TextField getNameTextField() {
		return nameTextField;
	}

	public TextField getSequenceTextField() {
		return sequenceTextField;
	}

	public GridPane getRecordPane() {
		return this;
	}
}
