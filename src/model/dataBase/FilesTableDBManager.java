package model.dataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FastaFile;

public class FilesTableDBManager {

	Statement statement = null;
	private ObservableList<FastaFile> fastaFiles;

	private static FilesTableDBManager instance = null;

	/**
	 * Singleton dla klasy bazy danych.
	 * 
	 * @return instance instancja klasy bazy danych
	 * 
	 */

	public static FilesTableDBManager getInstance() {
		if (instance == null) {
			instance = new FilesTableDBManager();
		}
		return instance;

	}

	public FilesTableDBManager() {

	}

	// do przekazania statement z DataBaseConnection
	public void setDBStatement(Statement s) {
		this.statement = s;
	}

	public void createTable() throws SQLException {

		statement.execute("CREATE TABLE pliki" + "(" + "name				VARCHAR(256),"
				+ "description		VARCHAR(256)," + "id_DB		VARCHAR(256)," + "version_DB		VARCHAR(256),"
				+ "sequence_id		VARCHAR(256)," + "sequence_name		VARCHAR(256)," + "rand_sequence		INT,"
				+ "prefix		VARCHAR(256)," + "rand_type		INT," + "positions_path		VARCHAR(256),"
				+ "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)" + ")");
	}

	public void addFile(FastaFile fastaFile) {

		try {
			statement.execute(
					"INSERT INTO pliki (name, description, id_DB, version_DB,sequence_id, sequence_name,rand_sequence,"
							+ "prefix, rand_type, positions_path) " + " VALUES('" + fastaFile.getName() + "', '"
							+ fastaFile.getDescription() + "', '" + fastaFile.getId_DB() + "', '" + fastaFile.getVersion_DB() + "', '"
							+ fastaFile.getSequence_id() + "', '" + fastaFile.getSequence_name() + "', " + fastaFile.getRand_sequence()
							+ ", '" + fastaFile.getPrefix() + "', " + fastaFile.getRand_type() + ", '" + fastaFile.getDstPath()
							+ "')");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void editFile(FastaFile fastaFile) {

		try {
			statement.execute("UPDATE pliki " + "SET name='" + fastaFile.getName() + "'," + "description='"
					+ fastaFile.getDescription() + "'," + "id_DB='" + fastaFile.getId_DB() + "'," + "version_DB='"
					+ fastaFile.getVersion_DB() + "'," + "sequence_id='" + fastaFile.getSequence_id() + "'," + "sequence_name='"
					+ fastaFile.getSequence_name() + "'," + "rand_sequence=" + fastaFile.getRand_sequence() + "," + "prefix='"
					+ fastaFile.getPrefix() + "'," + "rand_type=" + fastaFile.getRand_type() + ", " + "positions_path='"
					+ fastaFile.getDstPath() + " WHERE id = " + fastaFile.getFileId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeFile(int idx) {

		try {
			statement.execute("DELETE FROM pliki WHERE id =" + Integer.toString(idx));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FastaFile getFile(long idx) {

		FastaFile fastaFile = null;
		try {
			ResultSet rs = statement.executeQuery("SELECT * FROM pliki WHERE id=" + Long.toString(idx));

			if (rs.next()) {
				String name = rs.getString("name");
				String description = rs.getString("description");
				String id_DB = rs.getString("id_DB");
				String version_DB = rs.getString("version_DB");
				String sequence_id = rs.getString("sequence_id");
				String sequence_name = rs.getString("sequence_name");
				Long rand_sequence = Long.parseLong(rs.getString("rand_sequence"));
				String prefix = rs.getString("prefix");
				Long rand_type = Long.parseLong(rs.getString("rand_type"));
				String positions_path = rs.getString("positions_path");
				long id = Long.parseLong(rs.getString("id"));

				fastaFile = new FastaFile(id, name, description, id_DB, version_DB, sequence_id, sequence_name, rand_sequence,
						prefix, rand_type, positions_path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fastaFile;

	}

	public ObservableList<FastaFile> getAllFiles() {

		System.out.println("FilesTableDBManager.getAllFilesAsObservableList");
		fastaFiles = FXCollections.observableArrayList();

		try {
			ResultSet rs = statement.executeQuery("SELECT * FROM pliki");
			while (rs.next()) {
				String name = rs.getString("name");
				String description = rs.getString("description");
				String id_DB = rs.getString("id_DB");
				String version_DB = rs.getString("version_DB");
				String sequence_id = rs.getString("sequence_id");
				String sequence_name = rs.getString("sequence_name");
				Long rand_sequence = Long.parseLong(rs.getString("rand_sequence"));
				String prefix = rs.getString("prefix");
				Long rand_type = Long.parseLong(rs.getString("rand_type"));
				String positions_path = rs.getString("positions_path");
				long id = Long.parseLong(rs.getString("id"));

				FastaFile fastaFile = new FastaFile(id, name, description, id_DB, version_DB, sequence_id, sequence_name,
						rand_sequence, prefix, rand_type, positions_path);
				fastaFiles.add(fastaFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fastaFiles;

	}

}
