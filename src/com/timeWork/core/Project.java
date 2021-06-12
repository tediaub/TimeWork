package com.timeWork.core;

import java.util.UUID;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.paint.Color;

public class Project {

	private String uniqueID = UUID.randomUUID().toString();

	private SimpleStringProperty titleProperty = new SimpleStringProperty();
	private SimpleStringProperty colorProperty = new SimpleStringProperty();

	public Project(String id, String title, String color) {
		if(id != null){
			uniqueID = id;
		}

		titleProperty.setValue(title);
		colorProperty.setValue(color);
	}

	public Project(String title, String color) {
		this(null, title, color);
	}

	public String getId() {
		return uniqueID;
	}

	public SimpleStringProperty getTitleProperty(){
		return titleProperty;
	}

	public SimpleStringProperty getColorProperty(){
		return colorProperty;
	}

	@Override
	public String toString() {
		return getTitleProperty().getValue();
	}
}