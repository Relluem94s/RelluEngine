package de.relluem94.vulcan.gui;

import org.lwjgl.util.vector.Vector2f;

public class GuiTexture{
	
	private int texture;
	private Vector2f position;
	private Vector2f scale;
	private boolean isClickable = false;
	private boolean isVisible = true;
	
	private int id;

	
	
	public GuiTexture(int texture, Vector2f position, Vector2f scale, boolean isClickable) {
		this.texture = texture;
		this.position = position;
		this.scale = scale;
		this.isClickable = isClickable;
	}
	
	public void setVisible(boolean visible){
		isVisible = visible;
	}
	
	public boolean isVisible(){
		return isVisible;
	}

	public void setScale(Vector2f scale) {
		this.scale = scale;
	}

	public int getTexture() {
		return texture;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setClickable(boolean isClickable){
		this.isClickable = isClickable;
	}
	
	public boolean isClickable(){
		return isClickable;
	}
	
	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getScale() {
		return scale;
	}
}
