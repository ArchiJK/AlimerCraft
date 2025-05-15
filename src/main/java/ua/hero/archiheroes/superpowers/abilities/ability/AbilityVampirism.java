package ua.hero.archiheroes.superpowers.abilities.ability;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import lucraft.mods.lucraftcore.superpowers.abilities.AbilityToggle;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.common.MinecraftForge;
import ua.hero.archiheroes.AHIconHelper;

public class AbilityVampirism extends AbilityToggle {

    private static final float HEALING_PERCENTAGE = 0.1f; // 50% от нанесенного урона

    public AbilityVampirism(EntityLivingBase entity) {
        super(entity);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void updateTick() {
        // В этом примере обновления в каждом тике не требуются
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        // Проверяем, активна ли способность, и был ли нанесен урон игроком
        if (this.isEnabled() && event.getSource().getTrueSource() == this.entity) {
            // Получаем количество нанесенного урона
            float damage = event.getAmount();
            // Рассчитываем количество здоровья для восстановления
            float healingAmount = damage * HEALING_PERCENTAGE;

            // Восстанавливаем здоровье игрока
            if (entity instanceof EntityPlayer) {
                entity.heal(healingAmount);
            } else {
                entity.setHealth(Math.min(entity.getMaxHealth(), entity.getHealth() + healingAmount));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void drawIcon(Minecraft mc, Gui gui, int x, int y) {
        // Отрисовка иконки способности на экране
        AHIconHelper.drawIcon(mc, gui, x, y, 0, 12); // Подставьте правильные параметры для отображения иконки
    }
}