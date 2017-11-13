package com.timeWork.core;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.timeWork.Main;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Timer {

	private String uniqueID = UUID.randomUUID().toString();

	public static final boolean PLAY = true;
	public static final boolean PAUSE = false;

	private SimpleStringProperty titleProperty = new SimpleStringProperty();
	private SimpleStringProperty projectProperty = new SimpleStringProperty();
	private SimpleStringProperty descriptionProperty = new SimpleStringProperty();
	private SimpleStringProperty dateProperty = new SimpleStringProperty();

	private long time = 0;

	private SimpleBooleanProperty stateProperty = new SimpleBooleanProperty();
	private SimpleStringProperty timeHrMinTextProperty = new SimpleStringProperty();
	private SimpleStringProperty timeSecTextProperty = new SimpleStringProperty();

	private ScheduledExecutorService executor;

	public Timer(String id, String title, String consumer, String description, String date, long initTime) {
		this(title, consumer, description, date, initTime);
		uniqueID = id;
	}

	public Timer(String title, String consumer, String description, String date, long initTime) {
		this(title, consumer, description, date);
		time = initTime;
		setTextProperty(time);
	}

	public Timer(String title, String consumer, String description, String date) {
		stateProperty.setValue(PAUSE);
		setTextProperty(time);

		titleProperty.setValue(title);
		projectProperty.setValue(consumer);
		descriptionProperty.setValue(description);
		dateProperty.setValue(date);
	}

	public String getId(){
		return uniqueID;
	}

	public SimpleStringProperty getTitleProperty(){
		return titleProperty;
	}

	public SimpleStringProperty getProjectProperty(){
		return projectProperty;
	}

	public SimpleStringProperty getDescriptionProperty(){
		return descriptionProperty;
	}

	public SimpleStringProperty getDateProperty(){
		return dateProperty;
	}

	public long getTime(){
		return time;
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
		Main.updateTasks(this);
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
