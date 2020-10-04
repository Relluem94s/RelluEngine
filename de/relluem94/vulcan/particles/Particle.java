package de.relluem94.vulcan.particles;

import static de.relluem94.vulcan.toolbox.Variables.GRAVITY;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import de.relluem94.vulcan.main.Main;


public class Particle {

	private Vector3f position;
	private Vector3f velocity;
	
	private float gravityEffect;
	private float lifeLength;
	private float rotation;
	private float scale;
	
	private ParticleTexture texture;
	
	private Vector2f texOffset1 = new Vector2f();
	private Vector2f texOffset2 = new Vector2f();
	private float blend;
	private float distance;
	
	private Vector3f reusableChange = new Vector3f();
	
	@SuppressWarnings("unused")
	private boolean alive = false;

	public float getDistance() {
		return distance;
	}

	public Vector2f getTexOffset1() {
		return texOffset1;
	}

	public Vector2f getTexOffset2() {
		return texOffset2;
	}

	public float getBlend() {
		return blend;
	}

	private float elapsedTime = 0;

	public void setActive(ParticleTexture texture, Vector3f position, Vector3f velocity, float gravityEffect,
			float lifeLength, float rotation, float scale) {
		alive = true;
		this.texture = texture;
		this.position = position;
		this.velocity = velocity;
		this.gravityEffect = gravityEffect;
		this.lifeLength = lifeLength;
		this.rotation = rotation;
		this.scale = scale;
		ParticleMaster.addParticle(this);
	}

	public ParticleTexture getTexture() {
		return texture;
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public float getRotation() {
		return rotation;
	}

	public float getScale() {
		return scale;
	}
	
	protected boolean update(){
		velocity.y = +GRAVITY * gravityEffect * Main.getFrameTimeSeconds();
		reusableChange.set(velocity);
		reusableChange.scale(Main.getFrameTimeSeconds());
		Vector3f.add(reusableChange, position, position);
		distance = Vector3f.sub(Main.camc.getPosition(), position, null).length();
		elapsedTime += Main.getFrameTimeSeconds();
		updateTextureCoordInfo();
		return elapsedTime < lifeLength;
	}
	
	private void updateTextureCoordInfo(){
		float lifeFactor = elapsedTime / lifeLength;
		int stageCount = texture.getNumberOfRows() * texture.getNumberOfRows();
		float atlasProgression = lifeFactor * stageCount;
		int index1 = (int) Math.floor(atlasProgression);
		
		int index2 = index1 < stageCount -1 ? index1 +1 : index1;
		this.blend = atlasProgression % 1;
		setTextureOffSet(texOffset1, index1);
		setTextureOffSet(texOffset2, index2);
	}
	
	private void setTextureOffSet(Vector2f offset, int index){
		int column = index % texture.getNumberOfRows();
		int row = index / texture.getNumberOfRows();
		offset.x = (float) column / texture.getNumberOfRows();
		offset.y = (float) row /texture.getNumberOfRows();
		
	}
	
}
