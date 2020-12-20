package de.relluem94.vulcan.entities.components;

import de.relluem94.vulcan.main.Main;
import de.relluem94.vulcan.toolbox.Utils;
import de.relluem94.vulcan.toolbox.generators.Store;
import de.relluem94.vulcan.toolbox.maths.Vector3f;


public class HealthComponent {

	public static float getMaxHealth(int id) {
		return (float) Main.stores.get(id).get(14).getValue();
	}
	
	public static void setMaxHealth(int id, float maxHealth) {
		Main.stores.get(id).set(14, new Store(maxHealth));
	}
	
	

	public static float getHealth(int id) {
		return (float) Main.stores.get(id).get(13).getValue();
	}
	
	public static void setHealth(int id, float Health) {
		Main.stores.get(id).set(13, new Store(Health));
	}
	
	public static void kill(int id) {
		Main.stores.get(id).set(13, new Store((float)0));
	}
	
	public static void increaseHealth(int id, float Health) {
		float maxHealth = getMaxHealth(id);
		float currentHealth = getHealth(id);
		currentHealth += Health;
		
		if(currentHealth < 0){
			currentHealth = 0;
		}
		else if(currentHealth > maxHealth){
			currentHealth = maxHealth;
		}
		
		setHealth(id, currentHealth);
	}	

	public static void respawn(int id){
		PositionComponent.setPosition(id, new Vector3f(Main.spawnpoint));
		Utils.log("Entity " + id + " has Respawned", 0);
	}
	
	public static void update(int id) {
		if(getHealth(id) == 0){
			//respawn(id);
			Utils.log("Entity " + id + " died", 0);
			//setHealth(id, getMaxHealth(id));
		}
		else{
			//get Damage? 
		}
	}	
}
