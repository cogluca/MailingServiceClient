package client.controller.login;
import client.LoginManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ErrorLoginController implements Initializable {

    @FXML
    private Button closeError;

    LoginManager loginManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { };

    public void initManager(final LoginManager loginManager) {
        this.loginManager = loginManager;
    }

    public void handleError(ActionEvent actionEvent) {

        final Node source = (Node)actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
