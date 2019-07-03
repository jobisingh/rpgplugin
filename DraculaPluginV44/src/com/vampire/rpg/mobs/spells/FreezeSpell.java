package com.vampire.rpg.mobs.spells;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.vampire.rpg.mobs.MobData;
import com.vampire.rpg.particles.EffectFactory;
import com.vampire.rpg.particles.custom.spells.FreezeSpellEffect;
import com.vampire.rpg.particles.custom.spells.FreezeSpellEndEffect;
import com.vampire.rpg.spells.Spell;
import com.vampire.rpg.utils.VamLocaiton;
import com.vampire.rpg.utils.VamMath;
import com.vampire.rpg.utils.VamScheduler;
import com.vampire.rpg.utils.VamTicks;

public class FreezeSpell extends MobSpell {

    private int range;
    private long cooldown;
    private int durationSec;
    private int count;

    public FreezeSpell(int range, long cooldown, int durationSec, int count) {
        this.range = range;
        this.cooldown = cooldown;
        this.durationSec = durationSec;
        this.count = count;
    }

    public void castSpell(final LivingEntity caster, final MobData md, Player target) {
        ArrayList<Location> locs = VamLocaiton.findCastableLocs(caster.getLocation(), range, count);
        for (Location loc : locs) {
            FreezeSpellEffect effect = new FreezeSpellEffect(EffectFactory.em(), loc.add(0, 0.15, 0), 3);
            effect.setEntity(caster);
            effect.start();
            VamScheduler.schedule(Spell.plugin, () -> {
                Entity activator = null;
                for (Entity e : VamMath.getNearbyEntities(loc, 1)) {
                    if (e instanceof Player && Spell.canDamage(e, false)) {
                        ((Player) e).addPotionEffect(new PotionEffect(PotionEffectType.JUMP, VamTicks.seconds(durationSec), -100), false);
                        ((Player) e).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, VamTicks.seconds(durationSec), -100), false);
                        activator = e;
                    }
                }
                if (activator != null) {
                    FreezeSpellEndEffect end = new FreezeSpellEndEffect(EffectFactory.em(), loc, durationSec);
                    end.setEntity(activator);
                    end.start();
                }
            }, VamTicks.seconds(3));
        }
    }

    @Override
    public long getCastDelay() {
        return cooldown;
    }
}