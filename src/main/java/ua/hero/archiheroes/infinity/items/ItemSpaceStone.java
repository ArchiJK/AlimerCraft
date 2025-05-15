package ua.hero.archiheroes.infinity.items;

import lucraft.mods.lucraftcore.infinity.EnumInfinityStone;
import lucraft.mods.lucraftcore.infinity.ModuleInfinity;
import lucraft.mods.lucraftcore.infinity.items.ItemInfinityStone;
import lucraft.mods.lucraftcore.superpowers.abilities.Ability;
import lucraft.mods.lucraftcore.util.abilitybar.EnumAbilityBarColor;
import lucraft.mods.lucraftcore.util.helper.StringHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import ua.hero.archiheroes.infinity.ability.AbilityTimeLoop;
import ua.hero.archiheroes.infinity.ability.AbilityTimeWarp;
import ua.hero.archiheroes.superpowers.abilities.ability.*;

public class ItemSpaceStone extends ItemInfinityStone {
    public ItemSpaceStone(String name) {
        this.setTranslationKey(name);
        this.setRegistryName(StringHelper.unlocalizedToResourceName(name));
        this.setCreativeTab(ModuleInfinity.TAB);
    }

    public EnumInfinityStone getType() {
        return EnumInfinityStone.SPACE;
    }

    public boolean isContainer() {
        return false;
    }

    public Ability.AbilityMap addStoneAbilities(EntityLivingBase entity, Ability.AbilityMap abilities, Ability.EnumAbilityContext context) {
        //abilities.put("backstabteleport", new AbilityBackstabTeleport(entity).setDataValue(Ability.BAR_COLOR, EnumAbilityBarColor.LIME));

        //abilities.put("totemaction", new AbilityTotemOfUndyingAction(entity).setDataValue(Ability.BAR_COLOR, EnumAbilityBarColor.LIME));

        //abilities.put("abilitytimeregen", new AbilityTimeRegen(entity).setDataValue(Ability.BAR_COLOR, EnumAbilityBarColor.LIME));

        //abilities.put("abilityvampirism", new AbilityVampirism(entity).setDataValue(Ability.BAR_COLOR, EnumAbilityBarColor.LIME));

        //abilities.put("abilitymagneticshield", new AbilityMagneticShield(entity).setDataValue(Ability.BAR_COLOR, EnumAbilityBarColor.LIME));

        //abilities.put("abilityinvulnerability", new AbilityInvulnerability(entity).setDataValue(Ability.BAR_COLOR, EnumAbilityBarColor.LIME));

        return super.addStoneAbilities(entity, abilities, context);
    }
}