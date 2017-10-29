package application;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Main extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private static Stage stageHomeWindow;

	private static MainController control;
	private static ObservableList<Timer> timerList;

	private static TaskXml taskXml;

	@Override
	public void start(Stage primaryStage) {
		File xml = new File(System.getProperty("user.dir" ) + File.separator + "taskData.xml");
		if(!xml.exists()){
			TaskXml.createFileXML(xml.getPath());
		}
		try {
			taskXml = new TaskXml(xml.getPath());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		this.primaryStage = primaryStage;
		try {
            // Load the root layout from the fxml file
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("MainWindow.fxml"));
            rootLayout = (BorderPane) loader.load();
            control = (MainController)loader.getController();
            control.setMain(this);
            Scene scene = new Scene(rootLayout, Color.TRANSPARENT);
            primaryStage.setScene(scene);
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					System.exit(0);
				}
			});
            initStage();

            timerList = taskXml.getTaskList();

            FXMLLoader loader2 = new FXMLLoader(Main.class.getResource("homeView.fxml"));
            AnchorPane layout = (AnchorPane) loader2.load();
            HomeViewController controlHome = (HomeViewController)loader2.getController();
            controlHome.setTimerList(timerList);

            Scene scene2 = new Scene(layout, Color.TRANSPARENT);
            stageHomeWindow = new Stage();
            stageHomeWindow.setScene(scene2);
            stageHomeWindow.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public static void showHomeWindow(){
		stageHomeWindow.show();
	}

	public static void addTimer(Timer timer){
		timerList.add(timer);
		taskXml.addTask(timer);
	}

	public static void setWidgetTimer(Timer timer){
		control.setTimer(timer);
	}

	public Stage getPrimaryStage(){
		return primaryStage;
	}

	public void initStage(){
		primaryStage.initStyle(StageStyle.TRANSPARENT);

        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle maximumWindowBounds = graphicsEnvironment.getMaximumWindowBounds();

        primaryStage.setAlwaysOnTop(true);
        primaryStage.show();
        primaryStage.setX((maximumWindowBounds.getWidth()-primaryStage.getWidth())/2);
        primaryStage.setY(-(primaryStage.getHeight()-15));
	}

	public void setStageX(double value){
		primaryStage.setX(value);
	}

	public void setStageY(double value){
		primaryStage.setY(value);
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static void updateTasks(Timer timer) {
		taskXml.updateTasks(timer);
	}
}
