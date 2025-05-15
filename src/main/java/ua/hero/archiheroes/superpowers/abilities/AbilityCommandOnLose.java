package ua.hero.archiheroes.superpowers.abilities;

import com.google.gson.JsonObject;
import lucraft.mods.lucraftcore.superpowers.abilities.AbilityConstant;
import lucraft.mods.lucraftcore.superpowers.abilities.data.AbilityData;
import ua.hero.archiheroes.superpowers.abilities.data.AbilityDataCommandList;
import lucraft.mods.lucraftcore.superpowers.abilities.supplier.EnumSync;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AbilityCommandOnLose extends AbilityConstant {

    public static AbilityData<AbilityCommand.CommandList> COMMANDS = new AbilityDataCommandList("commands").setSyncType(EnumSync.NONE).enableSetting("commands", "Sets the commands which get executed when using the ability");
    public AbilityCommand.AbilityCommandSender commandSender = new AbilityCommand.AbilityCommandSender(this);

    public AbilityCommandOnLose(EntityLivingBase entity) {
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
    public void readFromAddonPack(JsonObject data, AbilityMap abilities) {
        super.readFromAddonPack(data, abilities);

        // Compatibility for addonpacks which used the old way of adding only one command
        if (JsonUtils.hasField(data, "command")) {
            this.dataManager.set(COMMANDS, new AbilityCommand.CommandList(data.get("command")));
        }
    }

    public boolean executeCommands() {
        return this.dataManager.get(COMMANDS).execute(this.entity.world.getMinecraftServer().getCommandManager(), this.commandSender) > 0;
    }

    @Override
    public void updateTick() {

    }
}

