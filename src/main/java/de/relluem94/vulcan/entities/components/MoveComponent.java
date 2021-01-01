package de.relluem94.vulcan.entities.components;

import org.lwjgl.util.vector.Vector2f;

import de.relluem94.vulcan.entities.TerrainEntity;
import de.relluem94.vulcan.main.Main;
import de.relluem94.vulcan.toolbox.Utils;
import de.relluem94.vulcan.toolbox.maths.Vector3f;

public class MoveComponent {

    private int id;
    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;
    private float RUN_SPEED = 0;
    private float BOOST_SPEED = 0;
    private float TURN_SPEED = 0;
    private float GRAVITY = 0;
    private float JUMP_POWER = 0;

    private float rotY;
    private float distance;
    private float dx;
    private float dz;

    private boolean isInAir = false;

    private Vector3f pos;
    private Vector3f rot;

    public MoveComponent(int id) {
        this.id = id;

    }

    public int getId() {
        return id;
    }

    public void set(float runspeed, float boostspeed, float gravity, float jumppower, float turnspeed) {
        this.RUN_SPEED = runspeed;
        this.BOOST_SPEED = boostspeed;
        this.GRAVITY = gravity;
        this.JUMP_POWER = jumppower;
        this.TURN_SPEED = turnspeed;
    }

    public void setRunSpeed(float runspeed) {
        this.RUN_SPEED = runspeed;
    }

    public void setBoostSpeed(float boostspeed) {
        this.BOOST_SPEED = boostspeed;
    }

    public void setGravity(float gravity) {
        this.GRAVITY = gravity;
    }

    public void setJumpPower(float jumppower) {
        this.JUMP_POWER = jumppower;
    }

    public void setTurnSpeed(float turnspeed) {
        this.TURN_SPEED = turnspeed;
    }

    /**
     * update
     *
     * @param id what to do <br>
     *
     * 0 stand<br>
     * 1 walkForward<br>
     * 2 sprint<br>
     * 3 walkBackward<br>
     * 4 turnLeft<br>
     * 5 turnRight<br>
     * 6 jump<br>
     *
     */
    public void update(int id) {
        if (id == 0) {
            stand();
        } else if (id == 1) {
            walkForward();
        } else if (id == 2) {
            sprint();
        } else if (id == 3) {
            walkBackward();
        } else if (id == 4) {
            turnLeft();
        } else if (id == 5) {
            turnRight();
        } else if (id == 6) {
            jump();
        }
        updateEntity(id);
    }

    private void updateEntity(int ide) {
        pos = PositionComponent.getPosition(id);
        rot = PositionComponent.getRotation(id);
        rotY = rot.y;
        distance = currentSpeed * Main.getFrameTimeSeconds();
        dx = (float) (distance * Math.sin(Math.toRadians(rotY)));
        dz = (float) (distance * Math.cos(Math.toRadians(rotY)));
        upwardsSpeed += GRAVITY * Main.getFrameTimeSeconds();
        PositionComponent.increaseRotation(id, new Vector3f(0, currentTurnSpeed * Main.getFrameTimeSeconds(), 0));
        PositionComponent.increasePosition(id, new Vector3f(dx, upwardsSpeed * Main.getFrameTimeSeconds(), dz));
        Vector2f posxz = PositionComponent.getPosXZ(id);

        int id = Utils.getTerrain(posxz);
        Main.terrainloader.update(TerrainEntity.getName(id));
        float terrainHeight = TerrainComponent.getHeightOfTerrain(id, posxz);
        if (pos.y < terrainHeight) {
            upwardsSpeed = 0;
            isInAir = false;
            pos.y = terrainHeight;
        }

    }

    private void jump() {
        if (!isInAir) {
            this.upwardsSpeed = JUMP_POWER;
            isInAir = true;
        }
    }

    private void walkForward() {
        float speed = RUN_SPEED;
        this.currentSpeed = speed;
    }

    private void sprint() {
        float speed = RUN_SPEED + BOOST_SPEED;
        this.currentSpeed = speed;
    }

    private void walkBackward() {
        this.currentSpeed = -RUN_SPEED;
    }

    private void turnLeft() {
        this.currentTurnSpeed = TURN_SPEED;
    }

    private void turnRight() {
        this.currentTurnSpeed = -TURN_SPEED;
    }

    private void stand() {
        this.currentTurnSpeed = 0;
        this.currentSpeed = 0;
    }

    public void turn(float xo) {
        this.currentTurnSpeed = ((float) (this.currentTurnSpeed + xo * 0.15D));
        if (this.currentTurnSpeed < -90.0F) {
            this.currentTurnSpeed = -90.0F;
        }
        if (this.currentTurnSpeed > 90.0F) {
            this.currentTurnSpeed = 90.0F;
        }
    }

}
