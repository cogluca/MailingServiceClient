package client.controller.logged;

import client.LoginManager;
import client.Navigator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import models.ListMailModel;
import utils.Controller;
import utils.JavaFXUtil;
import utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


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
    private BorderPane stackPane;

    private String user;

    private Timeline syncWorker;


    private ListMailModel listMailModel;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
        userName.setText(user);
        userEmail.setText(user + "@Parallel.com");
    }


    private LoginManager loginManager;

    public void setLoginManager(LoginManager loginManager) {
        this.loginManager = loginManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        JavaFXUtil.get().addAlwaysOneSelectedSupport(menu);
        listMailModel = new ListMailModel();

        Navigator.getInstance().setContentPanel(stackPane);

        //Navigator.navigate(Navigator.Route.INBOX);

        syncWorker = new Timeline(
                new KeyFrame(Duration.seconds(5),
                        event -> {
                            try {
                                if(NetworkUtils.checkUpdates(listMailModel.getIncomingListMail().size()) != 0) {
                                    System.out.println("There are updates!!!" + listMailModel.getIncomingListMail());
                                    System.out.println(listMailModel.getUpcomingListMail());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }));
        syncWorker.setCycleCount(Timeline.INDEFINITE);
        syncWorker.play();


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
        syncWorker.stop();
        new Thread(() -> {

            loginManager.logout();
        }).start();

    }


    public void handleSync(ActionEvent actionEvent) {
        try {
            listMailModel.setIncomingListMail((NetworkUtils.loadInbox()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
