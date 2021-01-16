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

    public static String trimUsers(String compositeLabel) {
        if(compositeLabel.contains("To: "))
            System.out.println("I'm in trimUsers");
            compositeLabel = compositeLabel.replace("To: ", "");
        if(compositeLabel.contains("@Parallel.com"))
            compositeLabel = compositeLabel.replace("@Parallel.com","");
        if(compositeLabel.contains(";"))
            compositeLabel = compositeLabel.replace(";","");
        if(compositeLabel.contains(" "))
            compositeLabel = compositeLabel.replace(" ", "");
        return compositeLabel;
    }


    public static String getText(String htmlText) {

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






