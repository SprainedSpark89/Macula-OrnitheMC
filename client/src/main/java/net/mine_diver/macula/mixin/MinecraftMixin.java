package net.mine_diver.macula.mixin;

import net.mine_diver.macula.util.MinecraftInstance;
import net.minecraft.client.C_5664496;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(C_5664496.class)
public class MinecraftMixin {
    //@Accessor("INSTANCE")
	
	
	@Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {
        MinecraftInstance.INSTANCE = (C_5664496)(Object)this; // store the created instance
    }
	
}
