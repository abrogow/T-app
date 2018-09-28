package model.dataBase;

import javafx.collections.ObservableList;
import model.File;
import model.Model;
import model.Record;

public class DataBaseModel extends Model {

	private static DataBaseModel instance = null;
	private RecordsTableDBManager recordsDB;
	private FilesTableDBManager filesDB;

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

		recordsDB = new RecordsTableDBManager();
		recordsDB.addRecord(record);

	}

	@Override
	public void addFile(File file) {

		FilesTableDBManager.getInstance().addFile(file);
	}

	@Override
	public void removeRecord(int idx) {
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
	public Record getRecord(int idx) {
		return RecordsTableDBManager.getInstance().getRecord(idx);
	}

	@Override
	public File getFIle(int idx) {

		return FilesTableDBManager.getInstance().getFile(idx);
	}

	@Override
	public ObservableList<Record> getAllRecords() {

		ObservableList<Record> records = RecordsTableDBManager.getInstance().getAllRecords();
		return records;
	}

	@Override
	public void getAllFiles() {

		FilesTableDBManager.getInstance().getAllFiles();
	}

}
