package ua.hero.archiheroes.superpowers.abilities.ability;

import lucraft.mods.lucraftcore.superpowers.abilities.AbilityToggle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.hero.archiheroes.AHIconHelper;

public class AbilityTimeRegen extends AbilityToggle {

    private static final int COOLDOWN_TICKS = 50; // Длительность кулдауна (пример 1000 тиков = 50 секунд)

    public AbilityTimeRegen(EntityLivingBase entity) {
        super(entity);
    }

    @Override
    public void updateTick() {
        if (isEnabled()) {
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                // Проверяем, чтобы способность работала только вне кулдауна
                if (player.getHealth() < player.getMaxHealth() && player.ticksExisted % 20 == 0) {
                    healPlayer(1.0F); // Лечит на 0.5 сердца каждую секунду, если здоровье не полное
                }
            }
        }

        // Если кулдаун завершён, отключаем способность, чтобы её нужно было заново включить вручную
        if (this.entity.getEntityData().getBoolean("_TimeRegenReset")) {
            this.entity.getEntityData().setBoolean("_TimeRegenReset", false);
            this.setCooldown(COOLDOWN_TICKS); // Устанавливаем кулдаун
            this.setEnabled(false); // Отключаем способность
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
                this.entity.getEntityData().setBoolean("_TimeRegenReset", true);
            }
        }
    }

    private void healPlayer(float amount) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            player.heal(amount);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        // Проверяем, не истёк ли кулдаун перед включением способности
        if (this.getCooldown() != 0 && enabled) {
            if (this.entity instanceof EntityPlayer) {
                // Показываем сообщение о кулдауне
                ((EntityPlayer) this.entity).sendStatusMessage(new TextComponentTranslation("text.alimercraft.time_regen_cooldown.name"), true);
            }
        } else {
            super.setEnabled(enabled);
        }
    }

    @SideOnly(Side.CLIENT)
    public void drawIcon(Minecraft mc, Gui gui, int x, int y) {
        AHIconHelper.drawIcon(mc, gui, x, y, 0, 14);
    }
}