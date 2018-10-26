package model.dataBase;

import javafx.collections.ObservableList;
import model.File;
import model.Model;
import model.Record;

public class DataBaseModel extends Model {

	private static DataBaseModel instance = null;
	private RecordsTableDBManager recordsDB;
	private FilesTableDBManager filesDB;
	private DataBaseConnection dbConnection;

	public static DataBaseModel getInstance() {
		if (instance == null) {
			instance = new DataBaseModel();
		}
		return instance;
	}

	public DataBaseModel() {

		DataBaseConnection dbConnection = new DataBaseConnection();
		dbConnection.createDataBase();
	}

	@Override
	public void addRecord(Record record) {

		RecordsTableDBManager.getInstance().addRecord(record);
		// recordsDB = new RecordsTableDBManager();
		// recordsDB.addRecord(record);

	}

	@Override
	public void addFile(File file) {

		FilesTableDBManager.getInstance().addFile(file);
	}

	@Override
	public void removeRecord(long idx) {
		RecordsTableDBManager.getInstance().removeRecord(idx);
	}

	@Override
	public void removeFile(int idx) {

		FilesTableDBManager.getInstance().removeFile(idx);
	}

	@Override
	public void editRecord(Record record) {
		RecordsTableDBManager.getInstance().editRecord(record);
	}

	@Override
	public void editFile(File file) {

		FilesTableDBManager.getInstance().editFile(file);
	}

	@Override
	public Record getRecord(long idx) {
		return RecordsTableDBManager.getInstance().getRecord(idx);
	}

	@Override
	public ObservableList<Record> getAllRecords() {

		ObservableList<Record> records = RecordsTableDBManager.getInstance().getAllRecords();
		return records;
	}

	@Override
	public ObservableList<File> getAllFiles() {

		ObservableList<File> files = FilesTableDBManager.getInstance().getAllFiles();
		return files;
	}

	@Override
	public File getFile(Long idx) {
		return FilesTableDBManager.getInstance().getFile(idx);
	}

}
