package model.dataBase;

import model.File;
import model.Model;
import model.Record;

public class DataBaseModel extends Model {

	private static DataBaseModel instance = null;

	public static DataBaseModel getInstance() {
		if (instance == null) {
			instance = new DataBaseModel();
		}
		return instance;
	}

	public void DataBaseModel() {

		DataBaseConnection dbConnection = new DataBaseConnection();
		dbConnection.createDataBase();
	}

	@Override
	public void addRecord(Record record) {
		RecordsTableDBCreator.getInstance().addRecord(record);
	}

	@Override
	public void addFile(File file) {

		FilesTableDBCreator.getInstance().addFile(file);
	}

	@Override
	public void removeRecord(int idx) {
		RecordsTableDBCreator.getInstance().removeRecord(idx);
	}

	@Override
	public void removeFile(int idx) {

		FilesTableDBCreator.getInstance().removeFile(idx);
	}

	@Override
	public void editRecord(Record record) {
		RecordsTableDBCreator.getInstance().editRecord(record);
	}

	@Override
	public void editFile(File file) {

		FilesTableDBCreator.getInstance().editFile(file);
	}

	@Override
	public Record getRecord(int idx) {
		return RecordsTableDBCreator.getInstance().getRecord(idx);
	}

	@Override
	public File getFIle(int idx) {

		return FilesTableDBCreator.getInstance().getFile(idx);
	}

	@Override
	public void getAllRecords() {

		RecordsTableDBCreator.getInstance().getAllRecords();
	}

	@Override
	public void getAllFiles() {

		FilesTableDBCreator.getInstance().getAllFiles();
	}

}
