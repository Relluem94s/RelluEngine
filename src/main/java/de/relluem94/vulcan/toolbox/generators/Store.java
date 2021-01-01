package de.relluem94.vulcan.toolbox.generators;

public class Store {

    private Object value;

    public Store(Object value) {
        if (value == null) {
            return;
        }

        this.value = value;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
