package utils;

import java.util.List;

/**
 * Abstract controller prototype.
 * Every controller MUST extends this class.
 * Provides an argument list of objects
 * which will be useful for passing data
 * from a controller to another.
 * Also provides methods for initialize
 * the controller once the arguments are loaded
 */
public abstract class Controller {

    private List<Object> argumentList;

    public List<Object> getArgumentList() {
        return argumentList;
    }

    public void setArgumentList(List<Object> argumentList) {
        this.argumentList = argumentList;
    }

    public void init() {    }

}
