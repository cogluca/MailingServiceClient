package client.controller.logged;

import client.model.Mail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class MessageListController implements Initializable {


    private ObservableList<Mail> mailObservableList = FXCollections.observableArrayList();

    @FXML
    private ListView mailList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadMessages();
        mailList.setItems(mailObservableList);
        mailList.setCellFactory((Callback<ListView<Mail>, ListCell<Mail>>) listView -> new MailCell());

    }

    private void loadMessages() {
        // Generate random values to fill listview.
        //TODO: Get data from server

        for(int i = 0; i < 10; i++) {
            Mail m = new Mail();
            m.setFrom("Da utente "); m.setObject("oggetto");
            m.setTo("A utente" + i ); m.setMessage("TestoTestoTestoTestoTestoTestoTestoTesto " + i );
            mailObservableList.add(m);
        }
        System.out.println(" ci son " + mailObservableList.size());
    }


}
