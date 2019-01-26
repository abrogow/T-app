package view.mainWindow;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.FastaFile;
import model.dataBase.DataBaseModel;

public class FilesTable extends VBox {

	private TableColumn<FastaFile, String> pathColumn;
	protected TableColumn<FastaFile, String> idColumn;
	protected TableColumn<FastaFile, String> nameColumn;
	private TableView<FastaFile> filesTable;
	private ObservableList<FastaFile> data = FXCollections.observableArrayList();
	protected ScrollPane scrollPane;
	private FastaFile fastaFile;

	public FilesTable() {

		createTable();
		updateTableView();
	}

	public void createTable() {

		idColumn = new TableColumn<FastaFile, String>("Id");
		idColumn.setPrefWidth(70);
		nameColumn = new TableColumn<FastaFile, String>("Nazwa pliku");
		nameColumn.setPrefWidth(200);
		pathColumn = new TableColumn<FastaFile, String>("Scie¿ka do pozycji rekordów");
		pathColumn.setPrefWidth(230);

		filesTable = new TableView<FastaFile>();
		filesTable.getColumns().addAll(nameColumn);
		filesTable.setPrefSize(500, 900);
		filesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		// idColumn.setCellValueFactory(new PropertyValueFactory<File,
		// String>("fileId"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<FastaFile, String>("name"));
		// pathColumn.setCellValueFactory(new PropertyValueFactory<File,
		// String>("dstPath"));
		;
		this.getChildren().addAll(filesTable);
		this.setPrefSize(450, 900);
	}

	public TableView<FastaFile> getFilesTable() {

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

	public void setFile(FastaFile fastaFile) {
		this.fastaFile = fastaFile;
	}

	public FastaFile getFile() {
		return fastaFile;
	}

}
