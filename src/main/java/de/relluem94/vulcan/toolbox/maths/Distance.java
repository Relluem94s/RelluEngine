package de.relluem94.vulcan.toolbox.maths;

public class Distance {

    public float distance;
    public final static int BEHIND = 0;
    public final static int INTERSECTS = 1;
    public final static int FRONT = 2;

    private int state;

    public Distance() {
        distance = 0f;
    }

    /**
     * @param state the state to set
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * @return the state
     */
    public int getState() {
        return state;
    }
}
