package model;

import javafx.collections.ObservableList;

public abstract class Model {

	// metody do laczenia z filesTableDBModel i recordsTableDBModel ->
	// potrzebne do odzielenia warsty controllera od widoku
	// widok nie widzi jaki interfejs ma baza danych tzn. bedzie korzystal tylko
	// z singletonu DataBaseModel

	public abstract void addFile(File file);

	public abstract void removeFile(int idx);

	public abstract void editFile(File file);

	public abstract File getFile(Long idx);

	public abstract ObservableList<File> getAllFiles();

}
