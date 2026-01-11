package net.mine_diver.macula.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.util.Id2ObjectBiMap;
import net.minecraft.util.registry.IdRegistry;

@Mixin(IdRegistry.class)
public interface IdRegistryAccessor {
	@Accessor("ids")
	Id2ObjectBiMap blocks();
}
