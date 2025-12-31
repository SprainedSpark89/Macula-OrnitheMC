package net.mine_diver.macula.mixin;

import net.mine_diver.macula.Shaders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.GameRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Inject(
            method = "renderWorld(F)V",
            at = @At("HEAD")
    )
    private void beginRender(float tickDelta, CallbackInfo ci) {
        //Shaders shaders = new Shaders();
		Shaders.beginRender(minecraft, tickDelta, 0L);
    }

    @Inject(
            method = "renderWorld(F)V",
            at = @At("RETURN")
    )
    private void endRender(CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.endRender();
    }

    @Inject(
            method = "renderWorld(F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/GameRenderer;setupCamera(FI)V"
            )
    )
    private void setClearColor(float tickDelta, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.setClearColor(fogRed, fogGreen, fogBlue);
    }

    @Inject(
            method = "renderWorld(F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/GameRenderer;setupCamera(FI)V",
                    shift = At.Shift.AFTER
            )
    )
    private void setCamera(float tickDelta, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.setCamera(0);
    }


    @Inject(
            method = "renderWorld(F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/world/WorldRenderer;render(Lnet/minecraft/entity/living/LivingEntity;ID)I",
                    ordinal = 0
            )
    )
    private void injectTerrainBegin(float tickDelta, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.beginTerrain();
    }


    @Inject(
            method = "renderWorld(F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/world/WorldRenderer;render(Lnet/minecraft/entity/living/LivingEntity;ID)I",
                    ordinal = 0,
                    shift = At.Shift.AFTER
            )
    )
    private void injectTerrainEnd(float tickDelta, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.endTerrain();
    }


    @Inject(
            method = "renderWorld(F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/world/WorldRenderer;render(Lnet/minecraft/entity/living/LivingEntity;ID)I",
                    ordinal = 1
            )
    )
    private void injectWaterBegin1(float tickDelta, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.beginWater();
    }


    @Inject(
            method = "renderWorld(F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/world/WorldRenderer;render(Lnet/minecraft/entity/living/LivingEntity;ID)I",
                    ordinal = 1,
                    shift = At.Shift.AFTER
            )
    )
    private void injectWaterEnd1(float tickDelta, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.endWater();
    }


    @Inject(
            method = "renderWorld(F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/world/WorldRenderer;render(Lnet/minecraft/entity/living/LivingEntity;ID)I",
                    ordinal = 2
            )
    )
    private void injectWaterBegin2(float tickDelta, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.beginWater();
    }


    @Inject(
            method = "renderWorld(F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/world/WorldRenderer;render(Lnet/minecraft/entity/living/LivingEntity;ID)I",
                    ordinal = 2,
                    shift = At.Shift.AFTER
            )
    )
    private void injectWaterEnd2(float l, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.endWater();
    }

    @Inject(
            method = "renderWorld(F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/world/WorldRenderer;render(ID)V"
            )
    )
    private void injectBeginWater3(float l, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.beginWater();
    }

    @Inject(
            method = "renderWorld(F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/world/WorldRenderer;render(ID)V",
                    shift = At.Shift.AFTER
            )
    )
    private void injectEndWater3(float l, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.endWater();
    }

    @Inject(
            method = "renderWorld(F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/GameRenderer;renderSnowAndRain(F)V"
            )
    )
    private void injectBeginWeather(float l, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.beginWeather();
    }

    @Inject(
            method = "renderWorld(F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/GameRenderer;renderSnowAndRain(F)V",
                    shift = At.Shift.AFTER
            )
    )
    private void injectEndWeather(float l, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.endWeather();
    }

    @Inject(
            method = "renderWorld(F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/GameRenderer;renderItemInHand(FI)V"
            )
    )
    private void injectBeginHand(float l, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.beginHand();
    }

    @Inject(
            method = "renderWorld(F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/GameRenderer;renderItemInHand(FI)V",
                    shift = At.Shift.AFTER
            )
    )
    private void injectEndHand(float l, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.endHand();
    }

    @Shadow
    private Minecraft minecraft;
    @Shadow
    float
            fogRed,
            fogGreen,
            fogBlue;
}
