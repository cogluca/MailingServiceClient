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

    private static boolean online = false;

    public static boolean isOnline() {
        return online;
    }

    public static void setOnline(boolean online) {
        NetworkUtils.online = online;
    }

    /**
     * @return socket object
     */

    public static Socket getSocket() {
        try {
            String address = InetAddress.getLocalHost().getHostName();
            return new Socket(address, port);
        } catch (IOException e) {
            setOnline(false);
            return null;
        }
    }

    /**
     * Tries to log the user in through server communication
     *
     * @param user input the user to check if has an account
     * @return Response obj forwarded from server
     */
    public static Response login(User user) throws IOException, ClassNotFoundException {
        Socket serverConn = getSocket();
        ObjectOutputStream sendLoginReq = new ObjectOutputStream(serverConn.getOutputStream());
        ObjectInputStream receiveLogin = new ObjectInputStream(serverConn.getInputStream());
        Response loginResult;

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

    /**
     * Tries to logout the current user by communicating it to the server
     */
    public static void logout() throws IOException {

        Socket serverConn = isOnline() ? getSocket() : null;
        if (serverConn != null) {

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
    }

    /**
     * Tries to load inbox by requesting current user inbox to the server
     *
     * @return List<Mail> obj
     */
    public static List<Mail> loadInbox() throws Exception {

        List<Mail> retList = null;
        Socket socket = isOnline() ? NetworkUtils.getSocket() : null;
        if (socket != null) {

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            outputStream.writeUTF("READ INBOX");
            outputStream.flush();

            outputStream.writeUTF(LoginManager.sessionId);
            outputStream.flush();

            retList = (List<Mail>) inputStream.readObject();

            inputStream.close();
            outputStream.close();
            socket.close();
        }
        return retList;

    }

    /**
     * Tries to load outbox by requesting current user inbox to the server
     *
     * @return List<Mail> obj
     */
    public static List<Mail> loadOutbox() throws Exception {

        List<Mail> retList = null;
        Socket socket = isOnline() ? NetworkUtils.getSocket() : null;
        if (socket != null) {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            outputStream.writeUTF("READ OUTBOX");
            outputStream.flush();

            outputStream.writeUTF(LoginManager.sessionId);
            outputStream.flush();

            retList = (List<Mail>) inputStream.readObject();

            inputStream.close();
            outputStream.close();
            socket.close();
        }
        return retList;

    }

    /**
     * Tries to load inbox by requesting current user inbox to the server
     *
     * @param clientNumber number of mails in client
     * @return List<Mail> obj
     */
    public static int checkUpdates(int clientNumber) throws Exception {

        int val = -1;
        Socket socket = isOnline() ? NetworkUtils.getSocket() : null;
        if (socket != null) {

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            outputStream.writeUTF("SYNC");
            outputStream.flush();

            outputStream.writeUTF(LoginManager.sessionId);
            outputStream.flush();

            outputStream.writeInt(clientNumber);
            outputStream.flush();

            val = inputStream.readInt();

            inputStream.close();
            outputStream.close();
            socket.close();
        }
        return val;

    }

    /**
     * Tries to delete chosen mail by requesting deletion to the server that in turn responds with a Response obj
     *
     * @param mailToDelete mail object to be deleted
     * @return Response obj
     */

    public static Response deleteMessage(Mail mailToDelete) throws Exception {

        Response retResponse = new Response(-1, "Server currently offline");

        Socket socket = isOnline() ? NetworkUtils.getSocket() : null;
        if (socket != null) {

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            outputStream.writeUTF("DELETE");
            outputStream.flush();

            outputStream.writeUTF(LoginManager.sessionId);
            outputStream.flush();

            outputStream.writeObject(mailToDelete);
            outputStream.flush();

            retResponse = (Response) inputStream.readObject();

            inputStream.close();
            outputStream.close();
            socket.close();
        }
        return retResponse;


    }

    /**
     * Tries to send a message by communicating to server the mail taken in input, in turn server responds with a Response obj
     *
     * @param mailToSend mail object to be send
     * @return Response obj
     */
    public static Response sendMessage(Mail mailToSend) throws Exception {

        Response serverResponse = new Response(-1, "Server currently offline");

        Socket serverConn = isOnline() ? NetworkUtils.getSocket() : null;
        if (serverConn != null) {
            ObjectOutputStream sendMsg;
            ObjectInputStream receiveState;

            sendMsg = new ObjectOutputStream(serverConn.getOutputStream());

            receiveState = new ObjectInputStream(serverConn.getInputStream());

            sendMsg.writeUTF("SEND");
            sendMsg.flush();

            sendMsg.writeUTF(LoginManager.sessionId);
            sendMsg.flush();

            sendMsg.writeObject(mailToSend);
            sendMsg.flush();


            serverResponse = (Response) receiveState.readObject();
        }

        return serverResponse;
    }

}
