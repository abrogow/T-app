package view.mainWindow;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.File;
import model.dataBase.DataBaseModel;

public class FilesTable extends VBox {

	private TableColumn<File, String> pathColumn;
	protected TableColumn<File, String> idColumn;
	protected TableColumn<File, String> nameColumn;
	private TableView<File> filesTable;
	private ObservableList<File> data = FXCollections.observableArrayList();
	protected ScrollPane scrollPane;
	private File file;

	public FilesTable() {

		createTable();
		updateTableView();
	}

	public void createTable() {

		idColumn = new TableColumn<File, String>("Id");
		idColumn.setPrefWidth(70);
		nameColumn = new TableColumn<File, String>("Nazwa pliku");
		nameColumn.setPrefWidth(200);
		pathColumn = new TableColumn<File, String>("Scie¿ka do pozycji rekordów");
		pathColumn.setPrefWidth(230);

		filesTable = new TableView<File>();
		filesTable.getColumns().addAll(idColumn, nameColumn, pathColumn);
		filesTable.setPrefSize(500, 900);
		filesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		idColumn.setCellValueFactory(new PropertyValueFactory<File, String>("fileId"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<File, String>("name"));
		pathColumn.setCellValueFactory(new PropertyValueFactory<File, String>("dstPath"));
		;
		this.getChildren().addAll(filesTable);
		this.setPrefSize(450, 900);
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
		// filesTable.setItems(DataBaseModel.getInstance().getAllFiles());
		// recordsTable.setItems(DataBaseModel.getInstance().getAllRecords());
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	public File getFile() {
		return file;
	}

}
