package net.mine_diver.macula.util;

import net.minecraft.client.Minecraft;
import net.mine_diver.macula.mixin.MinecraftAccessor;

public class MinecraftInstance {
    public static Minecraft get() {
        return MinecraftAccessor.getInstance();
    }
}
