package ua.hero.archiheroes.init;

import ua.hero.archiheroes.AlimerCraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import ua.hero.archiheroes.items.ModItems;

@EventBusSubscriber(value = Side.CLIENT, modid = AlimerCraft.MODID)
public class ModelRegistrationHandler {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        //registerModel(ModItems.FIRST_ITEM, 0);
        
        registerModel(ModItems.VIBRANIUM_SWORD, 0);
        registerModel(ModItems.VIBRANIUM_PICKAXE, 0);
        registerModel(ModItems.VIBRANIUM_AXE, 0);
        registerModel(ModItems.VIBRANIUM_SHOVEL, 0);
        //registerModel(ModItems.VIBRANIUM_HOE, 0);
        
        //registerModel(ModItems.VIBRANIUM_HELMET, 0);
        //registerModel(ModItems.VIBRANIUM_CHESTPLATE, 0);
        //registerModel(ModItems.VIBRANIUM_LEGGINGS, 0);
        //registerModel(ModItems.VIBRANIUM_BOOTS, 0);
        
    }

    private static void registerModel(Item item, int meta) {
        ModelLoader.setCustomModelResourceLocation(item, meta,
                new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

}