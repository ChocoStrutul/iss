package controllers;

import com.sun.nio.sctp.MessageInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.*;
import service.Service;
import utils.observer.EventType;
import utils.observer.Observer;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BossController implements Observer {

    public TableView<Log> tableViewEmployees;
    public TableColumn<Log, String> columnEmployeeName;
    public TableColumn<Log, String> columnEmployeeArrivalTime;
    public TextArea textAreaTaskDescription;
    public Label labelTaskSendError;
    public TextField textBoxEmployeeName;

    private Stage stage;
    private Service service;

    User selectedEmployee = null;

    ObservableList<Log> modelLogs = FXCollections.observableArrayList();

    public void setEnvironment(Stage stage, Service service) {

        this.stage = stage;
        this.service = service;

        service.addObserver(this);

        setTableLogs();

    }

    @FXML
    public void initialize() {

        columnEmployeeName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        columnEmployeeArrivalTime.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));

        tableViewEmployees.setItems(modelLogs);
    }

    private void setTableLogs() {

        this.modelLogs.setAll(StreamSupport.stream(service.getLogs().spliterator(), false).collect(Collectors.toList()));

    }

    public void handleSendTaskButtonPressed(ActionEvent actionEvent) {

        String employeeName = textBoxEmployeeName.getText();
        String taskDescription = textAreaTaskDescription.getText();

        if(employeeName.equals("") || taskDescription.equals("")) {
            labelTaskSendError.setText("Complete all task info");
            return;
        }

        if(selectedEmployee == null){
            labelTaskSendError.setText("Select an employee");
            return;
        }

        labelTaskSendError.setText("");

        Task task = new Task(taskDescription, TaskType.PRIVATE, selectedEmployee);
        service.sendTask(task);

        Alert a = new Alert(Alert.AlertType.INFORMATION, "Task sent");
        a.show();

        textAreaTaskDescription.setText("");

    }

    public void handleSelectionChangedEmployees(MouseEvent mouseEvent) {

        Log log = tableViewEmployees.getSelectionModel().getSelectedItem();
        if(log == null)
            return;

        textBoxEmployeeName.setText(log.getEmployeeName());
        selectedEmployee = log.getEmployee();

    }

    public void handleLogout(ActionEvent actionEvent) {

        service.removeObserver(this);
        stage.close();

    }

    public void handlePostOnFeedButtonPressed(ActionEvent actionEvent) {

        String taskDescription = textAreaTaskDescription.getText();

        if(taskDescription.equals("")) {
            labelTaskSendError.setText("Complete all task info");
            return;
        }

        labelTaskSendError.setText("");

        Task task = new Task(taskDescription, TaskType.PUBLIC);
        service.postTaskOnFeed(task);

        Alert a = new Alert(Alert.AlertType.INFORMATION, "Task posted on feed");
        a.show();

        textAreaTaskDescription.setText("");

    }

    @Override
    public void update(EventType e) {

        if (e.equals(EventType.LOGOUT) || e.equals(EventType.LOGIN)) {
            setTableLogs();
        }

    }

}
