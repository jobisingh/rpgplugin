package com.vampire.rpg.spells.reaper;


import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.spells.Spell;
import com.vampire.rpg.spells.SpellEffect;
import com.vampire.rpg.utils.VamMath;
import com.vampire.rpg.utils.VamParticles;
import com.vampire.rpg.utils.VamScheduler;

import de.slikey.effectlib.util.ParticleEffect;

public class Drain extends SpellEffect {

    @Override
    public boolean cast(final Player p, final PlayerData pd, int level) {
        int damage = pd.getDamage(true);
        switch (level) {
            case 1:
                damage *= 0.8;
                break;
            case 2:
                damage *= 0.9;
                break;
            case 3:
                damage *= 1.0;
                break;
            case 4:
                damage *= 1.1;
                break;
            case 5:
                damage *= 1.2;
                break;
            case 6:
                damage *= 1.3;
                break;
            case 7:
                damage *= 1.4;
                break;
            case 8:
                damage *= 1.5;
                break;
            case 9:
                damage *= 1.6;
                break;
            case 10:
                damage *= 1.7;
                break;
        }
        Vector dir = p.getLocation().getDirection().normalize().multiply(0.3);
        Location start = p.getLocation().add(0, p.getEyeHeight() * 0.75, 0).clone();
        Location curr = start.clone();
        Entity target = null;
        for (int k = 0; k < 30; k++) {
            for (Entity e : VamMath.getNearbyEntities(curr, 1.5)) {
                if (e != p) {
                    if (Spell.canDamage(e, true)) {
                        target = e;
                        break;
                    }
                }
            }
            if (target != null)
                break;
            curr.add(dir);
            if (!VamParticles.isAirlike(curr.getBlock()))
                break;
        }
        if (target == null) {
            p.sendMessage(ChatColor.RED + "Failed to find a Drain target.");
            return false;
        }
        final int fDamage = damage;
        final Entity fTarget = target;
        for (int k = 0; k < 3; k++) {
            VamScheduler.schedule(Spell.plugin, new Runnable() {
                public void run() {
                    final Beam b = new Beam();
                    b.curr = fTarget.getLocation().add(0, p.getEyeHeight() * 0.75, 0).clone();
                    for (int t = 0; t < 20; t++) {
                        final int tVal = t;
                        VamScheduler.schedule(Spell.plugin, new Runnable() {
                            public void run() {
                                if (!p.isOnline() || !fTarget.isValid() || (fTarget instanceof Player && !((Player) fTarget).isOnline()))
                                    return;
                                if (p.getWorld() != fTarget.getWorld() || VamMath.flatDistance(p.getLocation(), fTarget.getLocation()) > 50)
                                    return;
                                Location pLoc = p.getLocation().add(0, p.getEyeHeight() * 0.75, 0);
                                Vector v = pLoc.toVector().subtract(b.curr.toVector());
                                if (tVal < 10)
                                    v = v.multiply(0.1);
                                else if (tVal < 15)
                                    v = v.multiply(0.2);
                                else
                                    v = v.multiply(0.5);
                                if (tVal == 19) {
                                    int d = (int) Math.ceil(fDamage / 3.0);
                                    Spell.damageEntity(fTarget, d, p, true, false);
                                    pd.heal(d);
                                }
                                b.curr.add(v);
                                VamParticles.showWithSpeed(ParticleEffect.REDSTONE, b.curr, 0, 2);
                            }
                        }, t);
                    }
                }
            }, k * 20);
        }
        Spell.notify(p, "You begin draining health.");
        return true;
    }

    private static class Beam {
        public Location curr;
    }

}
