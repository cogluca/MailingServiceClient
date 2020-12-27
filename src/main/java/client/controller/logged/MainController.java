package client.controller.logged;

import client.LoginManager;
import client.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;


/*
 * Controller principale della GUI. Prima di essere utilizzata deve essere
 * settato l'user loggato tramite il metodo setUser(String).
 * Sono presenti gli handler per gestire i click sui vari componenti
 * della GUI.
 */
public class MainController implements Initializable {

    @FXML
    private Label inboxLabel;
    @FXML
    private Label outboxLabel;

    @FXML
    private BorderPane stackPane;

    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }


    private LoginManager loginManager;

    public void setLoginManager(LoginManager loginManager) {
        this.loginManager = loginManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Prima ci arrivo");

        Navigator n = Navigator.getInstance();
        System.out.println("Dopo ci arrivo");

        Navigator.setContentPanel(stackPane);
        Navigator.navigate(Navigator.Route.INBOX);

    }

    @FXML
    void handleNewMail(ActionEvent action) {
        System.out.println("writing new message");
        Navigator.navigate(Navigator.Route.SEND);
    }


    @FXML
    void handleInbox(ActionEvent event) {
        Navigator.navigate(Navigator.Route.INBOX);

    }

    @FXML
    void handleOutbox(ActionEvent event) {
        Navigator.navigate(Navigator.Route.OUTBOX);

    }

    public void handleLogout(ActionEvent event) {
        this.loginManager.showLoginScreen();

    }
}
