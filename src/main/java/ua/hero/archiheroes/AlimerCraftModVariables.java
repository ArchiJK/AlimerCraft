package ua.hero.archiheroes;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class AlimerCraftModVariables {
    public AlimerCraftModVariables() {
    }

    public static class WorldSavedDataSyncMessage implements IMessage {
        public int type;
        public WorldSavedData data;

        public WorldSavedDataSyncMessage() {
        }

        public WorldSavedDataSyncMessage(int type, WorldSavedData data) {
            this.type = type;
            this.data = data;
        }

        public void toBytes(ByteBuf buf) {
            buf.writeInt(this.type);
            ByteBufUtils.writeTag(buf, this.data.writeToNBT(new NBTTagCompound()));
        }

        public void fromBytes(ByteBuf buf) {
            this.type = buf.readInt();
            if (this.type == 0) {
                this.data = new MapVariables();
            } else {
                this.data = new WorldVariables();
            }

            this.data.readFromNBT(ByteBufUtils.readTag(buf));
        }
    }

    public static class WorldSavedDataSyncMessageHandler implements IMessageHandler<WorldSavedDataSyncMessage, IMessage> {
        public WorldSavedDataSyncMessageHandler() {
        }

        public IMessage onMessage(WorldSavedDataSyncMessage message, MessageContext context) {
            if (context.side == Side.SERVER) {
                context.getServerHandler().player.getServerWorld().addScheduledTask(() -> {
                    this.syncData(message, context, context.getServerHandler().player.world);
                });
            } else {
                Minecraft.getMinecraft().addScheduledTask(() -> {
                    this.syncData(message, context, Minecraft.getMinecraft().player.world);
                });
            }

            return null;
        }

        private void syncData(WorldSavedDataSyncMessage message, MessageContext context, World world) {
            if (context.side == Side.SERVER) {
                message.data.markDirty();
                if (message.type == 0) {
                    AlimerCraft.packetHandler.sendToAll(message);
                } else {
                    AlimerCraft.packetHandler.sendToDimension(message, world.provider.getDimension());
                }
            }

            if (message.type == 0) {
                world.getMapStorage().setData("alimercraft_mapvars", message.data);
            } else {
                world.getPerWorldStorage().setData("alimercraft_worldvars", message.data);
            }

        }
    }

    public static class WorldVariables extends WorldSavedData {
        public static final String DATA_NAME = "alimercraft_worldvars";

        public WorldVariables() {
            super("alimercraft_worldvars");
        }

        public WorldVariables(String s) {
            super(s);
        }

        public void readFromNBT(NBTTagCompound nbt) {
        }

        public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
            return nbt;
        }

        public void syncData(World world) {
            this.markDirty();
            if (world.isRemote) {
                AlimerCraft.packetHandler.sendToServer(new WorldSavedDataSyncMessage(1, this));
            } else {
                AlimerCraft.packetHandler.sendToDimension(new WorldSavedDataSyncMessage(1, this), world.provider.getDimension());
            }

        }

        public static WorldVariables get(World world) {
            WorldVariables instance = (WorldVariables)world.getPerWorldStorage().getOrLoadData(WorldVariables.class, "alimercraft_worldvars");
            if (instance == null) {
                instance = new WorldVariables();
                world.getPerWorldStorage().setData("alimercraft_worldvars", instance);
            }

            return instance;
        }
    }

    public static class MapVariables extends WorldSavedData {
        public static final String DATA_NAME = "alimercraft_mapvars";
        public boolean invasionStart = false;
        public double invasionX = 0.0;
        public double invasionY = 0.0;
        public double invasionZ = 0.0;

        public MapVariables() {
            super("alimercraft_mapvars");
        }

        public MapVariables(String s) {
            super(s);
        }

        public void readFromNBT(NBTTagCompound nbt) {
            this.invasionStart = nbt.getBoolean("invasionStart");
            this.invasionX = nbt.getDouble("invasionX");
            this.invasionY = nbt.getDouble("invasionY");
            this.invasionZ = nbt.getDouble("invasionZ");
        }

        public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
            nbt.setBoolean("invasionStart", this.invasionStart);
            nbt.setDouble("invasionX", this.invasionX);
            nbt.setDouble("invasionY", this.invasionY);
            nbt.setDouble("invasionZ", this.invasionZ);
            return nbt;
        }

        public void syncData(World world) {
            this.markDirty();
            if (world.isRemote) {
                AlimerCraft.packetHandler.sendToServer(new WorldSavedDataSyncMessage(0, this));
            } else {
                AlimerCraft.packetHandler.sendToAll(new WorldSavedDataSyncMessage(0, this));
            }

        }

        public static MapVariables get(World world) {
            MapVariables instance = (MapVariables)world.getMapStorage().getOrLoadData(MapVariables.class, "alimercraft_mapvars");
            if (instance == null) {
                instance = new MapVariables();
                world.getMapStorage().setData("alimercraft_mapvars", instance);
            }

            return instance;
        }
    }
}
