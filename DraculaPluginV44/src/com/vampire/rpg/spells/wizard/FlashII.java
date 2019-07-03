package com.vampire.rpg.spells.wizard;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.spells.Spell;
import com.vampire.rpg.spells.SpellEffect;
import com.vampire.rpg.utils.VamParticles;

import de.slikey.effectlib.util.ParticleEffect;

public class FlashII extends SpellEffect {

    @Override
    public boolean cast(final Player p, final PlayerData pd, final int level) {
        final Location start = p.getLocation().add(0, p.getEyeHeight() * 0.1, 0);
        Location permStart = p.getLocation().add(0, p.getEyeHeight() * 0.1, 0).clone();
        Location loc = start;
        Vector direction = p.getLocation().getDirection().normalize();
        direction.setY(0);
        int length = 10;
        Location prev = null;
        for (int k = 0; k < length; k++) {
            Location temp = loc.clone();
            loc = loc.add(direction);
            if (!VamParticles.isAirlike(loc.getBlock()))
                break;
            prev = temp.clone();
        }
        if(prev != null) {
            VamParticles.show(ParticleEffect.EXPLOSION_NORMAL, permStart, 15);
            VamParticles.show(ParticleEffect.EXPLOSION_NORMAL, prev, 15);
            p.teleport(prev);
        } else {
            p.sendMessage(ChatColor.RED + "You can't flash through walls!");
            return false;
        }
        Spell.notify(p, "You instantly teleport a medium distance.");
        return true;
    }
}
