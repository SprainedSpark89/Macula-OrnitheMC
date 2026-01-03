package net.mine_diver.macula.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.render.world.WorldRenderer;

@Mixin(WorldRenderer.class)
public interface WorldRendererInvoker {
	@Invoker("m_6748042")
	void refreshResources();
}
