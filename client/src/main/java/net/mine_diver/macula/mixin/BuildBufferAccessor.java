package net.mine_diver.macula.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import com.mojang.blaze3d.vertex.BufferBuilder;

@Mixin(BufferBuilder.class)
public interface BuildBufferAccessor {
	@Accessor("color")
	int getColor();
	
	@Accessor("color")
	void setColor(int color);
}
