package de.relluem94.vulcan.renderEngine.shaders;

import org.lwjgl.util.vector.Matrix4f;
import de.relluem94.vulcan.toolbox.Variables;

public class ShadowShader extends ShaderProgram {

    private int location_mvpMatrix;

    public ShadowShader() {
        super(Variables.ShadowShader_VERTEX_FILE, Variables.ShadowShader_FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        location_mvpMatrix = super.getUniformLocation("mvpMatrix");

    }

    public void loadMvpMatrix(Matrix4f mvpMatrix) {
        super.loadMatrix(location_mvpMatrix, mvpMatrix);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "in_position");
        super.bindAttribute(1, "in_textureCoords");
    }

}
