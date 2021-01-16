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

public class LoginController implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private Button loginButton;

    private LoginManager loginManager;

    private Navigator navigator;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigator = Navigator.getInstance();
    }

    public void initManager(final LoginManager loginManager) {
        this.loginManager = loginManager;
    }


    public void handleLogin(ActionEvent actionEvent) {

        Response loginResult = new Response();
        User loggingUser = new User();

        loginManager.generateSessionID();

        try {
            loggingUser.userProperty().bind(username.textProperty());
            loginResult = NetworkUtils.login(loggingUser);
        }
        catch (IOException | ClassNotFoundException e) {
            loginResult.setResponseText("An error occurred during login");
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, "Couldn't verify login", e);
        }
        catch (NullPointerException exception) {
            loginResult.setResponseText("Server is offline");
        }

        if (loginResult.getResponseCode() == 0) loginManager.showMainView(username.getText());
        else Utils.getAlert(loginResult.getResponseText());

    }

}
