package de.relluem94.vulcan.world;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import de.relluem94.vulcan.entities.TerrainEntity;
import de.relluem94.vulcan.entities.components.TerrainComponent;
import de.relluem94.vulcan.main.Main;
import de.relluem94.vulcan.models.TerrainTexture;
import de.relluem94.vulcan.toolbox.Utils;
import de.relluem94.vulcan.toolbox.Variables;
import de.relluem94.vulcan.toolbox.generators.TextureGen;
import de.relluem94.vulcan.toolbox.maths.Color3i;

public class World {
	private static int[] coordsX;
	private static int[] coordsZ;
	private int offset;
	private int worldSize;
	private int worldTiles;
	private final int blendmapcount = 256; 
	
	public static HashMap<String, Integer> terrains = new HashMap<String, Integer>();
	public static HashMap<Integer, Integer> terrain_ids = new HashMap<Integer, Integer>();
	
	private static TerrainTexture texture;
	private static TerrainEntity terrain;

	public World(int worldSize, int offset){
		this.offset = offset;

		this.worldSize = worldSize;
		this.worldTiles = (worldSize * worldSize);
		
		makeCoords();
		
		if(worldTiles > blendmapcount){
			int x = blendmapcount -1;
			int y = worldTiles/blendmapcount * worldTiles/blendmapcount;
			int z = y;
		
			
			
			int a = y;
			int b = blendmapcount * 2;
			int c = (blendmapcount *2) + z;
			int d = 0;
			

			
			
			for(int i = 0; i <= x; i++){
				texture = makeBlendMap(i);
				
				terrain = new TerrainEntity("Terrain_" + d);
				terrains.put(TerrainEntity.getName(terrain.getID()), terrain.getID());
				//Main.terrains.add(terrain.getID());
				TerrainComponent.setPosition(terrain.getID(), coordsX[d],  coordsZ[d]);
				TerrainComponent.setTexturePack(terrain.getID(), Main.texturePack);
				TerrainComponent.setBlendMap(terrain.getID(), texture);
				TerrainComponent.setSeed(terrain.getID(), Main.SEED);

				
				terrain = new TerrainEntity("Terrain_" + a);
				terrains.put(TerrainEntity.getName(terrain.getID()), terrain.getID());
				//Main.terrains.add(terrain.getID());
				TerrainComponent.setPosition(terrain.getID(), coordsX[a],  coordsZ[a]);
				TerrainComponent.setTexturePack(terrain.getID(), Main.texturePack);
				TerrainComponent.setBlendMap(terrain.getID(), texture);
				TerrainComponent.setSeed(terrain.getID(), Main.SEED);
				
				
				terrain = new TerrainEntity("Terrain_" + b);
				terrains.put(TerrainEntity.getName(terrain.getID()), terrain.getID());
				//Main.terrains.add(terrain.getID());
				TerrainComponent.setPosition(terrain.getID(), coordsX[b],  coordsZ[b]);
				TerrainComponent.setTexturePack(terrain.getID(), Main.texturePack);
				TerrainComponent.setBlendMap(terrain.getID(), texture);
				TerrainComponent.setSeed(terrain.getID(), Main.SEED);
				
				
				terrain = new TerrainEntity("Terrain_" + c);
				terrains.put(TerrainEntity.getName(terrain.getID()), terrain.getID());
				//Main.terrains.add(terrain.getID());
				TerrainComponent.setPosition(terrain.getID(), coordsX[c],  coordsZ[c]);
				TerrainComponent.setTexturePack(terrain.getID(), Main.texturePack);
				TerrainComponent.setBlendMap(terrain.getID(), texture);
				TerrainComponent.setSeed(terrain.getID(), Main.SEED);
				
				
				if(i == z -1){
					z = z + y;
					d = d + y;
					a = a + y;
					b = b + y;
					c = c + y;
				}
				d++;
				a++;
				b++;
				c++;	
				
				
			}
			
			//Utils.log(Main.terrain_stores.size() + " " + terrains.size() + " ", 0);
		}
		else{
			for(int i = 0; i < (worldTiles); i++){
				texture = makeBlendMap(i);
				
				terrain = new TerrainEntity("Terrain_" + i);
				TerrainComponent.setPosition(terrain.getID(), coordsX[i],  coordsZ[i]);
				TerrainComponent.setTexturePack(terrain.getID(), Main.texturePack);
				TerrainComponent.setBlendMap(terrain.getID(), texture);
				TerrainComponent.setSeed(terrain.getID(), Main.SEED);
				terrains.put(TerrainEntity.getName(terrain.getID()), terrain.getID());
				Main.terrains.add(terrain.getID());
			}
		}
		
		loadTerrain(Main.STARTTERRAIN);
	}
	
