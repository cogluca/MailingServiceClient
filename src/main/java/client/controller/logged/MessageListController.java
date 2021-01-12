package client.controller.logged;

import javafx.fxml.FXML;
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
    private String readType = "";

    @FXML
    private ListView<Mail> mailListView;


    @Override
    public void init() {
        super.init();

        if (readType.equals("INBOX")) {
            try {
                listMailModel.setIncomingListMail(NetworkUtils.loadInbox());
                mailListView.setItems(listMailModel.getIncomingListMail());

            } catch (Exception e) {e.printStackTrace();}
        }
        else if (readType.equals("OUTBOX")) {
            try {
                listMailModel.setUpcomingListMail(NetworkUtils.loadOutbox());
                mailListView.setItems(listMailModel.getUpcomingListMail());

            } catch (Exception e) {e.printStackTrace();}
        }
        else {
            System.out.println("ERROR: WRONG PARAMETER");
        }

        mailListView.setCellFactory(listView -> new MailCell());
    }


    @Override
    public void dispatch() {
        List<Object> arguments = getArgumentList();
        if (arguments == null || arguments.size() <= 1) return;
        readType = (String) arguments.get(0);
        listMailModel = (ListMailModel) arguments.get(1);
    }

}
