package de.relluem94.vulcan.entities.components;

import de.relluem94.vulcan.main.Main;
import de.relluem94.vulcan.toolbox.generators.Store;

public class LevelComponent {

    public static float getMaxLevel(int id) {
        return (float) Main.stores.get(id).get(12).getValue();
    }

    public static void setMaxLevel(int id, float maxLevel) {
        Main.stores.get(id).set(12, new Store(maxLevel));
    }

    public static float getLevel(int id) {
        return (float) Main.stores.get(id).get(11).getValue();
    }

    public static void setLevel(int id, float level) {
        Main.stores.get(id).set(11, new Store(level));
    }

    public static void increaseLevel(int id, float level) {
        float maxLevel = getMaxLevel(id);
        float currentLevel = getLevel(id);
        currentLevel += level;

        if (currentLevel < 0) {
            currentLevel = 0;
        } else if (currentLevel > maxLevel) {
            currentLevel = maxLevel;
        }

        setLevel(id, currentLevel);
    }
}
