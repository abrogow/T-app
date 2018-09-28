package view.additionalWindows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import view.mainWindow.TemplateGridPane;

public class CreateDBGridPane extends TemplateGridPane {

	private Button createButton, cancelButton;

	public CreateDBGridPane() {

		createControls();
		addControls();
		setProperties();
		configureGrid();
	}

	@Override
	public void createControls() {
		// TODO Auto-generated method stub
		createButton = new Button("Stwórz bazê");
		cancelButton = new Button("Anuluj");

	}

	@Override
	public void addControls() {
		// TODO Auto-generated method stub
		this.add(createButton, 0, 1);
		this.add(cancelButton, 1, 1);

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

	@Override
	public void createAndShowStage() {
		// TODO Auto-generated method stub

	}

}
