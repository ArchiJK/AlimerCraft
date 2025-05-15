package ua.hero.archiheroes.infinity.ability.Procedure;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ua.hero.archiheroes.ElementsAlimerMod.ModElement.Tag;
import ua.hero.archiheroes.ElementsAlimerMod;

@Tag
public class ProcedureTimeLoopEvent extends ElementsAlimerMod.ModElement {
    public ProcedureTimeLoopEvent(ElementsAlimerMod instance) {
        super(instance, 44);
    }

    public static void executeProcedure(Map<String, Object> dependencies) {
        if (dependencies.get("entity") == null) {
            System.err.println("Failed to load dependency entity for procedure TimeLoopEvent!");
        } else if (dependencies.get("x") == null) {
            System.err.println("Failed to load dependency x for procedure TimeLoopEvent!");
        } else if (dependencies.get("y") == null) {
            System.err.println("Failed to load dependency y for procedure TimeLoopEvent!");
        } else if (dependencies.get("z") == null) {
            System.err.println("Failed to load dependency z for procedure TimeLoopEvent!");
        } else if (dependencies.get("world") == null) {
            System.err.println("Failed to load dependency world for procedure TimeLoopEvent!");
        } else {
            Entity entity = (Entity)dependencies.get("entity");
            int x = (Integer)dependencies.get("x");
            int y = (Integer)dependencies.get("y");
            int z = (Integer)dependencies.get("z");
            World world = (World)dependencies.get("world");
            double Yaw = 0.0;
            if (entity.getEntityData().getDouble("_timeLoopTimer") > 0.0) {
                if (dependencies.get("event") != null) {
                    Object _obj = dependencies.get("event");
                    if (_obj instanceof Event) {
                        Event _evt = (Event)_obj;
                        if (_evt.isCancelable()) {
                            _evt.setCanceled(true);
                        }
                    }
                }

                if (entity instanceof EntityLivingBase) {
                    ((EntityLivingBase)entity).setHealth((float)entity.getEntityData().getDouble("_timeLoop_HP"));
                }

                entity.setPositionAndUpdate(entity.getEntityData().getDouble("_timeLoop_X"), entity.getEntityData().getDouble("_timeLoop_Y"), entity.getEntityData().getDouble("_timeLoop_Z"));
                entity.getEntityData().setBoolean("_TimeLoopReset", true);
                entity.getEntityData().setDouble("_timeLoopTimer", 0.0);
                entity.attackEntityFrom(DamageSource.GENERIC, 0.1F);
                entity.motionX = 0.0;
                entity.motionY = 0.0;
                entity.motionZ = 0.0;
                if (world instanceof WorldServer) {
                    ((WorldServer)world).spawnParticle(EnumParticleTypes.TOTEM, (double)x, (double)y, (double)z, 25, 0.25, 0.25, 0.25, 1.0, new int[0]);
                }
            }

        }
    }

    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {
        if (event != null && event.getEntity() != null) {
            Entity entity = event.getEntity();
            int i = (int)entity.posX;
            int j = (int)entity.posY;
            int k = (int)entity.posZ;
            World world = entity.world;
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