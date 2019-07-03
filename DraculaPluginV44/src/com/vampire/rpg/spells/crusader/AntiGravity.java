package com.vampire.rpg.spells.crusader;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.mobs.MobManager;
import com.vampire.rpg.spells.Spell;
import com.vampire.rpg.spells.SpellEffect;
import com.vampire.rpg.utils.VamMath;
import com.vampire.rpg.utils.VamParticles;

import de.slikey.effectlib.util.ParticleEffect;

public class AntiGravity extends SpellEffect {

    @Override
    public boolean cast(final Player p, PlayerData pd, int level) {
        double force = 0;
        switch (level) {
            case 1:
                force = 1.5;
                break;
            case 2:
                force = 3;
                break;
            case 3:
                force = 5;
                break;
        }
        for (Entity e : VamMath.getNearbyEntitiesCylinder(p.getLocation(), 7, 9)) {
            boolean pull = false;
            if (e instanceof Player && e != p) {
                Player p2 = (Player) e;
                PlayerData pd2 = Spell.plugin.getPD(p2);
                if (pd2 == null)
                    continue;
           //     if (pd.party != null && pd2.party != null && pd.party == pd2.party)
           //         continue;
           //     if (pd2 != null && pd2.isPVP())
                    pull = true;
            } else if (MobManager.spawnedMobs_onlyMain.containsKey(e.getUniqueId())) {
                pull = true;
            }
            if (pull) {
                Vector pushVector = e.getLocation().toVector().subtract(p.getLocation().toVector()).normalize();
                pushVector.multiply(force);
                if (pushVector.getY() > 0.5)
                    pushVector.setY(0.5);
                e.setVelocity(pushVector);
            }
        }
        VamParticles.show(ParticleEffect.EXPLOSION_LARGE, p.getLocation(), 5);
        Spell.notify(p, "You push away nearby enemies.");
        return true;
    }

}
