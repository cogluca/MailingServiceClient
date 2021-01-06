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
        this.incomingListMail.clear();
        this.incomingListMail.addAll(incomingListMail);
    }

    public ObservableList<Mail> getUpcomingListMail() {
        return upcomingListMail;
    }

    public void setUpcomingListMail(List<Mail> upcomingListMail) {
        this.incomingListMail.clear();
        this.incomingListMail.addAll(upcomingListMail);
    }
}
