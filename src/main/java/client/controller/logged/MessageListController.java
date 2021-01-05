package client.controller.logged;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import models.ListMailModel;
import models.Mail;
import utils.Controller;
import utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MessageListController extends Controller implements Initializable {


    private ListMailModel listMailModel;

    private String messageType = "";

    @FXML
    private ListView mailListView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @Override
    public void init() {
        dispatch();
        List<Mail> mails = new ArrayList<>();
        if (messageType.equals("INBOX")) {
            try {
                mails = NetworkUtils.loadInbox();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                mails = NetworkUtils.loadOutbox();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        listMailModel.setIncomingListMail(FXCollections.observableArrayList(mails));


        mailListView.setItems(listMailModel.getIncomingListMail());
        mailListView.setCellFactory((Callback<ListView<Mail>, ListCell<Mail>>) listView -> new MailCell());
    }


    @Override
    public void dispatch() {
        List<Object> arguments = getArgumentList();
        if (arguments == null || arguments.size() <= 1) return;
        messageType = (String) arguments.get(0);
        listMailModel = (ListMailModel) arguments.get(1);
    }

}
