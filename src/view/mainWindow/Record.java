package view.mainWindow;

import javafx.beans.property.SimpleStringProperty;

public class Record {

	private SimpleStringProperty id;

	public Record(String id) {
		this.id = new SimpleStringProperty(id);
	}

	public String getFileId() {
		return id.get();
	}

	public void setFileId(String id) {
		this.id.set(id);
	}
}
