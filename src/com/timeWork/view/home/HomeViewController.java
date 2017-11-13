package com.timeWork.view.home;

import com.timeWork.FxmlLoader;
import com.timeWork.IViewController;
import com.timeWork.core.Timer;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HomeViewController implements IViewController{

    @FXML
    private TextField nameText;
    @FXML
    private TextField customerText;
    @FXML
    private DatePicker creationDate;
    @FXML
    private TextField timeText;
    @FXML
    private TextArea descriptionText;
    @FXML
    private ListView<Timer> timerList;

    @FXML
	private void initialize(){
    	timerList.setCellFactory((ListView<Timer> lv) -> new PrimeListCell());
    }

	public void setTimerList(ObservableList<Timer> timerList2) {
		timerList.setItems(timerList2);
	}

	public void createTask(){
        AnchorPane rootLayout = (AnchorPane) FxmlLoader.NEW_TASK_VIEW.getContentPane();
        Scene scene = new Scene(rootLayout, Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
	}
}
