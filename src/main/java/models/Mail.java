package models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Model
public class Mail implements Serializable {

    private long id;

    private User sender;
    private List<User> receiver;

    private String object;
    private String message;


    public Mail(long id, User sender, List<User> receiver, String object, String message) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.object = object;
        this.message = message;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public List<User> getReceiver() {
        return receiver;
    }

    public void setReceiver(List<User> receiver) {
        this.receiver = receiver;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "sender=" + sender +
                ", receiver=" + receiver +
                ", object='" + object + '\'' +
                ", message='" + message + '\'' +
                ", timestamp=" + id +
                '}';
    }

    public List<User> trimUsername() {
        String toTrim = "";
        for( User toTrimReceiver : receiver ) {
            toTrim = toTrimReceiver.getUsername();
            Matcher m = Pattern.compile("^[A-Za-z0-9._%+-]+@Parallel.com").matcher(toTrim);
            if (m.find())
                toTrim.replace("@Parallel.com", "");
        }
        return receiver;
    }

}
