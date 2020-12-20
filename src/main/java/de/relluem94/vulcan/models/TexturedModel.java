package de.relluem94.vulcan.models;

import de.relluem94.vulcan.main.Main;


public class TexturedModel {
	
	private int modelID;
	private int textureID;
	
	public TexturedModel(RawModel model, ModelTexture texture){
		textureID = Main.textures.size();
		Main.textures.add(texture);
		modelID = Main.models.size();
		Main.models.add(model);
	}

	public int getModelID() {
		return modelID;
	}

	public int getTextureID() {
		return textureID;
	}
}