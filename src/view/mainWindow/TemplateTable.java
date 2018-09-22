package view.mainWindow;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.VBox;
import model.Record;

public abstract class TemplateTable extends VBox {

	protected ScrollPane scrollPane;

	protected TableColumn<Record, String> idColumn;
	protected TableColumn<Record, String> nameColumn;

	public abstract void createTable();

}
