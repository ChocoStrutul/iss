import controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Log;
import model.Task;
import model.User;
import repository.*;
import service.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainFX extends Application {

    private Properties properties;

    IUserRepository userRepository;
    Repository<Long, Log> logRepository;
    Repository<Long, Task> taskRepository;

    Service service;

    @Override
    public void start(Stage primaryStage) throws Exception {

        properties = new Properties();
        try {
            properties.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config " + e);
        }

        userRepository = new UserRepository(properties);
        logRepository = new LogRepository(properties);
        taskRepository = new TaskRepository(properties);

        service = new Service(userRepository, logRepository, taskRepository);

        initStage();

    }

    void initStage() throws IOException {


        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/login.fxml"));
        AnchorPane root=loader.load();

        LoginController loginController = loader.getController();
        loginController.setEnvironment(stage, service);

        stage.setScene(new Scene(root));
        stage.setTitle("Login");
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
