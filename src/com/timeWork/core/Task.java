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

public class Task implements Comparable<Task>{

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

	private SimpleBooleanProperty selectedProperty = new SimpleBooleanProperty();

	private SimpleBooleanProperty archivedProperty = new SimpleBooleanProperty();

	private Project project;

	public Task(String id, String title, Project project, String description, long initTime, boolean archived, String date) {
		if(id != null){
			uniqueID = id;
		}

		this.project = project;

		stateProperty.setValue(PAUSE);
		selectedProperty.setValue(false);
		archivedProperty.setValue(archived);

		titleProperty.setValue(title);
		projectProperty.bindBidirectional(project.getTitleProperty());
		descriptionProperty.setValue(description);

		String format = "dd/MM/yy";
		SimpleDateFormat formater = new SimpleDateFormat(format);
		if(date != null){
			dateProperty.setValue(date);
		}else{
			dateProperty.setValue(formater.format(new Date()));
		}

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

		UpdateTaskListener listener = new UpdateTaskListener(this);
		titleProperty.addListener(listener);
		projectProperty.addListener(listener);
		descriptionProperty.addListener(listener);
		dateProperty.addListener(listener);
		time.addListener(listener);
		archivedProperty.addListener(listener);
	}

	public Task(String id, String title, Project project, String description, long initTime) {
		this(id, title, project, description, initTime, false, null);
	}

	public Task(String title, Project project, String description, long initTime) {
		this(null, title, project, description, initTime);
	}

	public Task(String title, Project project, String description) {
		this(null, title, project, description, 0);
	}

	private class UpdateTaskListener implements ChangeListener<Object>{

		Task timer;

		public UpdateTaskListener(Task timer) {
			this.timer = timer;
		}

		@Override
		public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
			updateXmlTask(timer);
		}
	}

	public static void updateXmlTask(Task timer){
		TaskXml.updateTasks(timer);
		TaskXml.updateProject(timer.getProject());
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

	public Project getProject(){
		return project;
	}

	public void setProject(Project newProject){
		TaskXml.moveTaskProject(this, project, newProject);
		projectProperty.unbindBidirectional(project.getTitleProperty());
		project = newProject;
		projectProperty.bindBidirectional(project.getTitleProperty());
	}

	public long getTime(){
		return time.get();
	}

	public void start(){
		Timer task = new Timer();
		executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
		stateProperty.setValue(PLAY);
	}

	public void stop(){
		executor.shutdownNow();
		stateProperty.setValue(PAUSE);
	}

	private class Timer implements Runnable{
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

	public boolean isSelected() {
		return selectedProperty.get();
	}

	public SimpleBooleanProperty getSelectedProperty() {
		return selectedProperty;
	}

	public void setSelected(boolean b) {
		selectedProperty.set(b);
	}

	public boolean isArchived() {
		return archivedProperty.getValue();
	}

	public void setArchived(boolean b) {
		archivedProperty.set(b);
	}

	@Override
	public int compareTo(Task o) {
		System.out.println("teddy");
		return 0;
	}
}
