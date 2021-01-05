package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ListMailModel {

    private ObservableList incomingListMail = FXCollections.observableArrayList();

    private ObservableList upcomingListMail = FXCollections.observableArrayList();


    public ObservableList getIncomingListMail() {
        return incomingListMail;
    }

    public void setIncomingListMail(ObservableList incomingListMail) {
        this.incomingListMail = incomingListMail;
    }

    public ObservableList getUpcomingListMail() {
        return upcomingListMail;
    }

    public void setUpcomingListMail(ObservableList upcomingListMail) {
        this.upcomingListMail = upcomingListMail;
    }
}
