package net.mine_diver.macula.mixin;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.C_0877775;

@Mixin(C_0877775.class)
public class LoadingScreenMixin {
	
	@Inject(method="m_1991224(I)V", at=@At("HEAD"), cancellable = true)
	private void cancelLoadingScreen(int i, CallbackInfo cI) {
		System.out.println(i + "%");
		//GL11.glPopAttrib();
		//GL11.glPopClientAttrib();
		//GL11.glPopMatrix();
		//GL11.glPopName();
		cI.cancel();
	}
}
