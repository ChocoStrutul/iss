import controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.*;
import service.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainFX extends Application {

    static SessionFactory sessionFactory;

    private Properties properties;

    IUserRepository userRepository;
    ILogRepository logRepository;
    ITaskRepository taskRepository;

    Service service;

    @Override
    public void start(Stage primaryStage) throws Exception {

        properties = new Properties();
        try {
            properties.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config " + e);
        }

        initializeSessionFactory();

        //userRepository = new UserRepository(properties);
        userRepository = new UserRepository(properties, sessionFactory);
        //logRepository = new LogRepository(properties);
        logRepository = new LogRepository(properties, sessionFactory);
        //taskRepository = new TaskRepository(properties);
        taskRepository = new TaskRepository(properties, sessionFactory);

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

        stage.setOnCloseRequest(event -> {
            sessionFactory.close();
        });

        stage.setScene(new Scene(root));
        stage.setTitle("Login");
        stage.show();

    }

    static void initializeSessionFactory() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml") // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exception "+e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
