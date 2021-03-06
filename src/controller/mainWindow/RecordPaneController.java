package controller.mainWindow;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import model.FastaRecord;
import model.FastaFile;
import model.reader.FastaIndexBuilder;
import model.reader.FastaReader;
import model.reader.FastaRecordParser;
import model.tools.FileTools;
import model.web.WebReferencer;
import model.writer.FastaWriter;
import view.mainWindow.RecordPane;
import view.mainWindow.RecordsTable;

public class RecordPaneController {

	private RecordsTable recordsTable;
	private RecordPane recordPane;
	private RecordsTableController recordTableController;

	private Map<String, Long> idHashMap = null;
	FastaRecord fastaRecord;
	private FastaFile fastaFile;
	private String srcPath;

	public RecordPaneController(RecordsTable recordsTable, RecordPane recordPane,
			RecordsTableController recordTableController) {

		this.recordsTable = recordsTable;
		this.recordPane = recordPane;
		this.recordTableController = recordTableController;
		initializeHandlers();

	}

	private void initializeHandlers() {

		initializeSaveButton();
		initializeCancelButton();
		initializeWebButton();
	}

	private void initializeSaveButton() {

		recordPane.getSaveButton().setOnAction((event) -> {
			try {
				saveRecord();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			reloadFields();
			recordPane.getRecordPane().setDisable(true);
		});
	}

	private void initializeCancelButton() {

		recordPane.getCancelButton().setOnAction((event) -> {

			reloadFields();
			recordPane.getRecordPane().setDisable(true);

		});
	}

	private void initializeWebButton() {

		recordPane.getWebButton().setOnAction((event) -> {

			String id = recordPane.getIdTextField().getText();
			fastaFile = recordTableController.getFile();
			String dbType = fastaFile.getId_DB();
			try {
				WebReferencer.openURL(dbType, id);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});
	}

	private void reloadFields() {

		recordPane.getIdTextField().setText("");
		recordPane.getNameTextField().setText("");
		recordPane.getSequenceTextField().setText("");

	}

	private void loadFields() {

		fastaRecord = recordTableController.getFastaRecord();
		recordPane.getIdTextField().setText(fastaRecord.getIdentyfier());
		recordPane.getNameTextField().setText(fastaRecord.getEnteryName());
		recordPane.getSequenceTextField().setText(fastaRecord.getSequenceVersion());
	}

	private void getNewRecord() {

		String id = recordPane.getIdTextField().getText();
		String name = recordPane.getNameTextField().getText();
		String sequence = recordPane.getSequenceTextField().getText();

		fastaRecord.setIdentifier(id);
		fastaRecord.setEnteryName(name);
		fastaRecord.setSequence(sequence);
	}

	private void saveRecord() throws IOException {

		fastaRecord = recordTableController.getFastaRecord();
		fastaFile = recordTableController.getFile();
		srcPath = fastaFile.getDstPath();
		String fileName = "";
		StringBuilder recordString = new StringBuilder();

		FastaRecordParser parser = FastaRecordParser.getInstance(fastaFile.getId_DB());
		FastaReader reader = new FastaReader(srcPath, parser);
		FastaIndexBuilder indexBuilder = new FastaIndexBuilder(srcPath, parser);
		reader.readIndex();
		this.idHashMap = reader.getIdHashMap();
		reader.setPositionsListFromIdHashMap(idHashMap);
		reader.setPositionsMapFromPositionsList();
		reader.setRaf();

		Long startPos = reader.getStartPos(fastaRecord);
		Long endPos = reader.getEndPos(startPos);

		FastaWriter fastaWriter = FastaWriter.getInstance(fastaFile.getId_DB());
		;

		getNewRecord();

		String lineSeparator = FileTools.getLineSeparator(srcPath);
		recordString.append(fastaWriter.getDescLine(fastaRecord));
		recordString.append(lineSeparator);
		recordString.append(fastaWriter.getSequenceLine(fastaRecord));
		recordString.append(lineSeparator);

		// replace record and update file
		fastaWriter.replaceRecordAndUpdateFile(recordString.toString(), srcPath, Math.toIntExact(startPos),
				Math.toIntExact(endPos));

		// update hashmaps
		indexBuilder.buildIndex();

		// writer.closeFile();

	}
}
