package client.controller.logged;

import client.LoginManager;
import client.Navigator;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import models.ListMailModel;
import models.Mail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.web.WebView;
import models.Response;
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
import java.util.Date;
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
    private Label dataOraInvio;

    @FXML
    private WebView htmlView;

    private Navigator navigator;

    private Mail mail;

    private String messageType = "";

    private Mail fromMailCell;

    private User mandante;

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    List<Object> arguments = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //navigator = Navigator.getInstance();

    }

    @FXML
    public void forwardHandle(ActionEvent actionEvent) {

        arguments.add(mandante);
        arguments.add(fromMailCell);
        arguments.add("FWD");

        //TODO : Commentare con lista argomenti
        //arguments.add(htmlView.getAccessibleText());

        System.out.println("Forwarding message");
        Navigator.navigate(Navigator.Route.SEND, arguments);

    }

    @FXML
    public void answerHandle(ActionEvent actionEvent) {

        //String trimmedNewSender = Utils.trimUsers(receivers.getText());
        //System.out.println(trimmedNewSender);
        //Sto spostando la creazione del nuovo sender in avanti alla sendmessage
        //Perchè non passo più il parametro trimmedSender

        arguments.add(mandante);
        arguments.add(fromMailCell);
        arguments.add("ANSWER");

        //arguments.add(sender.getText());

        System.out.println("Answering message");
        Navigator.navigate(Navigator.Route.SEND, arguments);

    }

    @FXML
    public void answerAllHandle(ActionEvent actionEvent) {

        if(!fromMailCell.getReceiver().contains(mandante))
            fromMailCell.getReceiver().add(mandante);

        arguments.add(mandante);
        arguments.add(fromMailCell);
        arguments.add("ANSWERALL");

        //arguments.add(everyReceiver);

        System.out.println("Answering all");
        Navigator.navigate(Navigator.Route.SEND, arguments);
    }

    @FXML
    public void deleteHandle(ActionEvent actionEvent) {

        List<Object> arguments = new ArrayList<>();

        try {

            Response serverResponse = NetworkUtils.deleteMessage(fromMailCell);

            if (serverResponse.getResponseCode() == 0)
                Utils.getAlert("Successfully deleted mail");
            else
                Utils.getAlert("An error occurred deleting the mail");

        }catch(Exception e){
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

        List<Object> arguments = getArgumentList();
        fromMailCell = (Mail) arguments.get(0);

        if(fromMailCell.getReceiver().size()<2)
            answerAllBtn.setVisible(false);

        oggetto.textProperty().bind(Bindings.concat("Oggetto :", fromMailCell.objectProperty()));
        htmlView.accessibleTextProperty().bind(fromMailCell.messageProperty());
        sender.textProperty().bind(Bindings.concat("From: ", fromMailCell.getSender().userProperty()));
        receivers.textProperty().bind(Bindings.concat("To: ", fromMailCell.listAddresses()));
        //receivers.textProperty().bind(fromMailCell.listAddresses())


        //oggetto.setText("Object: " + fromMailCell.getObject());
        //htmlView.getEngine().loadContent("<body style='background-color:rgba(47,47,47, 1); color:white;' contenteditable='false'>" + fromMailCell.getMessage() + "</body>");
        //sender.setText(fromMailCell.getSender().getUsername());
        //receivers.setText("To: " + fromMailCell.listAddresses().getValue());


        mandante = new User();
        mandante.userProperty().bind(sender.textProperty());


        String dateSent = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date (fromMailCell.getId()));
        SimpleStringProperty sentDate = new SimpleStringProperty(dateSent);

        dataOraInvio.textProperty().bind(sentDate);
    }
}
