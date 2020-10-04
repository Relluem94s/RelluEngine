package de.relluem94.vulcan.entities.components;

import static de.relluem94.vulcan.main.Main.control;
import static de.relluem94.vulcan.main.Main.isChatOpen;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import de.relluem94.vulcan.main.Main;
import de.relluem94.vulcan.toolbox.generators.Store;

public class CameraComponent{

	private float distanceFromPlayer = 35;
	private float angleAroundPlayer = 0;
	
	private Vector3f position = new Vector3f(0, 0, 0);
	private float pitch = 20;
	private float yaw = 0;
	private float roll;	
	private float maxDistanceFromPlayer = 70;
	private float minDistanceFromPlayer = 12;
	private float angleIncrease = 0.3f;
	private float pitchIncrease = 0.2f;

	int id;
	
	public int getId() {
		return id;
	}
	
	public CameraComponent(int id){
		this.id = id;
		
	}
	
	public void init(){
		Main.stores.get(getId()).set(38, new Store(true));
	}

	public void move(int id){
		float rot = PositionComponent.getRotation(id).y;
		if(control){
			calculateZoom();
			calculatePitch();
			calculateAngleAroundPlayer();
			checkInput();
			float horizontalDistance = calculateHorizontalDistance();
			float verticalDistance = calculateVerticalDistance();
			calculateCameraPosition(id, rot, horizontalDistance, verticalDistance);
			this.yaw = 180 - (rot + angleAroundPlayer);
			yaw%=360;
		}
		else{
			if(!isChatOpen){
				increaseAngleAroundPlayer(angleIncrease);
				distanceFromPlayer = maxDistanceFromPlayer;
				float horizontalDistance = calculateHorizontalDistance();
				float verticalDistance = calculateVerticalDistance();
				calculateCameraPosition(id, rot, horizontalDistance, verticalDistance);
				this.yaw = 180 - (rot + angleAroundPlayer);
				yaw%=360;
			}
			else{
				increaseAngleAroundPlayer(0);
				float horizontalDistance = calculateHorizontalDistance();
				float verticalDistance = calculateVerticalDistance();
				calculateCameraPosition(id, rot, horizontalDistance, verticalDistance);
				this.yaw = 180 - (rot + angleAroundPlayer);
				yaw%=360;
			}
		}
		
		
	}
	
	private void checkInput(){
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
			increasePitch(+pitchIncrease);
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			increasePitch(-pitchIncrease);
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			increaseAngleAroundPlayer(-angleIncrease);
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			increaseAngleAroundPlayer(+angleIncrease);
		}
	}
	
	public void invertPitch(){
		this.pitch = -pitch;
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	private void calculateCameraPosition(int id, float rotY, float horizDistance, float verticDistance){
		float theta = rotY + angleAroundPlayer;
		float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
		
		de.relluem94.vulcan.toolbox.maths.Vector3f pos = PositionComponent.getPosition(id);
		
		position.x = pos.x - offsetX;
		position.z = pos.z - offsetZ;
		position.y = pos.y + verticDistance + 4;
	}
	
	private float calculateHorizontalDistance(){
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch+4)));
	}
	
	private float calculateVerticalDistance(){
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch+4)));
	}
	
	private void calculateZoom(){
		float zoomLevel = Mouse.getDWheel() * 0.35f;
		distanceFromPlayer -= zoomLevel;
		if(distanceFromPlayer < minDistanceFromPlayer){
			distanceFromPlayer = minDistanceFromPlayer;
		}
		else if(distanceFromPlayer > maxDistanceFromPlayer){
			distanceFromPlayer = maxDistanceFromPlayer;
		}
	}
	
	private void calculatePitch(){
		if(Mouse.isButtonDown(1)){
			float pitchChange = Mouse.getDY() * 0.3f;
			pitch -= pitchChange;
			if(pitch < 0){
				pitch = 0;
			}else if(pitch > 90){
				pitch = 90;
			}
		}
	}
	
	public void increasePitch(float pitchChange){
		pitch -= pitchChange;
		if(pitch < 0){
			pitch = 0;
		}else if(pitch > 90){
			pitch = 90;
		}
	}
	
	public void increaseAngleAroundPlayer(float increase){
		float angleChange = increase;
		angleAroundPlayer -= angleChange;
	}
	
	private void calculateAngleAroundPlayer(){
		if(Mouse.isButtonDown(0)){
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= angleChange;
		}
	}
	
	public void setAngleIncrease(float angleIncrease) {
		this.angleIncrease = angleIncrease;
	}

	public void setPitchIncrease(float pitchIncrease) {
		this.pitchIncrease = pitchIncrease;
	}
}
