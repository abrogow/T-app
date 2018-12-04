package view.additionalWindows;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import Controller.AdditionalWindowController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.File;
import view.mainWindow.FilesTable;
import view.mainWindow.TemplateGridPane;

public class AddEditFileWindow extends TemplateGridPane {

	private Label nameLabel, descriptionLabel, id_DBLabel, version_DBLabel, sequence_idLabel, sequence_nameLabel,
			rand_sequenceLabel, prefixLabel, rand_typeLabel, positions_pathLabel;;

	private TextField nameTextField, descriptionTextField, version_DBTextField, sequence_idTextField,
			sequence_nameTextField, rand_sequenceTextField, prefixTextField, rand_typeTextField,
			positions_pathTextFiled;

	private ComboBox id_DBComboBox;

	private Button loadButton;
	private Button webButton;
	private Button saveButton;
	private Button cancelButton;

	private Stage stage;
	private File file;
	private FilesTable filesTable;

	private ProgressBar progressBar;
	private TextField progressTextField;

	private final List<String> dataBaseType = Arrays.asList("UniProt", "NCBI", "IPI", "TAIR", "Other");

	public AddEditFileWindow() {

		createControls();
		addControls();
		setProperties();
		configureGrid();

	}

	@Override
	public void createControls() {

		nameLabel = new Label("Nazwa pliku FASTA");
		descriptionLabel = new Label("Opis pliku");
		id_DBLabel = new Label("Identyfikator bazy danych (long)");
		version_DBLabel = new Label("Wersja bazy danych");
		sequence_idLabel = new Label("Wyra¿enie regularne identyfikatorów sekwencji w linii opisu");
		sequence_nameLabel = new Label("Wyra¿enie regularne nazw sekewncji w linii opisu");
		rand_sequenceLabel = new Label("Infoormacja czy plik zawiera sekwencje randomizowane(long)");
		prefixLabel = new Label("Przedrostek identyfikatorów sekwencji randomizowanych");
		rand_typeLabel = new Label("Rodzaj randomizacji(long)");
		positions_pathLabel = new Label("Œcie¿ka do pliku");

		descriptionTextField = new TextField();
		id_DBComboBox = new ComboBox();

		for (String db : dataBaseType) {
			id_DBComboBox.getItems().add(db);
		}
		version_DBTextField = new TextField();
		sequence_idTextField = new TextField();
		sequence_nameTextField = new TextField();
		rand_sequenceTextField = new TextField();
		prefixTextField = new TextField();
		rand_typeTextField = new TextField();
		nameTextField = new TextField();
		positions_pathTextFiled = new TextField();

		loadButton = new Button("Wczytaj plik");
		webButton = new Button("Icon");
		saveButton = new Button("Zapisz");
		cancelButton = new Button("Anuluj");
	}

