package com.vampire.rpg.mobs.spells;

import java.util.ArrayList;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.vampire.rpg.mobs.MobData;
import com.vampire.rpg.spells.Spell;
import com.vampire.rpg.utils.VamParticles;
import com.vampire.rpg.utils.VamScheduler;
import com.vampire.rpg.utils.VamTicks;

import de.slikey.effectlib.util.ParticleEffect;

public class LeapSpell extends MobSpell {

    public void castSpell(final LivingEntity caster, final MobData md, Player target) {
        md.invuln = System.currentTimeMillis() + 1500;
        md.entity.setVelocity(target.getLocation().subtract(md.entity.getLocation()).toVector().normalize().setY(0.5));
        VamScheduler.schedule(Spell.plugin, new Runnable() {
            public void run() {
                if (md == null || md.dead || md.entity == null)
                    return;
                Spell.damageNearby(md.getDamage(), md.entity, md.entity.getLocation(), 3.0, new ArrayList<Entity>(), true, false, true);
                VamParticles.showWithOffset(ParticleEffect.EXPLOSION_LARGE, md.entity.getLocation(), 3.0, 30);
            }
        }, VamTicks.seconds(0.7));
    }

    @Override
    public long getCastDelay() {
        return (int) (Math.random() * 6000) + 8000;
    }
}
