package client;


import client.controller.login.LoginController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.NetworkUtils;

import java.io.IOException;

public class Main extends Application {
    /**
     * Method used to start graphically the interface, communicate with LoginManager and implement the login phase
     *
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Client");
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/css/style.css");

        primaryStage.setScene(scene);
        LoginManager loginManager = new LoginManager(scene);
        loginManager.showLoginScreen();
        primaryStage.show();


        primaryStage.setOnHiding(event -> Platform.runLater(() -> {
            if(!LoginManager.sessionId.equals("")) {
                try {
                    NetworkUtils.logout();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
            System.exit(0);
        }));
    }

    public static void main(String[] args) { launch(args); }
}
