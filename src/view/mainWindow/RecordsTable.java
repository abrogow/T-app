package view.mainWindow;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.Record;

public class RecordsTable extends VBox {

	private TableView<Record> recordsTable;
	private TableColumn<Record, String> idColumn;
	protected ScrollPane scrollPane;

	private ObservableList<Record> data = FXCollections.observableArrayList();

	// private ObservableList<Record> data;

	public RecordsTable() {

		createTable();
		updateTableView(data);
	}

	public void createTable() {

		idColumn = new TableColumn<Record, String>("Id");
		idColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("fileId"));
		idColumn.setPrefWidth(500);

		recordsTable = new TableView<Record>();
		recordsTable.getColumns().addAll(idColumn);
		recordsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		recordsTable.setPrefSize(500, 900);

		this.getChildren().addAll(recordsTable);
	}

	public TableView<Record> getRecordsTable() {

		return recordsTable;
	}

	public void updateTableView(ObservableList<Record> data) {

		recordsTable.setItems(data);
		recordsTable.refresh();
		System.out.println("RecordsTableController.updateTableView");
	}
}
