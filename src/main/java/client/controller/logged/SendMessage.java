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
import models.Response;
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

    private String messageType = "";

    private User sender;

    private ListMailModel listMailModel;

    private String oggettoPassato = "";
    private String msgView = "";
    private String receivers = "";
    private String singleReceiver = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigator = Navigator.getInstance();
    }

    @FXML
    public void sendHandle(ActionEvent actionEvent) {

        Mail toSend;
        List<User> receiver = new ArrayList<>();
        long timeStamp = System.currentTimeMillis();

        dispatch();
        System.out.println("Pre invio network util");
        receiver = Utils.identifyReceivers(destinatario.getText());
        System.out.println("Print destinatari in sendHandle "+ destinatario.getText());


        System.out.println("SendHandle reiceiver" + receiver.size() );
        if (receiver.size() < 1) {
            String issuesReceivers = "One or more receivers do not exist";
            Utils.getAlert(issuesReceivers);
        }

        toSend = new Mail(timeStamp, sender, receiver, oggetto.getText(), messageEditor.getHtmlText());
        Response serverResponse = NetworkUtils.sendMessage(toSend);
        System.out.println("Post invio network util");
        System.out.println(serverResponse.getResponseText());
        Utils.getAlert(serverResponse.getResponseText());

        System.out.println("Message sent");

        List<Object> arguments = new ArrayList<>();
        listMailModel = new ListMailModel();

        arguments.add("INBOX");
        arguments.add(listMailModel);

        Navigator.navigate(Navigator.Route.INBOX, arguments);
        System.out.println(messageEditor.getHtmlText());

    }

    @FXML
    public void deleteHandle(ActionEvent actionEvent) {

        List<Object> arguments = new ArrayList<>();
        listMailModel = new ListMailModel();

        arguments.add("INBOX");
        arguments.add(listMailModel);

        System.out.println("Message deleted");
        Navigator.navigate(Navigator.Route.INBOX, arguments);
    }

    @Override
    public void init() {

        List<Object> arguments = getArgumentList();
        if (arguments.size() > 2) {
            String function = (String) arguments.get(2);

            if (function.equals("FWD")) {

                oggettoPassato = (String) arguments.get(3);
                msgView = (String) arguments.get(4);

                oggetto.setText(oggettoPassato);
                messageEditor.setHtmlText(msgView);

            } else if (function.equals("ANSWER")) {

                oggettoPassato = (String) arguments.get(3);
                singleReceiver = (String) arguments.get(4);

                oggetto.setText(oggettoPassato);
                destinatario.setText(singleReceiver + "@Parallel.com");
            } else if (function.equals("ANSWERALL")) {

                oggettoPassato = (String) arguments.get(3);
                receivers = (String) arguments.get(4);

                oggetto.setText(oggettoPassato);
                destinatario.setText(receivers);
            }
        }
    }

    @Override
    public void dispatch() {

        List<Object> arguments = getArgumentList();

        if (arguments == null || arguments.size() <= 1) return;

        messageType = (String) arguments.get(0); //adesso è ridondante
        String senderId = (String) arguments.get(1);

        System.out.println("I'm in dispatchSendMessage" + senderId);
        sender = new User(senderId);

        //listMailModel = (ListMailModel) arguments.get(2);
    }
}
