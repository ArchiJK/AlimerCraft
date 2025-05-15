package ua.hero.archiheroes.items;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import ua.hero.archiheroes.AlimerCraft;

public class AHMaterial {

    public static final ToolMaterial AH_TOOL = EnumHelper.addToolMaterial(AlimerCraft.MODID + ":" + "ah_tool", 5, 3200, 12.0F, 4.5F, 10);

    public static final ArmorMaterial AH_ARMOR = EnumHelper.addArmorMaterial
            (AlimerCraft.MODID + ":" + "ah_armor", AlimerCraft.MODID + ":vibranium", 16, new int[]{2, 5, 6, 3}, 5, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);
}