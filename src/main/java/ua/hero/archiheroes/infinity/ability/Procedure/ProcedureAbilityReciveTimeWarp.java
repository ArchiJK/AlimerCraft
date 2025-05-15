package ua.hero.archiheroes.infinity.ability.Procedure;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import ua.hero.archiheroes.ElementsAlimerMod;
import ua.hero.archiheroes.ElementsAlimerMod.ModElement.Tag;

@Tag
public class ProcedureAbilityReciveTimeWarp extends ElementsAlimerMod.ModElement {
    public ProcedureAbilityReciveTimeWarp(ElementsAlimerMod instance) {
        super(instance, 30);
    }

    public static void executeProcedure(Map<String, Object> dependencies) {
        if (dependencies.get("entity") == null) {
            System.err.println("Failed to load dependency entity for procedure AbilityReciveTimeWarp!");
        } else if (dependencies.get("x") == null) {
            System.err.println("Failed to load dependency x for procedure AbilityReciveTimeWarp!");
        } else if (dependencies.get("y") == null) {
            System.err.println("Failed to load dependency y for procedure AbilityReciveTimeWarp!");
        } else if (dependencies.get("z") == null) {
            System.err.println("Failed to load dependency z for procedure AbilityReciveTimeWarp!");
        } else if (dependencies.get("world") == null) {
            System.err.println("Failed to load dependency world for procedure AbilityReciveTimeWarp!");
        } else {
            Entity entity = (Entity)dependencies.get("entity");
            final int x = (Integer)dependencies.get("x");
            final int y = (Integer)dependencies.get("y");
            final int z = (Integer)dependencies.get("z");
            final World world = (World)dependencies.get("world");
            if (entity.getEntityData().getBoolean("_ar_TimeWarp")) {
                entity.getEntityData().setBoolean("_ar_TimeWarp", false);
                if (entity.getEntityData().getDouble("_timeWarpTimer") == 0.0) {
                }

                entity.getEntityData().setDouble("_timeWarpTimer", 5.0);
            }

            if (entity.getEntityData().getDouble("_timeWarpTimer") > 0.0) {
                world.setWorldTime((int)(world.getWorldTime() + 20L));
                if (!world.isRemote && world.getMinecraftServer() != null) {
                    world.getMinecraftServer().getCommandManager().executeCommand(new ICommandSender() {
                        public String getName() {
                            return "";
                        }

                        public boolean canUseCommand(int permission, String command) {
                            return true;
                        }

                        public World getEntityWorld() {
                            return world;
                        }

                        public MinecraftServer getServer() {
                            return world.getMinecraftServer();
                        }

                        public boolean sendCommandFeedback() {
                            return false;
                        }

                        public BlockPos getPosition() {
                            return new BlockPos(x, y, z);
                        }

                        public Vec3d getPositionVector() {
                            return new Vec3d(x, y, z);
                        }
                    }, "gamerule randomTickSpeed 250");
                }

                entity.getEntityData().setDouble("_timeWarpTimer", entity.getEntityData().getDouble("_timeWarpTimer") - 1.0);
                if (entity.getEntityData().getDouble("_timeWarpTimer") <= 0.0 && !world.isRemote && world.getMinecraftServer() != null) {
                    world.getMinecraftServer().getCommandManager().executeCommand(new ICommandSender() {
                        public String getName() {
                            return "";
                        }

                        public boolean canUseCommand(int permission, String command) {
                            return true;
                        }

                        public World getEntityWorld() {
                            return world;
                        }

                        public MinecraftServer getServer() {
                            return world.getMinecraftServer();
                        }

                        public boolean sendCommandFeedback() {
                            return false;
                        }

                        public BlockPos getPosition() {
                            return new BlockPos(x, y, z);
                        }

                        public Vec3d getPositionVector() {
                            return new Vec3d(x, y, z);
                        }
                    }, "gamerule randomTickSpeed 3");
                }
            }

        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == Phase.END) {
            Entity entity = event.player;
            World world = entity.world;
            int i = (int)entity.posX;
            int j = (int)entity.posY;
            int k = (int)entity.posZ;
            HashMap<String, Object> dependencies = new HashMap();
            dependencies.put("x", i);
            dependencies.put("y", j);
            dependencies.put("z", k);
            dependencies.put("world", world);
            dependencies.put("entity", entity);
            dependencies.put("event", event);
            executeProcedure(dependencies);
        }

    }

    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }
}
