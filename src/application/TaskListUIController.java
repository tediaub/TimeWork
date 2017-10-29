package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TaskListUIController {

    @FXML
    private Label titleTask;
    @FXML
    private Label timeTask;
    @FXML
    private ImageView playPauseImage;

	private Timer timer;

	public void timerToWidget(){
		Main.setWidgetTimer(timer);
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
		titleTask.textProperty().bind(timer.getTitleProperty());
		timeTask.textProperty().bind(timer.getTimeHrMinTextProperty());

//		timer.getStateProperty().addListener(new ChangeListener<Boolean>() {
//			@Override
//			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//				if(newValue == Timer.PAUSE){
//					playPauseImage.setImage(new Image(MainController.class.getResourceAsStream("pauseDark.png")));
//				}else{
//					playPauseImage.setImage(new Image(MainController.class.getResourceAsStream("playDark.png")));
//				}
//			}
//		});
	}
}
