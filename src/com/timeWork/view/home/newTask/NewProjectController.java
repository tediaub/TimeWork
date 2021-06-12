package com.timeWork.view.home.newTask;

import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.timeWork.core.Project;
import com.timeWork.core.Task;
import com.timeWork.view.FxmlLoader;
import com.timeWork.view.ViewController;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class NewProjectController extends ViewController{

	@FXML
	private AnchorPane rootPane;
    @FXML
    private JFXTextField projectName;
    @FXML
    private JFXColorPicker projectColor;

    public void createProject(){
		mainControl.addProject(new Project(projectName.getText(), projectColor.getValue().toString()));
		Stage stage = (Stage) rootPane.getScene().getWindow();
		stage.close();
	}
}