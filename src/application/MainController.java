package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {

	@FXML
	private Label lbl;
	private Chrono chrono;

	@FXML
	private void initialize(){
		chrono = new Chrono();
		lbl.setText(chrono.getDureeTxt());
	}

	@FXML
	private void start(){
		System.out.println(chrono.state);
		if(chrono.state == 0){
			chrono.start();
		}else if (chrono.state == 1){
			chrono.pause();
		}else if (chrono.state == 2){
			chrono.resume();
		}
		System.out.println(chrono.getDureeSec());
		lbl.setText(chrono.getDureeTxt());
	}

	@FXML
	private void stop(){
		if(chrono.state == 1)
		chrono.stop();

		lbl.setText(chrono.getDureeTxt());
	}
}
