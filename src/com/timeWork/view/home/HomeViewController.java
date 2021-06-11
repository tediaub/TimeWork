package com.timeWork.view.home;

import java.util.ArrayList;

import com.timeWork.core.TaskXml;
import com.timeWork.core.Timer;
import com.timeWork.view.FxmlLoader;
import com.timeWork.view.ViewController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HomeViewController extends ViewController{

	@FXML
    private TextField searchText;
	@FXML
    private TextField archiveSearchText;
    @FXML
    private ListView<Timer> timerList;
    @FXML
    private ListView<Timer> archiveTimerList;

    private FilteredList<Timer> filteredData;
    private FilteredList<Timer> archiveFilteredData;

	private SortedList<Timer> sortedData;
	private SortedList<Timer> archiveSortedData;

	private ObservableList<Timer> list;
	private ObservableList<Timer> archiveList;

    @FXML
	private void initialize(){
    	timerList.setCellFactory((ListView<Timer> lv) -> new TaskListCell());
    	archiveTimerList.setCellFactory((ListView<Timer> lv) -> new TaskListCell());
    }

	public void setTimerList(ObservableList<Timer> allTimerList) {
		list = FXCollections.observableArrayList();
		archiveList = FXCollections.observableArrayList();

		for (int i = 0; i < allTimerList.size(); i++) {
			Timer timer = allTimerList.get(i);
			if(timer.isArchived()){
				archiveList.add(timer);
			}else{
				list.add(timer);
			}
		}

		allTimerList.addListener(new ListChangeListener<Timer>() {

			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Timer> c) {
				c.next();
				list.addAll(c.getAddedSubList());
			}
		});

		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
        filteredData = new FilteredList<>(list, p -> true);
        archiveFilteredData = new FilteredList<>(archiveList, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchText.textProperty().addListener(new SearchListener(searchText, filteredData));
        archiveSearchText.textProperty().addListener(new SearchListener(archiveSearchText, archiveFilteredData));

        // 3. Wrap the FilteredList in a SortedList.
        sortedData = new SortedList<Timer>(filteredData);
        archiveSortedData = new SortedList<Timer>(archiveFilteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        //sortedData.comparatorProperty().bind(deltaTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        timerList.setItems(sortedData);
        archiveTimerList.setItems(archiveSortedData);
	}

	public void createTask(){
        AnchorPane rootLayout = (AnchorPane) FxmlLoader.NEW_TASK_VIEW.getContentPane();
        Scene scene = new Scene(rootLayout, Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
	}

    @FXML
    void archiveTasks() {
    	ArrayList<Timer> timerToArchive = new ArrayList<>();
    	for (int i = 0; i < timerList.getItems().size(); i++) {
			Timer timer = timerList.getItems().get(i);
			if(timer.isSelected()){
				timer.setArchived(true);
				timer.setSelected(false);
				timerToArchive.add(timer);
			}
		}
    	list.removeAll(timerToArchive);
    	archiveList.addAll(timerToArchive);
    }

    @FXML
    void restoreTasks() {
    	ArrayList<Timer> timerToRestore = new ArrayList<>();
    	for (int i = 0; i < archiveTimerList.getItems().size(); i++) {
			Timer timer = archiveTimerList.getItems().get(i);
			if(timer.isSelected()){
				timer.setArchived(false);
				timer.setSelected(false);
				timerToRestore.add(timer);
			}
		}
    	list.addAll(timerToRestore);
    	archiveList.removeAll(timerToRestore);
    }

    @FXML
    void deleteTasks() {
    	ArrayList<Timer> timerToDelete = new ArrayList<>();
    	for (int i = 0; i < timerList.getItems().size(); i++) {
			Timer timer = timerList.getItems().get(i);
			if(timer.isSelected()){
				TaskXml.removeTasks(timer);
				timerToDelete.add(timer);
			}
		}
    	list.removeAll(timerToDelete);
    }

    @FXML
    void archiveDeleteTasks() {
    	ArrayList<Timer> timerToDelete = new ArrayList<>();
    	for (int i = 0; i < archiveTimerList.getItems().size(); i++) {
			Timer timer = archiveTimerList.getItems().get(i);
			if(timer.isSelected()){
				TaskXml.removeTasks(timer);
				timerToDelete.add(timer);
			}
		}
    	archiveList.removeAll(timerToDelete);
    }

    @FXML
    void selectAll() {
    	for (int i = 0; i < timerList.getItems().size(); i++) {
			Timer timer = timerList.getItems().get(i);
			timer.setSelected(true);
		}
    }

    @FXML
    void archiveSelectAll() {
    	for (int i = 0; i < archiveTimerList.getItems().size(); i++) {
			Timer timer = archiveTimerList.getItems().get(i);
			timer.setSelected(true);
		}
    }

    private class SearchListener implements ChangeListener<String>{
		private TextField searchTF;
		private FilteredList<Timer> filterList;

		public SearchListener(TextField searchTF, FilteredList<Timer> filterList) {
			this.searchTF = searchTF;
			this.filterList = filterList;
		}

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        	filterList.setPredicate(timer -> {
                String nameFilter = searchTF.getText();

                if(nameFilter == null || nameFilter.isEmpty() || timer.getTitleProperty().getValue().toLowerCase().contains(nameFilter.toLowerCase())){
                	return true;
                }
                return false;
            });
        }
    }
}
