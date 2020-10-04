package de.relluem94.vulcan.entities;

import java.util.UUID;

import de.relluem94.vulcan.entities.components.ModelComponent;
import de.relluem94.vulcan.entities.components.PhysicsComponent;
import de.relluem94.vulcan.main.Main;
import de.relluem94.vulcan.toolbox.generators.FixedSizeList;
import de.relluem94.vulcan.toolbox.generators.Store;
import static de.relluem94.vulcan.toolbox.Variables.trenn;

public class BaseEntity{
	
	private static boolean temp = false;
	private int id;
	
	/**
	 * <b>Store</b><br>
	 * 	0 Position_in_World Vec3 <br>
	 * 	1 Rotation_in_World Vec3 <br>
	 * 	2 Scale_in_World Float <br>
	 * 	3 TexturedModel TexturedModel<br>
	 * 	4 Texture_Index Integer<br>
	 * 	5 TexturedModel_hasShadow boolean <br>
	 * 	6 Light_Color Color3f<br>
	 * 	7 Light_Attenuation Vec3<br>
	 * 	8 Light_Offset Vec3<br>
	 * 	9 Particle_Direction Vec3 <br>
	 * 10 Particle_Deviation Float <br>
	 * 11 Experience_Level Float <br>
	 * 12 Experience_MaxLevel Float <br>
	 * 13 Life_Health Float <br>
	 * 14 Life_MaxHealth Float <br>
	 * 15 Physics_Weight <br>
	 * 16 Name:_:UUID <br>
	 * 17 ParticleSystem ParticleSystem<br>
	 * 18  <br>
	 * 19  <br>
	 * 20  <br>
	 * 21  <br>
	 * 22  <br>
	 * 23  <br>
	 * 24  <br>
	 * 25  <br>
	 * 26  <br>
	 * 27  <br>
	 * 28  <br>
	 * 29  <br>
	 * 30  <br>
	 * 31  <br>
	 * 32  <br>
	 * 33  <br>
	 * 34  <br>
	 * 35  <br>
	 * 36 rendern boolean <br>
	 * 37 hasComponent_Physics boolean <br>
	 * 38 hasComponent_Camera boolean <br>
	 * 39 hasComponent_Collision boolean <br>
	 * 
	 * @param String Name
	 * 
	 */
	public BaseEntity(String name){		
		String uuid = UUID.randomUUID().toString();
		FixedSizeList<Store> store = new FixedSizeList<Store>(40);
		store.set(16, new Store(name + trenn + uuid));
		store.set(36, new Store(false));
		store.set(37, new Store(false));
		store.set(38, new Store(false));
		store.set(39, new Store(false));
		this.id = Main.stores.size();
		Main.stores.add(store);
	}

	/**
	 * @return Defined Entity Name 
	 */
	public static String getName(int id){
		String[] name = ((String) getStore(id, 16).getValue()).split(trenn);
		return  name[0];
	}
	
	/**
	 * @return Universally Unique Identifier 
	 */
	public static String getUUID(int id){
		String[] name = ((String) getStore(id, 16).getValue()).split(trenn);
		return  name[1];
	}
	
	/**
	 * @return Fixed Store ID
	 */
	public int getID() {
		return id;
	}
	
	public static Store getStore(int entityID, int storeID){
		return Main.stores.get(entityID).get(storeID);
	}
	
	public static void setStore(int entityID, int storeID, Object value){
		Main.stores.get(entityID).set(storeID, new Store(value));
	}
	
	public static FixedSizeList<Store> getStoreList(int id){
		return Main.stores.get(id);
	}
	
		// ************* Checks *************//	
		
		public static boolean hasCollisionComponent(int id){
			if((boolean) getStore(id, 39).getValue() == true){
				return true;
			}
			else{
				return false;
			}
		}
		
		public static boolean hasPhysicsComponent(int id){
			if((boolean) getStore(id, 37).getValue()){
				return true;
			}
			else{
				return false;
			}
		}
		
		public static boolean hasLiquidComponent(int id){
			if(temp){
				return true;
			}
			else{
				return false;
			}
		}
		
		public static boolean hasControlComponent(){
			if(temp){
				return true;
			}
			else{
				return false;
			}
		}
		
		public static boolean hasLevelComponent(){
			if(temp){
				return true;
			}
			else{
				return false;
			}
		}
		
		public static boolean hasHealthComponent(){
			if(temp){
				return true;
			}
			else{
				return false;
			}
		}
		
		public static boolean hasCameraComponent(int id){
			if((boolean) getStore(id, 38).getValue() == true){
				return true;
			}
			else{
				return false;
			}
		}
		
		public static boolean hasLightComponent(int id){
			if(getStore(id, 6).getValue() != null){
				return true;
			}
			else{
				return false;
			}
		}
		
		public static boolean hasModelComponent(int id){
			if(Main.stores.get(id).get(3).getValue() != null){
				return true;
			}
			else{
				return false;
			}
		}
		
		public static boolean hasPositionComponent(int id){
			if(Main.stores.get(id).get(0).getValue() != null){
				return true;
			}
			else{
				return false;
			}
		}
		
		public static boolean hasTerrainComponent(){
			if(temp){
				return true;
			}
			else{
				return false;
			}
		}
		
		public static boolean hasSoundComponent(){
			if(temp){
				return true;
			}
			else{
				return false;
			}
		}
		
		
		
		// ************* Un/Loading *************//
		
		public static void unloadEntity(int id){
			ModelComponent.setRender(id, false);
			PhysicsComponent.set(id, false);
		}
		
		public static void loadEntity(int id){
			ModelComponent.setRender(id, true);
			PhysicsComponent.set(id, true);
		}

}
