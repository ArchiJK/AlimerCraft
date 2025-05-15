package ua.hero.archiheroes.superpowers.abilities;

import lucraft.mods.lucraftcore.superpowers.abilities.AbilityHeld;
import lucraft.mods.lucraftcore.superpowers.abilities.data.AbilityData;
import ua.hero.archiheroes.superpowers.abilities.data.AbilityDataCommandList;
import lucraft.mods.lucraftcore.superpowers.abilities.supplier.EnumSync;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AbilityCommandHeld extends AbilityHeld {

    public static AbilityData<AbilityCommand.CommandList> COMMANDS = new AbilityDataCommandList("commands").setSyncType(EnumSync.NONE).enableSetting("commands", "Sets the commands which get executed when using the ability");
    public AbilityCommand.AbilityCommandSender commandSender = new AbilityCommand.AbilityCommandSender(this);

    public AbilityCommandHeld(EntityLivingBase entity) {
        super(entity);
    }

    @Override
    public void registerData() {
        super.registerData();
        this.dataManager.register(COMMANDS, new AbilityCommand.CommandList().addCommand("say Hello").addCommand("say World"));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void drawIcon(Minecraft mc, Gui gui, int x, int y) {
        float zLevel = Minecraft.getMinecraft().getRenderItem().zLevel;
        Minecraft.getMinecraft().getRenderItem().zLevel = -100.5F;
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0);
        Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(new ItemStack(Blocks.COMMAND_BLOCK), 0, 0);
        GlStateManager.popMatrix();
        Minecraft.getMinecraft().getRenderItem().zLevel = zLevel;
    }

    @Override
    public void updateTick() {
        if(this.entity != null && this.entity.world != null && this.entity.world.getMinecraftServer() != null)
            this.dataManager.get(COMMANDS).execute(this.entity.world.getMinecraftServer().getCommandManager(), this.commandSender);
    }
}
