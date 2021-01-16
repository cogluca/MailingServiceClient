package utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import models.User;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    /**
     * Takes in input a String of receivers and checks if they match the mail service and canonical mail communication protocols.
     * After that returns a list with found mails
     * @param receivers receivs a String of receivers communicated by the interface receivers field
     *@return List<User> obj
     */
    public static List<User> identifyReceivers (String receivers) {

        String addresses[] = receivers.split(";\\s*");
        User sender;
        List<User> receivingUsers = new ArrayList<>();
        String s = "";

        for(int i = 0; i < addresses.length; i++){
            String addr = addresses[i];
            Matcher m = Pattern.compile("^[A-Za-z0-9._%+-]+@Parallel.com").matcher(addr);
            if(m.find()) {
                s = m.group();
                s = s.replace("@Parallel.com", "");
                sender = new User(s);

                receivingUsers.add(sender);
            }
        }

        return receivingUsers;

    }

    /**
     * Takes in input a String of receivers and checks if they match the mail service and canonical mail communication protocols.
     * After that returns a list with found mails
     * @param message takes a message param to set displayed message by popup
     *@return void
     */
    public static void getAlert(String message) {
        ButtonType confirm = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        Alert a = new Alert(Alert.AlertType.NONE, "Promote pawn to:", confirm);
        a.setTitle("Error");
        a.setResizable(true);
        a.setContentText(message);

        a.showAndWait().ifPresent(response -> {
            if (response == confirm) {
                a.close();
            }
        });

    }
    /**
     *Take a String of html like formatted text and returns plain text inside of html structure
     * @param htmlText html formatted text
     *@return String
     */
    public static String getTextFromHtml(String htmlText) {

        String result = "";

        Pattern pattern = Pattern.compile("<[^>]*>");
        Matcher matcher = pattern.matcher(htmlText);
        final StringBuffer text = new StringBuffer(htmlText.length());

        while (matcher.find()) {
            matcher.appendReplacement(
                    text,
                    " ");
        }

        matcher.appendTail(text);

        result = text.toString().trim();

        return result;
    }


}






