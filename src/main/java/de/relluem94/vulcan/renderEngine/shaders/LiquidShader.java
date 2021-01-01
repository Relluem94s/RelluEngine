package de.relluem94.vulcan.renderEngine.shaders;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import de.relluem94.vulcan.entities.components.LightComponent;
import de.relluem94.vulcan.entities.components.PositionComponent;
import de.relluem94.vulcan.toolbox.Variables;
import de.relluem94.vulcan.toolbox.maths.Maths;

public class LiquidShader extends ShaderProgram {

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition[];
    private int location_lightColour[];
    private int location_attenuation[];
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_useFakeLighting;
    private int location_skyColour;
    private int location_texturemulti;
    private int location_numberOfRows;
    private int location_offset;

    public LiquidShader() {
        super(Variables.LiquidShader_VERTEX_FILE, Variables.LiquidShader_FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_lightPosition = new int[Variables.MAX_LIGHTS];
        location_lightColour = new int[Variables.MAX_LIGHTS];
        location_attenuation = new int[Variables.MAX_LIGHTS];
        for (int i = 0; i < Variables.MAX_LIGHTS; i++) {
            location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
            location_lightColour[i] = super.getUniformLocation("lightColour[" + i + "]");
            location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
        }

        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_numberOfRows = super.getUniformLocation("numberOfRows");
        location_useFakeLighting = super.getUniformLocation("useFakeLighting");
        location_skyColour = super.getUniformLocation("skyColour");
        location_texturemulti = super.getUniformLocation("texturemulti");
        location_offset = super.getUniformLocation("offset");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

    public void loadOffset(float x, float y) {
        super.load2DVector(location_offset, new Vector2f(x, y));
    }

    public void loadNumberOfRows(int numberOfRows) {
        super.loadFloat(location_numberOfRows, numberOfRows);
    }

    public void loadTextureMultiVariables(float multi) {
        super.loadFloat(location_texturemulti, multi);
    }

    public void loadLights(List<Integer> lights) {
        for (int i = 0; i < Variables.MAX_LIGHTS; i++) {
            if (i < lights.size()) {
                int id = lights.get(i);

                Vector3f pos = PositionComponent.getPosition(id).toVector3f();
                pos.x += LightComponent.getOffset(id).x;
                pos.y += LightComponent.getOffset(id).y;
                pos.z += LightComponent.getOffset(id).z;

                super.loadVector(location_lightPosition[i], pos);
                super.loadColor(location_lightColour[i], LightComponent.getColour(id));
                super.loadVector(location_attenuation[i], LightComponent.getAttenuation(id).toVector3f());
            }
        }
    }

    public void loadSkyColour(float r, float g, float b) {
        super.loadVector(location_skyColour, new Vector3f(r, g, b));
    }

    public void loadFakeLightingVariable(boolean useFake) {
        super.loadBoolean(location_useFakeLighting, useFake);
    }

    public void loadShineVariables(float damper, float reflectivity) {
        super.loadFloat(location_shineDamper, damper);
        super.loadFloat(location_reflectivity, reflectivity);
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f projection) {
        super.loadMatrix(location_projectionMatrix, projection);
    }

//	public void loadLight(BaseEntity light){
//		if(light.hasLightComponent()){
//    		super.loadVector(location_lightPosition, light.getLightComponent().getPosition());
//            super.loadVector(location_lightColour, light.getLightComponent().getColour());
//    	}
//    	else{
//    		System.out.println("Error: No LightComponent");
//    	}
//    }
    public void loadViewMatrix() {
        Matrix4f viewMatrix = Maths.createViewMatrix();
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }

}
