package client.controller;

import client.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SendMessage implements Initializable {

    @FXML
    private Button sendbtn;

    @FXML
    private Button deletebtn;

    @FXML
    private SplitPane dividerPanel;

    private Navigator navigator;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigator = Navigator.getInstance(null);
    }

    @FXML
    public void sendHandle(ActionEvent actionEvent) {
        System.out.println("Message sent");
        Navigator.navigate("/fxml/ListMessages.fxml");

    }

    @FXML
    public void deleteHandle(ActionEvent actionEvent) {
        System.out.println("Message deleted");
        Navigator.navigate("/fxml/ListMessages.fxml");


    }

}
