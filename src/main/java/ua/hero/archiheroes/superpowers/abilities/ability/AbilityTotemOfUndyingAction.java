package ua.hero.archiheroes.superpowers.abilities.ability;

import lucraft.mods.lucraftcore.superpowers.abilities.AbilityToggle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.init.MobEffects;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.hero.archiheroes.AHIconHelper;

import lucraft.mods.lucraftcore.superpowers.abilities.AbilityToggle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.init.MobEffects;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.hero.archiheroes.AHIconHelper;

public class AbilityTotemOfUndyingAction extends AbilityToggle {

    private static final int COOLDOWN_TICKS = 1000; // Длительность кулдауна

    public AbilityTotemOfUndyingAction(EntityLivingBase entity) {
        super(entity);
    }

    @Override
    public void updateTick() {
        // Проверяем, активирована ли способность и есть ли кулдаун
        if (isEnabled() && entity.getHealth() <= 4.0F) {
            preventDeath(); // Если здоровье меньше порога, активируем способность и устанавливаем кулдаун
        }

        // Логика для кулдауна
        if (this.entity.getEntityData().getBoolean("_TotemReset")) {
            this.entity.getEntityData().setBoolean("_TotemReset", false);
            this.setCooldown(COOLDOWN_TICKS); // Устанавливаем кулдаун
            this.setEnabled(false); // Отключаем способность
        }
    }

    private void preventDeath() {
        // Проверяем здоровье и применяем эффекты только если способность активна
        if (entity.getHealth() <= 4.0F && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            // Восстанавливаем здоровье и применяем эффекты, как при использовании тотема бессмертия
            player.setHealth(4.0F);
            player.clearActivePotions();
            player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 900, 1));
            player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 100, 1));
            player.world.setEntityState(player, (byte) 35);

            // Логируем для отладки
            System.out.println("Player saved from death by AbilityTotemOfUndyingAction.");

            // После срабатывания активируем кулдаун
            this.entity.getEntityData().setBoolean("_TotemReset", true);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        // Проверяем, не истёк ли кулдаун перед включением способности
        if (this.getCooldown() != 0 && enabled) {
            if (this.entity instanceof EntityPlayer) {
                // Показываем сообщение о кулдауне
                ((EntityPlayer) this.entity).sendStatusMessage(new TextComponentTranslation("text.alimercraft.totem_cooldown.name"), true);
            }
        } else {
            super.setEnabled(enabled);
        }
    }

    @SubscribeEvent
    public void onEntityHit(LivingHurtEvent event) {
        // Проверяем, что способность активна и источник урона — это игрок
        if (isEnabled() && entity instanceof EntityPlayer && entity == event.getSource().getTrueSource()) {
            float damage = event.getAmount();
            if (damage > 0) {
                healPlayer(damage * 0.5F); // Лечит 0.5 сердца за каждое нанесенное сердце урона
                // Запускаем кулдаун после нанесения урона
                this.entity.getEntityData().setBoolean("_TotemReset", true);
            }
        }
    }

    private void healPlayer(float amount) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            player.heal(amount);
        }
    }

    @SideOnly(Side.CLIENT)
    public void drawIcon(Minecraft mc, Gui gui, int x, int y) {
        AHIconHelper.drawIcon(mc, gui, x, y, 0, 8);
    }
}