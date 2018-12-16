package model.dataBase;

import javafx.collections.ObservableList;
import model.File;
import model.Model;
import model.Rec;

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
	public void addRecord(Rec rec) {

		RecordsTableDBManager.getInstance().addRecord(rec);
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
	public void editRecord(Rec rec) {
		RecordsTableDBManager.getInstance().editRecord(rec);
	}

	@Override
	public void editFile(File file) {

		FilesTableDBManager.getInstance().editFile(file);
	}

	@Override
	public Rec getRecord(long idx) {
		return RecordsTableDBManager.getInstance().getRecord(idx);
	}

	@Override
	public ObservableList<Rec> getAllRecords() {

		ObservableList<Rec> recs = RecordsTableDBManager.getInstance().getAllRecords();
		return recs;
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
