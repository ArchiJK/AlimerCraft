package ua.hero.archiheroes.superpowers.powers;

import lucraft.mods.lucraftcore.superpowers.Superpower;
import lucraft.mods.lucraftcore.superpowers.SuperpowerHandler;
import lucraft.mods.lucraftcore.superpowers.abilities.*;
import lucraft.mods.lucraftcore.superpowers.abilities.predicates.AbilityConditionLevel;
import lucraft.mods.lucraftcore.superpowers.abilities.supplier.AbilityContainerSuperpower;
import lucraft.mods.lucraftcore.superpowers.effects.Effect;
import lucraft.mods.lucraftcore.superpowers.suitsets.SuitSet;
import lucraft.mods.lucraftcore.util.abilitybar.EnumAbilityBarColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.hero.archiheroes.AHIconHelper;
import ua.hero.archiheroes.AlimerCraft;
import ua.hero.archiheroes.superpowers.abilities.ability.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SuperpowerAlimer extends Superpower {

    public List<Effect> effects = new ArrayList<>();

    public SuperpowerAlimer() {
        super("alimer");
        this.setRegistryName(AlimerCraft.MODID, "alimer");

    }


    @SideOnly(Side.CLIENT)
    @Override
    public void renderIcon(Minecraft mc, Gui gui, int x, int y) {
        AHIconHelper.drawSuperpowerIcon(mc, gui, x, y, 0, 1);
    }

    public UUID uuid = UUID.fromString("53415337-7d5d-4b0e-80c2-62e664a06dfe");



    @Override
    public int getCapsuleColor() {
        return 724107;
    }


    @Override
    public boolean canLevelUp() {
        return true;
    }

    @Override
    public int getMaxLevel() {
        return 10;
    }

    @Override
    public Ability.AbilityMap addDefaultAbilities(EntityLivingBase entity, Ability.AbilityMap abilities, Ability.EnumAbilityContext context) {
        abilities.put("flight", new AbilityFlight(entity).setDataValue(AbilityFlight.SPEED, 1.5F).setDataValue(AbilityFlight.SPRINT_SPEED, 6.2F).setDataValue(Ability.BAR_COLOR, EnumAbilityBarColor.CYAN));
        
        abilities.put("abilitybackstabteleport", new AbilityBackstabTeleport(entity).setMaxCooldown(20).setDataValue(Ability.BAR_COLOR, EnumAbilityBarColor.CYAN).addCondition(new AbilityConditionLevel(3)));

        abilities.put("abilitytotemofundying", new AbilityTotemOfUndyingAction(entity).setMaxCooldown(1000).setDataValue(Ability.BAR_COLOR, EnumAbilityBarColor.CYAN).addCondition(new AbilityConditionLevel(9)));

        abilities.put("abilitytimeregen", new AbilityTimeRegen(entity).setMaxCooldown(100).setDataValue(Ability.BAR_COLOR, EnumAbilityBarColor.CYAN).addCondition(new AbilityConditionLevel(8)));

        abilities.put("abilityvampirism", new AbilityVampirism(entity).setMaxCooldown(5 * 10).setDataValue(Ability.BAR_COLOR, EnumAbilityBarColor.CYAN).addCondition(new AbilityConditionLevel(8)));

        abilities.put("abilityinvulnerability", new AbilityInvulnerability(entity).setMaxCooldown(200).setDataValue(Ability.BAR_COLOR, EnumAbilityBarColor.CYAN).addCondition(new AbilityConditionLevel(10)));

        abilities.put("tough_lungs", new AbilityToughLungs(entity));
        abilities.put("strength", new AbilityStrength(entity).setDataValue(AbilityAttributeModifier.UUID, uuid).setDataValue(AbilityAttributeModifier.AMOUNT, 14f));
        abilities.put("resistance", new AbilityDamageResistance(entity).setDataValue(AbilityAttributeModifier.UUID, uuid).setDataValue(AbilityAttributeModifier.AMOUNT, 6f));
        abilities.put("speed", new AbilitySprint(entity).setDataValue(AbilityAttributeModifier.UUID, uuid).setDataValue(AbilityAttributeModifier.AMOUNT, 0.22f));
        abilities.put("health", new AbilityHealth(entity).setDataValue(AbilityAttributeModifier.UUID, uuid).setDataValue(AbilityAttributeModifier.AMOUNT, 20f));
        abilities.put("jump", new AbilityJumpBoost(entity).setDataValue(AbilityAttributeModifier.UUID, uuid).setDataValue(AbilityAttributeModifier.AMOUNT, 1.3f).setDataValue(AbilityAttributeModifier.OPERATION, 1));
        abilities.put("fall_resistance", new AbilityFallResistance(entity).setDataValue(AbilityAttributeModifier.UUID, uuid).setDataValue(AbilityAttributeModifier.AMOUNT, 20f));
        return abilities;
    }

    public static final int XP_AMOUNT_KILL = 30;
    public static final int XP_AMOUNT_VAMPIRISM = 10;
    public static final int XP_AMOUNT_INVULNERABILITY = 10;

    public static void addXP(EntityLivingBase entity, int xp) {
     if (SuperpowerHandler.hasSuperpower(entity, AHSuperpowers.ALIMER)) {
            SuitSet suitSet = SuitSet.getSuitSet(entity);

            if (suitSet != null && suitSet.getData() != null && suitSet.getData().hasKey("alimer_multiplier"))
                xp *= suitSet.getData().getFloat("alimer_multiplier");

            ((AbilityContainerSuperpower) Ability.getAbilityContainer(Ability.EnumAbilityContext.SUPERPOWER, entity)).addXP(xp, false);
        }
    }

}
