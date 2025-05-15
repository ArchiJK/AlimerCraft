package ua.hero.archiheroes.items;

import ua.hero.archiheroes.AlimerCraft;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ua.hero.archiheroes.init.Flax;
import ua.hero.archiheroes.items.arminst.*;

@EventBusSubscriber(modid = AlimerCraft.MODID)
public class RegistrationHandler {

    @SubscribeEvent
    public static void registerItems(Register<Item> event) {
        final Item[] items = {

                // ITEMS
                //new Item().setRegistryName(ArchiHeroes.MODID, "first_item").setTranslationKey(ArchiHeroes.MODID + "." + "first_item").setCreativeTab(ArchiHeroes.AHTAB),



                Flax.setItemName(new AHSword(AHMaterial.AH_TOOL), "vibranium_sword").setCreativeTab(AlimerCraft.AHTAB),
                Flax.setItemName(new AHPickaxe(AHMaterial.AH_TOOL), "vibranium_pickaxe").setCreativeTab(AlimerCraft.AHTAB),
                Flax.setItemName(new AHAxe(AHMaterial.AH_TOOL, 8.0F, -3.1F), "vibranium_axe").setCreativeTab(AlimerCraft.AHTAB),
                Flax.setItemName(new AHShovel(AHMaterial.AH_TOOL), "vibranium_shovel").setCreativeTab(AlimerCraft.AHTAB),
                //Flax.setItemName(new AHHoe(AHMaterial.AH_TOOL), "vibranium_hoe").setCreativeTab(ArchiHeroes.AHTAB),

                //Flax.setItemName(new AHArmor(AHMaterial.AH_ARMOR, EntityEquipmentSlot.HEAD), "vibranium_helmet").setCreativeTab(ArchiHeroes.AHTAB),
                //Flax.setItemName(new AHArmor(AHMaterial.AH_ARMOR, EntityEquipmentSlot.CHEST), "vibranium_chestplate").setCreativeTab(ArchiHeroes.AHTAB),
                //Flax.setItemName(new AHArmor(AHMaterial.AH_ARMOR, EntityEquipmentSlot.LEGS), "vibranium_leggings").setCreativeTab(ArchiHeroes.AHTAB),
                //Flax.setItemName(new AHArmor(AHMaterial.AH_ARMOR, EntityEquipmentSlot.FEET), "vibranium_boots").setCreativeTab(ArchiHeroes.AHTAB)
                        };

        event.getRegistry().registerAll(items);
    }


}