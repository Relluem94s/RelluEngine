package de.relluem94.vulcan.gui;

import org.lwjgl.util.vector.Matrix4f;

import de.relluem94.vulcan.renderEngine.shaders.ShaderProgram;
import de.relluem94.vulcan.toolbox.Variables;

public class GuiShader extends ShaderProgram{

	private int location_transformationMatrix;

	public GuiShader() {
		super(Variables.GuiShader_VERTEX_FILE, Variables.GuiShader_FRAGMENT_FILE);
	}
	
	public void loadTransformation(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
	
	
	

}
