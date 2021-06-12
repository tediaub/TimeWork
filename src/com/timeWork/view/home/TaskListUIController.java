package com.timeWork.view.home;

import com.jfoenix.controls.JFXCheckBox;
import com.timeWork.core.Task;
import com.timeWork.view.ViewController;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TaskListUIController extends ViewController{

    @FXML
    private Label titleTask;
    @FXML
    private Label projectTask;
    @FXML
    private Label timeTask;
    @FXML
    private Button btnPlay;
    @FXML
    private Label timeSTask;
    @FXML
    private JFXCheckBox selected;
    @FXML
    private Circle circle;

	private Task timer;

	private SimpleBooleanProperty stateProperty = new SimpleBooleanProperty();

	private UpdateTaskListener listener = new UpdateTaskListener();

	@FXML
	private void initialize(){
		stateProperty.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				setButtonClass(newValue);
			}
		});
	}

	private void setButtonClass(boolean b){
		if(b == Task.PAUSE){
			btnPlay.pseudoClassStateChanged(PseudoClass.getPseudoClass("play"), false);
		}else{
			btnPlay.pseudoClassStateChanged(PseudoClass.getPseudoClass("play"), true);
		}
	}

	public void setTimer(Task timer) {
		this.timer = timer;

		titleTask.textProperty().unbind();
		projectTask.textProperty().unbind();
		timeTask.textProperty().unbind();
		timeSTask.textProperty().unbind();
		stateProperty.unbind();

		titleTask.textProperty().bind(timer.getTitleProperty());
		projectTask.textProperty().bind(timer.getProjectProperty());
		timeTask.textProperty().bind(timer.getTimeHrMinTextProperty());
		timeSTask.textProperty().bind(timer.getTimeSecTextProperty());
		stateProperty.bind(timer.getStateProperty());

		selected.setSelected(timer.getSelectedProperty().getValue());

		timer.getProject().getColorProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				circle.setFill(Color.valueOf(timer.getProject().getColorProperty().getValue()));
			}
		});

		circle.setFill(Color.valueOf(timer.getProject().getColorProperty().getValue()));
		projectTask.textProperty().addListener(listener);
	}

	private class UpdateTaskListener implements ChangeListener<Object>{

		@Override
		public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
			if(timer != null){
				if(observable.equals(projectTask.textProperty())){
					circle.setFill(Color.valueOf(timer.getProject().getColorProperty().getValue()));
				}
			}
		}
	}

	@FXML
	private void playPause(){
		if(timer.getState() == Task.PLAY){
			timer.stop();
		}else if(timer.getState() == Task.PAUSE){
			timer.start();
		}
	}

	@FXML
	private void showDetail(){
		mainControl.getDetailController().setTimer(timer);
		mainControl.showView(mainControl.getDetailController());
	}

	@FXML
	private void toggleSelect(){
		timer.setSelected(selected.isSelected());
	}
}
