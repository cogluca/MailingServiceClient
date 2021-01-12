package client.controller.logged;

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
import javafx.scene.web.WebView;
import models.Response;
import models.User;
import utils.Controller;
import utils.NetworkUtils;
import utils.Utils;
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
    private Label dataOraInvio;

    @FXML
    private WebView htmlView;

    private String messageType = "";

    private Mail fromMailCell;

    private User mandante;

    List<Object> arguments = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    @FXML
    public void forwardHandle(ActionEvent actionEvent) {

        arguments.add(mandante);
        arguments.add(fromMailCell);
        arguments.add("FWD");

        System.out.println("Forwarding message");
        Navigator.navigate(Navigator.Route.SEND, arguments);

    }

    @FXML
    public void answerHandle(ActionEvent actionEvent) {

        arguments.add(mandante);
        arguments.add(fromMailCell);
        arguments.add("ANSWER");

        System.out.println("Answering message");
        Navigator.navigate(Navigator.Route.SEND, arguments);

    }

    @FXML
    public void answerAllHandle(ActionEvent actionEvent) {

        if(mandante.getUsername().contains("From: "))
            mandante.setUsername(mandante.getUsername().replace("From: ",""));

        if(!fromMailCell.getReceiver().contains(mandante))
            fromMailCell.getReceiver().add(mandante);

        arguments.add(mandante);
        arguments.add(fromMailCell);
        arguments.add("ANSWERALL");

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
        htmlView.getEngine().loadContent("<body style='background-color:rgba(47,47,47, 1); color:white;' contenteditable='false'>" + fromMailCell.getMessage() + "</body>");
        sender.textProperty().bind(Bindings.concat("From: ", fromMailCell.getSender().userProperty()));
        receivers.textProperty().bind(Bindings.concat("To: ", fromMailCell.listAddresses()));

        mandante = new User();
        mandante.setUsername(sender.getText());

        String dateSent = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date (fromMailCell.getTimeSent()));
        SimpleStringProperty sentDate = new SimpleStringProperty(dateSent);

        dataOraInvio.textProperty().bind(sentDate);
    }
}
