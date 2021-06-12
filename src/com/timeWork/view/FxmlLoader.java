package com.timeWork.view;

import java.io.IOException;

import com.timeWork.view.language.LanguageSelector;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

/**
 * FxmlLoader enumeration list all fxml files use in the application.
 *
 *  @author AUBERT_T
 *  @version 1.0
 */
public enum FxmlLoader {

	HOME_VIEW("home/homeView.fxml", 0),
	WIDGET_VIEW("widget/widgetView.fxml", 1),
	TASK_LIST_UI("home/taskListUI.fxml", 2),
	NEW_TASK_VIEW("home/newTask/newTaskView.fxml", 3),
	TASK_DETAIL_VIEW("taskDetail/taskDetailView.fxml", 4),
	NEW_PROJECT_VIEW("home/newTask/newProjectView.fxml", 5),
	ANNIV_VIEW("anniv/anniv.fxml", 6);

	private FXMLLoader loader;
	private int index;
	private String path;

	private FxmlLoader(String path, int index) {
		this.path = path;
		this.index = index;
	}

	public Region getContentPane(){
		try {
			loader = new FXMLLoader(this.getClass().getResource(path), LanguageSelector.getRessourceBundle());
			return loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ViewController getController(){
		return loader.getController();
	}

	public int getIndex() {
		return index;
	}
}
