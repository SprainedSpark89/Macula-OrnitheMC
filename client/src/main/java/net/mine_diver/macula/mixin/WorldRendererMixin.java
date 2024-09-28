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

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Inject(
            method = "renderSky(F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getStarBrightness(F)F",
                    shift = At.Shift.AFTER
            )
    )
    private void onGetStarBrightness(float par1, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.setCelestialPosition();
    }

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
