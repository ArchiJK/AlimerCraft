package ua.hero.archiheroes.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.hero.archiheroes.AlimerCraft;

public class AHTab extends CreativeTabs {

    public AHTab() {
        super(AlimerCraft.MODID);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ItemStack createIcon() {
        return new ItemStack(StoneItems.WINTER_STONE);
    }

}