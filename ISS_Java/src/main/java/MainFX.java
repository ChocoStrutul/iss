import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainFX extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        initStage();

    }

    void initStage() throws IOException {

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/login.fxml"));
        AnchorPane root=loader.load();

        stage.setScene(new Scene(root));
        stage.setTitle("Login");
        stage.show();

        Stage eStage = new Stage();
        FXMLLoader eLoader = new FXMLLoader();
        eLoader.setLocation(getClass().getResource("/view/employeeWindow.fxml"));
        AnchorPane eRoot=eLoader.load();

        eStage.setScene(new Scene(eRoot));
        eStage.setTitle("Employee");
        eStage.show();

        Stage bStage = new Stage();
        FXMLLoader bLoader = new FXMLLoader();
        bLoader.setLocation(getClass().getResource("/view/bossWindow.fxml"));
        AnchorPane bRoot=bLoader.load();

        bStage.setScene(new Scene(bRoot));
        bStage.setTitle("Boss");
        bStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
