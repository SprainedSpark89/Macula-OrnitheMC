package net.mine_diver.macula.util;

import net.minecraft.client.Minecraft;

public class DeprecatedMinecraftInstance extends OldMinecraftInstance {
    public static Minecraft get() {
        try {
            // Check for the first method
            Class<?> fabricLoaderClass = Class.forName("net.fabricmc.loader.api.FabricLoader");
            Object fabricLoaderInstance = fabricLoaderClass.getMethod("getInstance").invoke(null);
            return (Minecraft) fabricLoaderClass.getMethod("getGameInstance").invoke(fabricLoaderInstance);
        } catch (Exception e1) {
            try {
                // Check for the second method
                Class<?> fabricLoaderOldClass = Class.forName("net.fabricmc.loader.FabricLoader");
                Object fabricLoaderOldInstance = fabricLoaderOldClass.getField("INSTANCE").get(null);
                return (Minecraft) fabricLoaderOldClass.getMethod("getGameInstance").invoke(fabricLoaderOldInstance);
            } catch (Exception e2) {
                e2.printStackTrace();
                return null; // Handle the case where neither method is available
            }
        }
    }
}
