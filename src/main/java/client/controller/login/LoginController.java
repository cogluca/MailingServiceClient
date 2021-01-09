package client.controller.login;

import client.LoginManager;
import client.Navigator;
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

        String loginResult = "";

        loginManager.generateSessionID();

        try {
            loginResult = NetworkUtils.login(new User(username.getText()));
        }
        catch (IOException exception) {
            loginResult = "An error occurred during login";
        }
        catch (NullPointerException exception) {
            loginResult = "Server is offline";
        }

        if (loginResult.equals("Login successfully")) loginManager.showMainView(username.getText());
        else Utils.getAlert(loginResult);   //qui devo far poppare un popup con login errato

    }

}
