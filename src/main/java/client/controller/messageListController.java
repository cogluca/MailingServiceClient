package client.controller;

import client.Navigator;
import client.model.Mail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class messageListController implements Initializable {


    @FXML
    private ListView mailList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(int i = 0; i < 10; i++) {
            Mail m = new Mail();
            m.setTo("asd");
            m.setObject("oggetto");
            m.setFrom("UtenteDa" + i );
            m.setMessage("Testo " + i );
            mailObservableList.add(m);


        }
        mailList.setItems(mailObservableList);
        mailList.setCellFactory((Callback<ListView<Mail>, ListCell<Mail>>) listView -> new MyCell());

    }

    ObservableList<Mail> mailObservableList = FXCollections.observableArrayList();



    @FXML
    void onClickItem(MouseEvent event) {
        if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            System.out.println(event.getTarget());
            if(event.getTarget().toString().contains("Mail"))
                Navigator.navigate("/fxml/WriteMessage.fxml");
        }


    }
}
