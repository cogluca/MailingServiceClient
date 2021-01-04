package client.controller.logged;

import client.LoginManager;
import client.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import models.Mail;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MessageListController implements Initializable {


    private ObservableList<Mail> inboxList = FXCollections.observableArrayList();
    private ObservableList<Mail> outboxList = FXCollections.observableArrayList();

    @FXML
    private ListView mailList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadInbox(LoginManager.sessionId);
        loadOutbox(LoginManager.sessionId);
        //loadInbox(); //come carico la inbox ora che devo passare l'attributo utente ?
        mailList.setItems(inboxList);
        mailList.setCellFactory((Callback<ListView<Mail>, ListCell<Mail>>) listView -> new MailCell());


    }

    private void loadInbox(String sessionId) {

        Socket socket = Utils.getSocket();


        ObjectInputStream inputStream = null;

        ObjectOutputStream outputStream = null;
        try {

            System.out.println("Connection established");

            outputStream = new ObjectOutputStream(socket.getOutputStream());

            outputStream.writeUTF("READ INBOX");
            outputStream.flush();

            outputStream.writeUTF(sessionId);
            outputStream.flush();

            inputStream = new ObjectInputStream(socket.getInputStream());

            List<Mail> inList = (List<Mail>) inputStream.readObject();

            for (Mail ma : inList) {
                outboxList.add(ma);
                System.out.println(ma.toString());
            }


        } catch (IOException | ClassNotFoundException se) {
            se.printStackTrace();
        } finally {

            if (socket != null)
                try {
                    if (inputStream != null) inputStream.close();
                    if (outputStream != null) outputStream.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }



    private void loadOutbox(String sessionId) {
        Socket socket = Utils.getSocket();


        ObjectInputStream inputStream = null;

        ObjectOutputStream outputStream = null;
        try {

            System.out.println("Connection established");

            outputStream = new ObjectOutputStream(socket.getOutputStream());

            outputStream.writeUTF("READ OUTBOX");
            outputStream.flush();

            outputStream.writeUTF(sessionId);
            outputStream.flush();

            inputStream = new ObjectInputStream(socket.getInputStream());

            List<Mail> inList = (List<Mail>) inputStream.readObject();

            for (Mail ma : inList) {
                inboxList.add(ma);
                System.out.println(ma.toString());
            }


        } catch (IOException | ClassNotFoundException se) {
            se.printStackTrace();
        } finally {

            if (socket != null)
                try {
                    if (inputStream != null) inputStream.close();
                    if (outputStream != null) outputStream.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
