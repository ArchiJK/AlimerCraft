package ua.hero.archiheroes.infinity.items;

import lucraft.mods.lucraftcore.infinity.EnumInfinityStone;
import lucraft.mods.lucraftcore.infinity.ModuleInfinity;
import lucraft.mods.lucraftcore.infinity.items.ItemInfinityStone;
import lucraft.mods.lucraftcore.superpowers.abilities.Ability;
import lucraft.mods.lucraftcore.superpowers.abilities.AbilityFlight;
import lucraft.mods.lucraftcore.util.abilitybar.EnumAbilityBarColor;
import lucraft.mods.lucraftcore.util.helper.StringHelper;
import lucraft.mods.speedsterheroes.abilities.AbilityPhasing;
import net.minecraft.entity.EntityLivingBase;

public class ItemSoulStone extends ItemInfinityStone {
    public ItemSoulStone(String name) {
        this.setTranslationKey(name);
        this.setRegistryName(StringHelper.unlocalizedToResourceName(name));
        this.setCreativeTab(ModuleInfinity.TAB);
    }

    public EnumInfinityStone getType() {
        return EnumInfinityStone.SOUL;
    }

    public boolean isContainer() {
        return false;
    }

    public Ability.AbilityMap addStoneAbilities(EntityLivingBase entity, Ability.AbilityMap abilities, Ability.EnumAbilityContext context) {
        abilities.put("phasing", (new AbilityPhasing(entity)).setDataValue(Ability.BAR_COLOR, EnumAbilityBarColor.YELLOW).setMaxCooldown(500));
        abilities.put("flight", (new AbilityFlight(entity)).setDataValue(AbilityFlight.SPEED, 0.5F).setDataValue(AbilityFlight.SPRINT_SPEED, 2.0F).setDataValue(Ability.BAR_COLOR, EnumAbilityBarColor.YELLOW));
        return super.addStoneAbilities(entity, abilities, context);
    }
}