package com.timeWork.view.home;

import com.timeWork.FxmlLoader;
import com.timeWork.core.Timer;

import javafx.scene.Node;
import javafx.scene.control.ListCell;

public class PrimeListCell extends ListCell<Timer> {

    Node renderer;
    TaskListUIController rendererController;

    public PrimeListCell() {
        super();
        renderer = (Node) FxmlLoader.TASK_LIST_UI.getContentPane();
        rendererController = (TaskListUIController) FxmlLoader.TASK_LIST_UI.getController();
    }

    @Override
    protected void updateItem(Timer value, boolean empty) {
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