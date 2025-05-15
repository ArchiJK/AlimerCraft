package ua.hero.archiheroes.infinity.items;

import lucraft.mods.lucraftcore.infinity.EnumInfinityStone;
import lucraft.mods.lucraftcore.infinity.ModuleInfinity;
import lucraft.mods.lucraftcore.infinity.items.ItemInfinityStone;
import lucraft.mods.lucraftcore.superpowers.abilities.Ability;
import lucraft.mods.lucraftcore.util.abilitybar.EnumAbilityBarColor;
import lucraft.mods.lucraftcore.util.helper.StringHelper;
import net.minecraft.entity.EntityLivingBase;
import ua.hero.archiheroes.infinity.ability.AbilityTimeLoop;
import ua.hero.archiheroes.infinity.ability.AbilityTimeWarp;

public class ItemTimeStone extends ItemInfinityStone {
    public ItemTimeStone(String name) {
        this.setTranslationKey(name);
        this.setRegistryName(StringHelper.unlocalizedToResourceName(name));
        this.setCreativeTab(ModuleInfinity.TAB);
    }

    public EnumInfinityStone getType() {
        return EnumInfinityStone.TIME;
    }

    public boolean isContainer() {
        return false;
    }

    public Ability.AbilityMap addStoneAbilities(EntityLivingBase entity, Ability.AbilityMap abilities, Ability.EnumAbilityContext context) {
        abilities.put("time_warp", new AbilityTimeWarp(entity).setDataValue(Ability.BAR_COLOR, EnumAbilityBarColor.LIME));
        abilities.put("time_loop", new AbilityTimeLoop(entity).setDataValue(Ability.BAR_COLOR, EnumAbilityBarColor.LIME).setMaxCooldown(1000));
        return super.addStoneAbilities(entity, abilities, context);
    }
}