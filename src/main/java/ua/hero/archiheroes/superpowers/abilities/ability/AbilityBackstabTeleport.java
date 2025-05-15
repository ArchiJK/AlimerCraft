package ua.hero.archiheroes.superpowers.abilities.ability;

import lucraft.mods.lucraftcore.superpowers.abilities.AbilityAction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.hero.archiheroes.AHIconHelper;

public class AbilityBackstabTeleport extends AbilityAction {

    public AbilityBackstabTeleport(EntityLivingBase entity) {
        super(entity);
    }

    @Override
    public boolean action() {
        EntityLivingBase target = findTarget();
        if (target != null) {
            Vec3d behindTargetVec = target.getPositionVector().add(target.getLookVec().scale(-4));
            BlockPos behindTarget = new BlockPos(behindTargetVec);
            if (isPositionSafe(behindTarget)) {
                entity.setPositionAndUpdate(behindTargetVec.x, behindTargetVec.y, behindTargetVec.z);
                lookAtEntity(target);
                return true;
            }
        }
        return false;
    }

    private boolean isPositionSafe(BlockPos pos) {
        return entity.world.getBlockState(pos).getMaterial().isReplaceable() &&
                entity.world.getBlockState(pos.up()).getMaterial().isReplaceable();
    }

    private EntityLivingBase findTarget() {
        Vec3d startVec = entity.getPositionEyes(1.0F);
        Vec3d lookVec = entity.getLook(1.0F);
        Vec3d endVec = startVec.add(lookVec.scale(20.0));
        RayTraceResult rayTraceResult = entity.world.rayTraceBlocks(startVec, endVec, false, false, false);

        if (rayTraceResult != null) {
            endVec = rayTraceResult.hitVec;
        }

        AxisAlignedBB aabb = entity.getEntityBoundingBox().expand(lookVec.x * 20, lookVec.y * 20, lookVec.z * 20)
                .expand(-lookVec.x * 20, -lookVec.y * 20, -lookVec.z * 20);

        for (EntityLivingBase e : entity.world.getEntitiesWithinAABB(EntityLivingBase.class, aabb)) {
            if (e != entity) {
                RayTraceResult entityRayTrace = e.getEntityBoundingBox().calculateIntercept(startVec, endVec);
                if (entityRayTrace != null) {
                    return e;
                }
            }
        }
        return null;
    }

    private void lookAtEntity(EntityLivingBase target) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            double dx = target.posX - player.posX;
            double dy = (target.posY + target.getEyeHeight()) - (player.posY + player.getEyeHeight());
            double dz = target.posZ - player.posZ;
            double distance = Math.sqrt(dx * dx + dz * dz);

            float yaw = (float) (MathHelper.atan2(dz, dx) * (180D / Math.PI)) - 90F;
            float pitch = (float) -(MathHelper.atan2(dy, distance) * (180D / Math.PI));

            player.rotationYaw = yaw;
            player.rotationPitch = pitch;
        } else {
            double dx = target.posX - entity.posX;
            double dy = (target.posY + target.getEyeHeight()) - (entity.posY + entity.getEyeHeight());
            double dz = target.posZ - entity.posZ;
            double distance = Math.sqrt(dx * dx + dz * dz);

            float yaw = (float) (MathHelper.atan2(dz, dx) * (180D / Math.PI)) - 90F;
            float pitch = (float) -(MathHelper.atan2(dy, distance) * (180D / Math.PI));

            entity.rotationYaw = yaw;
            entity.rotationPitch = pitch;
        }
    }

    @SideOnly(Side.CLIENT)
    public void drawIcon(Minecraft mc, Gui gui, int x, int y) {
        AHIconHelper.drawIcon(mc, gui, x, y, 0, 10);
    }
}