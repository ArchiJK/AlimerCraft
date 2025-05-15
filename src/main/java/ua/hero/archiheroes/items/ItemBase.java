package ua.hero.archiheroes.items;

import lucraft.mods.lucraftcore.util.helper.StringHelper;
import net.minecraft.item.Item;
import ua.hero.archiheroes.AlimerCraft;

public class ItemBase extends Item {
    public String name;

    public ItemBase(String name) {
        this.setTranslationKey(name);
        this.setRegistryName(StringHelper.unlocalizedToResourceName(name));
        this.name = name;
        this.setCreativeTab(AlimerCraft.AHTAB);
    }
}
