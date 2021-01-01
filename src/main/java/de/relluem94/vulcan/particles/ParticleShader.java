package de.relluem94.vulcan.particles;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import de.relluem94.vulcan.renderEngine.shaders.ShaderProgram;
import de.relluem94.vulcan.toolbox.Variables;

public class ParticleShader extends ShaderProgram {

    private int location_numberOfRows;
    private int location_projectionMatrix;
    private int location_skyColour;

    public ParticleShader() {
        super(Variables.ParticleShader_VERTEX_FILE, Variables.ParticleShader_FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_numberOfRows = super.getUniformLocation("numberOfRows");
        location_skyColour = super.getUniformLocation("skyColour");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "modelViewMatrix");
        super.bindAttribute(5, "texOffsets");
        super.bindAttribute(6, "poblendFactorsition");
    }

    public void loadNumberOfRows(float rows) {
        super.loadFloat(location_numberOfRows, rows);
    }

    public void loadSkyColour(float r, float g, float b) {
        super.loadVector(location_skyColour, new Vector3f(r, g, b));
    }

    protected void loadProjectionMatrix(Matrix4f projectionMatrix) {
        super.loadMatrix(location_projectionMatrix, projectionMatrix);
    }

}
