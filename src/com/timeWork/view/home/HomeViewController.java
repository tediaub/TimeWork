package com.timeWork.view.home;

import java.util.ArrayList;

import com.timeWork.core.Task;
import com.timeWork.core.TaskComparator;
import com.timeWork.core.TaskXml;
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
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HomeViewController extends ViewController{

	@FXML
    private TextField searchText;
	@FXML
    private TextField archiveSearchText;
    @FXML
    private ListView<Task> timerList;
    @FXML
    private ListView<Task> archiveTimerList;

    private FilteredList<Task> filteredData;
    private FilteredList<Task> archiveFilteredData;

	private SortedList<Task> sortedData;
	private SortedList<Task> archiveSortedData;

	private ObservableList<Task> list;
	private ObservableList<Task> archiveList;

    @FXML
	private void initialize(){
    	timerList.setCellFactory((ListView<Task> lv) -> new TaskListCell());
    	archiveTimerList.setCellFactory((ListView<Task> lv) -> new TaskListCell());
    }

	public void setTimerList(ObservableList<Task> allTimerList) {
		list = FXCollections.observableArrayList();
		archiveList = FXCollections.observableArrayList();

		for (int i = 0; i < allTimerList.size(); i++) {
			Task timer = allTimerList.get(i);
			if(timer.isArchived()){
				archiveList.add(timer);
			}else{
				list.add(timer);
			}
		}

		allTimerList.addListener(new ListChangeListener<Task>() {

			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Task> c) {
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
        sortedData = new SortedList<Task>(filteredData, new TaskComparator());
        archiveSortedData = new SortedList<Task>(archiveFilteredData, new TaskComparator());

        // 4. Bind the SortedList comparator to the TableView comparator.
        //sortedData.comparatorProperty().bind(timerList.comparatorProperty());

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
        //stage.getIcons().add(new Image(getClass().getResourceAsStream("../icon.png")));
        stage.setTitle("Nouvelle tâche");
        stage.show();
	}

    @FXML
    void archiveTasks() {
    	ArrayList<Task> timerToArchive = new ArrayList<>();
    	for (int i = 0; i < timerList.getItems().size(); i++) {
			Task timer = timerList.getItems().get(i);
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
    	ArrayList<Task> timerToRestore = new ArrayList<>();
    	for (int i = 0; i < archiveTimerList.getItems().size(); i++) {
			Task timer = archiveTimerList.getItems().get(i);
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
    	ArrayList<Task> timerToDelete = new ArrayList<>();
    	for (int i = 0; i < timerList.getItems().size(); i++) {
			Task timer = timerList.getItems().get(i);
			if(timer.isSelected()){
				TaskXml.removeTasks(timer);
				timerToDelete.add(timer);
			}
		}
    	list.removeAll(timerToDelete);
    }

    @FXML
    void archiveDeleteTasks() {
    	ArrayList<Task> timerToDelete = new ArrayList<>();
    	for (int i = 0; i < archiveTimerList.getItems().size(); i++) {
			Task timer = archiveTimerList.getItems().get(i);
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
			Task timer = timerList.getItems().get(i);
			timer.setSelected(true);
		}
    }

    @FXML
    void archiveSelectAll() {
    	for (int i = 0; i < archiveTimerList.getItems().size(); i++) {
			Task timer = archiveTimerList.getItems().get(i);
			timer.setSelected(true);
		}
    }

    private class SearchListener implements ChangeListener<String>{
		private TextField searchTF;
		private FilteredList<Task> filterList;

		public SearchListener(TextField searchTF, FilteredList<Task> filterList) {
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