	@Override
	public void addControls() {

		this.add(nameLabel, 0, 1);
		this.add(descriptionLabel, 0, 2);
		this.add(id_DBLabel, 0, 3);
		this.add(version_DBLabel, 0, 4);
		this.add(sequence_idLabel, 0, 5);
		this.add(sequence_nameLabel, 0, 6);
		this.add(rand_sequenceLabel, 0, 7);
		this.add(prefixLabel, 0, 8);
		this.add(rand_typeLabel, 0, 9);
		this.add(positions_pathLabel, 0, 10);

		this.add(nameTextField, 1, 1);
		this.add(descriptionTextField, 1, 2);
		this.add(id_DBComboBox, 1, 3);
		this.add(version_DBTextField, 1, 4);
		this.add(sequence_idTextField, 1, 5);
		this.add(sequence_nameTextField, 1, 6);
		this.add(rand_sequenceTextField, 1, 7);
		this.add(prefixTextField, 1, 8);
		this.add(rand_typeTextField, 1, 9);
		this.add(positions_pathTextFiled, 1, 10);

		this.add(loadButton, 2, 1);
		this.add(webButton, 2, 2);
		this.add(saveButton, 0, 11);
		this.add(cancelButton, 1, 11);

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
		this.id_DBComboBox.setPrefWidth(200);

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

	public ComboBox getIdDBComboBox() {
		return id_DBComboBox;
	}

	public TextField getNameTextField() {
		return nameTextField;
	}

	public TextField getDescriptionTextField() {
		return descriptionTextField;
	}

	public TextField getSequence_idTextField() {
		return sequence_idTextField;
	}

	public TextField getVersion_DBTextField() {
		return version_DBTextField;
	}

	public TextField getSequence_nameTextField() {
		return sequence_nameTextField;
	}

	public TextField getRand_sequenceTextField() {
		return rand_sequenceTextField;
	}

	public TextField getPrefixTextField() {
		return prefixTextField;
	}

	public TextField getRand_typeTextField() {
		return rand_typeTextField;
	}

	public TextField setRand_typeTextField() {
		return rand_typeTextField;
	}

	public TextField getPositions_PathTextField() {
		return positions_pathTextFiled;
	}

	public TextField setPositions_PathTextField() {
		return positions_pathTextFiled;
	}

	public void createAndShowStage(FilesTable filesTable, File file) {
		// TODO Auto-generated method stub

		if (file != null) {
			this.setDefValue(file);
		}

		AdditionalWindowController controller = new AdditionalWindowController(filesTable, this, file);
		stage = new Stage();
		stage.setScene(new Scene(this, 800, 500));
		stage.show();
	}

	public void setDefValue(File file) {

		this.getIdDBComboBox().setValue(file.getId_DB().toString());
		this.getNameTextField().setText(file.getName());
		this.getDescriptionTextField().setText(file.getDescription());
		this.getSequence_idTextField().setText(file.getSequence_id());
		this.getVersion_DBTextField().setText(file.getVersion_DB());
		this.getSequence_nameTextField().setText(file.getSequence_name());
		this.getRand_sequenceTextField().setText(file.getRand_sequence().toString());
		this.getPrefixTextField().setText(file.getPrefix());
		this.getRand_typeTextField().setText(file.getRand_type().toString());
		this.getPositions_PathTextField().setText(file.getDstPath());

	}

	public File getfile() {
		return file;
	}

	public ProgressIndicator createProgressIndicator() {

		Text txtState = new Text();
		ProgressIndicator pind = new ProgressIndicator(0);
		pind.indeterminateProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
				// TODO Auto-generated method stub
				if (t1)
					txtState.setText("Calculating Time");
				else
					txtState.setText("In Progress");
			}
		});
		return pind;
	}

	public void setProgressBar(float value) {

		NumberFormat formatter = NumberFormat.getInstance(Locale.US);
		formatter.setMaximumFractionDigits(2);
		formatter.setMinimumFractionDigits(2);
		formatter.setRoundingMode(RoundingMode.HALF_UP);
		Float formatedFloat = new Float(value);
		progressBar.setProgress(formatedFloat.floatValue());
	}

	public TextInputControl getProgressTextField() {
		// TODO Auto-generated method stub
		return progressTextField;
	}

	public ProgressBar getProgressBar() {
		// TODO Auto-generated method stub
		return progressBar;
	}

	public Stage createProgressBar() {

		Stage s = new Stage();
		s.setTitle("Processing...");

		progressBar = new ProgressBar(0.6);
		progressTextField = new TextField();
		GridPane grid = new GridPane();

		grid.setAlignment(Pos.CENTER);
		grid.setHgap(50);
		grid.setVgap(10);
		grid.setPadding(new Insets(5, 5, 5, 5));
		// grid.add(progressBar, 0, 1);
		grid.add(progressTextField, 0, 0);
		Scene scene = new Scene(grid, 500, 500);

		s.setScene(scene);
		return s;
	}

}
