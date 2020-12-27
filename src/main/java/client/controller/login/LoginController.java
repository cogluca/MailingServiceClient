package client.controller.login;

import client.LoginManager;
import client.Navigator;
import client.model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private Button loginButton;

    private Navigator navigator;

    private LoginManager loginManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigator = Navigator.getInstance();
    }

    public void initManager(final LoginManager loginManager) {
        this.loginManager = loginManager;
    }


    public void handleLogin(ActionEvent actionEvent) {

        String user = username.getText();
        boolean authorizedUser = User.retrieveUser(user); //prendo User per poi inserire la mail nella Mainview
        if (authorizedUser || true) {
            loginManager.showMainView(user);
        }
    }
}
