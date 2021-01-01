package de.relluem94.vulcan.entities.components;

import de.relluem94.vulcan.main.Main;
import de.relluem94.vulcan.particles.ParticleSystem;
import de.relluem94.vulcan.particles.ParticleTexture;
import de.relluem94.vulcan.toolbox.generators.Store;
import de.relluem94.vulcan.toolbox.maths.Vector3f;

public class ParticleComponent {

    public static void init(int id) {

        Main.stores.get(id).set(9, new Store(new Vector3f(0, 0, 0)));
        Main.stores.get(id).set(10, new Store(0f));

    }

    public static void set(int id, ParticleTexture texture, int count, int speed, float speederror, int gravity, int life, float lifeerror, int scale, float scaleerror, boolean randomize) {
        ParticleSystem system = new ParticleSystem(texture, count, speed, (gravity * -1), life, scale);
        if (randomize) {
            system.randomizeRotation();
        }

        system.setLifeError(lifeerror);
        system.setSpeedError(speederror);
        system.setScaleError(scaleerror);

        Main.stores.get(id).set(17, new Store(system));
    }

    private static ParticleSystem getSystem(int id) {
        return (ParticleSystem) Main.stores.get(id).get(17).getValue();
    }

    public static void setDirection(int id, Vector3f direction, float deviation) {
        Main.stores.get(id).set(9, new Store(direction));
        Main.stores.get(id).set(10, new Store(deviation));

        getSystem(id).setDirection(direction.toVector3f(), deviation);
    }

    public static void update(int id, Vector3f position) {
        // set in ParticleEvent to update
        getSystem(id).generateParticles(position.toVector3f());
    }
}
