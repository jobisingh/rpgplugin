package com.vampire.rpg.mobs.spells;

import org.bukkit.craftbukkit.v1_13_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import com.vampire.rpg.mobs.MobData;
import com.vampire.rpg.utils.VamMath;

import net.minecraft.server.v1_13_R2.EntityArrow;
import net.minecraft.server.v1_13_R2.EntityLiving;
import net.minecraft.server.v1_13_R2.EntityTippedArrow;
import net.minecraft.server.v1_13_R2.MathHelper;

public class AoeArrowSpell extends MobSpell {

    private long cooldown;

    public AoeArrowSpell(long cooldown) {
        this.cooldown = cooldown;
    }

    public void castSpell(final LivingEntity caster, final MobData md, Player target) {
        for (Entity p : VamMath.getNearbyEntitiesCylinder(md.entity.getLocation(), md.ai.rangeDistance, 9)) {
            if (p instanceof Player /*&& Spell.canDamage(p, false)*/) {
                EntityLiving e = (EntityLiving) ((CraftEntity) caster).getHandle();
                EntityArrow entityarrow = new EntityTippedArrow(e.world, e);
                EntityLiving etarget = (EntityLiving) (((CraftPlayer) p).getHandle());
                double d0 = etarget.locX - e.locX;
                
                //LINE 36 WAS EDITED, MAY NEED FIXING
                
                double d1 = etarget.getBoundingBox().a() + etarget.length / 3.0F - entityarrow.locY;
                double d2 = etarget.locZ - e.locZ;
                double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
                entityarrow.shoot(d0, d1 + d3 * 0.2D, d2, md.ai.rangePower, 10);
                e.world.addEntity(entityarrow, SpawnReason.CUSTOM);
            }
        }
    }

    @Override
    public long getCastDelay() {
        return cooldown;
    }
}
