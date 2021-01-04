package client.controller.login;

import client.LoginManager;
import client.Navigator;
import client.Utils;
import models.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
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
        ObjectOutputStream sendLoginReq = null;
        ObjectInputStream receiveLogin = null;
        loginManager.generateSessionID();


        try {
            User loggingUser = new User(username.getText());
            serverConn = Utils.getSocket();

            sendLoginReq = new ObjectOutputStream(serverConn.getOutputStream());


            sendLoginReq.writeUTF("LOGIN");
            sendLoginReq.writeUTF(LoginManager.sessionId);
            sendLoginReq.writeObject(loggingUser);


            sendLoginReq.flush();


            receiveLogin = new ObjectInputStream(serverConn.getInputStream());
            String loginResult = receiveLogin.readUTF();
            receiveLogin.close();

            serverConn.close();
            if (loginResult.equals("Login successfully")) {
                loginManager.showMainView(username.getText());
            }

            else{
                loginManager.popErrorDialog();   //qui devo far poppare un popup con login errato
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {

            try {
                if (sendLoginReq != null)
                    sendLoginReq.close();
                if (receiveLogin != null)
                    receiveLogin.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}
