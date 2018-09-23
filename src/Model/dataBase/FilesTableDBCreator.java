package model.dataBase;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.File;

public class FilesTableDBCreator {

	Connection connection = null; // obiekt reprezentujacy polaczenie z baza
	DatabaseMetaData dbmd = null; // obiekt przechowujacy informacje o bazie danych
	Statement statement = null; // obiekt wykorzystywany do zapytan do bazy danych
	ResultSet result = null; // obiekt zawierajacy wyniki zapytania do bazy danych

	private static FilesTableDBCreator instance = null;

	/**
	 * Singleton dla klasy bazy danych.
	 * 
	 * @return instance instancja klasy bazy danych
	 * 
	 */
	public static FilesTableDBCreator getInstance() {
		if (instance == null) {
			instance = new FilesTableDBCreator();
		}
		return instance;

	}

	public void createTable(Statement statement) throws SQLException {

		statement.execute("CREATE TABLE pliki" + "(" + "name				VARCHAR(256) NOT NULL,"
				+ "info		VARCHAR(256) NOT NULL,"
				+ "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)" + ")");

		System.out.println("Utworzono tabele pliki");
	}

	public void addFile(File file) {
		// TODO Auto-generated method stub

		System.out.println("FilesTableDBModel.addFile");
		try {
			statement.execute("INSERT INTO pliki (name," + "info) " + "VALUES('" + file.getFileName() + "', '"
					+ file.getFileInfo() + ")");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void editFile(File file) {
		// TODO Auto-generated method stub

		System.out.println("FilesTableDBModel.editFile");
		try {
			statement.execute("UPDATE pliki " + "SET name='" + file.getFileName() + "'," + "info='" + file.getFileInfo()
					+ " " + "WHERE id = " + file.getFileId());
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

	public File getFile(int idx) {
		// TODO Auto-generated method stub
		System.out.println("FilesTableDBModel.getFile");
		// TODO Auto-generated method stub
		File file = null;
		try {
			ResultSet rs = statement.executeQuery("SELECT * FROM pliki WHERE id=" + Integer.toString(idx));

			if (rs.next()) {
				String name = rs.getString("name");
				String info = rs.getString("info");
				long id = Long.parseLong(rs.getString("id"));

				file = new File(id, name, info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;

	}

	public void getAllFiles() {

	}

}
