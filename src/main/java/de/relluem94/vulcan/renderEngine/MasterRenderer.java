package de.relluem94.vulcan.renderEngine;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import de.relluem94.vulcan.entities.components.ControlComponent;
import de.relluem94.vulcan.entities.components.ModelComponent;
import de.relluem94.vulcan.entities.components.PositionComponent;
import de.relluem94.vulcan.entities.components.TerrainComponent;
import de.relluem94.vulcan.main.Main;
import de.relluem94.vulcan.models.ModelTexture;
import de.relluem94.vulcan.models.RawModel;
import de.relluem94.vulcan.models.TexturedModel;
import de.relluem94.vulcan.renderEngine.shaders.LiquidShader;
import de.relluem94.vulcan.renderEngine.shaders.NormalMappingShader;
import de.relluem94.vulcan.renderEngine.shaders.StaticShader;
import de.relluem94.vulcan.renderEngine.shaders.TerrainShader;
import de.relluem94.vulcan.toolbox.maths.Maths;

public class MasterRenderer {
	
	public static final float FOV = 70;
	public static final float NEAR_PLANE = 0.1f;
	public static final float FAR_PLANE = 360;

	public static float RED = 0.1f;
	public static float GREEN = 0.1f;
	public static float BLUE = 0.1f;
	
	public static float Day_RED = 2f;
	public static float Day_GREEN = 1.5f;
	public static float Day_BLUE = 1f;
	
	public static float Night_RED = 0f;
	public static float Night_GREEN = 0f;
	public static float Night_BLUE = 0f;
	
	private static Matrix4f projectionMatrix;
	private Matrix4f transformationMatrix;

	private StaticShader staticshader = new StaticShader();
	private TerrainShader terrainshader = new TerrainShader();
	private LiquidShader liquidshader = new LiquidShader();
	private NormalMappingShader nmshader = new NormalMappingShader();
	
	private static boolean culling = false;
	
	private ShadowMapMasterRenderer shadowMapRenderer;
//	private float time = 0;
	
	public MasterRenderer() {
		enableCulling();
		createProjectionMatrix();
		useMipmapping(Main.mipmapping);
		makeSkyColor();
		shadowMapRenderer = new ShadowMapMasterRenderer();
	}
	
	private void makeSkyColor(){
//		time += DisplayManager.getFrameTimeSeconds() * 1000;
//		time %= 44000;	
//		if(time >= 0 && time < 8000){
//			RED += Day_RED;
//			GREEN += Day_GREEN;
//			BLUE += Day_BLUE;
//			if(RED > Day_RED){
//				RED = Day_RED;
//			}
//			if(GREEN > Day_GREEN){
//				GREEN = Day_GREEN;
//			}
//			if(BLUE > Day_BLUE){
//				BLUE = Day_BLUE;
//			}
//			Log.log("Color 1: " + RED + " " + BLUE + " " + GREEN, 0);
//		}else if(time >= 8000 && time < 21000){
//			RED = Day_RED;
//			GREEN = Day_GREEN;
//			BLUE = Day_BLUE;
//			Log.log("Color 2: " + RED + " " + BLUE + " " + GREEN, 0);
//		}else{
//			RED -= Day_RED;
//			GREEN -= Day_GREEN;
//			BLUE -= Day_BLUE;
//			if(RED < Night_RED){
//				RED = Night_RED;
//			}
//			if(GREEN > Night_GREEN){
//				GREEN = Night_GREEN;
//			}
//			if(BLUE > Night_BLUE){
//				BLUE = Night_BLUE;
//			}
//			Log.log("Color 3: " + RED + " " + BLUE + " " + GREEN, 0);
//		}
//		
		RED = Day_RED;
		GREEN = Day_GREEN;
		BLUE = Day_BLUE;
	}
	
	/**
	 * Update before ParticleMaster
	 * 
	 */
	public static void updateProjectionMatrix(){
		createProjectionMatrix();
	}
	
	public static void enableCulling() {
		if(!culling){
			culling = true;
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glCullFace(GL11.GL_BACK);
		}
	}

	public static void disableCulling() {
		if(culling){
			culling = false;
			GL11.glDisable(GL11.GL_CULL_FACE);
		}
	}
	
