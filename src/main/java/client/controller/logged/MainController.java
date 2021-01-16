package client.controller.logged;

import client.LoginManager;
import client.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import models.ListMailModel;
import models.Response;
import models.User;
import utils.Controller;
import utils.JavaFXUtil;
import utils.NetworkUtils;

import java.net.URL;
import java.util.*;

//TODO: Add thread for Network request

/*
 * Controller principale della GUI. Prima di essere utilizzata deve essere
 * settato l'user loggato tramite il metodo setUser(String).
 * Sono presenti gli handler per gestire i click sui vari componenti
 * della GUI.
 */
public class MainController extends Controller implements Initializable {

    @FXML
    private ToggleGroup menu;

    @FXML
    private Label userName;

    @FXML
    private Label userEmail;

    @FXML
    private Label syncLabel;

    @FXML
    private Button syncButton;

    @FXML
    private BorderPane stackPane;

    @FXML
    private ToggleButton readInbox;

    @FXML
    private ToggleButton newMail;

    private User user = new User();

    private LoginManager loginManager;

    private Timer t1;

    private ListMailModel listMailModel;

    public ListMailModel getListMailModel() {
        return listMailModel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(String user) {

        this.user.setUsername(user);
        userName.textProperty().bind(this.user.userProperty());
        userEmail.setText(user + "@Parallel.com");

    }

    public BorderPane getStackPane() {
        return stackPane;
    }


    public void setLoginManager(LoginManager loginManager) {
        this.loginManager = loginManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        JavaFXUtil.get().addAlwaysOneSelectedSupport(menu);
        listMailModel = new ListMailModel();
        NetworkUtils.setOnline(true);

        Navigator.getInstance().setMainController(this);
        t1 = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(NetworkUtils.isOnline()) {
                    try {
                        if (NetworkUtils.checkUpdates(listMailModel.getIncomingListMail().size()) != 0) {
                            syncButton.setStyle("-fx-border-color: white; -fx-border-width: 3; -fx-border-radius:10; -fx-background-radius: 10");
                            syncLabel.setVisible(true);
                        }
                    }
                    catch (Exception e) {
                        NetworkUtils.setOnline(false);
                    }
                }
                else {
                    try {

                        Response response = NetworkUtils.login(user);

                        if(response.getResponseText().equals("Login successfully")) {
                            NetworkUtils.setOnline(true);

                        }
                    }
                    catch (Exception ignored) {}
                }
            }
        };

        t1.schedule(task, 5000,5000);

        readInbox.fire();

    }

    public void setFire(Navigator.Route r) {
        if(r.equals(Navigator.Route.INBOX))
            readInbox.setSelected(true);
        else if(r.equals(Navigator.Route.SEND))
            newMail.setSelected(true);

    }


    @FXML
    void handleNewMail(ActionEvent action) {
        System.out.println("writing new message");
        Navigator.navigate(Navigator.Route.SEND);
    }


    @FXML
    void handleInbox(ActionEvent event) {
        List<Object> arguments = new ArrayList<>();
        arguments.add("INBOX");
        arguments.add(listMailModel);
        Navigator.navigate(Navigator.Route.INBOX, arguments);

    }

    @FXML
    void handleOutbox(ActionEvent event) {
        List<Object> arguments = new ArrayList<>();
        arguments.add("OUTBOX");
        arguments.add(listMailModel);
        Navigator.navigate(Navigator.Route.OUTBOX, arguments);

    }

    public void handleLogout(ActionEvent event) {

        t1.cancel();
        loginManager.logout();


    }


    public void handleSync(ActionEvent actionEvent) {

        readInbox.fire();
        if (!readInbox.isSelected()) readInbox.setSelected(true);

        syncLabel.setVisible(false);
        syncButton.setStyle("");

    }
}
