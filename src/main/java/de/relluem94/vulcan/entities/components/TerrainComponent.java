package de.relluem94.vulcan.entities.components;

import static de.relluem94.vulcan.toolbox.Variables.Terrain_Environment_01_Count;
import static de.relluem94.vulcan.toolbox.Variables.Terrain_Environment_01_Name;
import static de.relluem94.vulcan.toolbox.Variables.Terrain_Environment_01_Physics;
import static de.relluem94.vulcan.toolbox.Variables.Terrain_Environment_02_Count;
import static de.relluem94.vulcan.toolbox.Variables.Terrain_Environment_02_Name;
import static de.relluem94.vulcan.toolbox.Variables.Terrain_Environment_02_Physics;
import static de.relluem94.vulcan.toolbox.Variables.Terrain_SIZE;
import static de.relluem94.vulcan.toolbox.Variables.Terrain_VERTX_COUNT;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import de.relluem94.vulcan.entities.BaseEntity;
import de.relluem94.vulcan.entities.TerrainEntity;
import de.relluem94.vulcan.main.Main;
import de.relluem94.vulcan.models.RawModel;
import de.relluem94.vulcan.models.TerrainTexture;
import de.relluem94.vulcan.models.TerrainTexturePack;
import de.relluem94.vulcan.models.TexturedModel;
import de.relluem94.vulcan.toolbox.Utils;
import de.relluem94.vulcan.toolbox.generators.HeightsGenerator;
import de.relluem94.vulcan.toolbox.maths.Maths;

public class TerrainComponent {

    public static boolean getGenerated(int entityID) {
        return (boolean) TerrainEntity.getStore(entityID, 9).getValue();
    }

    public static void setGenerated(int entityID, boolean generated) {
        TerrainEntity.setStore(entityID, 9, generated);
    }

    public static int getSeed(int entityID) {
        return (int) TerrainEntity.getStore(entityID, 2).getValue();
    }

    public static void setSeed(int entityID, int seed) {
        TerrainEntity.setStore(entityID, 2, seed);
    }

    public static void setPosition(int entityID, Vector2f position) {
        position.x = position.x * Terrain_SIZE;
        position.y = position.y * Terrain_SIZE;
        TerrainEntity.setStore(entityID, 1, position);
    }

    public static void setPosition(int entityID, float x, float z) {
        TerrainEntity.setStore(entityID, 1, new Vector2f(x * Terrain_SIZE, z * Terrain_SIZE));
    }

    public static Vector2f getPosition(int entityID) {
        Vector2f position = (Vector2f) TerrainEntity.getStore(entityID, 1).getValue();
        Vector2f position2 = new Vector2f(position.x, position.y);
        position2.x = position.x - Terrain_SIZE;
        position2.y = position.y - Terrain_SIZE;
        return position2;
    }

    public static float getX(int entityID) {
        return getPosition(entityID).x;
    }

    public static float getZ(int entityID) {
        return getPosition(entityID).y;
    }

    public static void setModel(int entityID, RawModel model) {
        TerrainEntity.setStore(entityID, 4, model);
    }

    public static RawModel getModel(int entityID) {
        return (RawModel) TerrainEntity.getStore(entityID, 4).getValue();
    }

    public static Vector3f getTerrainMid(int entityID) {
        float maxX = getX(entityID);
        float minX = maxX - Terrain_SIZE;
        float maxZ = getZ(entityID);
        float minZ = maxZ - Terrain_SIZE;
        float x = ((maxX - minX) + Terrain_SIZE / 2) + minX;
        float z = ((maxZ - minZ) + Terrain_SIZE / 2) + minZ;
        float y = getHeightOfTerrain(entityID, x, z);
        return new Vector3f(x, y, z);
    }

    public static void setTexturePack(int entityID, TerrainTexturePack terraintexturepack) {
        TerrainEntity.setStore(entityID, 6, terraintexturepack);
    }

    public static TerrainTexturePack getTexturePack(int entityID) {
        return (TerrainTexturePack) TerrainEntity.getStore(entityID, 6).getValue();
    }

    public static void setBlendMap(int entityID, TerrainTexture blendmap) {
        TerrainEntity.setStore(entityID, 7, blendmap);
    }

    public static TerrainTexture getBlendMap(int entityID) {
        return (TerrainTexture) TerrainEntity.getStore(entityID, 7).getValue();
    }

    @SuppressWarnings("unchecked")
    public static List<Integer> getEntitiesList(int entityID) {
        return (List<Integer>) TerrainEntity.getStore(entityID, 8).getValue();
    }

    public static void setEntitiesList(int entityID, List<Integer> list) {
        TerrainEntity.setStore(entityID, 8, list);
    }

    @SuppressWarnings("unchecked")
    public static void addEntityToList(int terrainID, int entityID) {
        ((List<Integer>) TerrainEntity.getStore(terrainID, 8).getValue()).add(entityID);
    }

