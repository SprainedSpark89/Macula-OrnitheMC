package net.mine_diver.macula.util;

import net.minecraft.unmapped.C_5664496;

public class MinecraftInstance {
	public static C_5664496 INSTANCE;
	
    public static C_5664496 get() { // possibly can replace with FabricLoader.INSTANCE.getGameInstance()
        return INSTANCE;
    }
}
