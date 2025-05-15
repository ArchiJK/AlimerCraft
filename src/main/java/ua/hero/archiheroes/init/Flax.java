package ua.hero.archiheroes.init;

import net.minecraft.item.Item;
import ua.hero.archiheroes.AlimerCraft;

public class Flax {

    public static Item setItemName(final Item item, final String name) {
        item.setRegistryName(AlimerCraft.MODID, name).setTranslationKey(AlimerCraft.MODID + "." + name);
        return item;
    }

}