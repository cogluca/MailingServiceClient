package utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import models.User;

import java.io.BufferedReader;
import java.util.List;
import java.util.Scanner;

public class Utils {

    public static List<User> identifySenders (String senders) {

        int i = 0;
        int state = 0;
        String s = "";
        String result = "";
        List<User> sendingUsers = null;

        while (state >= 0 && i < senders.length()) {
            char ch = senders.charAt(i++);
            switch (state) {
                case 0:
                    if (Character.isLetter(ch) || Character.isDigit(ch)) {
                        state = 0;
                        result += ch;
                    } else if (ch == '.') {
                        state = 1;
                        result += ch;
                    } else
                        state = -1;
                    break;
                case 1:
                    if (Character.isLetterOrDigit(ch) || Character.isDigit(ch)) {
                        state = 1;
                        result += ch;
                    } else if (ch == '@') {
                        state = 2;
                        result += ch;
                    }
                    else
                        state = -1;
                    break;
                case 2:
                    if (ch == 'P') {
                        while (!Character.isWhitespace(ch)) {
                            s = s + ch;
                            ch = senders.charAt(i++);
                        }
                        if (s.equals("Parallel.com")) {
                            state = 3;
                            sendingUsers.add(new User(result));
                        }
                    } else
                        state = -1;
                    break;
                case 3:
                    if (Character.isWhitespace(ch)) {
                        while (Character.isWhitespace(ch)) {
                            ch = senders.charAt(i++);
                        }
                    }
                    if (Character.isDigit(ch) || Character.isLetter(ch)) {
                        result = "";
                        s = "";
                        state = 0;
                    }
                    else
                        state = -1;
                    break;
                case -1:
                    return sendingUsers;

            }
        }
        return sendingUsers;
    }

    //public static createMail(String mittente, String oggetto, String destinatario, )


    public static void sendAlert() {
        ButtonType confirm = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        Alert a = new Alert(Alert.AlertType.NONE, "Promote pawn to:", confirm);
        a.setTitle("Error");
        a.setResizable(true);
        a.setContentText("One or more senders do not exist");
        a.showAndWait().ifPresent(response -> {
            if (response == confirm) {
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






