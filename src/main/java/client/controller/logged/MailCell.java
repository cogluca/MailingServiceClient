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
import utils.Controller;
import utils.NetworkUtils;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


            from.setText(mail.getSender().getUsername());
            oggetto.setText(mail.getObject() + " - " + getText(mail.getMessage()));
            String date = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date (mail.getId()));
            datainvio.setText(date);


            List<Object> arguments = new ArrayList<>();

            this.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    arguments.add(mail);
                    Navigator.navigate(Navigator.Route.READ, arguments);
                }
            });

            delete.setOnAction(actionEvent -> {
                System.out.println(getItem() + "  " + getIndex());
                try {
                    NetworkUtils.deleteMessage(getItem());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getListView().getItems().remove(getItem());

            });

            forward.setOnAction(actionEvent -> {
                Mail m = getItem();
                List<Object> argumentsToSend = new ArrayList<>();

                argumentsToSend.add("SEND");
                argumentsToSend.add(mail.getSender().getUsername());
                argumentsToSend.add("FWD");
                argumentsToSend.add(m.getObject());
                argumentsToSend.add(m.getMessage());

                Navigator.navigate(Navigator.Route.SEND, argumentsToSend);

            });


            setText(null);
            setGraphic(pane);
        }

    }
    public static String getText(String htmlText) {

        String result = "";

        Pattern pattern = Pattern.compile("<[^>]*>");
        Matcher matcher = pattern.matcher(htmlText);
        final StringBuffer text = new StringBuffer(htmlText.length());

        while (matcher.find()) {
            matcher.appendReplacement(
                    text,
                    " ");
        }

        matcher.appendTail(text);

        result = text.toString().trim();

        return result;
    }


}
