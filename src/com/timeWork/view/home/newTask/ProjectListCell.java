package com.timeWork.view.home.newTask;

import com.timeWork.core.Project;

import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class ProjectListCell extends ListCell<Project> {

    public ProjectListCell() {
        super();
    }

    @Override
    protected void updateItem(Project value, boolean empty) {
        super.updateItem(value, empty);

        if(value!=null){
        	setText(value.getTitleProperty().getValue());
        	Shape shape = new Circle(10, 10, 5);
            shape.setFill(Color.valueOf(value.getColorProperty().getValue()));
            setGraphic(shape);
        }
    }
}