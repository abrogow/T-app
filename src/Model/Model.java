package model;

public abstract class Model {

	// metody do laczenia z filesTableDBModel i recordsTableDBModel ->
	// potrzebne do odzielenia warsty controllera od widoku
	// widok nie widzi jaki interfejs ma baza danych tzn. bedzie korzystal tylko
	// z singletonu DataBaseModel

	public abstract void addRecord(Record record);

	public abstract void addFile(File file);

	public abstract void removeRecord(int idx);

	public abstract void removeFile(int idx);

	public abstract void editRecord(Record record);

	public abstract void editFile(File file);

	public abstract Record getRecord(int idx);

	public abstract File getFIle(int idx);

	public abstract void getAllRecords();

	public abstract void getAllFiles();

}
