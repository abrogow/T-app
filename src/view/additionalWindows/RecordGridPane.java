package view.additionalWindows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

}
