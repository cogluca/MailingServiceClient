package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

/**
*Model of list mails to be linked to Gui components, used as models to contain outbox and inbox messages transmitted from server
* in example : through client-server synchronization process
*
 */
public class ListMailModel {

    private ObservableList<Mail> incomingListMail = FXCollections.observableArrayList();

    private ObservableList<Mail> upcomingListMail = FXCollections.observableArrayList();


    public ObservableList<Mail> getIncomingListMail() {
        return incomingListMail;
    }

    public void setIncomingListMail(List<Mail> incomingListMail) {
        if(incomingListMail!=null) {
            this.incomingListMail.clear();
            this.incomingListMail.addAll(incomingListMail);
        }
    }

    public ObservableList<Mail> getUpcomingListMail() {
        return upcomingListMail;
    }

    public void setUpcomingListMail(List<Mail> upcomingListMail) {
        if(upcomingListMail!=null) {
            this.upcomingListMail.clear();
            this.upcomingListMail.addAll(upcomingListMail);
        }
    }
}
