package client;

import java.io.IOException;
import java.net.Socket;

public class Utils {

    private final static String address = "192.168.137.1";
    public static Socket s;

    public static Socket getSocket () {
        try {
            s = new Socket(address, 8189);
            return s;
        }
        catch(IOException e){
            System.out.println(e.getMessage());
            return null;
        }
    }


}
