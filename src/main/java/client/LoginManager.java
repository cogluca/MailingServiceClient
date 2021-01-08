package client;

import client.controller.logged.MainController;
import client.controller.login.ErrorLoginController;
import client.controller.login.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import utils.NetworkUtils;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginManager {

    private Scene scene;

    public LoginManager(Scene scene) {
        this.scene = scene;
    }

    public static String sessionId = "";


    public void logout() {

        try {
            NetworkUtils.logout();

        } catch (IOException exception) {
            exception.printStackTrace();
        }

        destroySession();
        showLoginScreen();
    }

    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/Login.fxml")
            );
            scene.setRoot((Parent) loader.load());
            LoginController controller =
                    loader.<LoginController>getController();
            controller.initManager(this);
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showMainView(String user) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/MainView.fxml")//need to replace with the main view
            );
            scene.setRoot((Parent) loader.load());
            MainController controller = (MainController)loader.getController();
            controller.setUser(user);
            controller.setLoginManager(this);
            //controller.initSessionID(this, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void popErrorDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/ErrorLogin.fxml"));

            scene.setRoot(loader.load());
            ErrorLoginController controller = loader.getController();
            controller.initManager(this);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void generateSessionID() {
        sessionId = generateRandom();
    }

    public void destroySession() {
        sessionId = "";
    }

    private static String generateRandom() {
        String dict = "abcdefghijklmnopqrstuvwxyz1234567890";
        int strLen = 64;
        Random rand = new Random();
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < strLen; i++) {
            int randIndex = rand.nextInt(dict.length());
            res.append(dict.charAt(randIndex));
        }
        return res.toString();
    }

}