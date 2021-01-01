package de.relluem94.vulcan.renderEngine.shaders;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import de.relluem94.vulcan.entities.components.LightComponent;
import de.relluem94.vulcan.entities.components.PositionComponent;
import de.relluem94.vulcan.renderEngine.ShadowMapMasterRenderer;
import de.relluem94.vulcan.toolbox.Variables;

public class NormalMappingShader extends ShaderProgram {

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPositionEyeSpace[];
    private int location_lightColour[];
    private int location_attenuation[];
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_skyColour;
    private int location_numberOfRows;
    private int location_offset;
    private int location_plane;
    private int location_modelTexture;
    private int location_normalMap;
    private int location_shadowMap;
    private int location_toShadowMapSpace;
    private int location_shadowMapSize;

    public NormalMappingShader() {
        super(Variables.NormalMappingShader_VERTEX_FILE, Variables.NormalMappingShader_FRAGMENT_FILE);
    }

    @Override
    public void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoordinates");
        super.bindAttribute(2, "normal");
        super.bindAttribute(3, "tangent");
    }

    @Override
    public void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_skyColour = super.getUniformLocation("skyColour");
        location_numberOfRows = super.getUniformLocation("numberOfRows");
        location_offset = super.getUniformLocation("offset");
        location_plane = super.getUniformLocation("plane");
        location_modelTexture = super.getUniformLocation("modelTexture");
        location_normalMap = super.getUniformLocation("normalMap");

        location_lightPositionEyeSpace = new int[Variables.MAX_LIGHTS];
        location_lightColour = new int[Variables.MAX_LIGHTS];
        location_attenuation = new int[Variables.MAX_LIGHTS];
        for (int i = 0; i < Variables.MAX_LIGHTS; i++) {
            location_lightPositionEyeSpace[i] = super.getUniformLocation("lightPositionEyeSpace[" + i + "]");
            location_lightColour[i] = super.getUniformLocation("lightColour[" + i + "]");
            location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
        }
        location_shadowMap = super.getUniformLocation("shadowMap");
        location_shadowMapSize = super.getUniformLocation("mapSize");
        location_toShadowMapSpace = super.getUniformLocation("toShadowMapSpace");
    }

    public void connectTextureUnits() {
        super.loadInt(location_modelTexture, 0);
        super.loadInt(location_normalMap, 1);
        super.loadInt(location_shadowMap, 5);
    }

    public void loadToShadowSpaceMatrix(Matrix4f matrix) {
        super.loadMatrix(location_toShadowMapSpace, matrix);
    }

    public void loadShadowMapSize() {
        super.loadFloat(location_shadowMapSize, ShadowMapMasterRenderer.SHADOW_MAP_SIZE);
    }

    public void loadClipPlane(Vector4f plane) {
        super.loadVector(location_plane, plane);
    }

    public void loadNumberOfRows(int numberOfRows) {
        super.loadFloat(location_numberOfRows, numberOfRows);
    }

    public void loadOffset(float x, float y) {
        super.load2DVector(location_offset, new Vector2f(x, y));
    }

    public void loadSkyColour(float r, float g, float b) {
        super.loadVector(location_skyColour, new Vector3f(r, g, b));
    }

    public void loadShineVariables(float damper, float reflectivity) {
        super.loadFloat(location_shineDamper, damper);
        super.loadFloat(location_reflectivity, reflectivity);
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadLights(List<Integer> lights, Matrix4f viewMatrix) {
        for (int i = 0; i < Variables.MAX_LIGHTS; i++) {
            if (i < lights.size()) {
                int id = lights.get(i);

                super.loadVector(location_lightPositionEyeSpace[i], getEyeSpacePosition(id, viewMatrix));
                super.loadColor(location_lightColour[i], LightComponent.getColour(id));
                super.loadVector(location_attenuation[i], LightComponent.getAttenuation(id).toVector3f());
            }
        }
    }

    public void loadViewMatrix(Matrix4f viewMatrix) {
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }

    public void loadProjectionMatrix(Matrix4f projection) {
        super.loadMatrix(location_projectionMatrix, projection);
    }

    public Vector3f getEyeSpacePosition(int id, Matrix4f viewMatrix) {
        Vector3f position = PositionComponent.getPosition(id).toVector3f();
        Vector4f eyeSpacePos = new Vector4f(position.x, position.y, position.z, 1f);
        Matrix4f.transform(viewMatrix, eyeSpacePos, eyeSpacePos);
        return new Vector3f(eyeSpacePos);
    }
}
