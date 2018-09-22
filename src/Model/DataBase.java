package Model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataBase {

	Connection connection = null; // obiekt reprezentujacy polaczenie z baza
	DatabaseMetaData dbmd = null; // obiekt przechowujacy informacje o bazie danych
	Statement statement = null; // obiekt wykorzystywany do zapytan do bazy danych
	ResultSet result = null; // obiekt zawierajacy wyniki zapytania do bazy danych

	private static DataBase instance = null;

	/**
	 * Singleton dla klasy bazy danych.
	 * 
	 * @return instance instancja klasy bazy danych
	 * 
	 */
	public static DataBase getInstance() {
		if (instance == null) {
			instance = new DataBase();
		}
		return instance;

	}

	/**
	 * Konstruktor klasy bazy danych.
	 * 
	 * @return
	 * 
	 */
	public void DataBaseModel() {

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

			// tworzenie tabeli
			RecordsTableDBModel.getInstance().createTable(result, dbmd, statement);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
