package client.controller.logged;

import client.model.Mail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MessageListController implements Initializable {


    private ObservableList<Mail> inboxList = FXCollections.observableArrayList();
    private ObservableList<Mail> outboxList = FXCollections.observableArrayList();

    @FXML
    private ListView mailList;

    private static final String host = "192.168.137.1";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadInbox(); //come carico la inbox ora che devo passare l'attributo utente ?
        mailList.setItems(inboxList);
        mailList.setCellFactory((Callback<ListView<Mail>, ListCell<Mail>>) listView -> new MailCell());

    }

    private void loadInbox(String user) {
        // Generate random values to fill listview.
        //TODO: Get data from server
        ArrayList<Mail> cachedInbox = new ArrayList<>();
        ObjectInputStream receiveInbox = null;
        ObjectOutputStream sendRequests = null;
        try {
            Socket s = new Socket(host, 8189);
            System.out.println("Connection established");
            receiveInbox = new ObjectInputStream(s.getInputStream());
            sendRequests = new ObjectOutputStream(s.getOutputStream());
            sendRequests.writeUTF(user);
            sendRequests.writeUTF("Inbox");
            cachedInbox = (ArrayList<Mail>) receiveInbox.readObject();
            for (Mail mail : cachedInbox) {
                inboxList.add(mail);
            }
        } catch (IOException | ClassNotFoundException e) { //serve più granulare per l'aggiornamento inbox costante ? granulare = singole mail
            System.out.println("Receiving inbox failed");
            System.out.println(e.getMessage());
        } finally {
            try {
                if (receiveInbox != null)
                    receiveInbox.close();
                if (sendRequests != null)
                    sendRequests.close();
            } catch (IOException ex) {
                System.out.println("closing connection failed");
                System.out.println(ex.getMessage());
            }
        }


    }
    private void loadOutbox(String user) {
        // Generate random values to fill listview.
        //TODO: Get data from server
        ArrayList<Mail> cachedInbox = new ArrayList<>();
        ObjectInputStream receiveOutbox = null;
        ObjectOutputStream sendRequests = null;
        try {
            Socket s = new Socket(host, 8189);
            System.out.println("Connection established");
            receiveOutbox = new ObjectInputStream(s.getInputStream());
            sendRequests = new ObjectOutputStream(s.getOutputStream());
            sendRequests.writeUTF(user);
            sendRequests.writeUTF("Outbox");
            cachedInbox = (ArrayList<Mail>) receiveOutbox.readObject();
            for (Mail mail : cachedInbox) {
                inboxList.add(mail);
            }
        } catch (IOException | ClassNotFoundException e) { //serve più granulare per l'aggiornamento inbox costante ? granulare = singole mail
            System.out.println("Receiving outbox failed");
            System.out.println(e.getMessage());
        } finally {
            try {
                if (receiveOutbox != null)
                    receiveOutbox.close();
                if (sendRequests != null)
                    sendRequests.close();
            } catch (IOException ex) {
                System.out.println("closing connection failed");
                System.out.println(ex.getMessage());
            }
        }


    }
}
