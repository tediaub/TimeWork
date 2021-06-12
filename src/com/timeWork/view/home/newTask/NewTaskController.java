package com.timeWork.view.home.newTask;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.timeWork.core.Project;
import com.timeWork.core.Task;
import com.timeWork.view.FxmlLoader;
import com.timeWork.view.ViewController;
import com.timeWork.view.home.TaskListCell;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class NewTaskController extends ViewController{

	@FXML
	private AnchorPane rootPane;
    @FXML
    private JFXTextField nameText;
    @FXML
    private JFXTextArea descriptionText;
    @FXML
    private JFXComboBox<Project> projectTaskValue;

    @FXML
	private void initialize(){
    	projectTaskValue.setCellFactory((ListView<Project> lv) -> new ProjectListCell());
    	projectTaskValue.setItems(mainControl.getProjectList());
	}

    public void createTask(){
		mainControl.addTimer(new Task(nameText.getText(),
				projectTaskValue.getValue(),
				descriptionText.getText(),
				0));
		Stage stage = (Stage) rootPane.getScene().getWindow();
		stage.close();
	}

    public void openAddProjectView(){
    	AnchorPane projectPane = (AnchorPane) FxmlLoader.NEW_PROJECT_VIEW.getContentPane();
        Scene scene = new Scene(projectPane, Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("../../icon.png")));
        stage.setTitle("Ajouter un projet");
        stage.show();
	}
}