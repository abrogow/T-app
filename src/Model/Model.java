package model;

import javafx.collections.ObservableList;

public abstract class Model {

	// metody do laczenia z filesTableDBModel i recordsTableDBModel ->
	// potrzebne do odzielenia warsty controllera od widoku
	// widok nie widzi jaki interfejs ma baza danych tzn. bedzie korzystal tylko
	// z singletonu DataBaseModel

	public abstract void addRecord(Rec rec);

	public abstract void addFile(File file);

	public abstract void removeRecord(long idx);

	public abstract void removeFile(int idx);

	public abstract void editRecord(Rec rec);

	public abstract void editFile(File file);

	public abstract Rec getRecord(long idx);

	public abstract File getFile(Long idx);

	public abstract ObservableList<Rec> getAllRecords();

	public abstract ObservableList<File> getAllFiles();

}
