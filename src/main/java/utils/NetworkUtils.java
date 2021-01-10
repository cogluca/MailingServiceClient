package utils;

import client.LoginManager;
import models.Mail;
import models.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

public class NetworkUtils {


    private static final int port = 8189;

    /**
     * @return socket object
     */

    private static boolean online;

    public static boolean isOnline() {
        return online;
    }

    public static void setOnline(boolean online) {
        NetworkUtils.online = online;
    }

    public static Socket getSocket() {
        try {
            String address = InetAddress.getLocalHost().getHostName();
            return new Socket(address, port);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String login(User user) throws IOException {
        Socket serverConn = getSocket();
        ObjectOutputStream sendLoginReq = new ObjectOutputStream(serverConn.getOutputStream());
        ObjectInputStream receiveLogin = new ObjectInputStream(serverConn.getInputStream());
        String loginResult = "";

        sendLoginReq.writeUTF("LOGIN");
        sendLoginReq.flush();

        sendLoginReq.writeUTF(LoginManager.sessionId);
        sendLoginReq.flush();

        sendLoginReq.writeObject(user);
        sendLoginReq.flush();

        loginResult = receiveLogin.readUTF();

        sendLoginReq.close();
        receiveLogin.close();
        serverConn.close();

        return loginResult;
    }

    public static void logout() throws IOException {

        if(!isOnline()) return;

        Socket serverConn = getSocket();
        ObjectOutputStream outputStream = new ObjectOutputStream(serverConn.getOutputStream());
        ObjectInputStream inputStream = new ObjectInputStream(serverConn.getInputStream());

        outputStream.writeUTF("LOGOUT");
        outputStream.flush();

        outputStream.writeUTF(LoginManager.sessionId);
        outputStream.flush();

        outputStream.close();
        inputStream.close();
        serverConn.close();

    }

    public static List<Mail> loadInbox() throws Exception {

        if(!isOnline()) return null;

        Socket socket = NetworkUtils.getSocket();

        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

        outputStream.writeUTF("READ INBOX");
        outputStream.flush();

        outputStream.writeUTF(LoginManager.sessionId);
        outputStream.flush();

        List<Mail> retList = (List<Mail>) inputStream.readObject();

        inputStream.close();
        outputStream.close();
        socket.close();
        return retList;

    }

    public static List<Mail> loadOutbox() throws Exception {

        if(!isOnline()) return null;

        Socket socket = NetworkUtils.getSocket();

        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

        outputStream.writeUTF("READ OUTBOX");
        outputStream.flush();

        outputStream.writeUTF(LoginManager.sessionId);
        outputStream.flush();

        List<Mail> retList = (List<Mail>) inputStream.readObject();

        inputStream.close();
        outputStream.close();
        socket.close();
        return retList;

    }

    public static int checkUpdates(int clientNumber) throws Exception {

        if(!isOnline()) return -1;

        Socket socket = NetworkUtils.getSocket();

        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

        outputStream.writeUTF("SYNC");
        outputStream.flush();

        outputStream.writeUTF(LoginManager.sessionId);
        outputStream.flush();

        outputStream.writeInt(clientNumber);
        outputStream.flush();

        int val = inputStream.readInt();

        inputStream.close();
        outputStream.close();
        socket.close();
        return val;

    }

    public static int deleteMessage(Mail m) throws Exception {

        if(!isOnline()) return -1;

        Socket socket = NetworkUtils.getSocket();

        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

        outputStream.writeUTF("DELETE");
        outputStream.flush();

        outputStream.writeUTF(LoginManager.sessionId);
        outputStream.flush();

        System.out.println("il messaggio è " + m);
        outputStream.writeObject(m);
        outputStream.flush();

        int status = inputStream.readInt();

        inputStream.close();
        outputStream.close();
        socket.close();
        return status;


    }

}
