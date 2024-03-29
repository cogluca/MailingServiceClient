package client.controller.logged;

import client.Navigator;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;
import models.Mail;
import models.Response;
import models.User;
import utils.Controller;
import utils.NetworkUtils;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Read Message screen's controller contains 4 button handlers and an initialization
 */

public class ReadMessage extends Controller {


    @FXML
    private Label oggetto;

    @FXML
    private Label receivers;

    @FXML
    private Label sender;

    @FXML
    private Button answerAllBtn;

    @FXML
    private Label dataOraInvio;

    @FXML
    private WebView htmlView;

    private Mail fromMailCell;

    /**
     * Handles the forward function bound to forward button on screen, communicates to subsequent screen (Write Message)
     * read message and function requested
     */

    @FXML
    public void forwardHandle(ActionEvent actionEvent) {

        List<Object> arguments = new ArrayList<>();
        arguments.add(fromMailCell);
        arguments.add("FWD");

        System.out.println("Forwarding message");
        Navigator.navigate(Navigator.Route.SEND, arguments);

    }

    /**
     * Handles the answer function bound to answer button on screen, communicates to subsequent screen (Write Message)
     * read message and function requested
     */
    @FXML
    public void answerHandle(ActionEvent actionEvent) {

        List<Object> arguments = new ArrayList<>();

        arguments.add(fromMailCell);
        arguments.add("ANSWER");

        System.out.println("Answering message");
        Navigator.navigate(Navigator.Route.SEND, arguments);

    }

    /**
     * Handles the answer all function bound to answer button on screen, communicates to subsequent screen (Write Message)
     * read message and function requested
     */
    @FXML
    public void answerAllHandle(ActionEvent actionEvent) {

        List<Object> arguments = new ArrayList<>();

        User senderUser = Navigator.getInstance().getMainController().getUser();
        if (!fromMailCell.getReceiver().contains(senderUser))
            fromMailCell.getReceiver().add(senderUser);

        arguments.add(fromMailCell);
        arguments.add("ANSWERALL");

        System.out.println("Answering all");
        Navigator.navigate(Navigator.Route.SEND, arguments);
    }

    /**
     * Handles the delete button on screen, requests email deletion to server and if successful routes to inbox screen and controller
     */
    @FXML
    public void deleteHandle(ActionEvent actionEvent) {

        try {
            Response serverResponse = NetworkUtils.deleteMessage(fromMailCell);
            Utils.getAlert(serverResponse.getResponseText());

            if (serverResponse.getResponseCode() == 0)
                Navigator.navigate(Navigator.Route.INBOX);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Initializes Read Message screen with parameters communicated from Mail Cell on double click, displays the requested message
     */
    @Override
    public void init() {

        List<Object> arguments = getArgumentList();
        fromMailCell = (Mail) arguments.get(0);

        if (fromMailCell.getReceiver().size() < 2)
            answerAllBtn.setVisible(false);

        oggetto.textProperty().bind(Bindings.concat("Object: ", fromMailCell.objectProperty()));
        htmlView.getEngine().loadContent("<body style='background-color:rgba(47,47,47, 1); color:white;' contenteditable='false'>" + fromMailCell.getMessage() + "</body>");
        sender.textProperty().bind(Bindings.concat("From: ", fromMailCell.getSender().userProperty()));
        receivers.textProperty().bind(Bindings.concat("To: ", fromMailCell.listAddresses()));

        String dateSent = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date(fromMailCell.getTimeSent()));
        SimpleStringProperty sentDate = new SimpleStringProperty(dateSent);
        dataOraInvio.textProperty().bind(sentDate);
    }
}
