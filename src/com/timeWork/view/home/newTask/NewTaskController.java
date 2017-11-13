package com.timeWork.view.home.newTask;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.timeWork.IViewController;
import com.timeWork.Main;
import com.timeWork.core.Timer;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NewTaskController implements IViewController{

	@FXML
	private AnchorPane rootPane;
    @FXML
    private JFXTextField nameText;
    @FXML
    private JFXTextField customerText;
    @FXML
    private JFXTextArea descriptionText;

    public void createTask(){
		Main.addTimer(new Timer(nameText.getText(),
				customerText.getText(),
				descriptionText.getText(),
				"0",
				0));
		Stage stage = (Stage) rootPane.getScene().getWindow();
		stage.close();
	}
}