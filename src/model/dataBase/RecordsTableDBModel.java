package model.dataBase;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import model.Record;

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

	public void createTable(ResultSet result, DatabaseMetaData dbmd, Statement statement) {

		try {
			// utworzenie tabeli plik�w (jezeli jeszcze nie istniala)
			result = dbmd.getTables(null, null, "PLIKI", null);

			if (!result.next()) {
				statement.execute("CREATE TABLE rekordy" + "(" + "name				VARCHAR(256) NOT NULL,"
						+ "info		VARCHAR(256) NOT NULL," + "sequence			VARCHAR(256) NOT NULL,"
						+ "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)" + ")");

				System.out.println("Utworzono tabele rekordy");
			} else {
				System.out.println("Tabela rekord�w juz istnieje");
				// usuwanie tabeli
				// statement.execute( "DROP TABLE pliki");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void addRecord(Record record) {
		// TODO Auto-generated method stub

		System.out.println("RecordsTableDBModel.addRecord");
		try {
			statement.execute("INSERT INTO rekordy (name," + "info," + "sequence) " + "VALUES('"
					+ record.getRecordName() + "', '" + record.getRecordInfo() + ")");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void editRecord(Record record) {
		// TODO Auto-generated method stub

		System.out.println("RecordsTableDBModel.editRecord");
		try {
			statement.execute("UPDATE rekordy " + "SET name='" + record.getRecordName() + "'," + "info='"
					+ record.getRecordInfo() + "'," + "sequence=" + record.getRecordSequence() + " " + "WHERE id = "
					+ Long.toString(record.getRecordId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeRecord(int idx) {
		// TODO Auto-generated method stub
		System.out.println("RecordsTableDBModel.removeRecord");

		try {
			statement.execute("DELETE FROM rekordy WHERE id =" + Integer.toString(idx));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Record getRecord(int idx) {
		// TODO Auto-generated method stub
		System.out.println("RecordsTableDBModel.getFile");
		// TODO Auto-generated method stub
		Record record = null;
		try {
			ResultSet rs = statement.executeQuery("SELECT * FROM rekordy WHERE id=" + Integer.toString(idx));

			if (rs.next()) {
				String name = rs.getString("name");
				String info = rs.getString("info");
				String sequence = rs.getString("sequence");
				long id = Long.parseLong(rs.getString("id"));

				record = new Record(id, name, info, sequence);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return record;

	}

	public ArrayList getAllRecords() {
		// TODO Auto-generated method stub
		System.out.println("RecordsTableDBModel.getAllFiles");
		ArrayList<Record> records = new ArrayList<Record>();

		try {
			ResultSet rs = statement.executeQuery("SELECT * FROM rekordy");

			while (rs.next()) {
				String name = rs.getString("name");
				String info = rs.getString("info");
				String sequence = rs.getString("sequence");
				long id = Long.parseLong(rs.getString("id"));

				Record record = new Record(id, name, info, sequence);
				records.add(record);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return records;
	}

	public Object[][] getAllRecordsAsArray() {
		// TODO Auto-generated method stub
		System.out.println("RecordsTableDBModel.getAllFilesAsArray");
		ArrayList<Record> records = getAllRecords();
		ArrayList<Object[]> tmp = new ArrayList<Object[]>();

		for (int i = 0; i < records.size(); i++) {
			Record rec = records.get(i);
			Object[] objRec = rec.toArray();
			tmp.add(objRec);
		}

		return tmp.toArray(new Object[0][]);
	}

	public int getNumOfRecords() {
		// TODO Auto-generated method stub
		return 0;
	}

}
