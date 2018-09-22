package Model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RecordsTableDBModel {

	Connection connection = null; // obiekt reprezentujacy polaczenie z baza
	DatabaseMetaData dbmd = null; // obiekt przechowujacy informacje o bazie danych
	Statement statement = null; // obiekt wykorzystywany do zapytan do bazy danych
	ResultSet result = null; // obiekt zawierajacy wyniki zapytania do bazy danych

	private static RecordsTableDBModel instance = null;

	/**
	 * Singleton dla klasy bazy danych.
	 * 
	 * @return instance instancja klasy bazy danych
	 * 
	 */
	public static RecordsTableDBModel getInstance() {
		if (instance == null) {
			instance = new RecordsTableDBModel();
		}
		return instance;

	}

	public RecordsTableDBModel() {

	}

	public void createTable(ResultSet result, DatabaseMetaData dbmd, Statement statement) throws SQLException {

		// utworzenie tabeli plików (jezeli jeszcze nie istniala)
		result = dbmd.getTables(null, null, "PLIKI", null);

		if (!result.next()) {
			statement.execute("CREATE TABLE rekordy" + "(" + "name				VARCHAR(256) NOT NULL,"
					+ "description		VARCHAR(256) NOT NULL," + "id_DB				INT NOT NULL,"
					+ "version_DB			VARCHAR(256) NOT NULL," + "sequence_id		VARCHAR(256) NOT NULL,"
					+ "sequence_name		VARCHAR(256) NOT NULL," + "rand_sequence		INT NOT NULL,"
					+ "prefix				VARCHAR(256) NOT NULL," + "rand_type			INT NOT NULL,"
					+ "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)" + ")");

			System.out.println("Utworzono tabele rekordy");
		} else {
			System.out.println("Tabela rekordów juz istnieje");
			// usuwanie tabeli
			// statement.execute( "DROP TABLE pliki");
		}

	}
}
