package com.timeWork.view.home;

import com.timeWork.core.Timer;
import com.timeWork.view.ViewController;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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

	private Timer timer;

	private SimpleBooleanProperty stateProperty = new SimpleBooleanProperty();

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
		if(b == Timer.PAUSE){
			btnPlay.pseudoClassStateChanged(PseudoClass.getPseudoClass("play"), false);
		}else{
			btnPlay.pseudoClassStateChanged(PseudoClass.getPseudoClass("play"), true);
		}
	}

	public void setTimer(Timer timer) {
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
	}

	@FXML
	private void playPause(){
		if(timer.getState() == Timer.PLAY){
			timer.stop();
		}else if(timer.getState() == Timer.PAUSE){
			timer.start();
		}
	}

	@FXML
	private void showDetail(){
		mainControl.getDetailController().setTimer(timer);
		mainControl.showView(mainControl.getDetailController());
	}
}
