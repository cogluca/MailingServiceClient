package client.controller.logged;

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

public class MessageListController extends Controller {


    private ListMailModel listMailModel;

    private String messageType = "";

    @FXML
    private ListView<Mail> mailListView;

    String workaround;



    @Override
    public void init() {
        dispatch();

        List<Mail> mails = new ArrayList<>();

        if (messageType.equals("INBOX")) {
            try {
                mails = NetworkUtils.loadInbox();
                listMailModel.setIncomingListMail(mails);
                mailListView.setItems(listMailModel.getIncomingListMail());

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                mails = NetworkUtils.loadOutbox();
                listMailModel.setUpcomingListMail(mails);
                mailListView.setItems(listMailModel.getUpcomingListMail());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        mailListView.setItems(listMailModel.getIncomingListMail());
        mailListView.setCellFactory(listView -> new MailCell());
    }


    @Override
    public void dispatch() {
        List<Object> arguments = getArgumentList();
        if (arguments == null || arguments.size() <= 1) return;
        messageType = (String) arguments.get(0);
        listMailModel = (ListMailModel) arguments.get(1);
    }

}
