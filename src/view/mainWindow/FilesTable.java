package view.mainWindow;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import model.Record;

public class FilesTable extends TemplateTable {

	private TableView<Record> filesTable;

	public FilesTable() {

		createTable();
	}

	public void createTable() {

		idColumn = new TableColumn<Record, String>("Id");
		nameColumn = new TableColumn<Record, String>("Nazwa pliku");

		filesTable = new TableView<Record>();
		filesTable.getColumns().addAll(idColumn, nameColumn);
		filesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		filesTable.setPrefSize(300, 100);

		scrollPane = new ScrollPane();
		scrollPane.setContent(filesTable);
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		this.setVgrow(scrollPane, Priority.ALWAYS);
		this.getChildren().addAll(scrollPane);
	}

}
