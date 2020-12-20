package de.relluem94.vulcan.renderEngine.shaders;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import de.relluem94.vulcan.toolbox.maths.Color3f;

public class PostProcessingShader extends ShaderProgram {

	private int location_resolution;
	private int location_color;
	private int location_offset;
	private int location_targetHeight;
	private int location_targetWidth;
	private int location_radius;
	private int location_softness;
	private int location_vignetteColor;

	
	public PostProcessingShader(String vertex, String fragment) {
		super(vertex, fragment);
	}

	@Override
	public void getAllUniformLocations() {	
		location_resolution = super.getUniformLocation("resolution");
		location_color = super.getUniformLocation("color");
		location_offset = super.getUniformLocation("offset");
		location_targetHeight = super.getUniformLocation("targetHeight");
		location_targetWidth = super.getUniformLocation("targetWidth");
		
		location_radius = super.getUniformLocation("RADIUS");
		location_softness = super.getUniformLocation("SOFTNESS");
		location_vignetteColor = super.getUniformLocation("vignetteColor");
	}
	
	public void loadVignetteData(float radius, float softness, Color3f vignetteColor){
		super.loadFloat(location_radius, radius);
		super.loadFloat(location_softness, softness);
		
		super.loadVector(location_vignetteColor, new Vector3f(vignetteColor.r,vignetteColor.g,vignetteColor.b));
	}
	
	public void loadTargetWidth(float width){
		super.loadFloat(location_targetWidth, width);
	}
	
	public void loadTargetHeight(float height){
		super.loadFloat(location_targetHeight, height);
	}

	@Override
	public void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	public void loadResolution(float x, float y){
		super.load2DVector(location_resolution, new Vector2f(x,y));
	}
	
	public void loadOffset(float offset){
		super.loadFloat(location_offset, offset);
	}
	
	public void loadColor(Vector3f vector){
		super.loadVector(location_color, vector);
	}
	
	public void loadColor(Color3f color){
		super.loadVector(location_color, new Vector3f(color.r,color.g,color.b));
	}
}
