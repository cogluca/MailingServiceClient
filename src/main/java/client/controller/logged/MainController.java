package client.controller.logged;

import client.LoginManager;
import client.Navigator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import models.ListMailModel;
import utils.Controller;
import utils.JavaFXUtil;
import utils.NetworkUtils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.io.IOException;
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
public class  MainController extends Controller implements Initializable {

    @FXML
    private ToggleGroup menu;

    @FXML
    private Label userName;

    @FXML
    private Label userEmail;

    @FXML
    private BorderPane stackPane;

    private String user;


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



        Navigator n = Navigator.getInstance();

        listMailModel = new ListMailModel();

        Navigator.setContentPanel(stackPane);
        //Navigator.navigate(Navigator.Route.INBOX);

        Timeline fiveSecondsWonder = new Timeline(
                new KeyFrame(Duration.seconds(5),
                        event -> {
                            try {
                                if(NetworkUtils.checkUpdates(listMailModel.getIncomingListMail().size()) == 0) {
                                    System.out.println("No updates");
                                }
                                else {
                                    System.out.println("There are updates!!!");
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();


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
        new Thread(() -> {
            try {
                NetworkUtils.logout();
                this.loginManager.showLoginScreen();

            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }).start();

    }


    @Override
    protected void dispatch() {

    }

    @Override
    public void init() {

    }

    public void handleSync(ActionEvent actionEvent) {
        try {
            listMailModel.setIncomingListMail((NetworkUtils.loadInbox()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
