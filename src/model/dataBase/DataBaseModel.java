package model.dataBase;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public abstract class DataBaseModel {

	public abstract void createTable(ResultSet result, DatabaseMetaData dbmd, Statement statement);

	public abstract void addRecord();

	public abstract void editRecord();

	public abstract void removeRecord();

	public abstract void getRecord();

	public abstract ArrayList getAllRecords();

	public abstract Object[][] getAllRecordsAsArray();

	public abstract int getNumOfRecords();

	public abstract void printRecord(int id);

}
