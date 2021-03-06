package controllers;

import exceptions.RepoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;
import service.Service;
import utils.observer.EventType;
import utils.observer.Observer;

import java.io.IOException;

public class LoginController {

    public TextField textBoxEmail;
    public TextField textBoxPassword;
    public TextField textBoxArrivalTime;
    public Label labelLoginError;
    Stage stage;
    Service service;

    public void setEnvironment(Stage stage, Service service) {

        this.stage = stage;
        this.service = service;

    }

    public void handleLogin(ActionEvent actionEvent) throws IOException {

        String email = textBoxEmail.getText();
        String password = textBoxPassword.getText();

        if(email.equals("") || password.equals("")) {
            labelLoginError.setText("Complete login information!");
            return;
        }

        User loggedInUser;
        try {
            loggedInUser = service.login(email, password);

            if (loggedInUser.getType().equals(UserType.BOSS))
                initBossWindow(loggedInUser);
            else {
                String arrivalTime = textBoxArrivalTime.getText();
                if (arrivalTime.equals("")) {
                    labelLoginError.setText("Type arrival time!");
                    return;
                }

                if (!validateTime(arrivalTime)) {
                    labelLoginError.setText("Invalid arrival time!");
                    return;
                }

                initEmployeeWindow(loggedInUser, arrivalTime);
            }
        } catch (RepoException ex) {
            labelLoginError.setText(ex.getMessage());
            return;
        }

        textBoxEmail.setText("");
        textBoxPassword.setText("");
        textBoxArrivalTime.setText("");

    }

    private boolean validateTime(String arrivalTime) {

        if(arrivalTime.length() != 5 || arrivalTime.charAt(2) != ':')
            return false;

        arrivalTime = arrivalTime.replace(":", "");

        try{
            int intArr = Integer.parseInt(arrivalTime);
            if(intArr >= 2400 || intArr % 100 >= 60)
                return false;
        } catch (Exception e) {
            return false;
        }

        return true;

    }

    private void initEmployeeWindow(User loggedInUser, String arrivalTime) throws IOException {

        Log log = new Log(arrivalTime, loggedInUser);

        service.addLog(log);

        Stage eStage = new Stage();
        FXMLLoader eLoader = new FXMLLoader();
        eLoader.setLocation(getClass().getResource("/view/employeeWindow.fxml"));
        AnchorPane eRoot=eLoader.load();

        EmployeeController employeeController = eLoader.getController();
        employeeController.setEnvironment(eStage, service, loggedInUser);

        eStage.setScene(new Scene(eRoot));
        eStage.setTitle("Employee: " + loggedInUser.getName());

        eStage.setOnCloseRequest(event -> {
            employeeController.handleLogout(null);
        });

        labelLoginError.setText("");
        eStage.show();

    }

    private void initBossWindow(User loggedInUser) throws IOException {

        Stage bStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/bossWindow.fxml"));
        AnchorPane bRoot=loader.load();

        BossController bossController = loader.getController();
        bossController.setEnvironment(bStage, service);

        bStage.setScene(new Scene(bRoot));
        bStage.setTitle("Boss");

        bStage.setOnCloseRequest(event -> {
            bossController.handleLogout(null);
        });

        labelLoginError.setText("");
        bStage.show();

    }

}
