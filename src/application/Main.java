package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

	private Stage primaryStage;
	private AnchorPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		try {
            // Load the root layout from the fxml file
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("MainWindow.fxml"));
            rootLayout = (AnchorPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public static void main(String[] args) {
		//launch(args);

		Chrono chrono = new Chrono();
		chrono.start(); // démarrage du chrono
		// ...
		// code à chronométrer
		// ...
		chrono.pause(); // on peut faire une pause

		chrono.resume(); // reprise du chrono
		// ...
		chrono.stop(); // arrêt
		System.out.println(chrono.getDureeMs()); // affichage du résultat en millisecondes
		System.out.println(chrono.getDureeSec()); // affichage en secondes
		System.out.println(chrono.getDureeTxt()); // affichage au format "
	}


}
