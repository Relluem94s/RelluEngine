package de.relluem94.vulcan.entities.components;

import de.relluem94.vulcan.main.Main;
import de.relluem94.vulcan.toolbox.generators.AABB;
import de.relluem94.vulcan.toolbox.generators.Store;
import de.relluem94.vulcan.toolbox.maths.Vector3f;

public class CollisionComponent {

	private AABB aabb;
	private AABB aabb2;
	
	float size = 2;
	
	private int id;
	
	public int getId() {
		return id;
	}
	
	public CollisionComponent(int id){
		this.id = id;
	}
	
	public void init(){
		Main.stores.get(getId()).set(39, new Store(true));
	}
	
	public boolean checkCollision(int collide){
	
		
		Vector3f pos = (Vector3f) Main.stores.get(id).get(0).getValue();
		
		float x = pos.x - size;
		float y = pos.y;
		float z = pos.z - size;
		
		float x2 = pos.x + size;
		float y2 = pos.y + size;
		float z2 = pos.z + size;
		
		aabb = new AABB(x,y,z, x2,y2,z2);
		
		Vector3f pos2 = (Vector3f) Main.stores.get(collide).get(0).getValue();		
		
		float x3 = pos2.x - size;
		float y3 = pos2.y;
		float z3 = pos2.z - size;
		
		float x4 = pos2.x + size;
		float y4 = pos2.y + size;
		float z4 = pos2.z + size;
		
		aabb2 = new AABB(x3,y3,z3, x4,y4,z4);
		
		if(aabb.intersects(aabb2)){
			return true;
		}
		else{
			return false;
		}
		
	}
	
	
	public Vector3f getCollision(int collide){
	
		
	Vector3f pos = (Vector3f) Main.stores.get(id).get(0).getValue();
		
		float x = pos.x - size;
		float y = pos.y;
		float z = pos.z - size;
		
		float x2 = pos.x + size;
		float y2 = pos.y + size;
		float z2 = pos.z + size;
		
		aabb = new AABB(x,y,z, x2,y2,z2);
		
		Vector3f pos2 = (Vector3f) Main.stores.get(collide).get(0).getValue();		
		
		float x3 = pos2.x - size;
		float y3 = pos2.y;
		float z3 = pos2.z - size;
		
		float x4 = pos2.x + size;
		float y4 = pos2.y + size;
		float z4 = pos2.z + size;
		
		aabb2 = new AABB(x3,y3,z3, x4,y4,z4);
		
		if(aabb.intersects(aabb2)){
			if(aabb.intersectX(aabb2)){
				return new Vector3f(1f,0f,0f);
			}
			else if(aabb.intersectY(aabb2)){
				return new Vector3f(0f,1f,0f);
			}
			else if(aabb.intersectZ(aabb2)){
				return new Vector3f(0f,0f,1f);
			}
			else{
				return null;
			}
		}
		else{
			return null;
		}
	}
	
	
	public AABB getAABB() {
	    return aabb;
	}
	
	public AABB getAABB2() {
	    return aabb2;
	}
	
}
