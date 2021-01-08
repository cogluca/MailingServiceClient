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
                System.out.println(m.group());
                s = m.group();
                s = s.replace("@Parallel.com", "");
                System.out.println(s);
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






