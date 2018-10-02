package view.mainWindow;

import javafx.scene.layout.GridPane;

public abstract class TemplateGridPane extends GridPane {

	public abstract void createAndShowStage(RecordsTable recordsTable);

	public abstract void createControls();

	public abstract void addControls();

	public abstract void configureGrid();

	public abstract void setProperties();

}
