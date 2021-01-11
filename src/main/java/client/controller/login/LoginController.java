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
            /*loggingUser.userProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {

                }
            });
             */

            //loginButton.defaultButtonProperty().bind(loggingUser.userProperty());
            /*loginButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    String user = username.getText();
                    loggingUser.setUsername(user);
                }
            });

             */

            loggingUser.userProperty().bind(username.textProperty());
            System.out.println(loggingUser.getUsername());
            loginResult = NetworkUtils.login(loggingUser);
        }
        catch (IOException | ClassNotFoundException e) {
            loginResult.setResponseText("An error occurred during login");
        }
        catch (NullPointerException exception) {
            loginResult.setResponseText("Server is offline");
        }

        if (loginResult.getResponseText().equals("Login successfully")) loginManager.showMainView(username.getText());
        else Utils.getAlert(loginResult.getResponseText());   //qui devo far poppare un popup con login errato

    }

}
