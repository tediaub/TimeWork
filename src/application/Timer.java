package application;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Timer {

	public static final boolean PLAY = true;
	public static final boolean PAUSE = false;

	private long time = 0;

	private SimpleBooleanProperty stateProperty = new SimpleBooleanProperty();
	private SimpleStringProperty timeHrMinTextProperty = new SimpleStringProperty();
	private SimpleStringProperty timeSecTextProperty = new SimpleStringProperty();

	private ScheduledExecutorService executor;

	public Timer() {
		stateProperty.setValue(PAUSE);
		setTextProperty(time);
	}

	public void start(){
		Task task = new Task();
		executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
		stateProperty.setValue(PLAY);
	}

	public void stop(){
		executor.shutdownNow();
		stateProperty.setValue(PAUSE);
	}

	private class Task implements Runnable{

		@Override
		public void run() {
			increaseTime();
		}
	}

	public void increaseTime(){
		time++;

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				setTextProperty(time);
			}
		});
	}

	public SimpleStringProperty getTimeHrMinTextProperty(){
		return timeHrMinTextProperty;
	}

	public SimpleStringProperty getTimeSecTextProperty(){
		return timeSecTextProperty;
	}

	private void setTextProperty(long tempsS) {
        int h = (int) (tempsS / 3600);
        int m = (int) ((tempsS % 3600) / 60);
        int s = (int) (tempsS % 60);

        String sh, sm, ss;

        if(h<10) {sh = "0" + h;}else{sh = "" + h;}
        if(m<10) {sm = "0" + m;}else{sm = "" + m;}
        if(s<10) {ss = "0" + s;}else{ss = "" + s;}

        timeHrMinTextProperty.set(sh + ":" + sm);
        timeSecTextProperty.set(ss);
	}

	public boolean getState(){
		return stateProperty.get();
	}

	public SimpleBooleanProperty getStateProperty(){
		return stateProperty;
	}
}
