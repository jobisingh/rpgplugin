package com.vampire.rpg.mobs.spells;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.vampire.rpg.mobs.MobData;
import com.vampire.rpg.spells.Spell;
import com.vampire.rpg.utils.VamMath;

public class BrawlerSpell extends MobSpell {

    private long cooldown;

    public BrawlerSpell(long cooldown) {
        this.cooldown = cooldown;
    }

    public void castSpell(final LivingEntity caster, final MobData md, Player target) {
        if (Spell.canDamage(target, false) && VamMath.flatDistance(target.getLocation(), caster.getLocation()) < 1.5) {
            Spell.damageEntity(target, md.getDamage(), caster, true, false);
        }
    }

    @Override
    public long getCastDelay() {
        return cooldown;
    }
}
