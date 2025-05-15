package ua.hero.archiheroes.infinity.items;

import lucraft.mods.heroesexpansion.abilities.AbilityForceField;
import lucraft.mods.heroesexpansion.abilities.AbilityHeatVision;
import lucraft.mods.heroesexpansion.abilities.AbilityPhotonBlast;
import lucraft.mods.heroesexpansion.conditions.AbilityConditionSolarEnergy;
import lucraft.mods.lucraftcore.infinity.EnumInfinityStone;
import lucraft.mods.lucraftcore.infinity.ModuleInfinity;
import lucraft.mods.lucraftcore.infinity.items.ItemInfinityStone;
import lucraft.mods.lucraftcore.superpowers.abilities.Ability;
import lucraft.mods.lucraftcore.superpowers.abilities.data.AbilityDataIcon;
import lucraft.mods.lucraftcore.util.abilitybar.EnumAbilityBarColor;
import lucraft.mods.lucraftcore.util.helper.StringHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import org.lwjgl.util.Color;
import ua.hero.archiheroes.superpowers.abilities.AbilityCommand;
import ua.hero.archiheroes.superpowers.abilities.AbilityCommandHeld;
import ua.hero.archiheroes.superpowers.abilities.AbilityCommandLoop;

import static lucraft.mods.lucraftcore.superpowers.abilities.Ability.drawIcon;
import static ua.hero.archiheroes.superpowers.abilities.AbilityCommand.COMMANDS;

public class ItemWinterStone extends ItemInfinityStone {
    public ItemWinterStone(String name) {
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
        abilities.put("winterstone_frost_barrier", new AbilityCommandHeld(entity).setDataValue(AbilityCommandHeld.COMMANDS, new AbilityCommand.CommandList().addCommand("cast ebwizardry:frost_barrier @p")).setDataValue(Ability.BAR_COLOR, EnumAbilityBarColor.WHITE).setDataValue(Ability.ICON, new AbilityDataIcon.Icon(new ResourceLocation("alimercraft", "textures/gui/icon/sun_addons/iceshield.png"))).setCustomTitle(new TextComponentTranslation("ability.winterstone_frost_barrier.name")));

        abilities.put("winterstone_forcefield", new AbilityCommand(entity).setDataValue(AbilityCommand.COMMANDS, new AbilityCommand.CommandList().addCommand("cast ebwizardry:forcefield @s")).setDataValue(Ability.BAR_COLOR, EnumAbilityBarColor.WHITE).setMaxCooldown(500).setDataValue(Ability.ICON, new AbilityDataIcon.Icon(new ResourceLocation("alimercraft", "textures/gui/icon/alimercraft/sphere_shield.png"))).setCustomTitle(new TextComponentTranslation("ability.winterstone_forcefield.name")));

        abilities.put("winterstone_wall_of_frost", new AbilityCommandHeld(entity).setDataValue(AbilityCommandHeld.COMMANDS, new AbilityCommand.CommandList().addCommand("cast ebwizardry:wall_of_frost @p 1")).setDataValue(Ability.BAR_COLOR, EnumAbilityBarColor.WHITE).setDataValue(Ability.ICON, new AbilityDataIcon.Icon(new ItemStack(Blocks.ICE))).setCustomTitle(new TextComponentTranslation("ability.winterstone_ground_of_frost.name")));

        abilities.put("winterstone_ice_block", new AbilityCommandHeld(entity).setDataValue(AbilityCommandHeld.COMMANDS, new AbilityCommand.CommandList().addCommand("/fill ~ ~-1 ~-1 ~ ~-1 ~1 packed_ice 0 replace air").addCommand("/fill ~-1 ~-1 ~ ~1 ~-1 ~ packed_ice 0 replace air")).setDataValue(Ability.BAR_COLOR, EnumAbilityBarColor.WHITE).setDataValue(Ability.ICON, new AbilityDataIcon.Icon(new ResourceLocation("alimercraft", "textures/gui/icon/alimercraft/ice_ground.png"))).setCustomTitle(new TextComponentTranslation("ability.winterstone_ice_block.name")));

        abilities.put("winterstone_lightning_disc", new AbilityCommand(entity).setDataValue(AbilityCommand.COMMANDS, new AbilityCommand.CommandList().addCommand("cast ebwizardry:lightning_disc @s")).setDataValue(Ability.BAR_COLOR, EnumAbilityBarColor.WHITE).setMaxCooldown(30).setDataValue(Ability.ICON, new AbilityDataIcon.Icon(new ResourceLocation("alimercraft", "textures/gui/icon/sun_addons/lightning_shuriken.png"))).setCustomTitle(new TextComponentTranslation("ability.winterstone_lightning_disc.name")));
        return super.addStoneAbilities(entity, abilities, context);
    }

}