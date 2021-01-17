package client.controller.logged;

import client.Navigator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import models.ListMailModel;
import models.Mail;
import utils.Controller;
import utils.NetworkUtils;

import java.util.List;

/**
 * Choose which list display on ListView (INBOX or OUTBOX) depending
 * on the parameter passed
 */
public class MessageListController extends Controller {

    @FXML
    private ListView<Mail> mailListView;

    @FXML
    private Label fromLabel;

    private ListMailModel listMailModel;
    private String readType = "INBOX";


    /**
     * Initializes the message list controller associated with Inbox and Outbox screens, requests server user's mails
     */
    @Override
    public void init() {
        List<Object> arguments = getArgumentList();
        if (arguments != null && arguments.size() > 0) readType = (String) arguments.get(0);


        listMailModel = Navigator.getInstance().getMainController().getListMailModel();

        if (readType.equals("INBOX")) {
            try {

                listMailModel.setIncomingListMail(NetworkUtils.loadInbox());
                mailListView.setItems(listMailModel.getIncomingListMail());


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (readType.equals("OUTBOX")) {
            try {
                listMailModel.setUpcomingListMail(NetworkUtils.loadOutbox());
                mailListView.setItems(listMailModel.getUpcomingListMail());
                fromLabel.setText("To");

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("ERROR: WRONG PARAMETER");
        }

        mailListView.setCellFactory(listView -> new MailCell());
    }

}