	public static RawModel getRawModel(int modelID) {
		return Main.models.get(modelID);
	}

	public static ModelTexture getTexture(int textureID) {
		return Main.textures.get(textureID);
	}
	
	
	
	public void renderScene() {
		if(!ControlComponent.polymode){
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		}
		else{
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
		}
		
		renderTerrains();
		//renderLiquids();
		renderEntities();
		renderNormalMappedEntities();
		renderShadows();
		
		if(!ControlComponent.polymode){
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
		}
	}
	
	public void renderShadows(){
		shadowMapRenderer.render();
	}
	
	public void renderTerrains() {
		if(!(Main.terrains.size() == 0)){
			prepareTerrain();
			for (int i = 0; i < Main.terrains.size(); i++){
				renderTerrain(Main.terrains.get(i), terrainshader);
			}
			terrainshader.stop();
			finish();
		}
	}
	
//	public void renderLiquids(){
//		if(!(Main.liquids.size() == 0)){
//			prepareLiquid();
//			for (int i = 0; i < Main.liquids.size(); i++){
//				renderLiquid(Main.liquids.get(i), liquidshader);
//			}
//			liquidshader.stop();
//			finish();
//		}
//	}
	
	public void renderEntities() {
		if(!(Main.entities.size() == 0)){
			prepareStatic();
			
//			if(!(Main.terrains.size() == 0)){
//				for(int i = 0; i < Main.terrains.size(); i++){
//					List<Integer> batch = TerrainComponent.getEntitiesList(Main.terrains.get(i));
//					for(int j = 0; j < batch.size(); j++){
//						int id = batch.get(j);
//						
//						
//						RawModel model = getRawModel(ModelComponent.getModel(id).getModelID());
//						ModelTexture texture = getTexture(ModelComponent.getModel(id).getTextureID());
//						GL30.glBindVertexArray(model.getVaoID());	
//						if(texture.hasTransparency()){
//				        	disableCulling();
//				        }
//				        else{
//				        	enableCulling();
//				        }
//						
//						staticshader.loadTextureMultiVariables(texture.getTextureMulti());
//						staticshader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
//						staticshader.loadNumberOfRows(texture.getNumberOfRows());
//				        staticshader.loadHasShadow(ModelComponent.hasShadow(id));
//						staticshader.loadOffset(ModelComponent.getTextureXOffset(id), ModelComponent.getTextureYOffset(id));
//				        staticshader.loadFakeLightingVariable(texture.isUseFakeLighting());
//				        staticshader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
//				        GL13.glActiveTexture(GL13.GL_TEXTURE0);
//				        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
//				        if(ModelComponent.getRender(id)){
//							transformationMatrix = Maths.createTransformationMatrix(PositionComponent.getPosition(id).toVector3f(), PositionComponent.getRotation(id).toVector3f(), PositionComponent.getScale(id));
//							staticshader.loadTransformationMatrix(transformationMatrix);
//					        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
//						}
//					}
//				}
//			}
			
			
			
			for(TexturedModel texmodel: Main.texmodels.keySet()){
				RawModel model = getRawModel(texmodel.getModelID());
				ModelTexture texture = getTexture(texmodel.getTextureID());
				GL30.glBindVertexArray(model.getVaoID());
				int id = Main.texmodels.get(texmodel).get(0);	
				if(texture.hasTransparency()){
		        	disableCulling();
		        }
		        else{
		        	enableCulling();
		        }
				
				staticshader.loadTextureMultiVariables(texture.getTextureMulti());
				staticshader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
				staticshader.loadNumberOfRows(texture.getNumberOfRows());
		        staticshader.loadHasShadow(ModelComponent.hasShadow(id));
				staticshader.loadOffset(ModelComponent.getTextureXOffset(id), ModelComponent.getTextureYOffset(id));
		        staticshader.loadFakeLightingVariable(texture.isUseFakeLighting());
		        staticshader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		        GL13.glActiveTexture(GL13.GL_TEXTURE0);
		        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());

				for(int i = 0; i < Main.texmodels.get(texmodel).size(); i++){
					id = Main.texmodels.get(texmodel).get(i);
					if(ModelComponent.getRender(id)){
						transformationMatrix = Maths.createTransformationMatrix(PositionComponent.getPosition(id).toVector3f(), PositionComponent.getRotation(id).toVector3f(), PositionComponent.getScale(id));
						staticshader.loadTransformationMatrix(transformationMatrix);
				        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
					}
				}
			}
			
			
			
			
			staticshader.stop();
			finish();
		}
	}
	
	public void renderNormalMappedEntities() {
		prepareNormal();	
		for (int i = 0; i < Main.normalMapEntities.size(); i++){
			int id = Main.normalMapEntities.get(i);
			nmshader.loadShineVariables(getTexture(ModelComponent.getModel(id).getTextureID()).getShineDamper(), getTexture(ModelComponent.getModel(id).getTextureID()).getReflectivity());
			nmshader.loadNumberOfRows(getTexture(ModelComponent.getModel(id).getTextureID()).getNumberOfRows());
			nmshader.loadOffset(ModelComponent.getTextureXOffset(id), ModelComponent.getTextureYOffset(id));
			renderNormalEntity(id, nmshader);
		}
		nmshader.stop();
		finish();
	}
	
	private void renderNormalEntity(int id, NormalMappingShader shader){
//		mc = new ModelComponent(id); wurde schon erstellt
		GL30.glBindVertexArray(getRawModel(ModelComponent.getModel(id).getModelID()).getVaoID());
		GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        GL20.glEnableVertexAttribArray(3);
        transformationMatrix = Maths.createTransformationMatrix(PositionComponent.getPosition(id).toVector3f(), PositionComponent.getRotation(id).toVector3f(), PositionComponent.getScale(id));
        shader.loadTransformationMatrix(transformationMatrix);
        ModelTexture texture = getTexture(ModelComponent.getModel(id).getTextureID());

        if(texture.hasTransparency()){
        	disableCulling();
        }
        else{
        	enableCulling();
        }
        
		shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, getTexture(ModelComponent.getModel(id).getTextureID()).getID());
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, getTexture(ModelComponent.getModel(id).getTextureID()).getNormalMap());
        GL11.glDrawElements(GL11.GL_TRIANGLES, getRawModel(ModelComponent.getModel(id).getModelID()).getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
    }

	private void renderTerrain(int id, TerrainShader shader) {
		GL30.glBindVertexArray(TerrainComponent.getModel(id).getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		GL13.glActiveTexture(GL13.GL_TEXTURE4);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, TerrainComponent.getBlendMap(id).getTextureID());
		
		transformationMatrix = Maths.createTransformationMatrix(
				new Vector3f(TerrainComponent.getX(id), 0, TerrainComponent.getZ(id)), 0, 0, 0, 1);
		
		shader.loadTransformationMatrix(transformationMatrix);
		GL11.glDrawElements(GL11.GL_TRIANGLES, TerrainComponent.getModel(id).getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
	}
	
//	private void renderLiquid(LiquidComponent liquid, LiquidShader shader) {
//        GL30.glBindVertexArray(getRawModel(liquid.getModel().getModelID()).getVaoID());
//        GL20.glEnableVertexAttribArray(0);
//        GL20.glEnableVertexAttribArray(1);
//        GL20.glEnableVertexAttribArray(2);
//        transformationMatrix = Maths.createTransformationMatrix(liquid.getPosition(),new Vector3f(0,0,0) , liquid.getScale());
//        if(!culling){
//        	enableCulling();
//        }
//        shader.loadFakeLightingVariable(getTexture(liquid.getModel().getTextureID()).isUseFakeLighting());
//		shader.loadShineVariables(getTexture(liquid.getModel().getTextureID()).getShineDamper(), getTexture(liquid.getModel().getTextureID()).getReflectivity());
//        GL13.glActiveTexture(GL13.GL_TEXTURE0);
//        GL11.glBindTexture(GL11.GL_TEXTURE_2D, getTexture(liquid.getModel().getTextureID()).getID());
//        shader.loadTransformationMatrix(transformationMatrix);
//        GL11.glDrawElements(GL11.GL_TRIANGLES, getRawModel(liquid.getModel().getModelID()).getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
//	}
	
	public static void useMipmapping(boolean mipmapping){
		if(mipmapping){
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL14.GL_GENERATE_MIPMAP, GL11.GL_TRUE);
		}
		else{
			
		}
	}
	
	public void cleanUp() {
		staticshader.cleanUp();
		liquidshader.cleanUp();
		terrainshader.cleanUp();
		shadowMapRenderer.cleanUp();
	}
	
	private void finish(){
		GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL20.glDisableVertexAttribArray(3);
        GL30.glBindVertexArray(0);
	}
	
	public int getShadowMapTexture(){
		return shadowMapRenderer.getShadowMap();
	}
	
	private void prepareStatic(){
		GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
		staticshader.start();
		staticshader.loadProjectionMatrix(projectionMatrix);
		staticshader.loadViewMatrix();
		staticshader.loadSkyColour(RED, GREEN, BLUE);
		staticshader.loadTextureMultiVariables(1);
		staticshader.loadFakeLightingVariable(true);	
		staticshader.connectTextureUnits();
		staticshader.loadToShadowSpaceMatrix(shadowMapRenderer.getToShadowMapSpaceMatrix());
		staticshader.loadShadowMapSize();	
		staticshader.loadLights(Main.lights);
    }
	
	
	private void prepareNormal(){
		nmshader.start();
		nmshader.loadProjectionMatrix(projectionMatrix);
		Matrix4f viewMatrix = Maths.createViewMatrix();
		nmshader.loadViewMatrix(viewMatrix);
		nmshader.loadSkyColour(RED, GREEN, BLUE);
		nmshader.connectTextureUnits();
		nmshader.loadShineVariables(10, 0);
		nmshader.loadToShadowSpaceMatrix(shadowMapRenderer.getToShadowMapSpaceMatrix());
		nmshader.loadShadowMapSize();	
		nmshader.loadLights(Main.lights, viewMatrix);
    }
	
	private void prepareTerrain(){
    	GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(RED, GREEN, BLUE, 1);
		GL13.glActiveTexture(GL13.GL_TEXTURE5);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, getShadowMapTexture());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		
		//TODO dd
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, TerrainComponent.getTexturePack(0).getBackgroundTexture().getTextureID());
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, TerrainComponent.getTexturePack(0).getrTexture().getTextureID());
		GL13.glActiveTexture(GL13.GL_TEXTURE2);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, TerrainComponent.getTexturePack(0).getgTexture().getTextureID());
		GL13.glActiveTexture(GL13.GL_TEXTURE3);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, TerrainComponent.getTexturePack(0).getbTexture().getTextureID());
		terrainshader.start();
		terrainshader.loadProjectionMatrix(projectionMatrix);
		terrainshader.loadViewMatrix();
		terrainshader.loadSkyColour(RED, GREEN, BLUE);
		terrainshader.connectTextureUnits();
		terrainshader.loadFakeLightingVariable(true);
		terrainshader.loadTextureMultiVariables(1);
		terrainshader.loadShineVariables(10, 0);
		terrainshader.loadOffset(0,0);
		terrainshader.loadToShadowSpaceMatrix(shadowMapRenderer.getToShadowMapSpaceMatrix());
		terrainshader.loadShadowMapSize();		
		terrainshader.loadLights(Main.lights);
    }
	
//	private void prepareLiquid(){
//		liquidshader.start();
//		liquidshader.loadProjectionMatrix(projectionMatrix);
//		liquidshader.loadViewMatrix();
//		liquidshader.loadSkyColour(RED, GREEN, BLUE);
//		liquidshader.loadTextureMultiVariables(1000);
//		liquidshader.loadFakeLightingVariable(true);
//		liquidshader.loadShineVariables(10, 0);
//		liquidshader.loadLights(Main.lights);
//    }
	
	 private static void createProjectionMatrix(){
	    	projectionMatrix = new Matrix4f();
			float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
			float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))));
			float x_scale = y_scale / aspectRatio;
			float frustum_length = FAR_PLANE - NEAR_PLANE;

			projectionMatrix.m00 = x_scale;
			projectionMatrix.m11 = y_scale;
			projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
			projectionMatrix.m23 = -1;
			projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
			projectionMatrix.m33 = 0;
	    }
	
	public boolean inView(){
		
		return true;
	}

	public static Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
}
