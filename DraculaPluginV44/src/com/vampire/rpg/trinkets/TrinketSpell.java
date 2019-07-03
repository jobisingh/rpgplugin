package com.vampire.rpg.trinkets;

import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;

public abstract class TrinketSpell {

    public abstract String getName();
    
    public abstract String getDescription();
    
    public abstract int getCooldown();
    
    public abstract boolean cast(Player p, PlayerData pd);
    
}
