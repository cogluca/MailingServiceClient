package client.model;

import java.io.Serializable;

// Model
public class Mail implements Serializable {
    public String from, to;

    public String object;
    public String message;

    public Mail() {}

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
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
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", object='" + object + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
