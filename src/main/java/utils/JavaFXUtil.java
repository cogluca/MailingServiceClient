package utils;

import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

/**
 * Utility for making act GroupToggleButton as RadioButton.
 * Force a GroupButton to be selected only one per time
 */
public class JavaFXUtil {

    private static JavaFXUtil me;

    private JavaFXUtil() {
    }

    public static JavaFXUtil get() {
        if (me == null) {
            me = new JavaFXUtil();
        }
        return me;
    }

    public EventHandler<MouseEvent> consumeMouseEventFilter = (MouseEvent mouseEvent) -> {
        if (((Toggle) mouseEvent.getSource()).isSelected()) {
            mouseEvent.consume();
        }
    };

    public void addAlwaysOneSelectedSupport(final ToggleGroup toggleGroup) {
        toggleGroup.getToggles().addListener((ListChangeListener.Change<? extends Toggle> c) -> {
            while (c.next()) {
                for (final Toggle addedToggle : c.getAddedSubList()) {
                    addConsumeMouseEventFilter(addedToggle);
                }
            }
        });
        toggleGroup.getToggles().forEach(this::addConsumeMouseEventFilter);
    }

    private void addConsumeMouseEventFilter(Toggle toggle) {
        ((ToggleButton) toggle).addEventFilter(MouseEvent.MOUSE_PRESSED, consumeMouseEventFilter);
        ((ToggleButton) toggle).addEventFilter(MouseEvent.MOUSE_RELEASED, consumeMouseEventFilter);
        ((ToggleButton) toggle).addEventFilter(MouseEvent.MOUSE_CLICKED, consumeMouseEventFilter);
    }

}