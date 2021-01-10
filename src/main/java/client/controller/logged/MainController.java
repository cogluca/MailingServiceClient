package client.controller.logged;

import client.LoginManager;
import client.Navigator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import models.ListMailModel;
import models.User;
import utils.Controller;
import utils.JavaFXUtil;
import utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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

    private String user;

    private Timeline syncWorker;
    private Timeline autoReconnectWorker;


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
        NetworkUtils.setOnline(true);

        Navigator.getInstance().setContentPanel(stackPane);

        //Navigator.navigate(Navigator.Route.INBOX);

        autoReconnectWorker = new Timeline(new KeyFrame(Duration.seconds(5), actionEvent -> {
        try {
            String response = NetworkUtils.login(new User(user));
            System.out.println(response);
            // TODO: Fix with Response object instead
            if(response.equals("Login successfully")) {
                NetworkUtils.setOnline(true);
                syncWorker.play();

                autoReconnectWorker.stop();
            }
        }
        catch (Exception ignored) {}

        }));
        autoReconnectWorker.setCycleCount(Timeline.INDEFINITE);


        syncWorker = new Timeline(
                new KeyFrame(Duration.seconds(5),
                        event -> {
                            try {
                                if (NetworkUtils.checkUpdates(listMailModel.getIncomingListMail().size()) != 0) {
                                    System.out.println("There are updates!!!" + listMailModel.getIncomingListMail());
                                    System.out.println(listMailModel.getUpcomingListMail());
                                }
                            } catch (Exception e) {
                                NetworkUtils.setOnline(false);

                                autoReconnectWorker.play();
                                syncWorker.stop();
                                // Server crashed / disconnected. Stop syncWorker and start autoReconnectWorker
                                /*TODO: In autoReconnectWorker, try to connect to the server AND if success,
                                   stop itself and restart syncWorker
                                */


                            }

                        }));
        syncWorker.setCycleCount(Timeline.INDEFINITE);
        syncWorker.play();


        readInbox.fire();
        try {
            listMailModel.setUpcomingListMail(NetworkUtils.loadOutbox());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @FXML
    void handleNewMail(ActionEvent action) {
        List<Object> arguments = new ArrayList<>();
        arguments.add("SEND");
        arguments.add(getUser());
        System.out.println("writing new message");
        Navigator.navigate(Navigator.Route.SEND, arguments);
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

        readInbox.fire();
        if (!readInbox.isSelected()) readInbox.setSelected(true);

        syncLabel.setVisible(false);
        syncButton.setStyle("");


    }
}
