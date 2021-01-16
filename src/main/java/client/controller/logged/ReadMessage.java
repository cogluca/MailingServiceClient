package client.controller.logged;

import client.Navigator;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
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

public class ReadMessage extends Controller {

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

    private Mail fromMailCell;

    @FXML
    public void forwardHandle(ActionEvent actionEvent) {

        List<Object> arguments = new ArrayList<>();
        arguments.add(fromMailCell);
        arguments.add("FWD");

        System.out.println("Forwarding message");
        Navigator.navigate(Navigator.Route.SEND, arguments);

    }

    @FXML
    public void answerHandle(ActionEvent actionEvent) {

        List<Object> arguments = new ArrayList<>();

        arguments.add(fromMailCell);
        arguments.add("ANSWER");

        System.out.println("Answering message");
        Navigator.navigate(Navigator.Route.SEND, arguments);

    }

    @FXML
    public void answerAllHandle(ActionEvent actionEvent) {

        List<Object> arguments = new ArrayList<>();

        User senderUser = Navigator.getInstance().getMainController().getUser();
        if(!fromMailCell.getReceiver().contains(senderUser))
            fromMailCell.getReceiver().add(senderUser);

        arguments.add(fromMailCell);
        arguments.add("ANSWERALL");

        System.out.println("Answering all");
        Navigator.navigate(Navigator.Route.SEND, arguments);
    }

    @FXML
    public void deleteHandle(ActionEvent actionEvent) {

        try {
            Response serverResponse = NetworkUtils.deleteMessage(fromMailCell);
            Utils.getAlert(serverResponse.getResponseText());

            if (serverResponse.getResponseCode() == 0)
                Navigator.navigate(Navigator.Route.INBOX);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
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

        String dateSent = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date (fromMailCell.getTimeSent()));
        SimpleStringProperty sentDate = new SimpleStringProperty(dateSent);
        dataOraInvio.textProperty().bind(sentDate);
    }
}
