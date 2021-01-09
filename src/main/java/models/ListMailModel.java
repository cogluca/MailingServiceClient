package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

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