    @SuppressWarnings("unchecked")
    public static void removeEntityFromList(int terrainID, int ID) {
        ((List<Integer>) TerrainEntity.getStore(terrainID, 8).getValue()).remove(ID);
    }

    public static void setLoaded(int entityID, boolean loaded) {
        TerrainEntity.setStore(entityID, 11, loaded);
    }

    public static boolean getLoaded(int entityID) {
        return (boolean) TerrainEntity.getStore(entityID, 11).getValue();
    }

    public static void setGenerator(int entityID, HeightsGenerator generator) {
        TerrainEntity.setStore(entityID, 12, generator);
    }

    public static HeightsGenerator getGenerator(int entityID) {
        return (HeightsGenerator) TerrainEntity.getStore(entityID, 12).getValue();
    }

    public static void setHeights(int entityID, float[][] heights) {
        TerrainEntity.setStore(entityID, 5, heights);
    }

    public static float[][] getHeights(int entityID) {
        return (float[][]) TerrainEntity.getStore(entityID, 5).getValue();
    }

    public static void unloadEntities(int entityID) {
        int count = 0;
        List<Integer> list = getEntitiesList(entityID);
        for (int i = 0; i < list.size(); i++) {
            BaseEntity.unloadEntity(list.get(i));
            count++;
        }
        Utils.log(count + " Unload Entities for " + TerrainEntity.getName(entityID), 0);
    }

    public static void loadEntities(int entityID) {
        int count = 0;
        List<Integer> list = getEntitiesList(entityID);
        for (int i = 0; i < list.size(); i++) {
            if (!(ModelComponent.getRender(list.get(i)))) {
                count++;
            }
            BaseEntity.loadEntity(list.get(i));
        }
        if (count != 0) {
            Utils.log("Loaded " + count + " Entities for " + TerrainEntity.getName(entityID), 0);
        }
    }

    private static void editEntity(int entityID, boolean physics, TexturedModel model, de.relluem94.vulcan.toolbox.maths.Vector3f position, de.relluem94.vulcan.toolbox.maths.Vector3f rotation, float scale) {
        ModelComponent.init(entityID);
        ModelComponent.set(entityID, model, 13, 1);
        PhysicsComponent.set(entityID, physics);
        PhysicsComponent.setWeight(entityID, Main.random.nextFloat() * (50 - 1 * 1) + 50);
        PositionComponent.set(entityID, position, rotation, scale);
        Main.entities.add(entityID);
    }

    public static void populateTerrain(int entityID) {
        List<Integer> entities = new ArrayList<>();
        float minX = getX(entityID);
        float maxX = minX + Terrain_SIZE;
        float minZ = getZ(entityID);
        float maxZ = minZ + Terrain_SIZE;

        BaseEntity bs;

        for (int i = 0; i < Terrain_Environment_01_Count; i++) {
            float x = Main.random.nextFloat() * (maxX - minX * 1) + minX;
            float z = Main.random.nextFloat() * (maxZ - minZ * 1) + minZ;
            float rotY = Main.random.nextFloat() * (360 - 0 * 1) + 0;
            float scale = Main.random.nextFloat() * (2 - 1 * 1) + 1.6f;

            bs = new BaseEntity(Terrain_Environment_01_Name + i);
            editEntity(bs.getID(), Terrain_Environment_01_Physics, Main.obsidian, new de.relluem94.vulcan.toolbox.maths.Vector3f(x, 140, z), new de.relluem94.vulcan.toolbox.maths.Vector3f(0, rotY, 0), scale);
            entities.add(bs.getID());
        }
        for (int i = 0; i < Terrain_Environment_02_Count; i++) {
            float x = Main.random.nextFloat() * (maxX - minX * 1) + minX;
            float z = Main.random.nextFloat() * (maxZ - minZ * 1) + minZ;
            float rotY = Main.random.nextFloat() * (360 - 0 * 1) + 0;
            float scale = Main.random.nextFloat() * (0.5f - 0.1f * 0.1f) + 0.6f;

            bs = new BaseEntity(Terrain_Environment_02_Name + i);
            editEntity(bs.getID(), Terrain_Environment_02_Physics, Main.stone, new de.relluem94.vulcan.toolbox.maths.Vector3f(x, 140, z), new de.relluem94.vulcan.toolbox.maths.Vector3f(0, rotY, 0), scale);
            entities.add(bs.getID());
        }
        setEntitiesList(entityID, entities);
    }

    public static void generateTerrain(int entityID) {
        if (!(getGenerated(entityID))) {
            int gridX = (int) (getX(entityID) / Terrain_SIZE);
            int gridZ = (int) (getZ(entityID) / Terrain_SIZE);

            setGenerator(entityID, new HeightsGenerator(gridX, gridZ, Terrain_VERTX_COUNT, getSeed(entityID)));
            setModel(entityID, genTerrain(entityID));
            populateTerrain(entityID);
            loadEntities(entityID);
            setGenerated(entityID, true);
        } else {
            loadEntities(entityID);
        }
    }

