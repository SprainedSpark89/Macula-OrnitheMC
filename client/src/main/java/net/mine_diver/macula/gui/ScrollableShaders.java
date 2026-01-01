package net.mine_diver.macula.gui;

import net.mine_diver.macula.Shaders;
import net.mine_diver.macula.mixin.ScrollableBaseAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.ButtonWidget;

import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.vertex.BufferBuilder;

class ScrollableShaders {
	private final Minecraft minecraft;
	protected final int width;
	private final int height;
	private final int minY;
	private final int maxY;
	private final int maxX;
	private final int minX;
	private final int entryHeight;
	private int upButtonId;
	private int downButtonId;
	private float mouseYStart = -2.0F;
	private float scrollSpeedMultiplier;
	private float scrollAmount;
	private int pos = -1;
	private long time = 0L;
    private List<String> shaderslist;
    private int selectedIndex;
    private final long lastClicked = Long.MIN_VALUE;
    private long lastClickedCached = 0L;
    final ShadersScreen shadersGui;
    int slotHeight;
    
    
    protected int getHeight() {
		return this.size() * this.slotHeight + 0;
	}

    public ScrollableShaders(ShadersScreen par1GuiShaders, int width, int height, int top, int bottom, int slotHeight) {
    	this.minecraft = par1GuiShaders.getMc();
		this.width = width;
		this.height = height;
		this.minY = top;
		this.maxY = bottom;
		this.entryHeight = slotHeight;
		this.minX = 0;
		this.maxX = width;
        this.slotHeight = slotHeight;
        this.shadersGui = par1GuiShaders;
        this.updateList();
    }

    public void updateList() {
        this.shaderslist = Shaders.listOfShaders();
        this.selectedIndex = 0;
        int i = 0;

        for (int j = this.shaderslist.size(); i < j; ++i) {
            if (this.shaderslist.get(i).equals(Shaders.currentShaderName)) {
                this.selectedIndex = i;
                break;
            }
        }
    }

    
    protected int size() {
        return this.shaderslist.size();
    }

    
    protected void entryClicked(int index, boolean twice) {
        if (index == this.selectedIndex && this.lastClicked == this.lastClickedCached) return;
        this.selectIndex(index);
    }

    private void selectIndex(int index) {
        this.selectedIndex = index;
        this.lastClickedCached = this.lastClicked;
        //Shaders shaders = new Shaders();
		Shaders.setShaderPack(this.shaderslist.get(index));
        shadersGui.updateButtons();
    }

    protected boolean isEntrySelected(int index) {
        return index == this.selectedIndex;
    }

    protected void renderBackground() {}

    
    protected void renderEntry(int index, int posX, int posY, int contentY, BufferBuilder tessellator) {
        String s = this.shaderslist.get(index);

        if (s.equals("OFF")) {
            s = "OFF";
        } else if (s.equals("(internal)")) {
            s = "(internal)";
        }

        this.shadersGui.drawCenteredString(shadersGui.getTextRenderer(), s, (this).width / 2, posY + 1, 0xe0e0e0);
    }
    
    private void capScrolling() {
		int var1 = this.getHeight() - (this.maxY - this.minY - 4);
		if (var1 < 0) {
			var1 /= 2;
		}

		if (this.scrollAmount < 0.0F) {
			this.scrollAmount = 0.0F;
		}

		if (this.scrollAmount > (float)var1) {
			this.scrollAmount = (float)var1;
		}
	}
    
    public void buttonClicked(ButtonWidget button) {
		if (button.active) {
			if (button.id == this.upButtonId) {
				this.scrollAmount = this.scrollAmount - (float)(this.entryHeight * 2 / 3);
				this.mouseYStart = -2.0F;
				this.capScrolling();
			} else if (button.id == this.downButtonId) {
				this.scrollAmount = this.scrollAmount + (float)(this.entryHeight * 2 / 3);
				this.mouseYStart = -2.0F;
				this.capScrolling();
			}
		}
	}
    
