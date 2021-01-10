package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class User implements Serializable {
    private transient StringProperty username;

    public User(String username) {
        this.username = new SimpleStringProperty(username);
    }


    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    private void init() {
        this.username = new SimpleStringProperty();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {

        out.writeUTF(getUsername());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        init();
        setUsername(in.readUTF());
    }


}
