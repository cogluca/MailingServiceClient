package client.controller;

import client.Navigator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import javafx.event.ActionEvent;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ListView mailList;

    @FXML
    private Label inboxLabel;
    @FXML
    private Label outboxLabel;
    @FXML
    private SplitPane dividerPanel;

    @FXML
    private AnchorPane contentPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Navigator n = Navigator.getInstance(contentPane);
        Navigator.navigate("/fxml/ListMessages.fxml");

    }

    @FXML
    void newMailhandle(ActionEvent action) {
        Navigator.navigate("/fxml/WriteMessage.fxml");
    }

    @FXML
    void changeVisualization(MouseEvent event) {
        System.out.println(event.getTarget());
        Navigator.navigate("/fxml/ListMessages.fxml");

    }
}
