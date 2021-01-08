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

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Client");
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/css/style.css");

        ((LoginController)loader.getController()).initManager(new LoginManager(scene));

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
            System.out.println("Application Closed by click to Close Button(X)");
            System.exit(0);
        }));



        //lancia l'applicazione
    }

    public static void main(String[] args) { launch(args); }
}
