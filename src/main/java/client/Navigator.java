package client;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import utils.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Singleton Class for Navigation
 */
//TODO: Better error Handling
//TODO: Generalize and improve views routing
public class Navigator {

    public enum Route {
        MAIN,
        INBOX,
        OUTBOX,
        SEND,
        READ
    }
    private static Map<Route, String> routes;
    private Route r = Route.MAIN;

    private void initRoutes() {
        routes = new EnumMap<>(Route.class);
        routes.put(Route.MAIN, "/fxml/MainView.fxml");
        routes.put(Route.INBOX, "/fxml/ListMessages.fxml");
        routes.put(Route.OUTBOX, "/fxml/ListMessages.fxml");
        routes.put(Route.SEND, "/fxml/WriteMessage.fxml");
        routes.put(Route.READ, "/fxml/ReadMessage.fxml");
    }




    private static Navigator instance = null;

    public BorderPane contentPanel = null;

    public static void navigate(Route r) {
        navigate(r, new ArrayList<>());
    }

    public static void navigate(Route r, List<Object> arguments) {

        if(!routes.containsKey(r)) System.out.println("ERROR: Wrong route");
        String text = routes.get(r);
        if (instance.contentPanel != null) {
            try {
                FXMLLoader loader = new FXMLLoader(Navigator.class.getResource(text));

                instance.contentPanel.setCenter(loader.load());


                Controller controller = loader.getController();
                controller.setArgumentList(arguments);
                controller.init();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
            System.out.println("ERROR:Can't load panel");
    }

    public void setContentPanel(BorderPane contentPanel) {
        if(instance == null) getInstance();
        instance.contentPanel = contentPanel;
    }

    /**
     * Creates or retrieve a Singleton Navigator instance
     * @return the singleton instance
     */
    public static Navigator getInstance() {
        if (instance == null) {
            instance = new Navigator();
            instance.initRoutes();
        }
        return instance;
    }





}
