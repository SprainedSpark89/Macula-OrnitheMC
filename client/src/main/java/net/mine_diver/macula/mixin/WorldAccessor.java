package net.mine_diver.macula.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.World;

@Mixin(World.class)
public interface WorldAccessor {
	@Accessor("ticks")
	int getTicks();
}
