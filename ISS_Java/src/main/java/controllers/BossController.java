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

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BossController {

    public TableView<Log> tableViewEmployees;
    public TableColumn<Log, String> columnEmployeeName;
    public TableColumn<Log, String> columnEmployeeArrivalTime;
    public TextArea textAreaTaskDescription;
    public Label labelTaskSendError;
    public TextField textBoxEmployeeName;

    Stage stage;
    Service service;

    User selectedEmployee = null;

    ObservableList<Log> modelLogs = FXCollections.observableArrayList();

    public void setEnvironment(Stage stage, Service service) {

        this.stage = stage;
        this.service = service;

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
}
