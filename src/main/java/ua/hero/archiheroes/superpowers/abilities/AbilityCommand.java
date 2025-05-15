package ua.hero.archiheroes.superpowers.abilities;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lucraft.mods.lucraftcore.superpowers.abilities.Ability;
import lucraft.mods.lucraftcore.superpowers.abilities.AbilityAction;
import lucraft.mods.lucraftcore.superpowers.abilities.data.AbilityData;
import ua.hero.archiheroes.superpowers.abilities.data.AbilityDataCommandList;
import lucraft.mods.lucraftcore.superpowers.abilities.supplier.EnumSync;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AbilityCommand extends AbilityAction {

    public static AbilityData<CommandList> COMMANDS = new AbilityDataCommandList("commands").setSyncType(EnumSync.NONE).enableSetting("commands", "Sets the commands which get executed when using the ability");
    public AbilityCommandSender commandSender = new AbilityCommandSender(this);

    public AbilityCommand(EntityLivingBase entity) {
        super(entity);
    }

    @Override
    public void registerData() {
        super.registerData();
        this.dataManager.register(COMMANDS, new CommandList().addCommand("say Hello").addCommand("say World"));
    }

    @Override
    public void readFromAddonPack(JsonObject data, AbilityMap abilities) {
        super.readFromAddonPack(data, abilities);

        // Compatibility for addonpacks which used the old way of adding only one command
        if (JsonUtils.hasField(data, "command")) {
            this.dataManager.set(COMMANDS, new CommandList(data.get("command")));
        }
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
    public boolean action() {
        return this.dataManager.get(COMMANDS).execute(this.entity.world.getMinecraftServer().getCommandManager(), this.commandSender) > 0;
    }

    public static class AbilityCommandSender implements ICommandSender {

        public final Ability ability;

        public AbilityCommandSender(Ability ability) {
            this.ability = ability;
        }

        @Override
        public String getName() {
            return "Command Ability";
        }

        @Override
        public ITextComponent getDisplayName() {
            return new TextComponentString(this.ability.getDisplayName());
        }

        @Override
        public boolean canUseCommand(int permLevel, String commandName) {
            return !commandName.equalsIgnoreCase("kick") && !commandName.equalsIgnoreCase("ban");
        }

        @Override
        public BlockPos getPosition() {
            return this.ability.getEntity().getPosition();
        }

        @Override
        public Vec3d getPositionVector() {
            return this.ability.getEntity().getPositionVector();
        }

        @Override
        public World getEntityWorld() {
            return this.ability.getEntity().world;
        }

        @Nullable
        @Override
        public Entity getCommandSenderEntity() {
            return this.ability.getEntity();
        }

        @Nullable
        @Override
        public MinecraftServer getServer() {
            return this.getEntityWorld().getMinecraftServer();
        }
    }

    public static class CommandList implements INBTSerializable<NBTTagList> {

        private List<String> commands;

        public CommandList() {
            this(new LinkedList<>());
        }

        public CommandList(JsonElement jsonElement) {
            this.parseJson(jsonElement);
        }

        public CommandList(List<String> commands) {
            this.commands = commands;
        }

        public CommandList addCommand(String command) {
            this.commands.add(command);
            return this;
        }

        public List<String> getCommands() {
            return commands;
        }

        public int execute(ICommandManager commandManager, ICommandSender sender) {
            AtomicInteger i = new AtomicInteger();
            this.commands.forEach(s -> i.set(Math.max(i.get(), commandManager.executeCommand(sender, s))));
            return i.get();
        }

        @Override
        public NBTTagList serializeNBT() {
            NBTTagList list = new NBTTagList();
            this.commands.forEach(s -> list.appendTag(new NBTTagString(s)));
            return list;
        }

        @Override
        public void deserializeNBT(NBTTagList nbt) {
            this.commands = new LinkedList<>();
            for (int i = 0; i < nbt.tagCount(); i++) {
                this.commands.add(nbt.getStringTagAt(i));
            }
        }

        public void parseJson(JsonElement jsonElement) {
            this.commands = new LinkedList<>();

            if (jsonElement.isJsonPrimitive()) {
                this.commands.add(jsonElement.getAsString());
            } else {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                for (int i = 0; i < jsonArray.size(); i++) {
                    this.commands.add(jsonArray.get(i).getAsString());
                }
            }
        }
    }

}

