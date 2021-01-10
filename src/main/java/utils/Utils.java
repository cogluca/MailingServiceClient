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

        String addresses[] = receivers.split(";");
        User sender;
        List<User> receivingUsers = new ArrayList<>();
        String s = "";

        //Matcher m = Pattern.compile("^[A-Za-z0-9._%+-]+@Parallel.com").matcher(franco);
        //Matcher m = p.matcher(franco);

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
        System.out.println("In utils" + receivingUsers.size() );
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


        //a.setContentText("One or more receivers do not exis

/*
        List<User> sender = null;
        Scanner reader = new Scanner(senders).useDelimiter("@Parallel.com\\s*");

        while (reader.hasNext()) {
            User userToAdd = new User(reader.next());
            sender.add(userToAdd);
        }

        return sender;

 */



}






