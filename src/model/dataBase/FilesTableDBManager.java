package model.dataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.File;

public class FilesTableDBManager {

	Statement statement = null;
	private ObservableList<File> files;

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
				+ "description		VARCHAR(256)," + "id_DB		INT," + "version_DB		VARCHAR(256),"
				+ "sequence_id		VARCHAR(256)," + "sequence_name		VARCHAR(256)," + "rand_sequence		INT,"
				+ "prefix		VARCHAR(256)," + "rand_type		INT," + "positions_path		VARCHAR(256),"
				+ "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)" + ")");

		System.out.println("Utworzono tabele pliki");
	}

	public void addFile(File file) {
		// TODO Auto-generated method stub

		System.out.println("FilesTableDBModel.addFile");
		try {
			System.out.println(
					"INSERT INTO pliki (name, description, id_DB, version_DB,sequence_id, sequence_name,rand_sequence, prefix, rand_type, positions_path) "
							+ " VALUES('" + file.getName() + "', '" + file.getDescription() + "', " + file.getId_DB()
							+ ", '" + file.getVersion_DB() + "', '" + file.getSequence_id() + "', '"
							+ file.getSequence_name() + "', " + file.getRand_sequence() + ", '" + file.getPrefix()
							+ "', " + file.getRand_type() + ", '" + file.getPositions_path() + "')");
			statement.execute(
					"INSERT INTO pliki (name, description, id_DB, version_DB,sequence_id, sequence_name,rand_sequence, prefix, rand_type, positions_path) "
							+ " VALUES('" + file.getName() + "', '" + file.getDescription() + "', " + file.getId_DB()
							+ ", '" + file.getVersion_DB() + "', '" + file.getSequence_id() + "', '"
							+ file.getSequence_name() + "', " + file.getRand_sequence() + ", '" + file.getPrefix()
							+ "', " + file.getRand_type() + ", '" + file.getPositions_path() + "')");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void editFile(File file) {
		// TODO Auto-generated method stub

		System.out.println("FilesTableDBModel.editFile");
		try {
			statement.execute("UPDATE pliki " + "SET name='" + file.getName() + "'," + "description='"
					+ file.getDescription() + "'," + "id_DB=" + file.getId_DB() + "," + "version_DB='"
					+ file.getVersion_DB() + "'," + "sequence_id='" + file.getSequence_id() + "'," + "sequence_name='"
					+ file.getSequence_name() + "'," + "rand_sequence=" + file.getRand_sequence() + "," + "prefix='"
					+ file.getPrefix() + "'," + "rand_type=" + file.getRand_type() + ", " + "positions_path='"
					+ file.getPositions_path() + " WHERE id = " + file.getFileId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeFile(int idx) {
		// TODO Auto-generated method stub
		System.out.println("FilesTableDBModel.removeFile");

		try {
			statement.execute("DELETE FROM pliki WHERE id =" + Integer.toString(idx));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public File getFile(long idx) {
		// TODO Auto-generated method stub
		System.out.println("FilesTableDBModel.getFile");
		// TODO Auto-generated method stub
		File file = null;
		try {
			ResultSet rs = statement.executeQuery("SELECT * FROM pliki WHERE id=" + Long.toString(idx));

			if (rs.next()) {
				String name = rs.getString("name");
				String description = rs.getString("description");
				Long id_DB = Long.parseLong(rs.getString("id_DB"));
				String version_DB = rs.getString("version_DB");
				String sequence_id = rs.getString("sequence_id");
				String sequence_name = rs.getString("sequence_name");
				Long rand_sequence = Long.parseLong(rs.getString("rand_sequence"));
				String prefix = rs.getString("prefix");
				Long rand_type = Long.parseLong(rs.getString("rand_type"));
				String positions_path = rs.getString("positions_path");
				long id = Long.parseLong(rs.getString("id"));

				file = new File(id, name, description, id_DB, version_DB, sequence_id, sequence_name, rand_sequence,
						prefix, rand_type, positions_path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;

	}

	public ObservableList<File> getAllFiles() {

		System.out.println("FilesTableDBManager.getAllFilesAsObservableList");
		files = FXCollections.observableArrayList();

		try {
			ResultSet rs = statement.executeQuery("SELECT * FROM pliki");
			while (rs.next()) {
				String name = rs.getString("name");
				String description = rs.getString("description");
				Long id_DB = Long.parseLong(rs.getString("id_DB"));
				String version_DB = rs.getString("version_DB");
				String sequence_id = rs.getString("sequence_id");
				String sequence_name = rs.getString("sequence_name");
				Long rand_sequence = Long.parseLong(rs.getString("rand_sequence"));
				String prefix = rs.getString("prefix");
				Long rand_type = Long.parseLong(rs.getString("rand_type"));
				String positions_path = rs.getString("positions_path");
				long id = Long.parseLong(rs.getString("id"));

				File file = new File(id, name, description, id_DB, version_DB, sequence_id, sequence_name,
						rand_sequence, prefix, rand_type, positions_path);
				files.add(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return files;

	}

}
