package ua.hero.archiheroes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;

public class ElementsAlimerMod implements IFuelHandler, IWorldGenerator {
    public final List<ModElement> elements = new ArrayList();
    public final List<Supplier<Block>> blocks = new ArrayList();
    public final List<Supplier<Item>> items = new ArrayList();
    public final List<Supplier<Biome>> biomes = new ArrayList();
    public final List<Supplier<EntityEntry>> entities = new ArrayList();
    public final List<Supplier<Potion>> potions = new ArrayList();
    public static Map<ResourceLocation, SoundEvent> sounds = new HashMap();
    private int messageID = 0;

    public ElementsAlimerMod() {
        sounds.put(new ResourceLocation("alimercraft", "snap"), new SoundEvent(new ResourceLocation("alimercraft", "snap")));
        sounds.put(new ResourceLocation("alimercraft", "ear_ringing"), new SoundEvent(new ResourceLocation("alimercraft", "ear_ringing")));
        sounds.put(new ResourceLocation("alimercraft", "use_infinity_gauntlet"), new SoundEvent(new ResourceLocation("alimercraft", "use_infinity_gauntlet")));
        sounds.put(new ResourceLocation("alimercraft", "infinity_gauntlet_radiation"), new SoundEvent(new ResourceLocation("alimercraft", "infinity_gauntlet_radiation")));
    }

    public void preInit(FMLPreInitializationEvent event) {
        try {
            Iterator var6 = event.getAsmData().getAll(ModElement.Tag.class.getName()).iterator();

            while(var6.hasNext()) {
                ASMDataTable.ASMData asmData = (ASMDataTable.ASMData)var6.next();
                Class<?> clazz = Class.forName(asmData.getClassName());
                if (clazz.getSuperclass() == ModElement.class) {
                    this.elements.add((ModElement)clazz.getConstructor(this.getClass()).newInstance(this));
                }
            }
        } catch (Exception var5) {
            Exception e = var5;
            e.printStackTrace();
        }

        Collections.sort(this.elements);
        this.elements.forEach(ModElement::initElements);
        this.addNetworkMessage(AlimerCraftModVariables.WorldSavedDataSyncMessageHandler.class, AlimerCraftModVariables.WorldSavedDataSyncMessage.class, Side.SERVER, Side.CLIENT);
    }

    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator cg, IChunkProvider cp) {
        this.elements.forEach((element) -> {
            element.generateWorld(random, chunkX * 16, chunkZ * 16, world, world.provider.getDimension(), cg, cp);
        });
    }

    public int getBurnTime(ItemStack fuel) {
        Iterator var2 = this.elements.iterator();

        int ret;
        do {
            if (!var2.hasNext()) {
                return 0;
            }

            ModElement element = (ModElement)var2.next();
            ret = element.addFuel(fuel);
        } while(ret == 0);

        return ret;
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.player.world.isRemote) {
            WorldSavedData mapdata = AlimerCraftModVariables.MapVariables.get(event.player.world);
            WorldSavedData worlddata = AlimerCraftModVariables.WorldVariables.get(event.player.world);
            if (mapdata != null) {
                AlimerCraft.packetHandler.sendTo(new AlimerCraftModVariables.WorldSavedDataSyncMessage(0, mapdata), (EntityPlayerMP)event.player);
            }

            if (worlddata != null) {
                AlimerCraft.packetHandler.sendTo(new AlimerCraftModVariables.WorldSavedDataSyncMessage(1, worlddata), (EntityPlayerMP)event.player);
            }
        }

    }

    @SubscribeEvent
    public void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!event.player.world.isRemote) {
            WorldSavedData worlddata = AlimerCraftModVariables.WorldVariables.get(event.player.world);
            if (worlddata != null) {
                AlimerCraft.packetHandler.sendTo(new AlimerCraftModVariables.WorldSavedDataSyncMessage(1, worlddata), (EntityPlayerMP)event.player);
            }
        }

    }

    public <T extends IMessage, V extends IMessage> void addNetworkMessage(Class<? extends IMessageHandler<T, V>> handler, Class<T> messageClass, Side... sides) {
        Side[] var4 = sides;
        int var5 = sides.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Side side = var4[var6];
            AlimerCraft.packetHandler.registerMessage(handler, messageClass, this.messageID, side);
        }

        ++this.messageID;
    }

    public List<ModElement> getElements() {
        return this.elements;
    }

    public List<Supplier<Block>> getBlocks() {
        return this.blocks;
    }

    public List<Supplier<Item>> getItems() {
        return this.items;
    }

    public List<Supplier<Biome>> getBiomes() {
        return this.biomes;
    }

    public List<Supplier<EntityEntry>> getEntities() {
        return this.entities;
    }

    public List<Supplier<Potion>> getPotions() {
        return this.potions;
    }

    public static class ModElement implements Comparable<ModElement> {
        protected final ElementsAlimerMod elements;
        protected final int sortid;

        public ModElement(ElementsAlimerMod elements, int sortid) {
            this.elements = elements;
            this.sortid = sortid;
        }

        public void initElements() {
        }

        public void init(FMLInitializationEvent event) {
        }

        public void preInit(FMLPreInitializationEvent event) {
        }

        public void generateWorld(Random random, int posX, int posZ, World world, int dimID, IChunkGenerator cg, IChunkProvider cp) {
        }

        public void serverLoad(FMLServerStartingEvent event) {
        }

        public void registerModels(ModelRegistryEvent event) {
        }

        public int addFuel(ItemStack fuel) {
            return 0;
        }

        public int compareTo(ModElement other) {
            return this.sortid - other.sortid;
        }

        @Retention(RetentionPolicy.RUNTIME)
        public @interface Tag {
        }
    }


}
