package view.additionalWindows;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import controller.additionalWindows.AdditionalWindowController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.FastaFile;
import view.mainWindow.FilesTable;

public class AddEditFileWindow extends GridPane {

	private Label nameLabel, descriptionLabel, id_DBLabel, version_DBLabel, sequence_idLabel, sequence_nameLabel,
			rand_sequenceLabel, prefixLabel, rand_typeLabel, positions_pathLabel;;

	private TextField nameTextField, descriptionTextField, version_DBTextField, sequence_idTextField,
			sequence_nameTextField, rand_sequenceTextField, prefixTextField, rand_typeTextField,
			positions_pathTextFiled;

	private ComboBox id_DBComboBox;

	private Button loadButton;
	private Button saveButton;
	private Button cancelButton;

	private Stage stage;
	private FastaFile fastaFile;
	private FilesTable filesTable;

	private ProgressBar progressBar;
	private TextField progressTextField;
	private Parent root;

	private Scene scene;

	private final List<String> dataBaseType = Arrays.asList("UniProt", "NCBI", "IPI", "TAIR", "SGD");

	public AddEditFileWindow() {

		createControls();
		addControls();
		setProperties();
		configureGrid();

		this.scene = new Scene(this, 800, 500);
	}

	public void createControls() {

		nameLabel = new Label("Nazwa pliku FASTA");
		descriptionLabel = new Label("Opis pliku");
		id_DBLabel = new Label("Identyfikator bazy danych ");
		version_DBLabel = new Label("Wersja bazy danych");
		sequence_idLabel = new Label("Wyra�enie regularne identyfikator�w sekwencji w linii opisu");
		sequence_nameLabel = new Label("Wyra�enie regularne nazw sekewncji w linii opisu");
		rand_sequenceLabel = new Label("Infoormacja czy plik zawiera sekwencje randomizowane");
		prefixLabel = new Label("Przedrostek identyfikator�w sekwencji randomizowanych");
		rand_typeLabel = new Label("Rodzaj randomizacji");
		positions_pathLabel = new Label("�cie�ka do pliku");

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
		saveButton = new Button("Zapisz");
		cancelButton = new Button("Anuluj");
	}

	public void addControls() {

		this.add(nameLabel, 0, 2);
		this.add(descriptionLabel, 0, 3);
		this.add(id_DBLabel, 0, 1);
		this.add(version_DBLabel, 0, 4);
		this.add(sequence_idLabel, 0, 5);
		this.add(sequence_nameLabel, 0, 6);
		this.add(rand_sequenceLabel, 0, 7);
		this.add(prefixLabel, 0, 8);
		this.add(rand_typeLabel, 0, 9);
		this.add(positions_pathLabel, 0, 10);

		this.add(nameTextField, 1, 2);
		this.add(descriptionTextField, 1, 3);
		this.add(id_DBComboBox, 1, 1);
		this.add(version_DBTextField, 1, 4);
		this.add(sequence_idTextField, 1, 5);
		this.add(sequence_nameTextField, 1, 6);
		this.add(rand_sequenceTextField, 1, 7);
		this.add(prefixTextField, 1, 8);
		this.add(rand_typeTextField, 1, 9);
		this.add(positions_pathTextFiled, 1, 10);

		this.add(loadButton, 2, 1);
		this.add(saveButton, 0, 11);
		this.add(cancelButton, 1, 11);

	}

	public void configureGrid() {
		// TODO Auto-generated method stub
		this.setAlignment(Pos.CENTER);
		this.setHgap(50);
		this.setVgap(10);
		this.setPadding(new Insets(5, 5, 5, 5));
	}

	public void setProperties() {
		// TODO Auto-generated method stub
		this.id_DBComboBox.setPrefWidth(200);

	}

	public Button getLoadButton() {
		return loadButton;
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

	public void createAndShowStage(FilesTable filesTable, FastaFile fastaFile) {
		// TODO Auto-generated method stub

		this.setDefValue(fastaFile);

		AdditionalWindowController controller = new AdditionalWindowController(filesTable, this, fastaFile);
		stage = new Stage();
		stage.setTitle("Dodawanie/Edycja pliku");
		stage.setScene(this.scene);
		stage.initModality(Modality.APPLICATION_MODAL);
		// stage.initOwner(aboutButton.getScene().getWindow());
		stage.showAndWait();
	}

	public void setDefValue(FastaFile fastaFile) {

		if (fastaFile != null) {
			this.getIdDBComboBox().setValue(fastaFile.getId_DB().toString());
			this.getNameTextField().setText(fastaFile.getName());
			this.getDescriptionTextField().setText(fastaFile.getDescription());
			this.getSequence_idTextField().setText(fastaFile.getSequence_id());
			this.getVersion_DBTextField().setText(fastaFile.getVersion_DB());
			this.getSequence_nameTextField().setText(fastaFile.getSequence_name());
			this.getRand_sequenceTextField().setText(fastaFile.getRand_sequence().toString());
			this.getPrefixTextField().setText(fastaFile.getPrefix());
			this.getRand_typeTextField().setText(fastaFile.getRand_type().toString());
			this.getPositions_PathTextField().setText(fastaFile.getDstPath());
		} else {
			this.getIdDBComboBox().getSelectionModel().clearSelection();
			this.getNameTextField().setText("");
			this.getDescriptionTextField().setText("");
			this.getSequence_idTextField().setText("");
			this.getVersion_DBTextField().setText("");
			this.getSequence_nameTextField().setText("");
			this.getRand_sequenceTextField().setText("");
			this.getPrefixTextField().setText("");
			this.getRand_typeTextField().setText("");
			this.getPositions_PathTextField().setText("");
		}

	}

	public FastaFile getfile() {
		return fastaFile;
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
