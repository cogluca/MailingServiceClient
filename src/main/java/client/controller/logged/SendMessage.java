package client.controller.logged;

import client.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import models.Mail;
import models.Response;
import models.User;
import utils.Controller;
import utils.NetworkUtils;
import utils.Utils;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller of SendMessage screen, contains all the necessary parameters to send a mail to server
 */

public class SendMessage extends Controller{

    @FXML
    private Button sendbtn;

    @FXML
    private Button deletebtn;

    @FXML
    private SplitPane dividerPanel;

    @FXML
    private HTMLEditor messageEditor;

    @FXML
    private TextField destinatario;

    @FXML
    private TextField oggetto;

    private User sender;

    /**
     * Bound to send button on Write Email screen, collects the various parameters of the email to send and communicates the
     * Mail obj to the server, in case of failure pops a dialog with either server response or error from exception
     * @param actionEvent
     */
    @FXML
    public void sendHandle(ActionEvent actionEvent) {

        List<User> receiver = Utils.identifyReceivers(destinatario.getText());

        if (receiver.size() < 1) {
            String issuesReceivers = "One or more receivers do not exist";
            Utils.getAlert(issuesReceivers);
            return;
        }

        long timeStamp = System.currentTimeMillis();

        Mail toSend = new Mail(-1, sender, receiver,oggetto.getText(), messageEditor.getHtmlText(), timeStamp);

        Response serverResponse;

        try {
            serverResponse = NetworkUtils.sendMessage(toSend);
        } catch (Exception e) {
            serverResponse = new Response(-2, "Error while sending a mail");
            e.printStackTrace();
        }

        Utils.getAlert(serverResponse.getResponseText());

        if(serverResponse.getResponseCode()==0) {
            Navigator.navigate(Navigator.Route.INBOX);

        }
    }
/**
 * Routs the screen back to inbox after deleting the mail that was supposedly written
 */
    @FXML
    public void deleteHandle(ActionEvent actionEvent) {

        Navigator.navigate(Navigator.Route.INBOX);

    }

    /**
     * Initializes the Write Message screen and if previous screen was Read Messages sets this screen's field according to called function
     * 3 functions : FWD, ANSWER, ANSWERALL
     */
    @Override
    public void init() {

        List<Object> arguments = getArgumentList();
        sender = Navigator.getInstance().getMainController().getUser();

        if( arguments.size() > 1) {

            Mail fromReadMessage = (Mail) arguments.get(0);
            String function = (String) arguments.get(1);

            if (function.equals("FWD")) {

                oggetto.setText(fromReadMessage.getObject());
                messageEditor.setHtmlText(fromReadMessage.getMessage());

            } else if (function.equals("ANSWER")) {

                oggetto.setText(fromReadMessage.getObject());
                destinatario.setText(fromReadMessage.getSender().getUsername() + "@Parallel.com");

            } else if (function.equals("ANSWERALL")) {

                oggetto.setText(fromReadMessage.getObject());
                destinatario.setText(fromReadMessage.listAddresses().getValue());

            }
        }
    }
}
