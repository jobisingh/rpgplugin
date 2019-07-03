package com.vampire.rpg.mobs.spells;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.vampire.rpg.Pluginc;
import com.vampire.rpg.mobs.MobData;
import com.vampire.rpg.utils.VamMath;
import com.vampire.rpg.utils.VamScheduler;


public class TeleportSpell extends MobSpell {

    private long cooldown;
    private long variance;

    public TeleportSpell(long cooldown, long variance) {
        this.cooldown = cooldown;
        this.variance = variance;
    }

    public void castSpell(final LivingEntity caster, final MobData md, Player target) {
        final Location loc = target.getLocation();
        if (VamMath.flatDistance(caster.getLocation(), loc) < 50) {
            VamScheduler.schedule(Pluginc.getInstance(), new Runnable() {
                public void run() {
                    if (target != null && target.isOnline()) {
                        for (Entity e : VamMath.getNearbyEntities(loc, 2))
                            if (e instanceof Player)
                                return;
                        md.entity.teleport(loc);
                        md.invuln = System.currentTimeMillis() + 1000;
                    }
                }
            });
        }
    }

    @Override
    public long getCastDelay() {
        return (int) (Math.random() * variance) + cooldown;
    }
}
