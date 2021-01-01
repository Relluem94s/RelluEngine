package de.relluem94.vulcan.toolbox.maths;

public class Color4f {

    public static final Color4f BLACK = new Color4f(0.0F, 0.0F, 0.0F, 1.0F);
    public static final Color4f WHITE = new Color4f(1.0F, 1.0F, 1.0F, 1.0F);
    public static final Color4f BLUE = new Color4f(0.0F, 0.0F, 1.0F, 1.0F);
    public static final Color4f RED = new Color4f(1.0F, 0.0F, 0.0F, 1.0F);
    public static final Color4f GREEN = new Color4f(0.0F, 1.0F, 0.0F, 1.0F);
    public static final Color4f GRAY = new Color4f(0.5F, 0.5F, 0.5F, 1.0F);

    public Color4f(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

//	 float r = (float)(color >> 24 & 0xff) / 255F;
//   float g = (float)(color >> 16 & 0xff) / 255F;
//   float b = (float)(color >> 8 & 0xff) / 255F;
//   float a = (float)(color & 0xff) / 255F;
    public float r;
    public float g;
    public float b;
    public float a;

    public Color4f getColor() {
        return this;
    }

}
