package client.controller.logged;

import client.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.web.HTMLEditor;
import utils.Controller;

import javax.swing.text.html.HTMLEditorKit;
import java.net.URL;
import java.util.ResourceBundle;

public class SendMessage extends Controller implements Initializable {
    @Override
    protected void dispatch() {

    }
    @FXML
    private Button sendbtn;

    @FXML
    private Button deletebtn;

    @FXML
    private SplitPane dividerPanel;

    @FXML
    private HTMLEditor messageEditor;

    private Navigator navigator;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigator = Navigator.getInstance();
    }

    @FXML
    public void sendHandle(ActionEvent actionEvent) {
        System.out.println("Message sent");
        Navigator.navigate(Navigator.Route.INBOX);
        System.out.println(messageEditor.getHtmlText());

    }

    @FXML
    public void deleteHandle(ActionEvent actionEvent) {
        System.out.println("Message deleted");
        Navigator.navigate(Navigator.Route.INBOX);
    }

    @Override
    public void init() {

    }
}
