package client.controller.logged;

import client.Navigator;
import javafx.scene.control.Label;
import models.Mail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.web.WebView;
import models.User;
import utils.Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReadMessage extends Controller implements Initializable {
    @Override
    protected void dispatch() {

    }
    @FXML
    private Button answerBtn;

    @FXML
    private Button forwardBtn;

    @FXML
    private Label oggetto;

    @FXML
    private Label receivers;

    @FXML
    private Label sender;

    @FXML
    private Button deletebtn;

    @FXML
    private Button answerAllBtn;

    @FXML
    private SplitPane dividerPanel;

    @FXML
    private WebView htmlView;

    private Navigator navigator;

    private Mail mail;

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initializing messageRead view");
        navigator = Navigator.getInstance();
        String html = "<html dir=\"ltr\"><head></head><body contenteditable=\"false\"><p><span style=\"font-family: &quot;&quot;;\">This</span></p><p style=\"text-align: center;\"><span style=\"font-family: &quot;&quot;;\">is</span></p><p><span style=\"font-family: &quot;&quot;; font-style: italic; font-weight: bold;\">A test message</span></p></body></html>\n";
        htmlView.getEngine().loadContent(html);
    }

    @FXML
    public void forwardHandle(ActionEvent actionEvent) {
        List<Object> arguments = new ArrayList<>();

        User mandante = new User(sender.getText());

        arguments.add("SEND");
        arguments.add(mandante);
        arguments.add("FWD");
        arguments.add(oggetto.getText());
        arguments.add(htmlView.getAccessibleText());

        System.out.println("Forwarding message");
        Navigator.navigate(Navigator.Route.SEND, arguments);

    }

    @FXML
    public void answerHandle(ActionEvent actionEvent) {
        List<Object> arguments = new ArrayList<>();

        User mandante = new User(receivers.getText());

        arguments.add("SEND");
        arguments.add(mandante);
        arguments.add("ANSWER");
        arguments.add(oggetto.getText());
        arguments.add(sender);

        System.out.println("Answering message");
        Navigator.navigate(Navigator.Route.SEND, arguments);

    }

    @FXML
    public void answerAllHandle(ActionEvent actionEvent) {

        List<Object> arguments = new ArrayList<>();

        String everyReceiver = sender.getText() + ";" + receivers.getText();

        User mandante = new User(sender.getText());

        arguments.add("SEND");
        arguments.add(mandante);
        arguments.add("ANSWERALL");
        arguments.add(oggetto.getText());
        arguments.add(everyReceiver);

        System.out.println("Answering all");
        Navigator.navigate(Navigator.Route.SEND, arguments);
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
