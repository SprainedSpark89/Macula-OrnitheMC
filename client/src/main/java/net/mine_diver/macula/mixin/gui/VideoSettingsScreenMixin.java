package net.mine_diver.macula.mixin.gui;

import net.mine_diver.macula.gui.ShadersScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.VideoOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.options.GameOptions;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VideoOptionsScreen.class)
public class VideoSettingsScreenMixin extends Screen {
    @Unique
    private static final int MACULA$SHADERS_BUTTON_ID = "macula:shaders".hashCode();
    
    @Shadow
    private static GameOptions.Option[] VIDEO_OPTIONS;

    @Inject(
            method = "init",
            at = @At("RETURN")
    )
    private void macula_addShadersButton(CallbackInfo cI) {
    	int y = VIDEO_OPTIONS.length;
        //noinspection unchecked
        buttons.add(new ButtonWidget(MACULA$SHADERS_BUTTON_ID, width / 2 - 75, 13, 150, 20, "Shaders..."));
    }

    @Inject(
            method = "buttonClicked",
            at = @At("HEAD"),
            cancellable = true
    )
    private void macula_shadersButtonClicked(ButtonWidget button, CallbackInfo ci) {
        if (button.id == MACULA$SHADERS_BUTTON_ID) {
            minecraft.options.save();
            minecraft.openScreen(new ShadersScreen(this));
            ci.cancel();
        }
    }
}
