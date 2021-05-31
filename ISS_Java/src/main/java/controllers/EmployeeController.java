package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Log;
import model.Task;
import model.User;
import service.Service;
import utils.observer.EventType;
import utils.observer.Observer;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class EmployeeController implements Observer {
    public ListView<Task> listViewTasks;
    public ListView<Task> listViewFeed;
    public Button buttonAcceptPublicTask;
    public TextArea textAreaTaskDescription;
    public Label labelAcceptTaskError;

    private Stage stage;
    private Service service;
    private User loggedIn;

    ObservableList<Task> modelTasks = FXCollections.observableArrayList();
    ObservableList<Task> modelFeed = FXCollections.observableArrayList();

    public void setEnvironment(Stage stage, Service service, User loggedIn) {

        this.stage = stage;
        this.service = service;
        this.loggedIn = loggedIn;

        service.addObserver(this);

        setListTasks();
        setFeedTasks();

    }

    private void setListTasks() {
        this.modelTasks.setAll(StreamSupport.stream(service.getEmployeeTasks(loggedIn).spliterator(), false).collect(Collectors.toList()));
    }

    private void setFeedTasks() {

        this.modelFeed.setAll(StreamSupport.stream(service.getFeedTasks().spliterator(), false).collect(Collectors.toList()));

        if(selectedListView == 2) {

            listViewFeed.getSelectionModel().select(selectedFeedIndex);
            handleListViewFeedSelectionChanged(null);

        }

    }

    @FXML
    public void initialize() {

        listViewTasks.setItems(modelTasks);
        listViewFeed.setItems(modelFeed);

    }


    public void handleSeeTasks(ActionEvent actionEvent) {

        listViewFeed.setVisible(false);
        buttonAcceptPublicTask.setVisible(false);
        listViewTasks.setVisible(true);

    }

    public void handleSeeFeed(ActionEvent actionEvent) {

        listViewTasks.setVisible(false);
        listViewFeed.setVisible(true);
        buttonAcceptPublicTask.setVisible(true);

    }

    int selectedListView = 0;

    public void handleListViewTaskSelectionChanged(MouseEvent mouseEvent) {

        Task selected = listViewTasks.getSelectionModel().getSelectedItem();
        if(selected == null)
            return;
        textAreaTaskDescription.setText(selected.getDescription());

        selectedListView = 1;

    }


    public void handleLogout(ActionEvent actionEvent) {

        service.logout(loggedIn);
        service.removeObserver(this);
        stage.close();

    }

    private int selectedFeedIndex = 0;

    public void handleListViewFeedSelectionChanged(MouseEvent mouseEvent) {

        Task selected = listViewFeed.getSelectionModel().getSelectedItem();
        if(selected == null)
            return;

        selectedFeedIndex = listViewFeed.getSelectionModel().getSelectedIndex();
        selectedListView = 2;

        if(selected.getUid() == null)
            textAreaTaskDescription.setText(selected.getDescription() + "\n\n          - pending");
        else
            textAreaTaskDescription.setText(selected.getDescription() + "\n\n          - accepted by " + selected.getEmployee().getName());

    }

    public void handleAcceptPublicTaskButtonPressed(ActionEvent actionEvent) {

        Task selected = listViewFeed.getSelectionModel().getSelectedItem();
        if(selected == null) {

            labelAcceptTaskError.setText("Select a task from feed!");
            return;

        }

        if(selected.getUid() != null) {

            labelAcceptTaskError.setText("Task already accepted!");
            return;

        }

        selected.setUid(loggedIn.getId());
        selected.setEmployee(loggedIn);
        service.acceptFeedTask(selected);

        textAreaTaskDescription.setText(selected.getDescription() + "\n\n          - accepted by " + loggedIn.getName());
        labelAcceptTaskError.setText("");

    }

    @Override
    public void update(EventType e) {

        if(e.equals(EventType.SEND_TASK))
            setListTasks();

        if(e.equals(EventType.POST_ON_FEED) || e.equals(EventType.ACCEPT_FEED_TASK))
            setFeedTasks();

    }

}
