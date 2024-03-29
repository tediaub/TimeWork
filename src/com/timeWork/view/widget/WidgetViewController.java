package com.timeWork.view.widget;

import com.timeWork.Main;
import com.timeWork.core.Task;
import com.timeWork.core.property.BlockedBooleanProperty;
import com.timeWork.view.ViewController;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WritableValue;
import javafx.css.PseudoClass;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class WidgetViewController extends ViewController{

	private Main main;
    private Task timer;
    private BlockedBooleanProperty extended = new BlockedBooleanProperty();

    @FXML
    private Pane root;
    @FXML
    private Label timeHrMinLabel;
    @FXML
    private Label timeSecLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private ImageView playPauseImage;


	@FXML
	private void initialize(){
		extended.addListener(new ChangeListener<Boolean>() {

			private Timeline timeline = new Timeline();

			WritableValue<Double> writableY = new WritableValue<Double>() {
			    @Override
			    public Double getValue() {
			        return main.getPrimaryStage().getY();
			    }

			    @Override
			    public void setValue(Double value) {
			    	main.getPrimaryStage().setY(value);
			    }
			};

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				timeline.stop();
				timeline = new Timeline();
				timeline.setCycleCount(1);

				KeyValue kv;
				if(newValue){
					kv = new KeyValue(writableY, new Double(0));
				}else{
					kv = new KeyValue(writableY, -(main.getPrimaryStage().getHeight()-15));
					timeline.setDelay(new Duration(200));
				}
				KeyFrame kf = new KeyFrame(Duration.millis(300), kv);

				timeline.getKeyFrames().add(kf);
				timeline.playFromStart();
			}
		});

		setDragPane(root);
	}

	public void setTimer(Task timer){
		this.timer = timer;

		titleLabel.textProperty().unbind();
		timeHrMinLabel.textProperty().unbind();
		timeSecLabel.textProperty().unbind();

		titleLabel.textProperty().bind(timer.getTitleProperty());
		timeHrMinLabel.textProperty().bind(timer.getTimeHrMinTextProperty());
		timeSecLabel.textProperty().bind(timer.getTimeSecTextProperty());

		timer.getStateProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue == Task.PAUSE){
					root.pseudoClassStateChanged(PseudoClass.getPseudoClass("play"), false);
					playPauseImage.setImage(new Image(WidgetViewController.class.getResourceAsStream("play.png")));
				}else{
					root.pseudoClassStateChanged(PseudoClass.getPseudoClass("play"), true);
					playPauseImage.setImage(new Image(WidgetViewController.class.getResourceAsStream("pause.png")));
				}
			}
		});
	}

	public void showHome(){
		Main.showMainWindow();
	}

	public void setMain(Main main){
		this.main = main;
	}

	@FXML
	private void extendStage(){
		extended.setValue(true);
	}

	@FXML
	private void reduceStage(){
		extended.setValue(false);
	}

	@FXML
	private void playPause(){
		if(timer.getState() == Task.PLAY){
			timer.stop();
		}else if(timer.getState() == Task.PAUSE){
			timer.start();
		}
	}

	public void setDragPane(Pane pane){
		final Delta dragDelta = new Delta();
		pane.setOnMousePressed(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent me) {
	            if (me.getButton() == MouseButton.PRIMARY) {
	                dragDelta.x = me.getSceneX();
	                extended.setPropertyBlocked(true);
	            }
	        }
	    });

		pane.setOnMouseReleased(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				extended.setPropertyBlocked(false);
			}
		});

		pane.setOnMouseDragged(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent me) {
	            if (me.getButton() == MouseButton.PRIMARY) {
	                main.setStageX(me.getScreenX() - dragDelta.x);
	            }
	        }
	    });
	}

	public class Delta {
	    public double x;
	    public double y;
	}
}
