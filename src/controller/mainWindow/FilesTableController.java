package controller.mainWindow;

import java.io.IOException;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableRow;
import model.FastaFile;
import model.reader.FastaReader;
import model.reader.FastaRecordParser;
import view.mainWindow.FilesTable;
import view.mainWindow.Record;
import view.mainWindow.RecordsTable;

public class FilesTableController {

	private FilesTable filesTable;
	private RecordsTable recordsTable;
	private FastaFile fastaFile;
	private String dstPath;
	private Map<String, Long> idHashMap = null;
	private ObservableList<Record> data = FXCollections.observableArrayList();
	private Record record;

	public FilesTableController(FilesTable filesTable, RecordsTable recordsTable) {

		this.recordsTable = recordsTable;
		this.filesTable = filesTable;
		initializeHandlers();
	}

	private void initializeHandlers() {

		filesTable.getFilesTable().setRowFactory(tv -> {
			TableRow<FastaFile> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					try {
						updateTableView(row);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			return row;
		});
	}

	private void updateTableView(TableRow<FastaFile> row) throws IOException {

		fastaFile = row.getItem();
		filesTable.setFile(fastaFile);
		dstPath = fastaFile.getDstPath();
		data.clear();

		FastaRecordParser parser = FastaRecordParser.getInstance(fastaFile.getId_DB());

		FastaReader reader = new FastaReader(dstPath, parser);
		reader.readIndex();
		this.idHashMap = reader.getIdHashMap();

		for (String key : idHashMap.keySet()) {

			record = new Record(key);
			data.add(record);
		}
		// data.add("rec");

		recordsTable.updateTableView(data, fastaFile);

	}
}
