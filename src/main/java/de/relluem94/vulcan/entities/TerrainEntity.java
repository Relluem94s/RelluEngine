package de.relluem94.vulcan.entities;

import static de.relluem94.vulcan.toolbox.Variables.Terrain_SIZE;
import static de.relluem94.vulcan.toolbox.Variables.trenn;

import java.util.UUID;

import de.relluem94.vulcan.main.Main;
import de.relluem94.vulcan.toolbox.generators.FixedSizeList;
import de.relluem94.vulcan.toolbox.generators.Store;

public class TerrainEntity{

	private int id;
	
	/**
	 * <b>Store</b><br>
	 * 	0 Name, UUID <br>
	 * 	1 PosXZ Vec2 <br>
	 * 	2 Seed Integer <br>
	 * 	3 seaLevel Float <br>
	 * 	4 model RawModel <br>
	 * 	5 heights float[][] <br>
	 * 	6 texturePack TerrainTexturePack <br>
	 * 	7 blendMap TerrainTexture <br>
	 * 	8 entities List<Integer> <br>
	 * 	9 generated Boolean <br>
	 * 10 Render Boolean <br>
	 * 11 getLoaded Boolean <br>
	 * 12 generator HeightsGenerator <br>
	 * 13  <br>
	 * 14  <br>
	 * 15  <br>
	 * 16  <br>
	 * 
	 * @param String Name
	 * 
	 */
	public TerrainEntity(String name){		
		String uuid = UUID.randomUUID().toString();
		FixedSizeList<Store> store = new FixedSizeList<Store>(13);
		store.set(0, new Store(name + trenn + uuid));
		store.set(9, new Store(false));
		store.set(10, new Store(false));
		store.set(11, new Store(false));
		this.id = Main.terrain_stores.size();
		Main.terrain_stores.add(store);
	}

	/**
	 * @return Defined Entity Name 
	 */
	public static String getName(int id){
		String[] name = ((String)  getStore(id, 0).getValue()).split(trenn);
		return  name[0];
	}
	
	/**
	 * @return Universally Unique Identifier 
	 */
	public static String getUUID(int id){
		String[] name = ((String) getStore(id, 0).getValue()).split(trenn);
		return  name[1];
	}
	
	/**
	 * @return Fixed Store ID
	 */
	public int getID() {
		return id;
	}
	
	public static float getSize() {
		return Terrain_SIZE;
	}
	
	public float getSeaLevel() {
		return Main.SEELEVEL;
	}
	
	public static Store getStore(int entityID, int storeID){
		//Utils.log(entityID + " " + storeID, 0);
		return Main.terrain_stores.get(entityID).get(storeID);
	}
	
	public static void setStore(int entityID, int storeID, Object value){
		Main.terrain_stores.get(entityID).set(storeID, new Store(value));
	}
	
	public static FixedSizeList<Store> getStoreList(int id){
		return Main.terrain_stores.get(id);
	}
	
	
}
