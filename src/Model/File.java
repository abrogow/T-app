package model;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class File {

	private SimpleLongProperty fileId;
	private SimpleStringProperty fileName;
	private SimpleStringProperty fileInfo;

	/**
	 * Konstruktor
	 * 
	 * @param id
	 * @param nazwa
	 * @param opis
	 */
	public File(long fileId, String fileName, String fileInfo) {

		this.fileId = new SimpleLongProperty(fileId);
		this.fileName = new SimpleStringProperty(fileName);
		this.fileInfo = new SimpleStringProperty(fileInfo);

	}

	/**
	 * @return id
	 */
	public SimpleLongProperty getFileId() {
		return fileId;
	}

	/**
	 * @param id
	 */
	public void setFileId(long fileId) {
		this.fileId.set(fileId);
	}

	/**
	 * @return name
	 */
	public SimpleStringProperty getFileName() {
		return fileName;
	}

	/**
	 * @param name
	 */
	public void setFileName(String fileName) {
		this.fileName.set(fileName);
	}

	/**
	 * @return info
	 */
	public SimpleStringProperty getFileInfo() {
		return fileInfo;
	}

	/**
	 * @param info
	 */
	public void setFileInfo(String fileInfo) {
		this.fileInfo.set(fileInfo);
	}
}
