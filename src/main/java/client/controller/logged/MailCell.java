package client.controller.logged;

import client.Navigator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import models.Mail;
import models.Response;
import utils.NetworkUtils;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MailCell extends ListCell<Mail> {

    @FXML
    private Label from;

    @FXML
    private Label oggetto;

    @FXML
    private Label datainvio;

    @FXML
    private Button delete;

    @FXML
    private Button update;

    @FXML
    private Button forward;

    @FXML
    private AnchorPane pane;

    /**
     * Matches one mail to one row of Message list, fills the sections contents and handles Delete, Forward and Double click functions
     */
    @Override
    protected void updateItem(Mail mail, boolean empty) {
        super.updateItem(mail, empty);

        if (empty || mail == null) {

            setText(null);
            setGraphic(null);

        } else {
            FXMLLoader mLLoader = new FXMLLoader(getClass().getResource("/fxml/ListCell.fxml"));
            mLLoader.setController(this);

            try {
                mLLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            from.setText(mail.isSent()? mail.getReceiver().toString() : mail.getSender().getUsername() );

            oggetto.setText(mail.getObject() + " - " + Utils.getTextFromHtml(mail.getMessage()));
            String date = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date (mail.getTimeSent()));
            datainvio.setText(date);

            List<Object> arguments = new ArrayList<>();

            this.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    arguments.add(mail);
                    Navigator.navigate(Navigator.Route.READ, arguments);
                }
            });

            delete.setOnAction(actionEvent -> {
                Response deletionRes;
                try {
                    deletionRes = NetworkUtils.deleteMessage(getItem());
                    Utils.getAlert(deletionRes.getResponseText());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                getListView().getItems().remove(getItem());

            });

            forward.setOnAction(actionEvent -> {
                List<Object> argumentsToSend = new ArrayList<>();

                argumentsToSend.add(mail);
                argumentsToSend.add("FWD");

                Navigator.navigate(Navigator.Route.SEND, argumentsToSend);

            });

            setText(null);
            setGraphic(pane);
        }

    }




}
