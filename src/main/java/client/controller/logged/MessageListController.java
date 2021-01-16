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


    private ListMailModel listMailModel;
    private String readType = "INBOX";

    @FXML
    private ListView<Mail> mailListView;


    @FXML
    private Label fromLabel;
    private boolean first = false;

    @Override
    public void init() {
        List<Object> arguments = getArgumentList();
        if (arguments != null && arguments.size() > 0) readType = (String) arguments.get(0);


        listMailModel = Navigator.getInstance().getMainController().getListMailModel();

        if (readType.equals("INBOX")) {
            try {
                listMailModel.setIncomingListMail(NetworkUtils.loadInbox());

                if(mailListView.getItems() == null) {
                    mailListView.setItems(listMailModel.getIncomingListMail());

                    first = true;
                }

            } catch (Exception e) {e.printStackTrace();}
        }
        else if (readType.equals("OUTBOX")) {
            try {
                listMailModel.setUpcomingListMail(NetworkUtils.loadOutbox());
                mailListView.setItems(listMailModel.getUpcomingListMail());
                fromLabel.setText("To");

            } catch (Exception e) {e.printStackTrace();}
        }
        else {
            System.out.println("ERROR: WRONG PARAMETER");
        }

        mailListView.setCellFactory(listView -> new MailCell());
    }

}
