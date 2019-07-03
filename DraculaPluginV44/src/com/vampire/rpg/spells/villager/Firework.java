package com.vampire.rpg.spells.villager;


import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.spells.Spell;
import com.vampire.rpg.spells.SpellEffect;
import com.vampire.rpg.utils.VamParticles;

public class Firework extends SpellEffect {

    @Override
    public boolean cast(Player p, PlayerData pd, int level) {
        VamParticles.spawnRandomFirework(p.getLocation());
        Spell.notify(p, "Pew pew! A beautiful firework shoots upwards.");
        pd.castedFirework = true;
        return true;
    }
}