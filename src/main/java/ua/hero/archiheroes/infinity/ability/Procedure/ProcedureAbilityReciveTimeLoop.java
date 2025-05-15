package ua.hero.archiheroes.infinity.ability.Procedure;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import ua.hero.archiheroes.ElementsAlimerMod;
import ua.hero.archiheroes.ElementsAlimerMod.ModElement.Tag;

@Tag
public class ProcedureAbilityReciveTimeLoop extends ElementsAlimerMod.ModElement {
    public ProcedureAbilityReciveTimeLoop(ElementsAlimerMod instance) {
        super(instance, 43);
    }

    public static void executeProcedure(Map<String, Object> dependencies) {
        if (dependencies.get("entity") == null) {
            System.err.println("Failed to load dependency entity for procedure AbilityReciveTimeLoop!");
        } else if (dependencies.get("x") == null) {
            System.err.println("Failed to load dependency x for procedure AbilityReciveTimeLoop!");
        } else if (dependencies.get("y") == null) {
            System.err.println("Failed to load dependency y for procedure AbilityReciveTimeLoop!");
        } else if (dependencies.get("z") == null) {
            System.err.println("Failed to load dependency z for procedure AbilityReciveTimeLoop!");
        } else if (dependencies.get("world") == null) {
            System.err.println("Failed to load dependency world for procedure AbilityReciveTimeLoop!");
        } else {
            Entity entity = (Entity)dependencies.get("entity");
            int x = (Integer)dependencies.get("x");
            int y = (Integer)dependencies.get("y");
            int z = (Integer)dependencies.get("z");
            World world = (World)dependencies.get("world");
            if (entity.getEntityData().getBoolean("_ar_TimeLoop")) {
                entity.getEntityData().setBoolean("_ar_TimeLoop", false);
                if (entity.getEntityData().getDouble("_timeLoopTimer") == 0.0) {
                    entity.getEntityData().setDouble("_timeLoop_X", entity.posX);
                    entity.getEntityData().setDouble("_timeLoop_Y", entity.posY);
                    entity.getEntityData().setDouble("_timeLoop_Z", entity.posZ);
                    entity.getEntityData().setDouble("_timeLoop_HP", (double)(entity instanceof EntityLivingBase ? ((EntityLivingBase)entity).getHealth() : -1.0F));
                }

                entity.getEntityData().setDouble("_timeLoopTimer", 10.0);
            }

            if (entity.getEntityData().getDouble("_timeLoopTimer") > 0.0) {
                entity.getEntityData().setDouble("_timeLoopTimer", entity.getEntityData().getDouble("_timeLoopTimer") - 1.0);
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

