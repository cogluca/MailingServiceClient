package client.controller.login;

import client.LoginManager;
import client.Navigator;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import models.Response;
import utils.NetworkUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.User;
import utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController {

    @FXML
    private TextField username;

    private LoginManager loginManager;

    /**
     * User to set this controller's login manager, function used in LoginManager
     * @param loginManager loginManager object
     */
    public void initManager(final LoginManager loginManager) {
        this.loginManager = loginManager;
    }

    /**
     * Bound to login button in login screen, executes the login logic and in case of failure pops up a dialog with associated error
     */
    public void handleLogin(ActionEvent actionEvent) {

        Response loginResult;
        User loggingUser = new User();

        loginManager.generateSessionID();

        try {
            loggingUser.userProperty().bind(username.textProperty());
            loginResult = NetworkUtils.login(loggingUser);
        }
        catch (IOException | ClassNotFoundException e) {
            loginResult = new Response(-1,"An error occurred during login");
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, "Couldn't verify login", e);
        }
        catch (NullPointerException exception) {
            loginResult = new Response(-2, "Server is offline");
        }

        if (loginResult.getResponseCode() == 0) loginManager.showMainView(username.getText());
        else Utils.getAlert(loginResult.getResponseText());

    }

}
