package view.mainWindow;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public abstract class TemplateTable extends VBox {

	protected ScrollPane scrollPane;

	public abstract void createTable();

}
