package com.vampire.rpg.mobs.spells;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.vampire.rpg.mobs.MobData;
import com.vampire.rpg.spells.Spell;
import com.vampire.rpg.utils.VamMath;


public class AoeBrawlerSpell extends MobSpell {

    private long cooldown;

    public AoeBrawlerSpell(long cooldown) {
        this.cooldown = cooldown;
    }

    public void castSpell(final LivingEntity caster, final MobData md, Player target) {
        int dmg = md.getDamage();
        for (Entity e : VamMath.getNearbyEntities(caster.getLocation(), 1.5)) {
            if (e instanceof Player && Spell.canDamage(e, false)) {
                Spell.damageEntity(e, dmg, caster, true, false);
            }
        }
    }

    @Override
    public long getCastDelay() {
        return cooldown;
    }
}
