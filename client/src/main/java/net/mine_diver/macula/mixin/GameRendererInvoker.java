package net.mine_diver.macula.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.render.GameRenderer;

@Mixin(GameRenderer.class)
public interface GameRendererInvoker {
	@Invoker("m_5195666")
	void renderWorld(float f);
}
