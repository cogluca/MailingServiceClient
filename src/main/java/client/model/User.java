package client.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Scanner;

public class User implements Serializable {

    private String email; //Franco Incomodo

    public User(String email) {

        this.email = email;
    }

    public String getEmail(){
        return this.email;
    }

    //inserire metodo per leggere user e password da file txt
    public static boolean retrieveUser(String username) {
        String path = "src/persistenceLevel";
        try {
            Scanner scan = new Scanner(new File(path + "/Users.txt"));
            while (scan.hasNextLine()) {
                if (username.equals(scan.nextLine()))
                    return true;
            }
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
