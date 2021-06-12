package com.timeWork.view.home;

import com.timeWork.core.Task;
import com.timeWork.view.FxmlLoader;

import javafx.scene.Node;
import javafx.scene.control.ListCell;

public class TaskListCell extends ListCell<Task> {

    Node renderer;
    TaskListUIController rendererController;

    public TaskListCell() {
        super();
        renderer = (Node) FxmlLoader.TASK_LIST_UI.getContentPane();
        rendererController = (TaskListUIController) FxmlLoader.TASK_LIST_UI.getController();
    }

    @Override
    protected void updateItem(Task value, boolean empty) {
        super.updateItem(value, empty);
        String text = null;
        Node graphic = null;
        if (!empty && value != null) {
            if (renderer != null) {
                graphic = renderer;
                rendererController.setTimer(value);
            } else {
                text = String.valueOf(value);
            }
        }
        setText(text);
        setGraphic(graphic);
    }
}