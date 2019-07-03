package com.vampire.rpg.mobs.spells;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.vampire.rpg.Pluginc;
import com.vampire.rpg.mobs.MobData;
import com.vampire.rpg.utils.VamMath;


public class DerplaxSnoreSpell extends MobSpell {

    public void castSpell(final LivingEntity caster, final MobData md, Player target) {
        md.shout("Yawwwwnnn....", 30);
        target.sendMessage(ChatColor.GRAY + "> " + ChatColor.WHITE + "You begin to feel tired and sluggish...");
        for (Entity e : VamMath.getNearbyEntities(caster.getLocation(), 30)) {
            if (e instanceof Player && Pluginc.getPD((Player) e) != null) {
                Pluginc.getPD((Player) e).giveSlow(6, 2);
            }
        }
    }

    @Override
    public long getCastDelay() {
        return (int) (Math.random() * 10000) + 15000;
    }
}
