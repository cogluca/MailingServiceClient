package client.controller.logged;

import client.LoginManager;
import client.Navigator;
import javafx.scene.control.Label;
import models.ListMailModel;
import models.Mail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.web.WebView;
import models.User;
import utils.Controller;
import utils.NetworkUtils;
import utils.Utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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

    private String messageType = "";

    private Mail fromMailCell;

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initializing messageRead view");
        navigator = Navigator.getInstance();

    }

    @FXML
    public void forwardHandle(ActionEvent actionEvent) {
        List<Object> arguments = new ArrayList<>();

        User mandante = new User(sender.getText());

        arguments.add("SEND");
        arguments.add(mandante.getUsername());
        arguments.add("FWD");
        arguments.add(oggetto.getText());
        //TODO : Commentare con lista argomenti
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
        arguments.add(mandante.getUsername());
        arguments.add("ANSWERALL");
        arguments.add(oggetto.getText());
        arguments.add(everyReceiver);

        System.out.println("Answering all");
        Navigator.navigate(Navigator.Route.SEND, arguments);
    }

    @FXML
    public void deleteHandle(ActionEvent actionEvent) {

        List<Object> arguments = new ArrayList<>();

        Socket serverConn = null;
        ObjectOutputStream sendMsg = null;
        ObjectInputStream receiveState = null;
        Mail toDelete;
        List<User> receiver = new ArrayList<>();
        String serverResponse = "";

        try {
            serverConn = NetworkUtils.getSocket();

            sendMsg = new ObjectOutputStream(serverConn.getOutputStream());

            receiveState = new ObjectInputStream(serverConn.getInputStream());

            sendMsg.writeUTF("DELETE");
            sendMsg.flush();

            sendMsg.writeUTF(LoginManager.sessionId);
            sendMsg.flush();

            //toDelete = new Mail();

            //sendMsg.writeObject(toDelete);
            sendMsg.flush();

            serverResponse = receiveState.readUTF();

            Utils.getAlert(serverResponse);

        }catch(IOException e){
            System.out.println(e.getMessage());
        }

        ListMailModel listMailModel = new ListMailModel();
        messageType = "INBOX";

        arguments.add(messageType);
        arguments.add(listMailModel);

        System.out.println("Message deleted");
        Navigator.navigate(Navigator.Route.INBOX, arguments);
    }

    @Override
    public void init() {
        List<Object> arguments = new ArrayList<>();
        fromMailCell = (Mail) arguments.get(0);
        oggetto.setText(fromMailCell.getObject());
        htmlView.getEngine().loadContent(fromMailCell.getMessage());
        sender.setText(fromMailCell.getSender().getUsername());
    }
}
