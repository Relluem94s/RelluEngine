package de.relluem94.vulcan.entities.components;

import static de.relluem94.vulcan.toolbox.Variables.GRAVITY;

import org.lwjgl.util.vector.Vector2f;

import de.relluem94.vulcan.main.Main;
import de.relluem94.vulcan.toolbox.Utils;
import de.relluem94.vulcan.toolbox.generators.Store;
import de.relluem94.vulcan.toolbox.maths.Vector3f;

public class PhysicsComponent {

    private static float upwardsSpeed = 0;
    private static float terrainHeight;
    private static Vector2f posXZ;

    public static void set(int id, boolean hasPhysic) {
        Main.stores.get(id).set(37, new Store(hasPhysic));
    }

    public static boolean isInRadius(PositionComponent posCom) {
        return true;
    }

    public static boolean isInAir(int id) {
        posXZ = PositionComponent.getPosXZ(id);
        if (PositionComponent.getPosition(id).y > TerrainComponent.getHeightOfTerrain(Utils.getTerrain(posXZ), posXZ)) {
            return true;
        } else {
            return false;
        }
    }

    public static void check(int id) {
        upwardsSpeed += ((GRAVITY + (getWeight(id) * -1)) * Main.getFrameTimeSeconds());
        PositionComponent.increasePosition(id, new Vector3f(0, upwardsSpeed, 0));
        if (PositionComponent.getPosition(id).y < terrainHeight) {
            upwardsSpeed = 0;
            PositionComponent.setPosition(id, new Vector3f(PositionComponent.getPosition(id).x, terrainHeight, PositionComponent.getPosition(id).z));
        }
    }

    public static float getWeight(int id) {
        return (float) Main.stores.get(id).get(15).getValue();
    }

    public static void setWeight(int id, float weight) {
        Main.stores.get(id).set(15, new Store(weight));
    }
}
