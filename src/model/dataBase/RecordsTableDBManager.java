package model.dataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Record;

public class RecordsTableDBManager {

	Statement statement = null;

	private ObservableList<Record> records;

	private static RecordsTableDBManager instance = null;

	/**
	 * Singleton dla klasy bazy danych.
	 * 
	 * @return instance instancja klasy bazy danych
	 * 
	 */
	public static RecordsTableDBManager getInstance() {
		if (instance == null) {
			instance = new RecordsTableDBManager();
		}
		return instance;

	}

	public RecordsTableDBManager() {

	}

	// do przekazania statement z DataBaseModel
	public void setDBStatement(Statement s) {
		this.statement = s;
	}

	public void createTable() throws SQLException {

		statement.execute("CREATE TABLE rekordy" + "(" + "identifier	VARCHAR(256) NOT NULL, "
				+ "name				VARCHAR(256) NOT NULL," + "info		VARCHAR(256) NOT NULL,"
				+ "sequence			VARCHAR(256) NOT NULL,"
				+ "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)" + ")");

		System.out.println("Utworzono tabele rekordy");

	}

	public void addRecord(Record record) {
		// TODO Auto-generated method stub

		System.out.println("RecordsTableDBModel.addRecord");
		try {
			String Query = "INSERT INTO rekordy (identifier, name," + "info," + "sequence) " + "VALUES ('"
					+ record.getRecordIdentifier() + "', '" + record.getRecordName() + "', '" + record.getRecordInfo()
					+ "', '" + record.getRecordSequence() + "')";
			statement.execute(Query);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void editRecord(Record record) {
		// TODO Auto-generated method stub

		System.out.println("RecordsTableDBModel.editRecord");
		try {
			statement.execute("UPDATE rekordy " + "SET " + "identifier='" + record.getRecordIdentifier() + "',"
					+ "name='" + record.getRecordName() + "'," + "info='" + record.getRecordInfo() + "'," + "sequence='"
					+ record.getRecordSequence() + "' " + "WHERE id = " + record.getRecordId().getValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeRecord(long idx) {
		// TODO Auto-generated method stub
		System.out.println("RecordsTableDBModel.removeRecord");

		try {
			statement.execute("DELETE FROM rekordy WHERE id =" + Long.toString(idx));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Record getRecord(long idx) {
		// TODO Auto-generated method stub
		System.out.println("RecordsTableDBModel.getRecord");
		// TODO Auto-generated method stub
		Record record = null;
		try {
			ResultSet rs = statement.executeQuery("SELECT * FROM rekordy WHERE id=" + Long.toString(idx));

			if (rs.next()) {
				String identifier = rs.getString("identifier");
				String name = rs.getString("name");
				String info = rs.getString("info");
				String sequence = rs.getString("sequence");
				long id = Long.parseLong(rs.getString("id"));

				record = new Record(id, identifier, name, info, sequence);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return record;

	}

	// czy nie trzeba przeksztalcic tego na ObservableList<Record> data ?????

	// public ArrayList getAllRecords() {
	// // TODO Auto-generated method stub
	// System.out.println("RecordsTableDBModel.getAllFiles");
	// ArrayList<Record> records = new ArrayList<Record>();
	//
	// try {
	// ResultSet rs = statement.executeQuery("SELECT * FROM rekordy");
	//
	// while (rs.next()) {
	// String name = rs.getString("name");
	// String info = rs.getString("info");
	// String sequence = rs.getString("sequence");
	// long id = Long.parseLong(rs.getString("id"));
	//
	// Record record = new Record(id, name, info, sequence);
	// records.add(record);
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// return records;
	// }

	public ObservableList<Record> getAllRecords() {

		System.out.println("RecordsTableDBCreator.getAllRecordsAsObservableList");
		records = FXCollections.observableArrayList();

		try {
			ResultSet rs = statement.executeQuery("SELECT * FROM rekordy");
			while (rs.next()) {
				long id = Long.parseLong(rs.getString("id"));
				String identifier = rs.getString("identifier");
				String name = rs.getString("name");
				String info = rs.getString("info");
				String sequence = rs.getString("sequence");

				Record rec = new Record(id, identifier, name, info, sequence);
				records.add(rec);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return records;
	}

	// public Object[][] getAllRecordsAsArray() {
	// // TODO Auto-generated method stub
	// System.out.println("RecordsTableDBModel.getAllFilesAsArray");
	// ArrayList<Record> records = getAllRecords();
	// ArrayList<Object[]> tmp = new ArrayList<Object[]>();
	//
	// for (int i = 0; i < records.size(); i++) {
	// Record rec = records.get(i);
	// Object[] objRec = rec.toArray();
	// tmp.add(objRec);
	// }
	//
	// return tmp.toArray(new Object[0][]);
	// }

	public int getNumOfRecords() {
		// TODO Auto-generated method stub
		return 0;
	}

}
