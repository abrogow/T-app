package Controller;

import java.io.IOException;
import java.util.Map;

import javafx.scene.control.TableRow;
import model.FastaRecord;
import model.File;
import model.Record;
import model.reader.FastaReader;
import model.reader.FastaRecordParser;
import model.reader.FastaUniprotRecordParser;
import view.mainWindow.FilesTable;
import view.mainWindow.RecordPane;
import view.mainWindow.RecordsTable;

public class RecordsTableController {

	private RecordsTable recordsTable;
	RecordPane recordPane;
	private FilesTable filesTable;
	private File file;
	private Map<String, Long> idHashMap = null;
	FastaRecord fastaRecord;

	public RecordsTableController(RecordsTable recordsTable, FilesTable filesTable, RecordPane recordPane) {

		this.recordsTable = recordsTable;
		this.filesTable = filesTable;
		this.recordPane = recordPane;
		initializeHandlers();
	}

	private void initializeHandlers() {

		recordsTable.getRecordsTable().setRowFactory(tv -> {
			TableRow<Record> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					try {
						recordPane.getRecordPane().setDisable(false);
						showRecord(row);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			return row;
		});

	}

	private void showRecord(TableRow<Record> row) throws IOException {

		Record record = recordsTable.getRecordsTable().getSelectionModel().getSelectedItem();
		String id = record.getFileId();
		file = filesTable.getFile();
		String dstPath = file.getDstPath();

		FastaRecordParser parser = new FastaUniprotRecordParser();

		FastaReader reader = new FastaReader(dstPath, parser);
		reader.readIndex();
		this.idHashMap = reader.getIdHashMap();

		reader.setPositionsListFromIdHashMap(idHashMap);
		reader.setPositionsMapFromPositionsList();
		reader.setRaf();
		fastaRecord = reader.getRecord(id);

		loadFields();

	}

	private void loadFields() {

		recordPane.getIdTextField().setText(fastaRecord.getIdentyfier());
		recordPane.getNameTextField().setText(fastaRecord.getEnteryName());
		recordPane.getSequenceTextField().setText(fastaRecord.getSequence());
	}

	public File getFile() {
		return file;
	}

	public FastaRecord getFastaRecord() {
		return fastaRecord;
	}
}
