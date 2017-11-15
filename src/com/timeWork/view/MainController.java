package com.timeWork.view;

import com.timeWork.core.TaskXml;
import com.timeWork.core.Timer;
import com.timeWork.view.home.HomeViewController;
import com.timeWork.view.taskDetail.TaskDetailViewController;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainController extends ViewController{

	private static ObservableList<Timer> timerList;

	private HomeViewController controlHome;
	private TaskDetailViewController controlDetail;

	private StackPane stack;

	public MainController() {
		this.setMainController(this);
	}

	public void createMainView(Stage primaryStage){
		timerList = TaskXml.getTaskList();

		Region homeView = (Region) FxmlLoader.HOME_VIEW.getContentPane();
        controlHome = (HomeViewController)FxmlLoader.HOME_VIEW.getController();
        controlHome.setView(homeView);
        controlHome.setTimerList(timerList);

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
        primaryStage.show();
	}

	public void addTimer(Timer timer){
		timerList.add(timer);
		TaskXml.addTask(timer);
	}

	public static void updateTasks(Timer timer) {
		TaskXml.updateTasks(timer);
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
