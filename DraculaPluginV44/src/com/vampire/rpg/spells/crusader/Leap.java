package com.vampire.rpg.spells.crusader;

import java.util.ArrayList;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.spells.Spell;
import com.vampire.rpg.spells.SpellEffect;
import com.vampire.rpg.utils.VamParticles;
import com.vampire.rpg.utils.VamScheduler;
import com.vampire.rpg.utils.VamTicks;

import de.slikey.effectlib.util.ParticleEffect;

public class Leap extends SpellEffect {

    @Override
    public boolean cast(final Player p, PlayerData pd, int level) {
        int damage = pd.getDamage(true);
        switch (level) {
            case 1:
                damage *= 1.5;
                break;
            case 2:
                damage *= 1.75;
                break;
            case 3:
                damage *= 2.0;
                break;
            case 4:
                damage *= 2.25;
                break;
            case 5:
                damage *= 2.5;
                break;
            case 6:
                damage *= 2.75;
                break;
            case 7:
                damage *= 3.0;
                break;
            case 8:
                damage *= 3.25;
                break;
            case 9:
                damage *= 3.5;
                break;
            case 10:
                damage *= 3.75;
                break;
        }
        final int fDamage = damage;
        p.setVelocity(p.getEyeLocation().getDirection().normalize().multiply(2).setY(0).add(new Vector(0, 0.5, 0)));
        VamScheduler.schedule(Spell.plugin, new Runnable() {
            public void run() {
                ArrayList<Entity> hit = new ArrayList<Entity>();
                Spell.damageNearby(fDamage, p, p.getLocation(), 3.0, hit);
                VamParticles.showWithOffset(ParticleEffect.EXPLOSION_LARGE, p.getLocation(), 3.0, 30);
            }
        }, VamTicks.seconds(0.7));
        Spell.notify(p, "Woosh!");
        return true;
    }

}
