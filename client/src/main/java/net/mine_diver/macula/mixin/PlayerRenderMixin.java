package net.mine_diver.macula.mixin;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.entity.living.player.PlayerEntity;

@Mixin(PlayerEntityRenderer.class)
public class PlayerRenderMixin {
	
	//boolean alpha;
	
	@Inject(
            method = "renderPlayerRightHandModel",
            at = @At("HEAD")
    )
	private void playerHandFix1(PlayerEntity player, CallbackInfo cI) {
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	/*@Inject(
            method = "renderPlayerRightHandModel",
            at = @At("RETURN")
    )
	private void playerHandFix2(PlayerEntity player, CallbackInfo cI) {
		GL11.glColorMask(true, true, true, alpha);
	}*/
}
