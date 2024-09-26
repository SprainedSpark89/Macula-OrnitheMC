package net.mine_diver.macula.mixin;

import net.mine_diver.macula.Shaders;
import net.mine_diver.macula.util.TessellatorAccessor;
import net.minecraft.block.Block;
import net.minecraft.client.render.BlockRenderer;
import net.minecraft.client.render.world.RenderChunk;
import net.minecraft.world.WorldRegion;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.mojang.blaze3d.vertex.BufferBuilder;

import java.util.HashSet;

@Mixin(RenderChunk.class)
public class ChunkBuilderMixin {
    @Inject(
            method = "compile()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/block/BlockRenderer;tessellateBlock(Lnet/minecraft/block/Block;III)Z"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void onRenderBlockByRenderType(CallbackInfo ci, int var1 , int var2, int  var3, int  var4, int  var5, int  var6, HashSet var7, int  var8, WorldRegion var9, BlockRenderer  var10, int  var11, int  var12, int  var13, int  var14, int  var15, int  var16, int  var17, int  var18, Block var19) {
        if (!Shaders.shaderPackLoaded) return;
        if (Shaders.entityAttrib >= 0)
            ((TessellatorAccessor) BufferBuilder.INSTANCE).setEntity(var19.id);
    }

    @Inject(method = "compile()V", at = @At(value = "RETURN"))
    private void onUpdateRenderer(CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        if (Shaders.entityAttrib >= 0)
            ((TessellatorAccessor) BufferBuilder.INSTANCE).setEntity(-1);
    }
}
