package ua.hero.archiheroes.superpowers.powers;

import lucraft.mods.lucraftcore.superpowers.Superpower;
import lucraft.mods.lucraftcore.superpowers.events.AbilityKeyEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ua.hero.archiheroes.AlimerCraft;
import ua.hero.archiheroes.superpowers.abilities.ability.AbilityInvulnerability;
import ua.hero.archiheroes.superpowers.abilities.ability.AbilityVampirism;

@Mod.EventBusSubscriber(modid = AlimerCraft.MODID)
public class AHSuperpowers {

    public static SuperpowerAlimer ALIMER = new SuperpowerAlimer();

    @SubscribeEvent
    public static void onRegisterSuperpowers(RegistryEvent.Register<Superpower> e) {
        e.getRegistry().register(ALIMER);
    }

    @SubscribeEvent
    public static void onKill(LivingDeathEvent e) {
        if (e.getSource() != null && e.getSource().getTrueSource() != null && e.getSource().getTrueSource() instanceof EntityLivingBase) {
            SuperpowerAlimer.addXP((EntityLivingBase) e.getSource().getTrueSource(), SuperpowerAlimer.XP_AMOUNT_KILL);
        }
    }

    @SubscribeEvent
    public static void onKill(AbilityKeyEvent e) {
        if (e.pressed) {
            if (e.ability instanceof AbilityVampirism)
                SuperpowerAlimer.addXP(e.ability.getEntity(), SuperpowerAlimer.XP_AMOUNT_VAMPIRISM);
            else if (e.ability instanceof AbilityInvulnerability)
                SuperpowerAlimer.addXP(e.ability.getEntity(), SuperpowerAlimer.XP_AMOUNT_INVULNERABILITY);
        }
    }

}
