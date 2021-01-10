package client.controller.logged;

import client.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.web.WebView;
import models.ListMailModel;
import models.Mail;
import models.User;
import utils.Controller;
import utils.NetworkUtils;
import utils.Utils;

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

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //navigator = Navigator.getInstance();

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
        arguments.add(sender.getText());

        System.out.println("Answering message");
        Navigator.navigate(Navigator.Route.SEND, arguments);

    }

    @FXML
    public void answerAllHandle(ActionEvent actionEvent) {

        String everyReceiver = "";
        List<Object> arguments = new ArrayList<>();

        if(receivers.getText().contains(sender.getText()))
            everyReceiver = receivers.getText().replace("To: ","");
        else
            everyReceiver = sender.getText() + "@Parallel.com; " + receivers.getText().replace("To: ","");

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

        try {

            int serverResponse = NetworkUtils.deleteMessage(fromMailCell);

            if (serverResponse == 1)
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

        oggetto.setText("Object: " + fromMailCell.getObject());
        htmlView.getEngine().loadContent("<body style='background-color:rgba(47,47,47, 1); color:white;' contenteditable='false'>" + fromMailCell.getMessage() + "</body>");
        sender.setText(fromMailCell.getSender().getUsername());
        receivers.setText("To: " + fromMailCell.listAddresses());

        String dateSent = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date (fromMailCell.getId()));
        dataOraInvio.setText(dateSent);
    }
}
