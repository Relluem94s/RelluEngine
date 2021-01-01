package de.relluem94.vulcan.toolbox.maths;

public class Vector2i {

    /**
     * Vector X. *
     */
    public int x;

    /**
     * Vector Y. *
     */
    public int y;

    /**
     * Vector2i constructor.
     *
     * @param x Vector X.
     * @param y Vector Y.
     */
    public Vector2i(int x, int y) {
        setTo(x, y);
    }

    public Vector2i(Vector2i v) {
        setTo(v);
    }

    /**
     * Vector2i constructor.
     */
    public Vector2i() {
        this.x = 0;
        this.y = 0;
    }

    public void setTo(Vector2i v) {
        this.x = v.x;
        this.y = v.y;

    }

    public void setTo(int x, int y) {
        this.x = x;
        this.y = y;

    }

    public void setZero() {
        this.x = 0;
        this.y = 0;

    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }

    public String toString() {
        return new String("X:" + x + " Y:" + y);
    }

}
