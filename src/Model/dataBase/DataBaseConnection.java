package model.dataBase;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataBaseConnection {

	Connection connection = null; // obiekt reprezentujacy polaczenie z baza
	DatabaseMetaData dbmd = null; // obiekt przechowujacy informacje o bazie danych
	Statement statement = null; // obiekt wykorzystywany do zapytan do bazy danych
	ResultSet result = null; // obiekt zawierajacy wyniki zapytania do bazy danych

	private static DataBaseConnection instance = null;

	/**
	 * Konstruktor klasy bazy danych.
	 * 
	 * @return
	 * 
	 */
	public DataBaseConnection() {

	}

	public void createDataBase() {

		try {

			// wczytanie sterownika bazy danych (tryb dostepu Embedded)
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			System.out.println("Sterownik bazy danych zostal uruchominy\n");

			// utworzenie polaczenia z baza danych
			connection = DriverManager.getConnection("jdbc:derby:Fasta_DB;create=true");
			System.out.println("Utworzono polaczenia z baza danych FASTA_DB");

			// pobranie informacji o bazie danych i utworzenie obiektu zapytania
			dbmd = connection.getMetaData();
			statement = connection.createStatement();

			// przekazuje statement do klas
			RecordsTableDBManager.getInstance().setDBStatement(statement);
			FilesTableDBManager.getInstance().setDBStatement(statement);

			// tworzenie tabeli rekordy
			result = dbmd.getTables(null, null, "REKORDY", null);
			if (!result.next()) {

				RecordsTableDBManager.getInstance().createTable();

			} else {
				System.out.println("Tabela rekordów juz istnieje");
				// usuwanie tabeli
				// statement.execute( "DROP TABLE REKORDY");
			}

			result = dbmd.getTables(null, null, "PLIKI", null);
			if (!result.next()) {

				FilesTableDBManager.getInstance().createTable();

			} else {
				System.out.println("Tabela plików juz istnieje");
				// usuwanie tabeli
				// statement.execute( "DROP TABLE PLIKI");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
