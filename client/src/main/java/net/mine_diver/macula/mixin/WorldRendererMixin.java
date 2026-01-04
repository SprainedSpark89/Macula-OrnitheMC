package net.mine_diver.macula.mixin;

import net.mine_diver.macula.Shaders;
import net.minecraft.client.render.world.WorldRenderer;

//import net.minecraft.client.render.WorldRenderer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.blaze3d.vertex.BufferBuilder;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
	/*@Inject(
            method = "renderSky(F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/lwjgl/opengl/GL11;glDepthMask(Z)V",
                    ordinal = 0,
                    shift = At.Shift.AFTER,
                    remap = false
            )
    )
    private void depthFix1(float par1, CallbackInfo ci) {
		if (!Shaders.shaderPackLoaded) {
            GL11.glEnable(2912);
            return;
        }
        Shaders.glEnableWrapper(2912);
    }*/

	@Redirect (
            method = "renderSky(F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;color(FFF)V", // 0
                    ordinal = 0
            )
    )
    private void hideSky(BufferBuilder bb, float r, float g, float b) {
		bb.color(r, g, b);
		((BuildBufferAccessor)(Object)bb).setColor(0 << 24 | (int)(b*255) << 16 | (int)(g*255) << 8 | (int)(r*255));
    }
	


    /*@Inject(
            method = "renderSky(F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;m_2705875(F)F",
                    shift = At.Shift.AFTER
            )
    )
    private void onGetStarBrightness(float par1, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.setCelestialPosition();
    }*/

    @Redirect(
            method = "*",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/lwjgl/opengl/GL11;glEnable(I)V",
                    remap = false
            )
    )
    private void onGlEnable(int i) {
        if (!Shaders.shaderPackLoaded) {
            GL11.glEnable(i);
            return;
        }
        Shaders.glEnableWrapper(i);
    }

    @Redirect(
            method = "*",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/lwjgl/opengl/GL11;glDisable(I)V",
                    remap = false
            )
    )
    private void onGlDisable(int i) {
        if (!Shaders.shaderPackLoaded) {
            GL11.glDisable(i);
            return;
        }
        Shaders.glDisableWrapper(i);
    }
}
