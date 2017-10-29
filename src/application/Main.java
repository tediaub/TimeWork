package application;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Main extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		try {
            // Load the root layout from the fxml file
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("MainWindow.fxml"));
            rootLayout = (BorderPane) loader.load();
            ((MainController)loader.getController()).setMain(this);
            Scene scene = new Scene(rootLayout, Color.TRANSPARENT);
            primaryStage.setScene(scene);
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					System.exit(0);
				}
			});
            initStage();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
