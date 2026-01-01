package net.mine_diver.macula.mixin;

import net.mine_diver.macula.util.MinecraftInstance;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    //@Accessor("INSTANCE")
	
	
	@Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {
        MinecraftInstance.INSTANCE = (Minecraft)(Object)this; // store the created instance
    }
	
}
