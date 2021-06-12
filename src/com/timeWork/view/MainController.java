package com.timeWork.view;

import java.util.Calendar;

import com.timeWork.core.Project;
import com.timeWork.core.Task;
import com.timeWork.core.TaskXml;
import com.timeWork.view.home.HomeViewController;
import com.timeWork.view.taskDetail.TaskDetailViewController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainController extends ViewController{

	private static ObservableList<Task> taskList;
	private static ObservableList<Project> projectList;

	private HomeViewController controlHome;
	private TaskDetailViewController controlDetail;

	private StackPane stack;

	public MainController() {
		this.setMainController(this);
	}

	public void createMainView(Stage primaryStage){
		projectList = FXCollections.observableArrayList();
		taskList = FXCollections.observableArrayList();
		TaskXml.getData(projectList, taskList);

		Region homeView = (Region) FxmlLoader.HOME_VIEW.getContentPane();
        controlHome = (HomeViewController)FxmlLoader.HOME_VIEW.getController();
        controlHome.setView(homeView);
        controlHome.setTimerList(taskList);

        Region detailView = (Region) FxmlLoader.TASK_DETAIL_VIEW.getContentPane();
        controlDetail = (TaskDetailViewController)FxmlLoader.TASK_DETAIL_VIEW.getController();
        controlDetail.setView(detailView);

        stack = new StackPane(detailView, homeView);

        Scene scene = new Scene(stack, Color.TRANSPARENT);
        primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				System.exit(0);
			}
		});
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        primaryStage.setTitle("TimeWork v1.1");
        primaryStage.show();

        Calendar calendar = Calendar.getInstance();
        if ( calendar.get( Calendar.MONTH )==Calendar.JUNE && calendar.get( Calendar.DAY_OF_MONTH ) == 12) {
        	BorderPane annivLayout = (BorderPane) FxmlLoader.ANNIV_VIEW.getContentPane();
            Scene sceneAnniv = new Scene(annivLayout, Color.TRANSPARENT);
            Stage stageAnniv = new Stage();
            stageAnniv.setResizable(false);
            stageAnniv.setScene(sceneAnniv);
            stageAnniv.setTitle("Bon anniversaire !!!");
            stageAnniv.show();
        }
	}

	public void addTimer(Task task){
		taskList.add(task);
		TaskXml.addTask(task);
	}

	public static void updateTasks(Task task) {
		TaskXml.updateTasks(task);
	}

	public void addProject(Project project){
		projectList.add(project);
		TaskXml.addProject(project);
	}

	public ObservableList<Project> getProjectList(){
		return projectList;
	}

	public HomeViewController getHomeController(){
		return controlHome;
	}

	public TaskDetailViewController getDetailController(){
		return controlDetail;
	}

	public void showView(ViewController control){
		for (int i = 0; i < stack.getChildren().size(); i++) {
			if(control.getView().equals(stack.getChildren().get(i))){
				stack.getChildren().get(i).setVisible(true);
			}else{
				stack.getChildren().get(i).setVisible(false);
			}
		}
	}
}
