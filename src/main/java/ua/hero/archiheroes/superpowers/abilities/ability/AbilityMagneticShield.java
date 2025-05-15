package ua.hero.archiheroes.superpowers.abilities.ability;

import lucraft.mods.lucraftcore.superpowers.abilities.AbilityToggle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.List;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import lucraft.mods.lucraftcore.superpowers.abilities.AbilityToggle;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static org.lwjgl.opengl.Display.isActive;

public class AbilityMagneticShield extends AbilityToggle {

    public AbilityMagneticShield(EntityLivingBase entity) {
        super(entity);
    }

    @Override
    public void updateTick() {

    }

    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent event) {
        if (isActive() && event.getEntityLiving() == this.entity) {
            Entity attacker = event.getSource().getTrueSource();

            if (attacker != null) {
                // Отражаем 50% урона атакующему
                attacker.attackEntityFrom(DamageSource.causeThornsDamage(entity), event.getAmount() * 0.5F);
            }

            // Если урон нанесен фаерболом, возвращаем урон его владельцу
            if (event.getSource().getImmediateSource() instanceof EntityFireball) {
                EntityFireball fireball = (EntityFireball) event.getSource().getImmediateSource();
                if (fireball.shootingEntity != null) {
                    fireball.shootingEntity.attackEntityFrom(DamageSource.causeThornsDamage(entity), event.getAmount() * 0.5F);
                }
            }

            event.setAmount(0); // Блокируем урон для игрока
        }
    }



}