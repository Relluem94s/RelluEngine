package de.relluem94.vulcan.entities.components;

import static de.relluem94.vulcan.toolbox.Variables.BOOST_SPEED;
import static de.relluem94.vulcan.toolbox.Variables.GRAVITY;
import static de.relluem94.vulcan.toolbox.Variables.JUMP_POWER;
import static de.relluem94.vulcan.toolbox.Variables.RUN_SPEED;
import static de.relluem94.vulcan.toolbox.Variables.TURN_SPEED;
import de.relluem94.vulcan.entities.BaseEntity;
import de.relluem94.vulcan.toolbox.Utils;
import de.relluem94.vulcan.toolbox.generators.AABB;
import de.relluem94.vulcan.toolbox.maths.Vector3f;

public class AiComponent {

    MoveComponent mc;

    public void init(int id) {
        mc = new MoveComponent(id);
        mc.set(RUN_SPEED, BOOST_SPEED, GRAVITY, JUMP_POWER, TURN_SPEED);
    }

    private int searchRadius = 10;

    public void follow(int id, int ide) {
        Vector3f pos2 = (Vector3f) BaseEntity.getStore(id, 0).getValue();
        Vector3f pos = (Vector3f) BaseEntity.getStore(ide, 0).getValue();

        AABB aabb = new AABB(pos.x - searchRadius, pos.y - searchRadius, pos.z - searchRadius, pos.x + searchRadius, pos.y + searchRadius, pos.z + searchRadius);
        AABB aabb2 = new AABB(pos2.x - searchRadius, pos2.y - searchRadius, pos2.z - searchRadius, pos2.x + searchRadius, pos2.y + searchRadius, pos2.z + searchRadius);
        if (aabb.intersects(aabb2)) {
            if (aabb.intersectX(aabb2)) {
                if (pos.x > aabb2.x0 && pos.z < aabb2.z1) {
                    Utils.log("YES 1", 0);
                    mc.update(3);
                } else if (pos.x > aabb2.x0 && pos.z > aabb2.z1) {
                    Utils.log("YES 2", 0);
                    mc.update(1);
                } else if (pos.x < aabb2.x1 && pos.z < aabb2.z1) {
                    Utils.log("YES 3", 0);
                    mc.update(3);

                } else if (pos.x < aabb2.x1 && pos.z > aabb2.z1) {
                    Utils.log("YES 4", 0);
                    mc.update(1);
                } else {
                    Utils.log("No 1", 0);
                }
            }
        }

    }

}
