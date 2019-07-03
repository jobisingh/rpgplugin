package com.vampire.rpg.spells.paladin;

import java.util.ArrayList;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.spells.Spell;
import com.vampire.rpg.spells.SpellEffect;
import com.vampire.rpg.utils.VamParticles;

import de.slikey.effectlib.util.ParticleEffect;

public class Smash extends SpellEffect {

    @Override
    public boolean cast(final Player p, PlayerData pd, int level) {
        int damage = pd.getDamage(true);
        damage *= functions[0].applyAsDouble(level) / 100.0;
        VamParticles.show(ParticleEffect.EXPLOSION_LARGE, p.getLocation(), 5);
        Spell.damageNearby(damage, p, p.getLocation(), 3, new ArrayList<Entity>());
        Spell.notify(p, "You smash the ground with a powerful blow.");
        return true;
    }

}