	public void saveWorld(){
		File save = new File(Variables.SavesDir + "/test.txt");
		if(save.exists()){
			Utils.writeWorldFile(save, Main.stores);
		}else{
			try {
				save.createNewFile();
			} catch (IOException e) {}
		}
		
	}
	
	public int getTerrainsSize(){
		return terrains.size();
	}
	
	public  void loadTerrain(int id){
		TerrainComponent.generateTerrain(id);
		terrain_ids.put(id, Main.terrains.size());
		Main.terrains.add(id);
		
	}
	
	public  void loadTerrain(String name){
		int id = getTerrain(name);
		TerrainComponent.generateTerrain(id);
		terrain_ids.put(id, Main.terrains.size());
		Main.terrains.add(id);
		
	}
	
	public void unloadTerrain(String name){
		TerrainComponent.unloadEntities(getTerrain(name));
		
		Main.terrains.remove(terrain_ids.get(getTerrain(name)));
		terrain_ids.remove(getTerrain(name));
	}
	
	public int getTerrain(String name){
		int id;
		try{
			id = terrains.get(name);
			
		}catch(NullPointerException e){
			id = terrains.get(Main.STARTTERRAIN);
		}
		return id;
	}
	
	public void loadTerrains(){
		for(int i = 0; i < terrains.size(); i++){
			int id = terrains.get("Terrain_" + i);
			TerrainComponent.generateTerrain(id);
			TerrainComponent.loadEntities(id);
			Main.terrains.add(id);
		}
	}
	
	public void unloadTerrains(){
		for(int i = 0; i < Main.terrains.size(); i++){
			int id = Main.terrains.get(i);
			TerrainComponent.unloadEntities(id);
			Main.terrains.remove(id);
		}
	}

	/**
	 * Noch leicht Buggy muss die ganze Welt neu generieren nicht nur ein paar Terrains
	 */
	public void regenerateWorld(){
		int seed = Main.SEED2.nextInt(10000000);
		for(int i = 0; i < Main.terrains.size(); i++){
			int id = Main.terrains.get(i);
			TerrainComponent.regenerateTerrain(id, seed);
		}
	}
	
	public void generateWorld(){
		for(int i = 0; i < Main.terrains.size(); i++){
			int id = Main.terrains.get(i);		
			TerrainComponent.generateTerrain(id);
			TerrainComponent.loadEntities(id);
		}
	}
	
	private TerrainTexture makeBlendMap(int i){
		//TerrainTexture texture = new TerrainTexture(Main.loader.loadTexture("terrain/blendMap/blendMap-" + i));	
		TerrainTexture texture = new TerrainTexture(Main.loader.loadTexture(TextureGen.SolidColorTexture,Color3i.BLACK, 4));	
		return texture;
	}
	
	private void makeCoords(){
		int choordsSize = (int) (worldTiles);
		coordsX = new int[choordsSize];
		coordsZ = new int[choordsSize];
		int i = 0;
		
		for(int w = 0; w < worldSize; w++){
			for(int h = 0; h < worldSize; h++){
				int x = h + offset;
				int z = w + offset;
				coordsX[i] = x; 
				coordsZ[i] = z;
				i = i + 1;
			}
		}		
	}
}
