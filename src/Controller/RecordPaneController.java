package Controller;

import java.io.FileNotFoundException;
import java.util.Map;

import model.FastaRecord;
import model.File;
import model.writer.UniprotWriter;
import model.writer.Writer;
import view.mainWindow.RecordPane;
import view.mainWindow.RecordsTable;

public class RecordPaneController {

	private RecordsTable recordsTable;
	private RecordPane recordPane;
	private RecordsTableController recordTableController;

	private Map<String, Long> idHashMap = null;
	FastaRecord fastaRecord;
	private File file;
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
	}

	private void initializeSaveButton() {

		recordPane.getSaveButton().setOnAction((event) -> {
			try {
				saveRecord();
			} catch (FileNotFoundException e) {
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

		fastaRecord = recordTableController.getFastaRecord();

		fastaRecord.setIdentifier(id);
		fastaRecord.setEnteryName(name);
		fastaRecord.setSequence(sequence);
	}

	private void saveRecord() throws FileNotFoundException {

		getNewRecord();
		file = recordTableController.getFile();
		srcPath = file.getDstPath();
		String fileName = "";

		Writer writer = new UniprotWriter();
		writer.createAndOpenFile(fileName, srcPath);

		// replace record and save

		writer.closeFile();

	}
}
