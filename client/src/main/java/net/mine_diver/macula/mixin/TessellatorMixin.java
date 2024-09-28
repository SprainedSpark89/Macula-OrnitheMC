package net.mine_diver.macula.mixin;

import net.mine_diver.macula.Shaders;
import net.mine_diver.macula.util.TessellatorAccessor;

import com.mojang.blaze3d.platform.MemoryTracker;
import com.mojang.blaze3d.vertex.BufferBuilder;
import org.lwjgl.opengl.ARBVertexProgram;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

@Mixin(BufferBuilder.class)
public class TessellatorMixin implements TessellatorAccessor {
    @Shadow private int drawMode;

    @Shadow private static boolean f_5537920;

    @Shadow private int nextVertexCount;

    @Shadow private boolean hasNormals;

    @Shadow private int[] buffer;

    @Shadow private int index;

    @Inject(
            method = "<init>(I)V",
            at = @At("RETURN")
    )
    private void onCor(int var1, CallbackInfo ci) {
        shadersData = new short[] {-1, 0};
        shadersBuffer = MemoryTracker.createByteBuffer(var1 / 8 * 4);
        shadersShortBuffer = shadersBuffer.asShortBuffer();
    }

    @Inject(
            method = "end()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/lwjgl/opengl/GL11;glDrawArrays(III)V",
                    remap = false
            )
    )
    private void onDraw1(CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        if (Shaders.entityAttrib >= 0) {
            ARBVertexProgram.glEnableVertexAttribArrayARB(Shaders.entityAttrib);
            ARBVertexProgram.glVertexAttribPointerARB(Shaders.entityAttrib, 2, false, false, 4, (ShortBuffer) shadersShortBuffer.position(0));
        }
    }

    @Inject(
            method = "end()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/lwjgl/opengl/GL11;glDrawArrays(III)V",
                    shift = At.Shift.AFTER,
                    remap = false
            )
    )
    private void onDraw2(CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        if (Shaders.entityAttrib >= 0)
            ARBVertexProgram.glDisableVertexAttribArrayARB(Shaders.entityAttrib);
    }

    @Inject(
            method = "clear()V",
            at = @At(value = "RETURN")
    )
    private void onReset(CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        shadersBuffer.clear();
    }

    @Inject(
            method = "vertex(DDD)V",
            at = @At(value = "HEAD")
    )
    private void onAddVertex(CallbackInfo ci) {
        if (!Shaders.shaderPackLoaded) return;
        if (drawMode == 7 && f_5537920 && (nextVertexCount + 1) % 4 == 0 && hasNormals) {
            buffer[index + 6] = buffer[(index - 24) + 6];
            shadersBuffer.putShort(shadersData[0]).putShort(shadersData[1]);
            buffer[index + 8 + 6] = buffer[(index + 8 - 16) + 6];
            shadersBuffer.putShort(shadersData[0]).putShort(shadersData[1]);
        }
        shadersBuffer.putShort(shadersData[0]).putShort(shadersData[1]);
    }

    @Override
    @Unique
    public void setEntity(int id) {
        shadersData[0] = (short) id;
    }

    public ByteBuffer shadersBuffer;
    public ShortBuffer shadersShortBuffer;
    public short[] shadersData;
}