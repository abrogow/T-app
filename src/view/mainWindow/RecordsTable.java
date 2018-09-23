package view.mainWindow;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import model.Record;

public class RecordsTable extends TemplateTable {

	private TableView<Record> recordsTable;

	private final ObservableList<Record> data = FXCollections.observableArrayList(
			new Record(1, "Smith", "jacob.smith@example.com", "jacob.smith@example.com"),
			new Record(2, "Johnson", "isabella.johnson@example.com", "jacob.smith@example.com"),
			new Record(3, "Williams", "ethan.williams@example.com", "jacob.smith@example.com"),
			new Record(4, "Jones", "emma.jones@example.com", "jacob.smith@example.com"),
			new Record(5, "Brown", "michael.brown@example.com", "jacob.smith@example.com"));

	public RecordsTable() {

		createTable();
	}

	public void createTable() {

		idColumn = new TableColumn<Record, String>("Id");
		nameColumn = new TableColumn<Record, String>("Nazwa rekordu");

		// idColumn.setCellValueFactory(cellData -> new
		// SimpleLongProperty(cellData.getValue().getRecord().getRecordId()));

		recordsTable = new TableView<Record>();
		recordsTable.getColumns().addAll(idColumn, nameColumn);
		recordsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		recordsTable.setPrefSize(300, 100);

		idColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("recordId"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("recordName"));

		recordsTable.setItems(data);

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
