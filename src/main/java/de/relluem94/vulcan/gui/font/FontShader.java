package de.relluem94.vulcan.gui.font;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import de.relluem94.vulcan.renderEngine.shaders.ShaderProgram;
import de.relluem94.vulcan.toolbox.Variables;

public class FontShader extends ShaderProgram{

	
	
	private int location_colour;
	private int location_translation;
	private int location_width;
	private int location_edge;
	private int location_borderwidth;
	private int location_borderedge;
	private int location_outlinecolor;
	
	public FontShader() {
		super(Variables.FontShader_VERTEX_FILE, Variables.FontShader_FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_colour = super.getUniformLocation("colour");
		location_translation = super.getUniformLocation("translation");
		location_width = super.getUniformLocation("width");
		location_edge = super.getUniformLocation("edge");
		location_borderwidth = super.getUniformLocation("borderWidth");
		location_borderedge = super.getUniformLocation("borderEdge");
		location_outlinecolor = super.getUniformLocation("outlineColour");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}
	
	protected void loadColour(Vector3f colour){
		super.loadVector(location_colour, colour);
	}
	
	protected void loadTranslation(Vector2f translation){
		super.load2DVector(location_translation, translation);
	}

	protected void loadOutlineColour(Vector3f colour){
		super.loadVector(location_outlinecolor, colour);
	}
	
	protected void loadBorderWidth(float width){
		super.loadFloat(location_borderwidth, width);
	}
	
	protected void loadEdge(float edge){
		super.loadFloat(location_edge, edge);
	}
	
	protected void loadBorderEdge(float edge){
		super.loadFloat(location_borderedge, edge);
	}
	
	protected void loadWidth(float width){
		super.loadFloat(location_width, width);
	}
	

}
