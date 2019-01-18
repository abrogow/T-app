package model.dataBase;

import javafx.collections.ObservableList;
import model.File;
import model.Model;

public class DataBaseModel extends Model {

	private static DataBaseModel instance = null;
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
	public void addFile(File file) {

		FilesTableDBManager.getInstance().addFile(file);
	}

	@Override
	public void removeFile(int idx) {

		FilesTableDBManager.getInstance().removeFile(idx);
	}

	@Override
	public void editFile(File file) {

		FilesTableDBManager.getInstance().editFile(file);
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
