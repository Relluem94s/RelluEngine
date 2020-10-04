package de.relluem94.vulcan.entities.components;

import org.lwjgl.util.vector.Vector2f;

import de.relluem94.vulcan.main.Main;
import de.relluem94.vulcan.toolbox.generators.Store;
import de.relluem94.vulcan.toolbox.maths.Vector3f;

public class PositionComponent{
	
	public static void setPosition(int id, Vector3f position) {
		Main.stores.get(id).set(0, new Store(position));
	}
	
	public static Vector3f getPosition(int id){
		return (Vector3f) Main.stores.get(id).get(0).getValue();
	}
	
	public static Vector2f getPosXZ(int id){
		return new Vector2f(((Vector3f) (Main.stores.get(id).get(0).getValue())).x, ((Vector3f) (Main.stores.get(id).get(0).getValue())).z);
	}
	
	public static Vector3f getRotation(int id){
		return (Vector3f) Main.stores.get(id).get(1).getValue();
	}
	
	public static float getScale(int id){
		return (float) Main.stores.get(id).get(2).getValue();
	}
	
	public static void increasePosition(int id, Vector3f position) {
		Vector3f pos = (Vector3f) Main.stores.get(id).get(0).getValue();
		pos.x += position.x;
		pos.y += position.y;
		pos.z += position.z;
		Main.stores.get(id).set(0, new Store(pos));
	}

	public static void setRotation(int id, Vector3f rotation) {
		Main.stores.get(id).set(1, new Store(rotation));
	}
	
	public static void increaseRotation(int id, Vector3f rotation) {
		Vector3f rot = (Vector3f) Main.stores.get(id).get(1).getValue();

		rot.x += rotation.x;
		rot.y += rotation.y;
		rot.z += rotation.z;
		
		Main.stores.get(id).get(1).setValue(new Vector3f(rot));
	}
	
	public static void increaseYRotation(int id, float rotation) {
		Vector3f rot = (Vector3f) Main.stores.get(id).get(1).getValue();

		rot.y += rotation;
		
		Main.stores.get(id).get(1).setValue(new Vector3f(rot));
	}

	public static void decreaseYRotation(int id, float rotation) {
		Vector3f rot = (Vector3f) Main.stores.get(id).get(1).getValue();

		rot.y -= rotation;
		
		Main.stores.get(id).get(1).setValue(new Vector3f(rot));
	}
	
	public static void setScale(int id, float scale) {
		Main.stores.get(id).set(2, new Store(scale));
	}

	public static void increaseScale(int id, float scale) {
		float sc = (float) Main.stores.get(id).get(2).getValue();
		
		sc += scale;
		
		Main.stores.get(id).set(2, new Store(sc));
	}
	
	public static void set(int id, Vector3f position, Vector3f rotation, float scale) {
		Main.stores.get(id).set(0, new Store(position));
		Main.stores.get(id).set(1, new Store(rotation));
		Main.stores.get(id).set(2, new Store(scale));
	}

}