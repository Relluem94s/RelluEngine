package de.relluem94.vulcan.models;

public class ModelTexture {
	
	private int textureID;
	private int normalMap;
	
	private float shineDamper = 1;
	private float reflectivity = 0;
	
	private boolean hasTransparency = false;
	private boolean hasNormalMap = false;
	private boolean useFakeLighting = false;
	
	private int numberOfRows = 1;
	private int textureMulti = 1;
	
	public ModelTexture(int texture){
		this.textureID = texture;
	}
		
	public void setTextureMulti(int textureMulti){
		this.textureMulti = textureMulti;
	}
	
	public int getTextureMulti() {
		return textureMulti;
	}
	
	public int getNumberOfRows() {
		return numberOfRows;
	}

	public int getNormalMap() {
		return normalMap;
	}

	public void setNormalMap(int normalMap) {
		hasNormalMap = true;
		this.normalMap = normalMap;
	}

	public boolean hasNormalMap(){
		return hasNormalMap;
	}
	
	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	public boolean hasTransparency() {
		return hasTransparency;
	}

	public boolean isUseFakeLighting() {
		return useFakeLighting;
	}


	public void setUseFakeLighting(boolean useFakeLighting) {
		this.useFakeLighting = useFakeLighting;
	}

	public void setHasTransparency(boolean hasTransparency) {
		this.hasTransparency = hasTransparency;
	}


	public int getID(){
		return textureID;
	}

	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}

}
