package net.mine_diver.macula.util;

import net.minecraft.client.Minecraft;

import java.lang.reflect.Field;

public class MinecraftInstance {
    public static Minecraft get() {
        try {
            Field instanceField = Minecraft.class.getDeclaredField("INSTANCE");
            instanceField.setAccessible(true);
            return (Minecraft) instanceField.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return DeprecatedMinecraftInstance.get(); // Handle this according to your needs
        }
    }
}
