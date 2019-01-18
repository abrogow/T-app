package view.mainWindow;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class ButtonsPane extends GridPane {

	private Button addButton, removeButton, editButton;

	public ButtonsPane() {

		createControls();
		setProperties();
		addControls();
		configureGrid();
	}

	public void createControls() {

		addButton = new Button("Dodaj");
		editButton = new Button("Edytuj");
		removeButton = new Button("Usuñ");
	}

	public void addControls() {

		this.add(addButton, 1, 1);
		this.add(editButton, 2, 1);
		this.add(removeButton, 3, 1);
	}

	public void configureGrid() {

		this.setAlignment(Pos.BOTTOM_LEFT);
		this.setHgap(50);
		this.setVgap(10);
		this.setPadding(new Insets(5, 5, 5, 5));
	}

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

}
