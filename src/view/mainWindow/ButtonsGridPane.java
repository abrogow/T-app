package view.mainWindow;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

public class ButtonsGridPane extends TemplateGridPane {

	private Button addButton, removeButton, editButton;

	public ButtonsGridPane() {

		createControls();
		setProperties();
		addControls();
		configureGrid();
	}

	public void createControls() {

		addButton = new Button("Dodaj");
		editButton = new Button("Edytuj");
		removeButton = new Button("Usu�");
	}

	public void addControls() {

		this.add(addButton, 0, 1);
		this.add(editButton, 1, 1);
		this.add(removeButton, 2, 1);
	}

	public void configureGrid() {

		this.setAlignment(Pos.CENTER);
		this.setHgap(50);
		this.setVgap(10);
		this.setPadding(new Insets(5, 5, 5, 5));
	}

	@Override
	public void setProperties() {
		// TODO Auto-generated method stub

	}

	public Button getAddButton() {
		return addButton;
	}

	public Button getEditButton() {
		return editButton;
	}

	public Button getRemoveButton() {
		return removeButton;
	}

	@Override
	public void createAndShowStage(RecordsTable recordsTable) {
		// TODO Auto-generated method stub

	}

}
