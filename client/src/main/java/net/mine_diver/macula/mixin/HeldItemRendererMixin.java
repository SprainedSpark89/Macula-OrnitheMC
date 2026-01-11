package net.mine_diver.macula.mixin;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.HeldItemRenderer;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
	@Inject(
            method = "renderHand",
            at = @At("HEAD")
    )
	private void playerHandFix1(float tickDelta, CallbackInfo cI) {
		GL11.glDisable(GL11.GL_BLEND);
	}
}
