package Model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Record {

	private LongProperty recordId;
	private StringProperty recordName;
	private StringProperty recordInfo;
	private StringProperty recordSequence;

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

	/**
	 * @return id
	 */
	public long getRecordId() {
		return recordId.get();
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
