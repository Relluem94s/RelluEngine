package de.relluem94.vulcan.entities.components;

import org.lwjgl.util.vector.Vector3f;

import de.relluem94.vulcan.models.TexturedModel;

public class LiquidComponent {

    private float height;
    private float x, z;
    private float scale;

    private TexturedModel model;

    private int id;

    public LiquidComponent(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void set(float centerX, float centerZ, float height, float scale, TexturedModel model) {
        this.x = centerX;
        this.z = centerZ;
        this.height = height;
        this.model = model;
        this.scale = scale;
    }

    public void setModel(TexturedModel model) {
        this.model = model;
    }

    public TexturedModel getModel() {
        return model;
    }

    public float getHeight() {
        return height;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void increaseScale(float scale) {

        this.scale += scale;

    }

    public float getX() {
        return x;
    }

    public float getZ() {
        return z;
    }

    public Vector3f getPosition() {
        return new Vector3f(x, height, z);
    }

}
