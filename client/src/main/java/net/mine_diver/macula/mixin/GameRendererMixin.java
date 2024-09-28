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
            method = "renderWorld(FJ)V",
            at = @At("HEAD")
    )
    private void beginRender(float var1, long var2, CallbackInfo ci) {
        //Shaders shaders = new Shaders();
		Shaders.beginRender(minecraft, var1, var2);
    }

    @Inject(
            method = "renderWorld(FJ)V",
            at = @At("RETURN")
    )
    private void endRender(CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.endRender();
    }

    @Inject(
            method = "renderWorld(FJ)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/GameRenderer;setupCamera(FI)V"
            )
    )
    private void setClearColor(float l, long par2, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.setClearColor(fogRed, fogGreen, fogBlue);
    }

    @Inject(
            method = "renderWorld(FJ)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/GameRenderer;setupCamera(FI)V",
                    shift = At.Shift.AFTER
            )
    )
    private void setCamera(float l, long par2, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.setCamera(l);
    }


    @Inject(
            method = "renderWorld(FJ)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/WorldRenderer;render(Lnet/minecraft/entity/Living;ID)I",
                    ordinal = 0
            )
    )
    private void injectTerrainBegin(float l, long par2, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.beginTerrain();
    }


    @Inject(
            method = "renderWorld(FJ)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/WorldRenderer;render(Lnet/minecraft/entity/Living;ID)I",
                    ordinal = 0,
                    shift = At.Shift.AFTER
            )
    )
    private void injectTerrainEnd(float l, long par2, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.endTerrain();
    }


    @Inject(
            method = "renderWorld(FJ)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/WorldRenderer;render(Lnet/minecraft/entity/Living;ID)I",
                    ordinal = 1
            )
    )
    private void injectWaterBegin1(float l, long par2, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.beginWater();
    }


    @Inject(
            method = "renderWorld(FJ)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/WorldRenderer;render(Lnet/minecraft/entity/Living;ID)I",
                    ordinal = 1,
                    shift = At.Shift.AFTER
            )
    )
    private void injectWaterEnd1(float l, long par2, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.endWater();
    }


    @Inject(
            method = "renderWorld(FJ)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/WorldRenderer;render(Lnet/minecraft/entity/Living;ID)I",
                    ordinal = 2
            )
    )
    private void injectWaterBegin2(float l, long par2, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.beginWater();
    }


    @Inject(
            method = "renderWorld(FJ)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/WorldRenderer;render(Lnet/minecraft/entity/Living;ID)I",
                    ordinal = 2,
                    shift = At.Shift.AFTER
            )
    )
    private void injectWaterEnd2(float l, long par2, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.endWater();
    }

    @Inject(
            method = "renderWorld(FJ)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/WorldRenderer;render(ID)V"
            )
    )
    private void injectBeginWater3(float l, long par2, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.beginWater();
    }

    @Inject(
            method = "renderWorld(FJ)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/WorldRenderer;render(ID)V",
                    shift = At.Shift.AFTER
            )
    )
    private void injectEndWater3(float l, long par2, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.endWater();
    }

    @Inject(
            method = "renderWorld(FJ)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/GameRenderer;renderSnowAndRain(F)V"
            )
    )
    private void injectBeginWeather(float l, long par2, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.beginWeather();
    }

    @Inject(
            method = "renderWorld(FJ)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/GameRenderer;renderSnowAndRain(F)V",
                    shift = At.Shift.AFTER
            )
    )
    private void injectEndWeather(float l, long par2, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.endWeather();
    }

    @Inject(
            method = "renderWorld(FJ)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/GameRenderer;renderItemInHand(FI)V"
            )
    )
    private void injectBeginHand(float l, long par2, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.beginHand();
    }

    @Inject(
            method = "renderWorld(FJ)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/GameRenderer;renderItemInHand(FI)V",
                    shift = At.Shift.AFTER
            )
    )
    private void injectEndHand(float l, long par2, CallbackInfo ci) {
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
