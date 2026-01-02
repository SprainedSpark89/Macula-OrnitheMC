package net.mine_diver.macula.mixin.gui;

import net.mine_diver.macula.gui.ShadersScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.options.GameOptions;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionsScreen.class)
public class VideoSettingsScreenMixin extends Screen {
    @Unique
    private static final int MACULA$SHADERS_BUTTON_ID = "macula:shaders".hashCode();
    
    

    @Inject(
            method = "init",
            at = @At("RETURN")
    )
    public void macula_addShadersButton(CallbackInfo ci) {
        //noinspection unchecked    	
        buttons.add(new ButtonWidget(MACULA$SHADERS_BUTTON_ID, this.width / 2 - 100, this.height / 6 + 120 + 30, "Shaders..."));
    }

    @Inject(
            method = "buttonClicked",
            at = @At("HEAD"),
            cancellable = true
    )
    private void macula_shadersButtonClicked(ButtonWidget button, CallbackInfo ci) {
        if (button.id == MACULA$SHADERS_BUTTON_ID) {
            minecraft.f_9967940.save();
            minecraft.m_6408915(new ShadersScreen(this));
            ci.cancel();
        }
    }
}
