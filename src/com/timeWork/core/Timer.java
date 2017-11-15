package com.timeWork.core;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Timer {

	private String uniqueID = UUID.randomUUID().toString();

	public static final boolean PLAY = true;
	public static final boolean PAUSE = false;

	private SimpleStringProperty titleProperty = new SimpleStringProperty();
	private SimpleStringProperty projectProperty = new SimpleStringProperty();
	private SimpleStringProperty descriptionProperty = new SimpleStringProperty();
	private SimpleStringProperty dateProperty = new SimpleStringProperty();

	private SimpleLongProperty time = new SimpleLongProperty(0);

	private SimpleBooleanProperty stateProperty = new SimpleBooleanProperty();
	private SimpleStringProperty timeHrMinTextProperty = new SimpleStringProperty();
	private SimpleStringProperty timeSecTextProperty = new SimpleStringProperty();

	private ScheduledExecutorService executor;

	public Timer(String id, String title, String consumer, String description, long initTime) {
		if(id != null){
			uniqueID = id;
		}

		stateProperty.setValue(PAUSE);

		titleProperty.setValue(title);
		projectProperty.setValue(consumer);
		descriptionProperty.setValue(description);

		String format = "dd/MM/yy H:mm:ss";
		SimpleDateFormat formater = new SimpleDateFormat(format);
		dateProperty.setValue(formater.format(new Date()));

		time.set(initTime);
		setTextProperty();
		time.addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						setTextProperty();
					}
				});
			}
		});

		UpdateTaskListener listener = new UpdateTaskListener();
		titleProperty.addListener(listener);
		projectProperty.addListener(listener);
		descriptionProperty.addListener(listener);
		dateProperty.addListener(listener);
		time.addListener(listener);
	}

	public Timer(String title, String consumer, String description, long initTime) {
		this(null, title, consumer, description, initTime);
	}

	public Timer(String title, String consumer, String description) {
		this(null, title, consumer, description, 0);
	}

	private class UpdateTaskListener implements ChangeListener<Object>{

		@Override
		public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
			updateXmlTask();
		}
	}

	public void updateXmlTask(){
		TaskXml.updateTasks(this);
	}

	public String getId(){
		return uniqueID;
	}

	public SimpleStringProperty getTitleProperty(){
		return titleProperty;
	}

	public SimpleLongProperty getTimeProperty(){
		return time;
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
		return time.get();
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
		time.set(time.get()+1);
		TaskXml.updateTasks(this);
	}

	public SimpleStringProperty getTimeHrMinTextProperty(){
		return timeHrMinTextProperty;
	}

	public SimpleStringProperty getTimeSecTextProperty(){
		return timeSecTextProperty;
	}

	private void setTextProperty() {
        String sh = getHoursText(60);
        String sm = getMinutesText(60);
        String ss = getSecondesText(60);

        timeHrMinTextProperty.set(sh + ":" + sm);
        timeSecTextProperty.set(ss);
	}

	public String getHoursText(int base){
		int h = (int) (time.get() / 3600);
		if(h<10) {return "0" + h;}else{return "" + h;}
	}

	public String getMinutesText(int base){
		int m = (int) ((time.get() % 3600)/60);
		m = m * base /60;
		if(m<10) {return "0" + m;}else{return "" + m;}
	}

	public String getSecondesText(int base){
		int s = (int) (time.get() % 60);
		s = s * base /60;
		if(s<10) {return "0" + s;}else{return "" + s;}
	}

	public boolean getState(){
		return stateProperty.get();
	}

	public SimpleBooleanProperty getStateProperty(){
		return stateProperty;
	}
}
