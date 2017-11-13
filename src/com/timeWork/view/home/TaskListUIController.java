package com.timeWork.view.home;

import com.timeWork.IViewController;
import com.timeWork.Main;
import com.timeWork.core.Timer;
import com.timeWork.view.widget.WidgetViewController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class TaskListUIController implements IViewController{

    @FXML
    private Label titleTask;
    @FXML
    private Label timeTask;
    @FXML
    private ImageView playPauseImage;
    @FXML
    private Label timeSTask;

	private Timer timer;

	@FXML
	private void timerToWidget(){
		Main.setWidgetTimer(timer);
	}

	public void setTimer(Timer timer) {
		this.timer = timer;

		titleTask.textProperty().unbind();
		timeTask.textProperty().unbind();
		timeSTask.textProperty().unbind();

		titleTask.textProperty().bind(timer.getTitleProperty());
		timeTask.textProperty().bind(timer.getTimeHrMinTextProperty());
		timeSTask.textProperty().bind(timer.getTimeSecTextProperty());

		timer.getStateProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue == Timer.PAUSE){
					playPauseImage.setImage(new Image(WidgetViewController.class.getResourceAsStream("pauseDark.png")));
				}else{
					playPauseImage.setImage(new Image(WidgetViewController.class.getResourceAsStream("playDark.png")));
				}
			}
		});
	}

	@FXML
	private void playPause(){
		if(timer.getState() == Timer.PLAY){
			timer.stop();
		}else if(timer.getState() == Timer.PAUSE){
			timer.start();
		}
	}
}
