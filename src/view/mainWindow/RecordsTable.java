package view.mainWindow;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Record;
import model.dataBase.DataBaseModel;

public class RecordsTable extends VBox {

	private TableView<Record> recordsTable;
	private TableColumn<Record, String> idColumn;
	private TableColumn<Record, String> nameColumn;
	protected ScrollPane scrollPane;

	private ObservableList<Record> data = FXCollections.observableArrayList();

	// private ObservableList<Record> data;

	public RecordsTable() {

		createTable();
		updateTableView();
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

		idColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("recordIdentifier"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("recordName"));

		// updateTableView();

		scrollPane = new ScrollPane();
		scrollPane.setContent(recordsTable);
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		this.setVgrow(scrollPane, Priority.ALWAYS);
		this.getChildren().addAll(scrollPane);
	}

	public TableView<Record> getRecordsTable() {

		return recordsTable;
	}

	public void updateTableView() {

		// data.add(rec1);
		System.out.println("RecordsTable.updateTableView");
		data.clear();
		data = DataBaseModel.getInstance().getAllRecords();
		recordsTable.setItems(data);
		recordsTable.refresh();
		// recordsTable.setItems(DataBaseModel.getInstance().getAllRecords());
	}
}
