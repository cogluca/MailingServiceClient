package client;

import client.controller.logged.MainController;
import client.controller.login.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import models.User;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

//TODO: Controllare meglio showLoginScreen e showMainView. Passare User a showMainView
/**
 * Class for handling login / logout transition
 */
public class LoginManager {

    private final Scene scene;
    private final String sessionDictionary = "ABCDEFGHILMNOPQRSTUVZXWKJ1234567890";
    private final int sessionIDLen = 64;
    public static String sessionId = "";


    /**
     * Class constructor
     */
    public LoginManager(Scene scene) {
        this.scene = scene;
    }


    /**
     * generate a random sessionID
     */
    public void generateSessionID() {
        sessionId = generateRandomString();
    }

    /**
     * destroy the sessionID
     */
    private void destroySessionID() {
        sessionId = "";
    }

    /**
     * Generate a random string according to attributes class policy
     *
     * @return Random generated string
     */
    private String generateRandomString() {
        Random rand = new Random();
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < sessionIDLen; i++) {
            int randIndex = rand.nextInt(sessionDictionary.length());
            res.append(sessionDictionary.charAt(randIndex));
        }
        return res.toString();
    }


    /**
     * Do the graphical transition once the user log out
     */
    public void logout() {
        destroySessionID();
        showLoginScreen();
    }

    /**
     *
     */
    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/Login.fxml")
            );
            scene.setRoot(loader.load());
            scene.getWindow().setHeight(263);
            scene.getWindow().setWidth(459);

            LoginController controller =
                    loader.getController();
            controller.initManager(this);
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, "Login transition failed", ex);
        }
    }


    /**
     * Do the graphical transition once the user successfully log in
     * @param user user logged in
     */
    public void showMainView(String user) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/MainView.fxml")
            );
            scene.setRoot(loader.load());
            scene.getWindow().setHeight(600);
            scene.getWindow().setWidth(1000);
            MainController controller = loader.getController();
            controller.setUser(user);
            controller.setLoginManager(this);
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, "Failed loading main view", ex);
        }
    }


}