package com.timeWork;

import java.io.File;

import com.timeWork.core.TaskXml;
import com.timeWork.core.Timer;
import com.timeWork.view.MainController;
import com.timeWork.view.widget.WidgetViewController;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private AnchorPane detailView;
	private AnchorPane homeView;

	private static Stage stageMainWindow;

	private static WidgetViewController control;
	private static ObservableList<Timer> timerList;

	@Override
	public void start(Stage primaryStage) {

		File xml = new File(System.getProperty("user.dir" ) + File.separator + "taskData.xml");
		if(!xml.exists()){
			TaskXml.createFileXML(xml.getPath());
		}
		try {
			TaskXml.init(xml.getPath());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		new MainController().createMainView(primaryStage);
//		this.primaryStage = primaryStage;
//
//        // Load the root layout from the fxml file
//		rootLayout = (BorderPane) FxmlLoader.WIDGET_VIEW.getContentPane();
//        control = (WidgetViewController)FxmlLoader.WIDGET_VIEW.getController();
//        control.setMain(this);
//        Scene scene = new Scene(rootLayout, Color.TRANSPARENT);
//        primaryStage.setScene(scene);
//        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//
//			@Override
//			public void handle(WindowEvent event) {
//				System.exit(0);
//			}
//		});
//        initStage();
	}

//	public static void showMainWindow(){
//		stageMainWindow.show();
//	}
//
//	public static void setWidgetTimer(Timer timer){
//		control.setTimer(timer);
//	}
//
//	public Stage getPrimaryStage(){
//		return primaryStage;
//	}
//
//	public void initStage(){
//		primaryStage.initStyle(StageStyle.TRANSPARENT);
//
//        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
//        Rectangle maximumWindowBounds = graphicsEnvironment.getMaximumWindowBounds();
//
//        primaryStage.setAlwaysOnTop(true);
//        primaryStage.show();
//        primaryStage.setX((maximumWindowBounds.getWidth()-primaryStage.getWidth())/2);
//        primaryStage.setY(-(primaryStage.getHeight()-15));
//	}
//
//	public void setStageX(double value){
//		primaryStage.setX(value);
//	}
//
//	public void setStageY(double value){
//		primaryStage.setY(value);
//	}

	public static void main(String[] args) {
		launch(args);
	}
}
