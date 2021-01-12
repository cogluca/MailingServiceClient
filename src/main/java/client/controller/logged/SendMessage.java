package client.controller.logged;

import client.LoginManager;
import client.Navigator;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    }

    @FXML
    public void sendHandle(ActionEvent actionEvent) {

        Mail toSend = new Mail();
        List<User> receiver = new ArrayList<>();

        dispatch();

        System.out.println("Questo è il destinatario" + destinatario.getText());
        receiver = Utils.identifyReceivers(destinatario.getText());
        System.out.println(receiver);

        if (receiver.size() < 1) {
            String issuesReceivers = "One or more receivers do not exist";
            Utils.getAlert(issuesReceivers);
        }

        //QUESTO DOVREBBE ESSERE IL PROBLEMA DELLA SERIALIZZAZIONE LATO SERVER MANCATA

        //ObservableList<User> observableList = FXCollections.observableList(receiver);
        //receiversToSend = new SimpleListProperty<User>(observableList);
        //receiversToSend.setAll(receiver); //deve fare una semplice cosa, settare in questa observable list
        long timeStamp = System.currentTimeMillis();

        toSend.setReceiver(receiver);
        toSend.setSender(sender);
        toSend.setSent(false);
        toSend.setId(-1);
        toSend.setTimeSent(timeStamp);
        toSend.setObject(oggetto.getText());
        toSend.setMessage(messageEditor.getHtmlText());

        Response serverResponse = NetworkUtils.sendMessage(toSend);

        System.out.println(serverResponse.getResponseText());
        Utils.getAlert(serverResponse.getResponseText());

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
        sender = (User) arguments.get(0);

        if( arguments.size() > 1) {

            Mail fromReadMessage = (Mail) arguments.get(1);
            String function = (String) arguments.get(2);

            if (function.equals("FWD")) {

                oggetto.setText(fromReadMessage.getObject());
                messageEditor.setHtmlText(fromReadMessage.getMessage());

                //oggetto.textProperty().bind(fromReadMessage.objectProperty());
                //messageEditor.accessibleTextProperty().bind(fromReadMessage.messageProperty());

            } else if (function.equals("ANSWER")) {

                oggetto.setText(fromReadMessage.getObject());
                destinatario.setText(fromReadMessage.getSender().getUsername() + "@Parallel.com");

                //oggetto.textProperty().bind(fromReadMessage.objectProperty());
                //destinatario.textProperty().bind(Bindings.concat(fromReadMessage.getSender().userProperty(),"@Parallel.com"));

            } else if (function.equals("ANSWERALL")) {

                oggetto.setText("To: " + fromReadMessage.getObject());
                destinatario.setText(fromReadMessage.listAddresses().getValue());
                System.out.println("readMessage AnswerAll "+fromReadMessage.listAddresses().getValue());

                //oggetto.textProperty().bind(fromReadMessage.objectProperty());
                //destinatario.textProperty().bind(fromReadMessage.listAddresses());

            }
        }
    }

    @Override
    public void dispatch() {

        List<Object> arguments = getArgumentList();

        if (arguments == null || arguments.size() <= 1) return;

        //sender = (User) arguments.get(0);

        //listMailModel = (ListMailModel) arguments.get(2);
    }
}
