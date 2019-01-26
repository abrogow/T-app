package model.dataBase;

import javafx.collections.ObservableList;
import model.FastaFile;

public class DataBaseModel {

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

	public void addFile(FastaFile fastaFile) {

		FilesTableDBManager.getInstance().addFile(fastaFile);
	}

	public void removeFile(int idx) {

		FilesTableDBManager.getInstance().removeFile(idx);
	}

	public void editFile(FastaFile fastaFile) {

		FilesTableDBManager.getInstance().editFile(fastaFile);
	}

	public ObservableList<FastaFile> getAllFiles() {

		ObservableList<FastaFile> fastaFiles = FilesTableDBManager.getInstance().getAllFiles();
		return fastaFiles;
	}

	public FastaFile getFile(Long idx) {
		return FilesTableDBManager.getInstance().getFile(idx);
	}

}
