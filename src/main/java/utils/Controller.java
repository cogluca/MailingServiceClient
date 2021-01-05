package utils;

import java.util.List;

public abstract class Controller {

    private List<Object> argumentList;

    public List<Object> getArgumentList() {
        return argumentList;
    }

    public void setArgumentList(List<Object> argumentList) {
        this.argumentList = argumentList;
    }

    public abstract void init();

    protected abstract void dispatch();
}
