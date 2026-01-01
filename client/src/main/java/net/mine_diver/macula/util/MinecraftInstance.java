package net.mine_diver.macula.util;

import net.minecraft.client.Minecraft;

public class MinecraftInstance {
	public static Minecraft INSTANCE;
	
    public static Minecraft get() { // possibly can replace with FabricLoader.INSTANCE.getGameInstance()
        return INSTANCE;
    }
}
