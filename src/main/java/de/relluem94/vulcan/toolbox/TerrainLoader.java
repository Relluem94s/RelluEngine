package de.relluem94.vulcan.toolbox;

import de.relluem94.vulcan.entities.components.TerrainComponent;
import de.relluem94.vulcan.main.Main;

public class TerrainLoader {

    private boolean update = true;

    public boolean mustUpdate() {
        return update;
    }

    private String lastname;

    public void update(String name) {
        if (name == null) {
            name = Main.STARTTERRAIN;
        }
        if (lastname == null) {
            lastname = Main.STARTTERRAIN;
        }

        if (lastname.equals(name)) {
            update = false;
            lastname = name;
        } else {
            update = true;
            //Utils.log("TerrainLoader update", 0);
            lastname = name;
        }

        int zahl = Integer.parseInt(lastname.replace("Terrain_", ""));

        //Utils.log(zahl + " das ist die Terrain Zahl im TerrainLoader", 0);
        //
        if (update) {
            if (!TerrainComponent.getLoaded(zahl)) {
//				int ide = Main.world.getTerrain("Terrain_" + zahl);
//				Vector2f pos = TerrainComponent.getPosition(ide);
//				float x = pos.x;
//				float z = pos.y;
//				
//				
//				Main.world.loadTerrain(ide);
//				
//				int id_0 = Utils.getTerrain(x, z + Variables.Terrain_SIZE);
//				x = pos.x;
//				z = pos.y;
//				id_0 = Main.world.getTerrain("Terrain_" + id_0);
//				
//				int id_1 = Utils.getTerrain(x, z - Variables.Terrain_SIZE);
//				x = pos.x;
//				z = pos.y;
//				id_1 = Main.world.getTerrain("Terrain_" + id_1);
//				
//				int id_2 = Utils.getTerrain(x + Variables.Terrain_SIZE, z);
//				x = pos.x;
//				z = pos.y;
//				id_2 = Main.world.getTerrain("Terrain_" + id_2);
//				
//				int id_3 = Utils.getTerrain(x - Variables.Terrain_SIZE, z);
//				x = pos.x;
//				z = pos.y;
//				id_3 = Main.world.getTerrain("Terrain_" + id_3);
//				
//				int id_4 = Utils.getTerrain(x - Variables.Terrain_SIZE, z + Variables.Terrain_SIZE);
//				x = pos.x;
//				z = pos.y;
//				id_4 = Main.world.getTerrain("Terrain_" + id_4);
//				
//				int id_5 = Utils.getTerrain(x - Variables.Terrain_SIZE, z - Variables.Terrain_SIZE);
//				x = pos.x;
//				z = pos.y;
//				id_5 = Main.world.getTerrain("Terrain_" + id_5);
//				
//				Utils.log(id_0 + " " + id_1 + " " + id_2 + " " + id_3 + " " + id_4 + " " + id_5 + " z " + (z) + " z+size " + (z + Variables.Terrain_SIZE), 0);
//				
//				if(!TerrainComponent.getLoaded(id_0)){
//					Main.world.loadTerrain("Terrain_" + (id_0));			
//				}
//				
//				if(!TerrainComponent.getLoaded(id_1)){
//					Main.world.loadTerrain("Terrain_" + (id_1));			
//				}
//				
//				if(!TerrainComponent.getLoaded(id_2)){
//					Main.world.loadTerrain("Terrain_" + (id_2));			
//				}
//				
//				if(!TerrainComponent.getLoaded(id_3)){
//					Main.world.loadTerrain("Terrain_" + (id_3));			
//				}
//				
//				if(!TerrainComponent.getLoaded(id_4)){
//					Main.world.loadTerrain("Terrain_" + (id_4));			
//				}
//				
//				if(!TerrainComponent.getLoaded(id_5)){
//					Main.world.loadTerrain("Terrain_" + (id_5));			
//				}

                Main.world.loadTerrain("Terrain_" + (zahl));

                if (!TerrainComponent.getLoaded(zahl + 1)) {
                    Main.world.loadTerrain("Terrain_" + (zahl + 1));
                }
                if (!TerrainComponent.getLoaded(zahl - 1)) {
                    Main.world.loadTerrain("Terrain_" + (zahl - 1));
                }
                if (!TerrainComponent.getLoaded(zahl + Main.WORLDSIZE)) {
                    Main.world.loadTerrain("Terrain_" + (zahl + Main.WORLDSIZE));
                }
                if (!TerrainComponent.getLoaded(zahl - Main.WORLDSIZE)) {
                    Main.world.loadTerrain("Terrain_" + (zahl - Main.WORLDSIZE));
                }
                if (!TerrainComponent.getLoaded(zahl + Main.WORLDSIZE + 1)) {
                    Main.world.loadTerrain("Terrain_" + (zahl + Main.WORLDSIZE + 1));
                }
                if (!TerrainComponent.getLoaded(zahl + Main.WORLDSIZE - 1)) {
                    Main.world.loadTerrain("Terrain_" + (zahl + Main.WORLDSIZE - 1));
                }
                if (!TerrainComponent.getLoaded(zahl - Main.WORLDSIZE + 1)) {
                    Main.world.loadTerrain("Terrain_" + (zahl - Main.WORLDSIZE + 1));
                }
                if (!TerrainComponent.getLoaded(zahl - Main.WORLDSIZE - 1)) {
                    Main.world.loadTerrain("Terrain_" + (zahl - Main.WORLDSIZE - 1));
                }

                if (TerrainComponent.getLoaded(zahl - 2)) {
                    Main.world.unloadTerrain("Terrain_" + (zahl - 2));
                }
                if (TerrainComponent.getLoaded(zahl + 2)) {
                    Main.world.unloadTerrain("Terrain_" + (zahl + 2));
                }
                if (TerrainComponent.getLoaded(zahl + Main.WORLDSIZE + Main.WORLDSIZE)) {
                    Main.world.unloadTerrain("Terrain_" + (zahl + Main.WORLDSIZE + Main.WORLDSIZE));
                }
                if (TerrainComponent.getLoaded(zahl - Main.WORLDSIZE - Main.WORLDSIZE)) {
                    Main.world.unloadTerrain("Terrain_" + (zahl - Main.WORLDSIZE - Main.WORLDSIZE));
                }

                if (TerrainComponent.getLoaded(zahl - Main.WORLDSIZE - Main.WORLDSIZE - 1)) {
                    Main.world.unloadTerrain("Terrain_" + (zahl - Main.WORLDSIZE - Main.WORLDSIZE - 1));
                }
                if (TerrainComponent.getLoaded(zahl - Main.WORLDSIZE - Main.WORLDSIZE + 1)) {
                    Main.world.unloadTerrain("Terrain_" + (zahl - Main.WORLDSIZE - Main.WORLDSIZE + 1));
                }
                if (TerrainComponent.getLoaded(zahl + Main.WORLDSIZE + Main.WORLDSIZE - 1)) {
                    Main.world.unloadTerrain("Terrain_" + (zahl + Main.WORLDSIZE + Main.WORLDSIZE - 1));
                }
                if (TerrainComponent.getLoaded(zahl + Main.WORLDSIZE + Main.WORLDSIZE + 1)) {
                    Main.world.unloadTerrain("Terrain_" + (zahl + Main.WORLDSIZE + Main.WORLDSIZE + 1));
                }

                if (TerrainComponent.getLoaded(zahl + Main.WORLDSIZE + Main.WORLDSIZE - 2)) {
                    Main.world.unloadTerrain("Terrain_" + (zahl + Main.WORLDSIZE + Main.WORLDSIZE - 2));
                }
                if (TerrainComponent.getLoaded(zahl + Main.WORLDSIZE + Main.WORLDSIZE + 2)) {
                    Main.world.unloadTerrain("Terrain_" + (zahl + Main.WORLDSIZE + Main.WORLDSIZE + 2));
                }
                if (TerrainComponent.getLoaded(zahl - Main.WORLDSIZE - Main.WORLDSIZE - 2)) {
                    Main.world.unloadTerrain("Terrain_" + (zahl - Main.WORLDSIZE - Main.WORLDSIZE - 2));
                }
                if (TerrainComponent.getLoaded(zahl - Main.WORLDSIZE - Main.WORLDSIZE + 2)) {
                    Main.world.unloadTerrain("Terrain_" + (zahl - Main.WORLDSIZE - Main.WORLDSIZE + 2));
                }

                if (TerrainComponent.getLoaded(zahl + 2 + Main.WORLDSIZE)) {
                    Main.world.unloadTerrain("Terrain_" + (zahl + 2 + Main.WORLDSIZE));
                }
                if (TerrainComponent.getLoaded(zahl + 2 - Main.WORLDSIZE)) {
                    Main.world.unloadTerrain("Terrain_" + (zahl + 2 - Main.WORLDSIZE));
                }
                if (TerrainComponent.getLoaded(zahl - 2 + Main.WORLDSIZE)) {
                    Main.world.unloadTerrain("Terrain_" + (zahl - 2 + Main.WORLDSIZE));
                }
                if (TerrainComponent.getLoaded(zahl - 2 - Main.WORLDSIZE)) {
                    Main.world.unloadTerrain("Terrain_" + (zahl - 2 - Main.WORLDSIZE));
                }

                Main.initPhysics(); // Nur wenn Taste gedr�ckt..
            }
        }
    }
}
