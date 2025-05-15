package ua.hero.archiheroes.superpowers.abilities.ability;


import lucraft.mods.lucraftcore.superpowers.abilities.AbilityAction;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;

public class AbilityStepBackTeleport extends AbilityAction {

    public AbilityStepBackTeleport(EntityLivingBase entity) {
        super(entity);
    }

    @Override
    public boolean action() {
        Vec3d lookVec = entity.getLookVec().scale(-2);
        Vec3d newPos = entity.getPositionVector().add(lookVec);
        entity.setPositionAndUpdate(newPos.x, newPos.y, newPos.z);
        return true;
    }
}