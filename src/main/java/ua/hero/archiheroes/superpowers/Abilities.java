package ua.hero.archiheroes.superpowers;

import lucraft.mods.lucraftcore.superpowers.abilities.AbilityEntry;
import lucraft.mods.lucraftcore.superpowers.abilities.AbilityToggle;
import lucraft.mods.lucraftcore.superpowers.abilities.predicates.AbilityCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ua.hero.archiheroes.infinity.ability.AbilityTimeLoop;
import ua.hero.archiheroes.infinity.ability.AbilityTimeWarp;
import ua.hero.archiheroes.superpowers.abilities.*;
import ua.hero.archiheroes.superpowers.abilities.ability.*;

@EventBusSubscriber(
        modid = "alimercraft"
)
public class Abilities {
    public Abilities() {
    }

    @SubscribeEvent
    public static void onRegisterAbilities(RegistryEvent.Register<AbilityEntry> e) {
        e.getRegistry().register(new AbilityEntry(AbilityCommand.class, new ResourceLocation("alimercraft", "abilitycommand")));
        e.getRegistry().register(new AbilityEntry(AbilityCommandHeld.class, new ResourceLocation("alimercraft", "abilitycommand_held")));
        e.getRegistry().register(new AbilityEntry(AbilityCommandLoop.class, new ResourceLocation("alimercraft", "abilitycommand_loop")));
        e.getRegistry().register(new AbilityEntry(AbilityCommandOnGain.class, new ResourceLocation("alimercraft", "abilitycommand_ongain")));
        e.getRegistry().register(new AbilityEntry(AbilityCommandOnLose.class, new ResourceLocation("alimercraft", "abilitycommand_onlose")));
        e.getRegistry().register(new AbilityEntry(AbilityCommandToggle.class, new ResourceLocation("alimercraft", "abilitycommand_toggle")));

        e.getRegistry().register(new AbilityEntry(AbilityTogglePowerTwo.class, new ResourceLocation("alimercraft", "abilitytogglepower_two")));

        e.getRegistry().register(new AbilityEntry(AbilityInvulnerability.class, new ResourceLocation("alimercraft", "abilityinvulnerability")));

        //e.getRegistry().register(new AbilityEntry(AbilityMagneticShield.class, new ResourceLocation("alimercraft", "abilitymagneticshield")));

        e.getRegistry().register(new AbilityEntry(AbilityTimeRegen.class, new ResourceLocation("alimercraft", "abilitytimeregen")));
        e.getRegistry().register(new AbilityEntry(AbilityVampirism.class, new ResourceLocation("alimercraft", "abilityvampirism")));

        e.getRegistry().register(new AbilityEntry(AbilityBackstabTeleport.class, new ResourceLocation("alimercraft", "backstabteleport")));
        e.getRegistry().register(new AbilityEntry(AbilityTotemOfUndyingAction.class, new ResourceLocation("alimercraft", "totemaction")));


        e.getRegistry().register(new AbilityEntry(AbilityTimeWarp.class, new ResourceLocation("alimercraft", "time_warp")));
        e.getRegistry().register(new AbilityEntry(AbilityTimeLoop.class, new ResourceLocation("alimercraft", "time_loop")));
    }

    @SubscribeEvent
    public static void onRegisterConditions(RegistryEvent.Register<AbilityCondition.ConditionEntry> e) {
    }
}
