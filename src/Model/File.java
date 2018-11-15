package model;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import model.reader.Reader;

public class File {

	private SimpleLongProperty id;
	private SimpleStringProperty name;
	private SimpleStringProperty description;
	private SimpleStringProperty id_DB;
	private SimpleStringProperty version_DB;
	private SimpleStringProperty sequence_id;
	private SimpleStringProperty sequence_name;
	private SimpleLongProperty rand_sequence;
	private SimpleStringProperty prefix;
	private SimpleLongProperty rand_type;
	private SimpleStringProperty positions_path;
	private Reader reader;

	public File(String name, String description, String id_DB, String version_DB, String sequence_id,
			String sequence_name, Long rand_sequence, String prefix, Long rand_type, String positions_path) {

		this.name = new SimpleStringProperty(name);
		this.description = new SimpleStringProperty(description);
		this.id_DB = new SimpleStringProperty(id_DB);
		this.version_DB = new SimpleStringProperty(version_DB);
		this.sequence_id = new SimpleStringProperty(sequence_id);
		this.sequence_name = new SimpleStringProperty(sequence_name);
		this.rand_sequence = new SimpleLongProperty(rand_sequence);
		this.prefix = new SimpleStringProperty(prefix);
		this.rand_type = new SimpleLongProperty(rand_type);
		this.positions_path = new SimpleStringProperty(positions_path);
		this.reader = null;
	}

	public File(Long id, String name, String description, String id_DB, String version_DB, String sequence_id,
			String sequence_name, Long rand_sequence, String prefix, Long rand_type, String positions_path) {

		super();
		this.id = new SimpleLongProperty(id);
		this.name = new SimpleStringProperty(name);
		this.description = new SimpleStringProperty(description);
		this.id_DB = new SimpleStringProperty(id_DB);
		this.version_DB = new SimpleStringProperty(version_DB);
		this.sequence_id = new SimpleStringProperty(sequence_id);
		this.sequence_name = new SimpleStringProperty(sequence_name);
		this.rand_sequence = new SimpleLongProperty(rand_sequence);
		this.prefix = new SimpleStringProperty(prefix);
		this.rand_type = new SimpleLongProperty(rand_type);
		this.positions_path = new SimpleStringProperty(positions_path);
		this.reader = null;
	}

	public File() {

	}

	public Object[] toArray() {
		return new Object[] { id, name, description, id_DB, version_DB, sequence_id, sequence_name, rand_sequence,
				prefix, rand_type, positions_path };

	}

	public Long getFileId() {
		return id.get();
	}

	public void setFileId(Long id) {
		this.id.set(id);
	}

	public String getId_DB() {
		return id_DB.get();
	}

	public void setId_DB(String id_DB) {
		this.id_DB.set(id_DB);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public String getDescription() {
		return description.get();
	}

	public void setDescription(String description) {
		this.description.set(description);
	}

	public String getSequence_id() {
		return sequence_id.get();
	}

	public void setSequence_id(String sequence_id) {
		this.sequence_id.set(sequence_id);
	}

	public String getVersion_DB() {
		return version_DB.get();
	}

	public void setVersion_DB(String version_DB) {
		this.version_DB.set(version_DB);
	}

	public String getSequence_name() {
		return sequence_name.get();
	}

	public void setSequence_name(String sequence_name) {
		this.sequence_name.set(sequence_name);
	}

	public Long getRand_sequence() {
		return rand_sequence.get();
	}

	public void setRand_sequence(Long rand_sequence) {
		this.rand_sequence.set(rand_sequence);
	}

	public String getPrefix() {
		return prefix.get();
	}

	public void setPrefix(String prefix) {
		this.prefix.set(prefix);
	}

	public Long getRand_type() {
		return rand_type.get();
	}

	public void setRand_type(Long rand_type) {
		this.rand_type.set(rand_type);
	}

	public String getPositions_path() {
		return positions_path.get();
	}

	public void setPositions_path(String positions_path) {
		this.positions_path.set(positions_path);
	}

	// public static Reader createReader() {
	// if (UNIPROT_READER.equals(id_DB))
	// return new UniprotReader(addEditFileWindow);
	// else
	// return null;
	// }
}
