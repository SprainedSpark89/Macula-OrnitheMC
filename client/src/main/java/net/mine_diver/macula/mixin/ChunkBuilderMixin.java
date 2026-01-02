package net.mine_diver.macula.mixin;

import net.mine_diver.macula.Shaders;
import net.mine_diver.macula.util.TessellatorAccessor;
import net.minecraft.client.render.world.RenderChunk;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.mojang.blaze3d.vertex.BufferBuilder;

@Mixin(RenderChunk.class)
public class ChunkBuilderMixin {

    @Inject(
        method = "compile()Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/BlockRenderer;tessellateLiquid(Lnet/minecraft/block/Block;III)Z"
        ),
        locals = LocalCapture.NO_CAPTURE
    )
    private void onRenderBlockByRenderType(CallbackInfoReturnable<?> ci) {
        if (!Shaders.shaderPackLoaded) return;
        if (Shaders.entityAttrib >= 0)
            ((TessellatorAccessor)(Object) BufferBuilder.INSTANCE).setEntity(-1); // fallback
    }

    @Inject(method = "compile()Z", at = @At("RETURN"))
    private void onUpdateRenderer(CallbackInfoReturnable<Boolean> cir) {
        if (!Shaders.shaderPackLoaded) return;
        if (Shaders.entityAttrib >= 0)
            ((TessellatorAccessor)(Object) BufferBuilder.INSTANCE).setEntity(-1);
    }
}