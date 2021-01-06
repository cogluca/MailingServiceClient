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
import utils.NetworkUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MailCell extends ListCell<Mail> {

    @FXML
    private Label from;

    @FXML
    private Label Testo;

    @FXML
    private Button delete;

    @FXML
    private Button update;

    @FXML
    private AnchorPane pane;


    @Override
    protected void updateItem(Mail mail, boolean empty) {
        super.updateItem(mail, empty);

        if(empty || mail == null) {

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
            Testo.setText(mail.getMessage());

            this.setOnMouseClicked(event -> {
                if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    System.out.println("Clickato sulla mail " + mail);
                        Navigator.navigate(Navigator.Route.READ);
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

            update.setOnAction(actionEvent -> {
                Mail m = getItem();
                List<Object> arguments = new ArrayList<>();
                arguments.add(m);
                Navigator.navigate(Navigator.Route.SEND, arguments);

            });


            setText(null);
            setGraphic(pane);
        }

    }

}
