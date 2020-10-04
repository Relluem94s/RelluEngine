package de.relluem94.vulcan.particles;

public class ParticleTexture {

	private int textureID;
	private int numberOFRows;
	private boolean additive;
	
	
	
	public ParticleTexture(int textureID, int numberOFRows, boolean additive) {
		this.textureID = textureID;
		this.numberOFRows = numberOFRows;
		this.additive = additive;
	}


	public int getNumberOfRows() {
		return numberOFRows;
	}


	public int getTextureID() {
		return textureID;
	}


	public boolean usesAdditiveBlending() {
		return additive;
	}

}
