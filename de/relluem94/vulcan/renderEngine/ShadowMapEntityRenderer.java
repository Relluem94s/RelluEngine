package de.relluem94.vulcan.renderEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import de.relluem94.vulcan.entities.components.ModelComponent;
import de.relluem94.vulcan.entities.components.PositionComponent;
import de.relluem94.vulcan.entities.components.TerrainComponent;
import de.relluem94.vulcan.main.Main;
import de.relluem94.vulcan.renderEngine.shaders.ShadowShader;
import de.relluem94.vulcan.toolbox.maths.Maths;

public class ShadowMapEntityRenderer {

	private Matrix4f projectionViewMatrix;
	private ShadowShader shader;
	public TerrainComponent terrain;
	
	
	public ShadowMapEntityRenderer(ShadowShader shader, Matrix4f projectionViewMatrix) {
		this.shader = shader;
		this.projectionViewMatrix = projectionViewMatrix;
	}


	public void renderEntities(){
		for(Integer id : Main.entities){
			render(id);
	    }
		for (int i = 0; i < Main.normalMapEntities.size(); i++){
			int id = Main.normalMapEntities.get(i);
			render(id);
	    }
		if(Main.player != null){
			int id = Main.player.getID();
			render(id);
		}
	}
	
	private void render(int id){
		GL30.glBindVertexArray(MasterRenderer.getRawModel(ModelComponent.getModel(id).getModelID()).getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        Matrix4f modelMatrix = Maths.createTransformationMatrix(PositionComponent.getPosition(id).toVector3f(), PositionComponent.getRotation(id).toVector3f(), PositionComponent.getScale(id));
		Matrix4f mvpMatrix = Matrix4f.mul(projectionViewMatrix, modelMatrix, null);
		shader.loadMvpMatrix(mvpMatrix);
        if(MasterRenderer.getTexture(ModelComponent.getModel(id).getTextureID()).hasTransparency()){
        	MasterRenderer.disableCulling();
        }
        else{
        	MasterRenderer.enableCulling();
        }
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, MasterRenderer.getTexture(ModelComponent.getModel(id).getTextureID()).getID());
        MasterRenderer.useMipmapping(Main.mipmapping);
        GL11.glDrawElements(GL11.GL_TRIANGLES, MasterRenderer.getRawModel(ModelComponent.getModel(id).getModelID()).getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
	}
	
	public void cleanUp() {
		GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
	}
}