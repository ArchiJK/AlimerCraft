package ua.hero.archiheroes.infinity.items;

import lucraft.mods.heroesexpansion.abilities.AbilityForceField;
import lucraft.mods.heroesexpansion.abilities.AbilityPortal;
import lucraft.mods.lucraftcore.infinity.EnumInfinityStone;
import lucraft.mods.lucraftcore.infinity.ModuleInfinity;
import lucraft.mods.lucraftcore.infinity.items.ItemInfinityStone;
import lucraft.mods.lucraftcore.superpowers.abilities.Ability;
import lucraft.mods.lucraftcore.util.abilitybar.EnumAbilityBarColor;
import lucraft.mods.lucraftcore.util.helper.StringHelper;
import net.minecraft.entity.EntityLivingBase;

public class ItemMindStone extends ItemInfinityStone {
    public ItemMindStone(String name) {
        this.setTranslationKey(name);
        this.setRegistryName(StringHelper.unlocalizedToResourceName(name));
        this.setCreativeTab(ModuleInfinity.TAB);
    }

    public EnumInfinityStone getType() {
        return EnumInfinityStone.MIND;
    }

    public boolean isContainer() {
        return false;
    }

    public Ability.AbilityMap addStoneAbilities(EntityLivingBase entity, Ability.AbilityMap abilities, Ability.EnumAbilityContext context) {
        return super.addStoneAbilities(entity, abilities, context);
    }
}