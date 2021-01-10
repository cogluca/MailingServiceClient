package utils;

import client.LoginManager;
import models.Mail;
import models.Response;
import models.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
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

    public static Response login(User user) throws IOException,ClassNotFoundException {
        Socket serverConn = getSocket();
        ObjectOutputStream sendLoginReq = new ObjectOutputStream(serverConn.getOutputStream());
        ObjectInputStream receiveLogin = new ObjectInputStream(serverConn.getInputStream());
        Response loginResult = null;

        sendLoginReq.writeUTF("LOGIN");
        sendLoginReq.flush();

        sendLoginReq.writeUTF(LoginManager.sessionId);
        sendLoginReq.flush();

        sendLoginReq.writeObject(user);
        sendLoginReq.flush();

        loginResult = (Response) receiveLogin.readObject();


        sendLoginReq.close();
        receiveLogin.close();
        serverConn.close();

        return loginResult;
    }

    public static void logout() throws IOException {

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

        if (!isOnline()) return null;

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

        if (!isOnline()) return null;

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

        if (!isOnline()) return -1;

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

    public static Response deleteMessage(Mail m) throws Exception {

        if (!isOnline()) return new Response(-1 , "Server currently offline");

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

        Response status = (Response) inputStream.readObject();

        inputStream.close();
        outputStream.close();
        socket.close();
        return status;


    }

    public static Response sendMessage (Mail mailToSend) {

        Socket serverConn = null;
        ObjectOutputStream sendMsg = null;
        ObjectInputStream receiveState = null;
        List<User> receiver = new ArrayList<>();

        Response serverResponse = null;

        try {
            serverConn = NetworkUtils.getSocket();

            sendMsg = new ObjectOutputStream(serverConn.getOutputStream());

            receiveState = new ObjectInputStream(serverConn.getInputStream());

            sendMsg.writeUTF("SEND");
            sendMsg.flush();

            sendMsg.writeUTF(LoginManager.sessionId);
            sendMsg.flush();

            sendMsg.writeObject(mailToSend);
            sendMsg.flush();


            serverResponse = (Response) receiveState.readObject();


        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return serverResponse;
    }

}
