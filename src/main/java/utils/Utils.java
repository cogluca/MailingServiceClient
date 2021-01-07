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
            if(m.find())
                System.out.println(m.group());
                s = m.group();
                sender = new User(s);

                receivingUsers.add(sender);



        }


        return receivingUsers;

    }


    public static Alert getAlert() {
        ButtonType confirm = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        Alert a = new Alert(Alert.AlertType.NONE, "Promote pawn to:", confirm);
        a.setTitle("Error");
        a.setResizable(true);

        return a;
    }


    public static void sendAlert(Alert a) {

        //a.setContentText("One or more receivers do not exist");
        a.showAndWait().ifPresent(response -> {
            if (response == a.confirm) {
                a.close();
            }
        });

    }

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






