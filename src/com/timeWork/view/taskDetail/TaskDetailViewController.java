package com.timeWork.view.taskDetail;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.timeWork.core.Project;
import com.timeWork.core.Task;
import com.timeWork.view.ViewController;
import com.timeWork.view.home.newTask.ProjectListCell;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class TaskDetailViewController extends ViewController{

	@FXML
	private Label taskName;
	@FXML
	private Label taskDate;
	@FXML
    private JFXTextField tfTask;
    @FXML
    private JFXComboBox<Project> cbProject;
    @FXML
    private JFXTextArea taDescription;
    @FXML
    private JFXTextField tfHours;
    @FXML
    private JFXTextField tfMinutes;
    @FXML
    private JFXTextField tfSeconds;
    @FXML
    private TextField altTime;

	private Task timer;

	private UpdateTaskListener listener = new UpdateTaskListener();

	@FXML
	public void initialize(){

	}

	public void setTimer(Task timer) {
		this.timer = timer;

		tfHours.textProperty().removeListener(listener);
		tfMinutes.textProperty().removeListener(listener);
		tfSeconds.textProperty().removeListener(listener);

		taskName.textProperty().bind(timer.getTitleProperty());
		taskDate.textProperty().bind(timer.getDateProperty());
		tfTask.textProperty().bindBidirectional(timer.getTitleProperty());
		taDescription.textProperty().bindBidirectional(timer.getDescriptionProperty());

		cbProject.setCellFactory((ListView<Project> lv) -> new ProjectListCell());
		cbProject.setItems(mainControl.getProjectList());
		cbProject.setValue(timer.getProject());

		tfHours.textProperty().addListener(listener);
		tfMinutes.textProperty().addListener(listener);
		tfSeconds.textProperty().addListener(listener);

		cbProject.valueProperty().addListener(listener);

		setTimeParameter(60);
		altTime.setText(String.format("%s:%s heure(s)", timer.getHoursText(100), timer.getMinutesText(100)));
	}

	private class UpdateTaskListener implements ChangeListener<Object>{

		@Override
		public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
			if(timer != null){
				Long time = getTimeFromView();
				if(time != null){
					timer.getTimeProperty().set(time);
					altTime.setText(String.format("%s:%s heure(s)", timer.getHoursText(100), timer.getMinutesText(100)));
				}

				if(observable.equals(cbProject.valueProperty())){
					timer.setProject(cbProject.valueProperty().getValue());
				}
			}
		}
	}

	private void setTimeParameter(int base){
		tfHours.setText(timer.getHoursText(base));
		tfMinutes.setText(timer.getMinutesText(base));
		tfSeconds.setText(timer.getSecondesText(base));
	}

	//
	private Long getTimeFromView(){
		if(!tfHours.getText().isEmpty()
				&& !tfMinutes.getText().isEmpty()
				&& !tfSeconds.getText().isEmpty()){
			long time = Long.parseLong(tfHours.getText()) * 3600;
			int base = 60;
			time += Long.parseLong(tfMinutes.getText()) * 3600/ base ;
			time += Long.parseLong(tfSeconds.getText()) * 60/ base ;
			return time;
		}
		return null;
	}

	@FXML
	public void backToMenu(){
		taskName.textProperty().unbind();
		taskDate.textProperty().unbind();
		tfTask.textProperty().unbindBidirectional(timer.getTitleProperty());
		taDescription.textProperty().unbindBidirectional(timer.getDescriptionProperty());
		cbProject.valueProperty().removeListener(listener);

		mainControl.showView(mainControl.getHomeController());
	}
}