package net.mine_diver.macula.util;

import net.minecraft.client.C_5664496;

import java.lang.reflect.Field;

public class OldMinecraftInstance { // just in case something goes wrong
    public static C_5664496 get() {
        try {
            Field instanceField = C_5664496.class.getDeclaredField("INSTANCE");
            instanceField.setAccessible(true);
            return (C_5664496) instanceField.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return DeprecatedMinecraftInstance.get(); // Handle this according to your needs
        }
    }
}
