package de.relluem94.vulcan.entities.components;

import de.relluem94.vulcan.main.Main;
import de.relluem94.vulcan.toolbox.generators.Store;
import de.relluem94.vulcan.toolbox.maths.Color3f;
import de.relluem94.vulcan.toolbox.maths.Vector3f;

public class LightComponent {
	
	public static void init(int id){
		Main.stores.get(id).set(7, new Store(new Vector3f(1, 1, 1)));
	}
	
	public static Vector3f getOffset(int id){
		return (Vector3f) Main.stores.get(id).get(8).getValue();
	}

	public static void setOffset(int id, Vector3f offset) {
		Main.stores.get(id).set(8, new Store(offset));
	}
	
	
	public static Vector3f getAttenuation(int id){
		return (Vector3f) Main.stores.get(id).get(7).getValue();
	}

	public static void setAttenuation(int id, Vector3f attenuation) {
		Main.stores.get(id).set(7, new Store(attenuation));
	}

	public static Color3f getColour(int id) {
		return (Color3f) Main.stores.get(id).get(6).getValue();
	}

	public static void setColour(int id, Color3f colour) {
		Main.stores.get(id).set(6, new Store(colour));
	}
	
	public static void set(int id, Color3f colour, Vector3f attenuation, Vector3f offset) {
		Main.stores.get(id).set(6, new Store(colour));
		Main.stores.get(id).set(7, new Store(attenuation));
		Main.stores.get(id).set(8, new Store(offset));
	}
	
	public static void set(int id, Vector3f colour, Vector3f attenuation, Vector3f offset) {
		Main.stores.get(id).set(6, new Store(new Color3f(colour.x, colour.y, colour.z)));
		Main.stores.get(id).set(7, new Store(attenuation));
		Main.stores.get(id).set(8, new Store(offset));
	}
	
}
