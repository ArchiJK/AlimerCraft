package ua.hero.archiheroes.items.block;

import net.minecraftforge.oredict.OreDictionary;
import ua.hero.archiheroes.items.ModBlocks;

public class OreDictionaryInit
{
    public static void registerOres()
    {
        OreDictionary.registerOre("blockSilver", ModBlocks.MURAGEN_BLOCK);
    }
}