package client.controller.logged;

import client.Navigator;
import client.model.Mail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class ReadMessage implements Initializable {

    @FXML
    private Button sendbtn;

    @FXML
    private Button deletebtn;

    @FXML
    private SplitPane dividerPanel;

    @FXML
    private WebView htmlView;

    private Navigator navigator;

    private Mail mail;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("asdas");
        navigator = Navigator.getInstance();
        //System.out.println(mail.toString());
        String html = "<html dir=\"ltr\"><head></head><body contenteditable=\"false\"><p><span style=\"font-family: &quot;&quot;;\">This</span></p><p style=\"text-align: center;\"><span style=\"font-family: &quot;&quot;;\">is</span></p><p><span style=\"font-family: &quot;&quot;; font-style: italic; font-weight: bold;\">A test message</span></p></body></html>\n";
        htmlView.getEngine().loadContent(html);
    }

    @FXML
    public void sendHandle(ActionEvent actionEvent) {
        System.out.println("Message sent");
        Navigator.navigate(Navigator.Route.INBOX);

    }

    @FXML
    public void deleteHandle(ActionEvent actionEvent) {
        System.out.println("Message deleted");
        Navigator.navigate(Navigator.Route.INBOX);


    }

}
