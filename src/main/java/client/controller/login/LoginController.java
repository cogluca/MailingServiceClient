package client.controller.login;

import client.LoginManager;
import client.Navigator;
import client.Utils;
import client.model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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

        Socket serverConn;

        try {

            serverConn = Utils.getSocket();
            ObjectOutputStream sendLoginReq = new ObjectOutputStream(serverConn.getOutputStream());
            sendLoginReq.writeUTF(username.getText());
            ObjectInputStream receiveLogin = new ObjectInputStream(serverConn.getInputStream());
            //se ricevo un oggetto user potrei fare get username e se è valido mostrare la Mainview

        boolean authorizedUser = User.retrieveUser(user); //prendo User per poi inserire la mail nella Mainview
        if (authorizedUser || true) {
            loginManager.showMainView(user);
        }
    }



}
