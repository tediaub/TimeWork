package com.timeWork;

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

	HOME_VIEW("view/home/homeView.fxml", 0),
	WIDGET_VIEW("view/widget/widgetView.fxml", 1),
	TASK_LIST_UI("view/home/taskListUI.fxml", 2),
	NEW_TASK_VIEW("view/home/newTask/newTaskView.fxml", 3);

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

	public IViewController getController(){
		return loader.getController();
	}

	public int getIndex() {
		return index;
	}
}
