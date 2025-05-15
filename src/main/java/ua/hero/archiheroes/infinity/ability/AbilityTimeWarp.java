package ua.hero.archiheroes.infinity.ability;

import lucraft.mods.lucraftcore.superpowers.abilities.AbilityHeld;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.hero.archiheroes.items.StoneItems;

public class AbilityTimeWarp extends AbilityHeld {
    public AbilityTimeWarp(EntityLivingBase entity) {
        super(entity);
    }

    public void updateTick() {
        this.entity.getEntityData().setBoolean("_ar_TimeWarp", true);
    }

    @SideOnly(Side.CLIENT)
    public void drawIcon(Minecraft mc, Gui gui, int x, int y) {
        float zLevel = Minecraft.getMinecraft().getRenderItem().zLevel;
        mc.getRenderItem().zLevel = -100.5F;
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, 0.0F);
        mc.getRenderItem().renderItemIntoGUI(new ItemStack(StoneItems.TIME_STONE), 0, 0);
        GlStateManager.popMatrix();
        mc.getRenderItem().zLevel = zLevel;
    }
}