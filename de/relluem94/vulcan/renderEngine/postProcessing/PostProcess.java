package de.relluem94.vulcan.renderEngine.postProcessing;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import de.relluem94.vulcan.renderEngine.ImageRenderer;
import de.relluem94.vulcan.renderEngine.shaders.PostProcessingShader;
import de.relluem94.vulcan.toolbox.Variables;

public class PostProcess {
	private ImageRenderer renderer;
	private PostProcessingShader shader;
	private int id;
	
	public int getID(){
		return id;
	}
	
	public PostProcess(int id){
		renderer = new ImageRenderer();
		initShader(id);
	}
	
	public void update(int id){
		initShader(id);
	}
	
	private void initShader(int id){
		this.id = id;
		if(id == 0){
			shader = new PostProcessingShader(Variables.PostProcessing_Normal_VERTEX_FILE, Variables.PostProcessing_Normal_FRAGMENT_FILE);
		}
		else if(id == 1){
			shader = new PostProcessingShader(Variables.PostProcessing_Vignette_VERTEX_FILE, Variables.PostProcessing_Vignette_FRAGMENT_FILE);
		}
		else if(id == 2){
			shader = new PostProcessingShader(Variables.PostProcessing_Contrast_VERTEX_FILE, Variables.PostProcessing_Contrast_FRAGMENT_FILE);
		}
		else if(id == 3){
			shader = new PostProcessingShader(Variables.PostProcessing_Color_VERTEX_FILE, Variables.PostProcessing_Color_FRAGMENT_FILE);
		}
		else if(id == 4){
			shader = new PostProcessingShader(Variables.PostProcessing_Underwater_VERTEX_FILE, Variables.PostProcessing_Underwater_FRAGMENT_FILE);
		}
		else if(id == 5){
			shader = new PostProcessingShader(Variables.PostProcessing_Greyscale_VERTEX_FILE, Variables.PostProcessing_Greyscale_FRAGMENT_FILE);
		}
		else if(id == 6){
			shader = new PostProcessingShader(Variables.PostProcessing_Sepia_VERTEX_FILE, Variables.PostProcessing_Sepia_FRAGMENT_FILE);
		}
		else if(id == 7){
			shader = new PostProcessingShader(Variables.PostProcessing_Blur_VERTEX_FILE, Variables.PostProcessing_Blur_FRAGMENT_FILE);
		}
		else if(id == 8){
			shader = new PostProcessingShader(Variables.PostProcessing_DOF_VERTEX_FILE, Variables.PostProcessing_DOF_FRAGMENT_FILE);
		}
		else if(id == 9){
			shader = new PostProcessingShader(Variables.PostProcessing_GaussianBlur_Horizontal_VERTEX_FILE, Variables.PostProcessing_GaussianBlur_FRAGMENT_FILE);
		}
		else if(id == 10){
			shader = new PostProcessingShader(Variables.PostProcessing_GaussianBlur_Vertical_VERTEX_FILE, Variables.PostProcessing_GaussianBlur_FRAGMENT_FILE);
		}
	}
	
	float offset = 0;
	
	public void render(int texture){
		shader.start();
		if(id == 0){
			renderIt(texture);
		}
		else if(id == 1){
			shader.loadResolution(Display.getWidth(), Display.getHeight());
			shader.loadVignetteData(Variables.PostProcessing_VignetteRadius, Variables.PostProcessing_VignetteSoftness, Variables.PostProcessing_VignetteColor);
			renderIt(texture);
		}
		else if(id == 2){
			renderIt(texture);
		}
		else if(id == 3){
			shader.loadColor(Variables.PostProcessing_TintColor);
			renderIt(texture);
		}
		else if(id == 4){
			
			shader.loadOffset(offset);
			offset = offset + 0.00823f;
			if(offset > 6.19f){
				offset = 0;
			}
			renderIt(texture);
		}
		else if(id == 5){
			renderIt(texture);
		}
		else if(id == 6){
			renderIt(texture);
		}
		else if(id == 7){
			renderIt(texture);
		}
		else if(id == 8){
			renderIt(texture);
		}
		else if(id == 9){
			shader.loadTargetWidth(Display.getWidth());
			renderIt(texture);
		}
		else if(id == 10){
			shader.loadTargetHeight(Display.getHeight());
			renderIt(texture);
		}
		shader.stop();
	}
	
	private void renderIt(int texture){
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D,  texture);
		renderer.renderQuad();
	}
	
	public void cleanUp(){
		shader.cleanUp();
	}
}