    public static void regenerateTerrain(int entityID, int seed) {
        int gridX = (int) (getX(entityID) / Terrain_SIZE);
        int gridZ = (int) (getZ(entityID) / Terrain_SIZE);

        setGenerator(entityID, new HeightsGenerator(gridX, gridZ, Terrain_VERTX_COUNT, seed));
        setModel(entityID, genTerrain(entityID));
        loadEntities(entityID);
        setGenerated(entityID, true);
    }

    private static float getHeightOfTerrainCalc(int entityID, float worldX, float worldZ) {
        //long time1 = System.nanoTime();
        //TODO Optimieren hier gibt es viel zugriffe

        float[][] heights = getHeights(entityID);
        float terrainX = worldX - getX(entityID);
        float terrainZ = worldZ - getZ(entityID);
        float answer = 0;
        float gridSquareSize = Terrain_SIZE / ((float) heights.length - 1);
        int gridX = (int) Math.floor(terrainX / gridSquareSize);
        int gridZ = (int) Math.floor(terrainZ / gridSquareSize);

        if (gridX >= heights.length - 1 || gridZ >= heights.length - 1 || gridX < 0 || gridZ < 0) {
            return 0;
        }

        float xCoord = (terrainX % gridSquareSize) / gridSquareSize;
        float zCoord = (terrainZ % gridSquareSize) / gridSquareSize;

        if (xCoord <= (1 - zCoord)) {
            answer = Maths.barryCentric(new Vector3f(0, heights[gridX][gridZ], 0), new Vector3f(1,
                    heights[gridX + 1][gridZ], 0), new Vector3f(0,
                    heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
        } else {
            answer = Maths.barryCentric(new Vector3f(1, heights[gridX + 1][gridZ], 0), new Vector3f(1,
                    heights[gridX + 1][gridZ + 1], 1), new Vector3f(0,
                    heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
        }

//        long time2 = System.nanoTime();
//        
//        long time = time2 - time1;
//        Utils.log(time + " Time", 0);
        return answer;
    }

    public static float getHeightOfTerrain(int entityID, float worldX, float worldZ) {
        return getHeightOfTerrainCalc(entityID, worldX, worldZ);
    }

    public static float getHeightOfTerrain(int entityID, Vector2f world) {
        return getHeightOfTerrainCalc(entityID, world.x, world.y);
    }

    private static Vector3f calculateNormal(int x, int z, HeightsGenerator generator) {
        float heightL = getHeight(x - 1, z, generator);
        float heightR = getHeight(x + 1, z, generator);
        float heightD = getHeight(x, z - 1, generator);
        float heightU = getHeight(x, z + 1, generator);
        Vector3f normal = new Vector3f(heightL - heightR, 2f, heightD - heightU);
        normal.normalise();
        return normal;
    }

    private static float getHeight(int x, int z, HeightsGenerator generator) {
        return generator.generateHeight(x, z);
    }

    private static RawModel genTerrain(int entityID) {
        int count = Terrain_VERTX_COUNT * Terrain_VERTX_COUNT;

        setHeights(entityID, new float[Terrain_VERTX_COUNT][Terrain_VERTX_COUNT]);

        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textureCoords = new float[count * 2];
        int[] indices = new int[6 * (Terrain_VERTX_COUNT - 1) * (Terrain_VERTX_COUNT * 1)];
        int vertexPointer = 0;
        for (int i = 0; i < Terrain_VERTX_COUNT; i++) {
            for (int j = 0; j < Terrain_VERTX_COUNT; j++) {
                vertices[vertexPointer * 3] = (float) j / ((float) Terrain_VERTX_COUNT - 1) * Terrain_SIZE;
                float height = getHeight(j, i, getGenerator(entityID));
                vertices[vertexPointer * 3 + 1] = height;
                getHeights(entityID)[j][i] = height;
                vertices[vertexPointer * 3 + 2] = (float) i / ((float) Terrain_VERTX_COUNT - 1) * Terrain_SIZE;
                Vector3f normal = calculateNormal(j, i, getGenerator(entityID));
                normals[vertexPointer * 3] = normal.x;
                normals[vertexPointer * 3 + 1] = normal.y;
                normals[vertexPointer * 3 + 2] = normal.z;
                textureCoords[vertexPointer * 2] = (float) j / ((float) Terrain_VERTX_COUNT - 1);
                textureCoords[vertexPointer * 2 + 1] = (float) i / ((float) Terrain_VERTX_COUNT - 1);
                vertexPointer++;
            }
        }

        int pointer = 0;
        for (int gz = 0; gz < Terrain_VERTX_COUNT - 1; gz++) {
            for (int gx = 0; gx < Terrain_VERTX_COUNT - 1; gx++) {
                int topLeft = (gz * Terrain_VERTX_COUNT) + gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz + 1) * Terrain_VERTX_COUNT) + gx;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }

        return Main.loader.loadToVAO(vertices, textureCoords, normals, indices);
    }
}
