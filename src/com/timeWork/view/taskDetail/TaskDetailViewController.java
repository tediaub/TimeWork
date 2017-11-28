package com.timeWork.view.taskDetail;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.timeWork.core.Timer;
import com.timeWork.view.ViewController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TaskDetailViewController extends ViewController{

	@FXML
	private Label taskName;
	@FXML
	private Label taskDate;
	@FXML
    private JFXTextField tfTask;
    @FXML
    private JFXTextField tfProject;
    @FXML
    private JFXTextArea taDescription;
    @FXML
    private JFXTextField tfHours;
    @FXML
    private JFXTextField tfMinutes;
    @FXML
    private JFXTextField tfSeconds;
    @FXML
    private JFXToggleButton toggleBase;

	private Timer timer;

	private UpdateTaskListener listener = new UpdateTaskListener();

	@FXML
	public void initialize(){

	}

	public void setTimer(Timer timer) {
		this.timer = timer;

		tfHours.textProperty().removeListener(listener);
		tfMinutes.textProperty().removeListener(listener);
		tfSeconds.textProperty().removeListener(listener);

		taskName.textProperty().bind(timer.getTitleProperty());
		taskDate.textProperty().bind(timer.getDateProperty());
		tfTask.textProperty().bindBidirectional(timer.getTitleProperty());
		tfProject.textProperty().bindBidirectional(timer.getProjectProperty());
		taDescription.textProperty().bindBidirectional(timer.getDescriptionProperty());

		if(toggleBase.isSelected()){
			setTimeParameter(100);
		}else{
			setTimeParameter(60);
		}

		toggleBase.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue){
					setTimeParameter(100);
				}else {
					setTimeParameter(60);
				}
			}
		});

		tfHours.textProperty().addListener(listener);
		tfMinutes.textProperty().addListener(listener);
		tfSeconds.textProperty().addListener(listener);
	}

	private class UpdateTaskListener implements ChangeListener<Object>{

		@Override
		public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
			if(timer != null){
				Long time = getTimeFromView();
				if(time != null){
					timer.getTimeProperty().set(time);
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
			if(toggleBase.isSelected()){
				base = 100;
			}
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
		tfProject.textProperty().unbindBidirectional(timer.getProjectProperty());
		taDescription.textProperty().unbindBidirectional(timer.getDescriptionProperty());

		mainControl.showView(mainControl.getHomeController());
	}
}