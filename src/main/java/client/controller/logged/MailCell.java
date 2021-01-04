package client.controller.logged;

import client.Navigator;
import models.Mail;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MailCell extends ListCell<Mail> {

    @FXML
    private Label from;

    @FXML
    private Label Testo;

    @FXML
    private Button Delete;

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
                    if(!event.getTarget().toString().contains("null")) {
                        Navigator.navigate(Navigator.Route.READ);
                    }

                }

            });
            Delete.setOnAction(actionEvent -> {
                setText(null);
                setGraphic(null);
            });


            setText(null);
            setGraphic(pane);
        }

    }

}
