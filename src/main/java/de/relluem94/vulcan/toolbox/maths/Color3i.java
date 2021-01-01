package de.relluem94.vulcan.toolbox.maths;

public class Color3i {

    public static final Color3i BLACK = new Color3i(0, 0, 0);
    public static final Color3i WHITE = new Color3i(255, 255, 255);
    public static final Color3i BLUE = new Color3i(0, 0, 255);
    public static final Color3i RED = new Color3i(255, 0, 0);
    public static final Color3i GREEN = new Color3i(0, 255, 0);
    public static final Color3i GRAY = new Color3i(128, 128, 128);
    public static final Color3i LIGHT_GREY = new Color3i(200, 200, 200);
    public static final Color3i LIME = new Color3i(127, 255, 0);
    public static final Color3i PURPLE = new Color3i(139, 0, 139);
    public static final Color3i BROWN = new Color3i(139, 69, 19);

    public static final Color3i RELLU_RED = new Color3i(227, 59, 46);
    public static final Color3i RELLU_GRAY = new Color3i(121, 116, 114);

    public Color3i(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int r;
    public int g;
    public int b;

    public Color3i getColor() {
        return this;
    }

    public int[] toArray() {
        int[] array = new int[3];
        array[0] = r;
        array[1] = g;
        array[2] = b;
        return array;
    }

    public String toString() {
        return "r" + r + ", g" + g + ", b" + b;
    }
}
