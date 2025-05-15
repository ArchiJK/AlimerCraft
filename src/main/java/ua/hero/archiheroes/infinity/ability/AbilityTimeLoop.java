package ua.hero.archiheroes.infinity.ability;

import lucraft.mods.lucraftcore.superpowers.abilities.AbilityToggle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.hero.archiheroes.AHIconHelper;

public class AbilityTimeLoop extends AbilityToggle {
    public AbilityTimeLoop(EntityLivingBase entity) {
        super(entity);
    }

    public void updateTick() {
        this.entity.getEntityData().setBoolean("_ar_TimeLoop", true);
        if (this.entity.getEntityData().getBoolean("_TimeLoopReset")) {
            this.entity.getEntityData().setBoolean("_TimeLoopReset", false);
            this.setCooldown(1000);
            this.setEnabled(false);
        }

    }

    public void setEnabled(boolean enabled) {
        if (this.getCooldown() != 0 && enabled) {
            if (this.entity instanceof EntityPlayer) {
                ((EntityPlayer)this.entity).sendStatusMessage(new TextComponentString("This ability is still cooling down!"), true);
            }
        } else {
            super.setEnabled(enabled);
        }

    }

    @SideOnly(Side.CLIENT)
    public void drawIcon(Minecraft mc, Gui gui, int x, int y) {
        AHIconHelper.drawIcon(mc, gui, x, y, 0, 1);
    }
}
