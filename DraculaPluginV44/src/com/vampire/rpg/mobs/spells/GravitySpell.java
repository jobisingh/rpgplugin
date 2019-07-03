package com.vampire.rpg.mobs.spells;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.vampire.rpg.mobs.MobData;
import com.vampire.rpg.spells.Spell;
import com.vampire.rpg.utils.VamMath;

public class GravitySpell extends MobSpell {

    private int range;

    private long cooldown;

    public GravitySpell(int range, long cooldown) {
        this.range = range;
        this.cooldown = cooldown;
    }

    public void castSpell(final LivingEntity caster, final MobData md, Player target) {
        Vector v = caster.getLocation().toVector();
        for (Entity e : VamMath.getNearbyEntities(caster.getLocation(), range)) {
            if (e instanceof Player) {
                Player p = (Player) e;
                if (p != null && p.isOnline()) {
                    if (Spell.canDamage(p, false)) {
                        Vector pullVector = p.getLocation().toVector().subtract(v).normalize().multiply(-1.8);
                        pullVector.setY(pullVector.getY() + 0.35);
                        p.setVelocity(pullVector);
                    }
                }
            }
        }
    }

    @Override
    public long getCastDelay() {
        return cooldown;
    }
}
