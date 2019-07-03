package com.vampire.rpg.mobs.spells;

import java.util.ArrayList;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.vampire.rpg.mobs.MobData;
import com.vampire.rpg.spells.Spell;
import com.vampire.rpg.utils.VamParticles;


public class PikochoThunderSpell extends MobSpell {

    public void castSpell(final LivingEntity caster, final MobData md, Player target) {
        VamParticles.sendLightning(target, target.getLocation());
        Spell.damageNearby((int) (md.getDamage() * 1.1), caster, target.getLocation(), 1.5, new ArrayList<Entity>(), true, false, true);
    }

    @Override
    public long getCastDelay() {
        return (int) (Math.random() * 7000) + 8000;
    }
}
