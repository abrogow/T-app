package model;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Record {

	private SimpleLongProperty recordId;
	private SimpleStringProperty recordName;
	private SimpleStringProperty recordInfo;
	private SimpleStringProperty recordSequence;

	/**
	 * Konstruktor
	 * 
	 * @param id
	 * @param nazwa
	 * @param opis
	 * @param sekwencja
	 */
	public Record(long recordId, String recordName, String recordInfo, String recordSequence) {
		this.recordId = new SimpleLongProperty(recordId);
		this.recordName = new SimpleStringProperty(recordName);
		this.recordInfo = new SimpleStringProperty(recordInfo);
		this.recordSequence = new SimpleStringProperty(recordSequence);

	}

	public Object[] toArray() {
		return new Object[] { recordId, recordName, recordInfo, recordSequence };

	}

	/**
	 * @return id
	 */
	public SimpleLongProperty getRecordId() {
		return recordId;
	}

	/**
	 * @param id
	 */
	public void setRecordId(long recordId) {
		this.recordId.set(recordId);
	}

	/**
	 * @return nazwa
	 */
	public String getRecordName() {
		return recordName.get();
	}

	/**
	 * @param nazwa
	 */
	public void setRecordName(String recordName) {
		this.recordName.set(recordName);
	}

	/**
	 * @return opis
	 */
	public String getRecordInfo() {
		return recordInfo.get();
	}

	/**
	 * @param opis
	 */
	public void setRecordInfo(String recordInfo) {
		this.recordInfo.set(recordInfo);
	}

	/**
	 * @return sekwencja
	 */
	public String getRecordSequence() {
		return recordSequence.get();
	}

	/**
	 * @param sekwencja
	 */
	public void setRecordSequence(String recordSequence) {
		this.recordSequence.set(recordSequence);
	}

}
