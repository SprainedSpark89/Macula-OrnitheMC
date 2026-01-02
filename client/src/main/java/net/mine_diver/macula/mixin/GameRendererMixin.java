package net.mine_diver.macula.mixin;

import net.mine_diver.macula.Shaders;
import net.minecraft.client.C_5664496;
import net.minecraft.client.render.GameRenderer;

import org.lwjgl.opengl.GL11;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Inject(
            method = "m_5195666(F)V",
            at = @At("HEAD")
    )
    private void beginRender(float tickDelta, CallbackInfo ci) {
        //Shaders shaders = new Shaders();
		Shaders.beginRender(minecraft, tickDelta, 0L);
    }

    @Inject(
            method = "m_5195666(F)V",
            at = @At("RETURN")
    )
    private void endRender(CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.endRender();
        GL11.glGetError(); // yeet and delete
    }

    
    @Inject(
    		method = "m_5195666(F)V",
    	    at = @At(
    	        value = "FIELD", 
    	        target = "Lnet/minecraft/client/render/GameRenderer;viewDistance:F", 
    	        opcode = Opcodes.PUTFIELD,
    	        shift = At.Shift.BEFORE
    	    )
    	)
    private void setClearColor(float tickDelta, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.setClearColor(fogRed, fogGreen, fogBlue);
    }

    @Inject(
            method = "m_5195666(F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/Frustum;compute()Lnet/minecraft/client/render/FrustumData;",
                    shift = At.Shift.BEFORE
            )
    )
    private void setCamera(float tickDelta, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.setCamera(0);
    }


    @Inject(
            method = "m_5195666(F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/world/WorldRenderer;render(Lnet/minecraft/entity/living/player/PlayerEntity;ID)I",
                    ordinal = 0
            )
    )
    private void injectTerrainBegin(float tickDelta, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.beginTerrain();
    }


    @Inject(
            method = "m_5195666(F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/world/WorldRenderer;render(Lnet/minecraft/entity/living/player/PlayerEntity;ID)I",
                    ordinal = 0,
                    shift = At.Shift.AFTER
            )
    )
    private void injectTerrainEnd(float tickDelta, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.endTerrain();
    }


    @Inject(
            method = "m_5195666(F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/world/WorldRenderer;render(Lnet/minecraft/entity/living/player/PlayerEntity;ID)I",
                    ordinal = 1
            )
    )
    private void injectWaterBegin1(float tickDelta, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.beginWater();
    }


    @Inject(
            method = "m_5195666(F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/world/WorldRenderer;render(Lnet/minecraft/entity/living/player/PlayerEntity;ID)I",
                    ordinal = 1,
                    shift = At.Shift.AFTER
            )
    )
    private void injectWaterEnd1(float tickDelta, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.endWater();
    }


    @Inject(
            method = "m_5195666(F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/world/WorldRenderer;render(Lnet/minecraft/entity/living/player/PlayerEntity;ID)I",
                    ordinal = 2
            )
    )
    private void injectWaterBegin2(float tickDelta, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.beginWater();
    }


    @Inject(
            method = "m_5195666(F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/world/WorldRenderer;render(Lnet/minecraft/entity/living/player/PlayerEntity;ID)I",
                    ordinal = 2,
                    shift = At.Shift.AFTER
            )
    )
    private void injectWaterEnd2(float l, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.endWater();
    }

    @Inject(
            method = "m_5195666(F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/world/WorldRenderer;m_0907931(ID)V"
            )
    )
    private void injectBeginWater3(float l, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.beginWater();
    }

    @Inject(
            method = "m_5195666(F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/world/WorldRenderer;m_0907931(ID)V",
                    shift = At.Shift.AFTER
            )
    )
    private void injectEndWater3(float l, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.endWater();
    }

    /*@Inject(
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
    }*/

    
    @Inject(
    	    method = "m_5195666(F)V",
    	    at = @At(
    	        value = "FIELD",
    	        target = "Lnet/minecraft/client/C_1331819;f_5010661:Z", // 7 of these?
    	        opcode = Opcodes.GETFIELD,
    	        shift = At.Shift.BEFORE
    	    )
    	)
    private void injectBeginHand(float l, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.beginHand();
    }

    @Inject(
    	    method = "m_5195666(F)V",
    	    at = @At(
    	        value = "INVOKE",
    	        target = "Lorg/lwjgl/opengl/GL11;glColorMask(ZZZZ)V", // 7th, so 6
    	        ordinal = 6,
    	        shift = At.Shift.BEFORE,
    	        remap = false
    	    )
    	)
    private void injectEndHand(float l, CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        Shaders.endHand();
    }

    @Shadow
    private C_5664496 minecraft;
    @Shadow
    float
            fogRed,
            fogGreen,
            fogBlue;
}