    public void render(int mouseX, int mouseY, float tickDelta) {
		this.renderBackground();
		int size = this.size();
		int innerWidth = this.width / 2 + 124;
		int outline = innerWidth + 6;
		if (Mouse.isButtonDown(0)) {
			if (this.mouseYStart == -1.0F) {
				if (mouseY >= this.minY && mouseY <= this.maxY) {
					int var7 = this.width / 2 - 110;
					int var8 = this.width / 2 + 110;
					int var9 = (mouseY - this.minY + (int)this.scrollAmount - 2) / this.entryHeight;
					if (mouseX >= var7 && mouseX <= var8 && var9 >= 0 && var9 < size) {
						boolean var10 = var9 == this.pos && System.currentTimeMillis() - this.time < 250L;
						this.entryClicked(var9, var10);
						this.pos = var9;
						this.time = System.currentTimeMillis();
					}

					if (mouseX >= innerWidth && mouseX <= outline) {
						this.scrollSpeedMultiplier = -1.0F;
						int var19 = this.getHeight() - (this.maxY - this.minY - 4);
						if (var19 < 1) {
							var19 = 1;
						}

						int var11 = (this.maxY - this.minY) * (this.maxY - this.minY) / this.getHeight();
						if (var11 < 32) {
							var11 = 32;
						}

						if (var11 > this.maxY - this.minY - 8) {
							var11 = this.maxY - this.minY - 8;
						}

						this.scrollSpeedMultiplier = this.scrollSpeedMultiplier / ((float)(this.maxY - this.minY - var11) / (float)var19);
					} else {
						this.scrollSpeedMultiplier = 1.0F;
					}

					this.mouseYStart = (float)mouseY;
				} else {
					this.mouseYStart = -2.0F;
				}
			} else if (this.mouseYStart >= 0.0F) {
				this.scrollAmount = this.scrollAmount - ((float)mouseY - this.mouseYStart) * this.scrollSpeedMultiplier;
				this.mouseYStart = (float)mouseY;
			}
		} else {
			this.mouseYStart = -1.0F;
		}

		this.capScrolling();
		GL11.glDisable(2896);
		GL11.glDisable(2912);
		BufferBuilder buffer = BufferBuilder.INSTANCE;
		GL11.glBindTexture(3553, this.minecraft.textureManager.load("/gui/background.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float repeat = 32.0F;
		buffer.start();
		buffer.color(2105376);
		buffer.vertex((double)this.minX, (double)this.maxY, 0.0, (double)((float)this.minX / repeat), (double)((float)(this.maxY + (int)this.scrollAmount) / repeat));
		buffer.vertex((double)this.maxX, (double)this.maxY, 0.0, (double)((float)this.maxX / repeat), (double)((float)(this.maxY + (int)this.scrollAmount) / repeat));
		buffer.vertex((double)this.maxX, (double)this.minY, 0.0, (double)((float)this.maxX / repeat), (double)((float)(this.minY + (int)this.scrollAmount) / repeat));
		buffer.vertex((double)this.minX, (double)this.minY, 0.0, (double)((float)this.minX / repeat), (double)((float)(this.minY + (int)this.scrollAmount) / repeat));
		buffer.end();

		for (int entryNumber = 0; entryNumber < size; entryNumber++) {
			int outlineWidth = this.width / 2 - 92 - 16;
			int outlineHight = this.minY + 4 + entryNumber * this.entryHeight - (int)this.scrollAmount;
			byte var12 = 32;
			if (this.isEntrySelected(entryNumber)) {
				int var13 = this.width / 2 - 110;
				int var14 = this.width / 2 + 110;
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glDisable(3553);
				buffer.start();
				buffer.color(8421504);
				buffer.vertex((double)var13, (double)(outlineHight + var12 + 2), 0.0, 0.0, 1.0);
				buffer.vertex((double)var14, (double)(outlineHight + var12 + 2), 0.0, 1.0, 1.0);
				buffer.vertex((double)var14, (double)(outlineHight - 2), 0.0, 1.0, 0.0);
				buffer.vertex((double)var13, (double)(outlineHight - 2), 0.0, 0.0, 0.0);
				buffer.color(0);
				buffer.vertex((double)(var13 + 1), (double)(outlineHight + var12 + 1), 0.0, 0.0, 1.0);
				buffer.vertex((double)(var14 - 1), (double)(outlineHight + var12 + 1), 0.0, 1.0, 1.0);
				buffer.vertex((double)(var14 - 1), (double)(outlineHight - 1), 0.0, 1.0, 0.0);
				buffer.vertex((double)(var13 + 1), (double)(outlineHight - 1), 0.0, 0.0, 0.0);
				buffer.end();
				GL11.glEnable(3553);
			}

			this.renderEntry(entryNumber, outlineWidth, outlineHight, var12, buffer);
		}

		byte var18 = 4;
		this.renderHoleBackground(0, this.minY, 255, 255);
		this.renderHoleBackground(this.maxY, this.height, 255, 255);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glDisable(3008);
		GL11.glShadeModel(7425);
		GL11.glDisable(3553);
		buffer.start();
		buffer.color(0, 0, 0, 0);
		buffer.vertex((double)this.minX, (double)(this.minY + var18), 0.0, 0.0, 1.0);
		buffer.vertex((double)this.maxX, (double)(this.minY + var18), 0.0, 1.0, 1.0);
		buffer.color(0, 0, 0, 255);
		buffer.vertex((double)this.maxX, (double)this.minY, 0.0, 1.0, 0.0);
		buffer.vertex((double)this.minX, (double)this.minY, 0.0, 0.0, 0.0);
		buffer.end();
		buffer.start();
		buffer.color(0, 0, 0, 255);
		buffer.vertex((double)this.minX, (double)this.maxY, 0.0, 0.0, 1.0);
		buffer.vertex((double)this.maxX, (double)this.maxY, 0.0, 1.0, 1.0);
		buffer.color(0, 0, 0, 0);
		buffer.vertex((double)this.maxX, (double)(this.maxY - var18), 0.0, 1.0, 0.0);
		buffer.vertex((double)this.minX, (double)(this.maxY - var18), 0.0, 0.0, 0.0);
		buffer.end();
		int var21 = this.getHeight() - (this.maxY - this.minY - 4);
		if (var21 > 0) {
			int var23 = (this.maxY - this.minY) * (this.maxY - this.minY) / this.getHeight();
			if (var23 < 32) {
				var23 = 32;
			}

			if (var23 > this.maxY - this.minY - 8) {
				var23 = this.maxY - this.minY - 8;
			}

			int var24 = (int)this.scrollAmount * (this.maxY - this.minY - var23) / var21 + this.minY;
			if (var24 < this.minY) {
				var24 = this.minY;
			}

			buffer.start();
			buffer.color(0, 0, 0, 255);
			buffer.vertex((double)innerWidth, (double)this.maxY, 0.0, 0.0, 1.0);
			buffer.vertex((double)outline, (double)this.maxY, 0.0, 1.0, 1.0);
			buffer.vertex((double)outline, (double)this.minY, 0.0, 1.0, 0.0);
			buffer.vertex((double)innerWidth, (double)this.minY, 0.0, 0.0, 0.0);
			buffer.end();
			buffer.start();
			buffer.color(0x80, 0x80, 0x80, 255);
			buffer.vertex((double)innerWidth, (double)(var24 + var23), 0.0, 0.0, 1.0);
			buffer.vertex((double)outline, (double)(var24 + var23), 0.0, 1.0, 1.0);
			buffer.vertex((double)outline, (double)var24, 0.0, 1.0, 0.0);
			buffer.vertex((double)innerWidth, (double)var24, 0.0, 0.0, 0.0);
			buffer.end();
			buffer.start();
			buffer.color(0xC0, 0xC0, 0xC0, 255);
			buffer.vertex((double)innerWidth, (double)(var24 + var23 - 1), 0.0, 0.0, 1.0);
			buffer.vertex((double)(outline - 1), (double)(var24 + var23 - 1), 0.0, 1.0, 1.0);
			buffer.vertex((double)(outline - 1), (double)var24, 0.0, 1.0, 0.0);
			buffer.vertex((double)innerWidth, (double)var24, 0.0, 0.0, 0.0);
			buffer.end();
		}

		GL11.glEnable(3553);
		GL11.glShadeModel(7424);
		GL11.glEnable(3008);
		GL11.glDisable(3042);
	}

	private void renderHoleBackground(int top, int bottom, int topAlpha, int bottomAlpha) {
		BufferBuilder var5 = BufferBuilder.INSTANCE;
		GL11.glBindTexture(3553, this.minecraft.textureManager.load("/gui/background.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float var6 = 32.0F;
		var5.start();
		var5.color(0x40, 0x40, 0x40, bottomAlpha);
		var5.vertex(0.0, (double)bottom, 0.0, 0.0, (double)((float)bottom / var6));
		var5.vertex((double)this.width, (double)bottom, 0.0, (double)((float)this.width / var6), (double)((float)bottom / var6));
		var5.color(0x40, 0x40, 0x40, topAlpha);
		var5.vertex((double)this.width, (double)top, 0.0, (double)((float)this.width / var6), (double)((float)top / var6));
		var5.vertex(0.0, (double)top, 0.0, 0.0, (double)((float)top / var6));
		var5.end();
	}
}
