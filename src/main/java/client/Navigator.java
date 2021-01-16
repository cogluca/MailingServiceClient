package client;

import client.controller.logged.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import utils.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

//TODO: Save controllers OR save MainController at least
/**
 * Singleton Class for Navigation across fxml
 */
public class Navigator {

    /**
     * Possible routes
     */
    public enum Route {
        MAIN,
        INBOX,
        OUTBOX,
        SEND,
        READ
    }

    /**
     * Here is stored the association Route -> ResourcePath (fxml)
     */
    private Map<Route, String> routes;
    private static Navigator instance = null;
    private MainController mainController = null;
    private BorderPane contentPanel = null;

    /**
     * Initialize the routes Map
     */
    private void initRoutes() {
        routes = new EnumMap<>(Route.class);
        routes.put(Route.MAIN, "/fxml/MainView.fxml");
        routes.put(Route.INBOX, "/fxml/ListMessages.fxml");
        routes.put(Route.OUTBOX, "/fxml/ListMessages.fxml");
        routes.put(Route.SEND, "/fxml/WriteMessage.fxml");
        routes.put(Route.READ, "/fxml/ReadMessage.fxml");
    }


    /**
     * Load a route inside the contentPanel. navigate across fxml
     * @param r the route element where should navigate
     */
    public static void navigate(Route r) {
        navigate(r, new ArrayList<>());
    }

    /**
     * Load a route inside the contentPanel. navigate across fxml
     * @param r the route element where should navigate
     * @param arguments a list of arguments that should be passed to the new controller
     */
    public static void navigate(Route r, List<Object> arguments) {

        if (!instance.routes.containsKey(r)) {
            System.out.println("ERROR: Wrong route");
            return;
        }

        String routeResource = instance.routes.get(r);
        if (instance.contentPanel != null) {
            try {
                FXMLLoader loader = new FXMLLoader(Navigator.class.getResource(routeResource));

                instance.contentPanel.setCenter(loader.load());

                Controller controller = loader.getController();
                controller.setArgumentList(arguments);
                controller.init();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            System.out.println("ERROR:Can't load panel");
    }

    /**
     * Setter for contentPanel
     */
    public void setContentPanel(BorderPane contentPanel) {
        if (instance == null) getInstance();
        instance.contentPanel = contentPanel;
    }

    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Creates or retrieve a Singleton Navigator instance
     *
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
