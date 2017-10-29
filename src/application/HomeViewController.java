package application;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HomeViewController {

    @FXML
    private TextField nameText;
    @FXML
    private TextField customerText;
    @FXML
    private DatePicker creationDate;
    @FXML
    private TextField timeText;
    @FXML
    private TextArea descriptionText;
    @FXML
    private ListView<Timer> timerList;

    @FXML
	private void initialize(){
    	timerList.setCellFactory((ListView<Timer> lv) -> new PrimeListCell());
    }

	public void setTimerList(ObservableList<Timer> timerList2) {
		timerList.setItems(timerList2);
	}

	public void createTask(){
		Main.addTimer(new Timer(nameText.getText(),
				customerText.getText(),
				descriptionText.getText(),
				creationDate.getValue().toString(),
				Long.parseLong(timeText.getText())));
	}
}
