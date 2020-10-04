package de.relluem94.vulcan.gui;

import org.lwjgl.util.vector.Vector2f;

import de.relluem94.vulcan.gui.font.GUIText;
import de.relluem94.vulcan.gui.font.TextMaster;
import de.relluem94.vulcan.main.Main;
import de.relluem94.vulcan.toolbox.Utils;
import de.relluem94.vulcan.toolbox.maths.Color3f;
import de.relluem94.vulcan.toolbox.maths.Vector4f;

public abstract class GuiElement implements IClickable{
	
	private int texture;
	private Vector2f position;
	private Vector2f scale;
	private Vector2f hover_scale;
	private Vector2f normal_scale;
	private boolean isClickable = false;
	private boolean isVisible = true;
	@SuppressWarnings("unused")
	private boolean shouldHover = true;
	private GUIText text;
	private GUIText title;
	private int id;

	
	
	public GuiElement(int texture, Vector2f position, Vector2f scale, Vector2f textoffset, boolean isClickable, boolean visible, String text) {
		this.texture = texture;
		this.position = position;
		this.scale = scale;
		this.normal_scale = scale;
		this.hover_scale = new Vector2f(scale.x + 0.02f, scale.y + 0.02f);
		this.isClickable = isClickable;
		
		float x = position.x;
		float y = -position.y;
		
		x = (((x+1)/2) -textoffset.x);
		y = (((y+1)/2) -textoffset.y);
		
		this.text = new GUIText(text, 1.3f, Main.font, new Vector2f(x,y), 0.3f, false);
		this.text.set(new Vector4f(0.2f, 0.2f, 0.4f, 0.1f), Color3f.WHITE, Color3f.WHITE);
		this.text.setVisible(visible);
		TextMaster.loadText(this.text);
	}
	
	public GuiElement(int texture, Vector2f position, Vector2f scale, Vector2f textoffset, boolean isClickable, boolean visible, String text, float linelength) {
		this.texture = texture;
		this.position = position;
		this.scale = scale;
		this.normal_scale = scale;
		this.hover_scale = new Vector2f(scale.x + 0.02f, scale.y + 0.02f);
		this.isClickable = isClickable;
		
		float x = position.x;
		float y = -position.y;
		
		x = (((x+1)/2) -textoffset.x);
		y = (((y+1)/2) -textoffset.y);
		
		this.text = new GUIText(text, 1.3f, Main.font, new Vector2f(x,y), linelength, false);
		this.text.set(new Vector4f(0.2f, 0.2f, 0.4f, 0.1f), Color3f.WHITE, Color3f.WHITE);
		this.text.setVisible(visible);
		TextMaster.loadText(this.text);
	}
	
	public GuiElement(int texture, Vector2f position, Vector2f scale, Vector2f textoffset, boolean isClickable, boolean visible, String text, String title) {
		this.texture = texture;
		this.position = position;
		this.scale = scale;
		this.normal_scale = scale;
		this.hover_scale = new Vector2f(scale.x + 0.02f, scale.y + 0.02f);
		this.isClickable = isClickable;
		
		float x = position.x;
		float y = -position.y;
		
		x = (((x+1)/2) -textoffset.x);
		y = (((y+1)/2) -textoffset.y);
		
		this.text = new GUIText(text, 1.3f, Main.font, new Vector2f(x,y), 0.3f, false);
		this.text.set(new Vector4f(0.2f, 0.2f, 0.4f, 0.1f), Color3f.WHITE, Color3f.WHITE);
		this.text.setVisible(visible);
		TextMaster.loadText(this.text);
		
		
		this.title = new GUIText(title, 1.9f, Main.font, new Vector2f(x,y -0.07f), 0.3f, false);
		this.title.set(new Vector4f(0.2f, 0.2f, 0.4f, 0.1f), Color3f.WHITE, Color3f.WHITE);
		this.title.setVisible(visible);
		TextMaster.loadText(this.title);
	}
	
	@Override
	public void onMouseOver() {
		this.scale = this.hover_scale;
	}
	
	@Override
	public void onMouseOut(){
		this.scale = this.normal_scale;
	}
	
	public void setVisible(boolean visible){
		isVisible = visible;
		if(title != null){
			Utils.updateText(title, visible);
		}
		if(text != null){
			Utils.updateText(text, visible);
		}
	}
	
	public void setCetered(boolean center){
		if(text != null){
			Utils.updateTextAlign(text, center);
		}
	}
	
	public void setText(String message){
		if(text != null){
			Utils.updateText(text, message);
		}
	}
	
	public String getText(){
		if(text != null){
			return text.getTextString();
		}
		else{
			return null;
		}
	}
	
	public void setTitle(String message){
		if(title != null){
			Utils.updateText(title, message);
		}
	}
	
	public boolean isVisible(){
		return isVisible;
	}

	public void setScale(Vector2f scale) {
		this.scale = scale;
	}
	
	public void setPosition(Vector2f pos) {
		this.position = pos;
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
