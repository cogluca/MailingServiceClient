package client;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Navigator {

    private static Navigator instance = null;

    public String nav = "/fxml/mainView.fxml";

    public AnchorPane pan = null;

    private Navigator() {    }

    public static void navigate(String text) {
        if(instance.pan != null) {
            try {
                AnchorPane lowerAnchorPane = new FXMLLoader(Navigator.class.getResource(text)).load();
                instance.pan.getChildren().setAll(lowerAnchorPane);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }


    public static Navigator getInstance(AnchorPane pan)
    {
        if (instance == null) {
            instance = new Navigator();
            instance.pan = pan;
        }
        return instance;
    }
}
