package com.vampire.rpg.mobs.spells;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.vampire.rpg.mobs.MobData;
import com.vampire.rpg.particles.EffectFactory;
import com.vampire.rpg.particles.custom.spells.HweenPumpkinBombEffect;
import com.vampire.rpg.spells.Spell;
import com.vampire.rpg.utils.VamMath;
import com.vampire.rpg.utils.VamScheduler;
import com.vampire.rpg.utils.VamTicks;


public class HweenPumpkinBomb extends MobSpell {

    @Override
    public void castSpell(LivingEntity caster, MobData md, Player target) {
        for (int k = 0; k < 10; k++) {
            VamScheduler.schedule(Spell.plugin, new Runnable() {
                public void run() {
                    if (caster == null || !caster.isValid() || caster.isDead() || md.dead || md.despawned)
                        return;
                    Location loc = caster.getLocation();
                    double range = 10;
                    Location l = loc.clone().add(VamMath.randDouble(-range, range), VamMath.randDouble(5, 10), VamMath.randDouble(-range, range));
                    FallingBlock fall = l.getWorld().spawnFallingBlock(l, Material.JACK_O_LANTERN, (byte) 0);
                    fall.setDropItem(false);
                    fall.setHurtEntities(false);
                    VamScheduler.schedule(Spell.plugin, new Runnable() {
                        public void run() {
                            if (fall.isDead()) {
                                HweenPumpkinBombEffect effect = new HweenPumpkinBombEffect(EffectFactory.em(), fall.getLocation());
                                effect.run();
                                Spell.damageNearby(md.getDamage() * 3, caster, fall.getLocation(), 2, null);
                            } else {
                                VamScheduler.schedule(Spell.plugin, this, 3);
                            }
                        }
                    });
                }
            }, VamMath.randInt(VamTicks.seconds(1), VamTicks.seconds(7)));
        }
    }

    @Override
    public long getCastDelay() {
        return 10000;
    }

}
