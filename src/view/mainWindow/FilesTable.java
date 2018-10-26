package view.mainWindow;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import model.File;
import model.dataBase.DataBaseModel;

public class FilesTable extends TemplateTable {

	private TableColumn<File, String> pathColumn;
	protected TableColumn<File, String> idColumn;
	protected TableColumn<File, String> nameColumn;
	private TableView<File> filesTable;
	private ObservableList<File> data = FXCollections.observableArrayList();

	public FilesTable() {

		createTable();
		updateTableView();
	}

	public void createTable() {

		idColumn = new TableColumn<File, String>("Id");
		nameColumn = new TableColumn<File, String>("Nazwa pliku");
		pathColumn = new TableColumn<File, String>("Scie¿ka do pozycji rekordów");

		filesTable = new TableView<File>();
		filesTable.getColumns().addAll(idColumn, nameColumn, pathColumn);
		filesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		filesTable.setPrefSize(300, 100);

		idColumn.setCellValueFactory(new PropertyValueFactory<File, String>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<File, String>("name"));
		pathColumn.setCellValueFactory(new PropertyValueFactory<File, String>("positions_path"));

		scrollPane = new ScrollPane();
		scrollPane.setContent(filesTable);
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		this.setVgrow(scrollPane, Priority.ALWAYS);
		this.getChildren().addAll(scrollPane);
	}

	public TableView<File> getFilesTable() {

		return filesTable;
	}

	public void updateTableView() {

		System.out.println("FilesTable.updateTableView");
		data.clear();
		data = DataBaseModel.getInstance().getAllFiles();
		filesTable.setItems(data);
		filesTable.refresh();
		// recordsTable.setItems(DataBaseModel.getInstance().getAllRecords());
	}

}
