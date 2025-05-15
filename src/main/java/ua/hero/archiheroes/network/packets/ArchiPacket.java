package ua.hero.archiheroes.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ArchiPacket implements IMessage {

    public ArchiPacket() {
        // NO-OP
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        // NO-OP
    }

    @Override
    public void toBytes(ByteBuf buf) {
        // NO-OP
    }

    public static class Handler implements IMessageHandler<ArchiPacket, IMessage> {

        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(ArchiPacket message, MessageContext ctx) {
            EntityPlayer player = FMLClientHandler.instance().getClientPlayerEntity();

            if (player != null) {
                player.sendStatusMessage(new TextComponentString("AlimerCraft!"), false);
            }

            return null;
        }
    }

}
