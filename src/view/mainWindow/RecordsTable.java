package view.mainWindow;

import Model.Record;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;

public class RecordsTable extends TemplateTable {

	private TableView<Record> recordsTable;

	public RecordsTable() {

		createTable();
	}

	public void createTable() {

		idColumn = new TableColumn<Record, String>("Id");
		nameColumn = new TableColumn<Record, String>("Nazwa rekordu");

		recordsTable = new TableView<Record>();
		recordsTable.getColumns().addAll(idColumn, nameColumn);
		recordsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		recordsTable.setPrefSize(300, 100);

		scrollPane = new ScrollPane();
		scrollPane.setContent(recordsTable);
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		this.setVgrow(scrollPane, Priority.ALWAYS);
		this.getChildren().addAll(scrollPane);
	}

}
