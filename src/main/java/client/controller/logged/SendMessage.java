package client.controller.logged;

import client.LoginManager;
import client.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import jdk.jshell.execution.Util;
import models.ListMailModel;
import models.Mail;
import models.User;
import utils.Controller;
import utils.NetworkUtils;
import utils.Utils;

import javax.swing.text.html.HTMLEditorKit;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SendMessage extends Controller implements Initializable {

    @FXML
    private Button sendbtn;

    @FXML
    private Button deletebtn;

    @FXML
    private SplitPane dividerPanel;

    @FXML
    private HTMLEditor messageEditor;

    @FXML
    private TextField destinatario;

    @FXML
    private TextField oggetto;

    private Navigator navigator;

    String messageType = "";

    private User sender;

    private ListMailModel listMailModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigator = Navigator.getInstance();
    }

    @FXML
    public void sendHandle(ActionEvent actionEvent) {

        Socket serverConn = null;
        ObjectOutputStream sendMsg = null;
        ObjectInputStream receiveState = null;
        Mail toSend;
        List<User> receiver = new ArrayList<>();

        long timeStamp = System.currentTimeMillis();

        dispatch();

        try {
            serverConn = NetworkUtils.getSocket();

            sendMsg = new ObjectOutputStream(serverConn.getOutputStream());

            receiveState = new ObjectInputStream(serverConn.getInputStream());

            sendMsg.writeUTF("SEND");
            sendMsg.flush();

            sendMsg.writeUTF(LoginManager.sessionId);
            sendMsg.flush();

            receiver = Utils.identifyReceivers(destinatario.getText());

            if(receiver == null) {
                String issuesReceivers = "One or more receivers do not exist";
                Utils.getAlert(issuesReceivers);
            }
            else {
                toSend = new Mail(timeStamp, sender, receiver, oggetto.getText(), messageEditor.getHtmlText());

                sendMsg.writeObject(toSend);
                sendMsg.flush();
            }

            String receipt = receiveState.readUTF();
            System.out.println(receipt);

            Utils.getAlert(receipt);

        }catch(IOException e){
            System.out.println(e.getMessage());
        }

        System.out.println("Message sent");
        List<Object> arguments = new ArrayList<>();
        arguments.add("INBOX");
        listMailModel = new ListMailModel();
        arguments.add(listMailModel);
        Navigator.navigate(Navigator.Route.INBOX, arguments);
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

    @Override
    public void dispatch() {
        List<Object> arguments = getArgumentList();
        if (arguments == null || arguments.size() <= 1) return;
        messageType = (String) arguments.get(0);
        String senderId = (String) arguments.get(1);
        sender = new User(senderId);
        //listMailModel = (ListMailModel) arguments.get(2);
    }
}
