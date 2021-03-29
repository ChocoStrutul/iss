package controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class EmployeeController {
    public ListView listViewTasks;
    public ListView listViewFeed;
    public Button buttonAcceptPublicTask;

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
}
