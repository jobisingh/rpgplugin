package com.vampire.rpg.spells;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;


public class PassiveSpellEffect extends SpellEffect {
    public boolean cast(Player p, PlayerData pd, int level) {
        p.sendMessage(ChatColor.RED + "Error 103 - passive cast.");
        return false;
    }
}
