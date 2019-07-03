package com.vampire.rpg.spells;

import java.util.function.IntToDoubleFunction;

import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;


public abstract class SpellEffect {
    protected IntToDoubleFunction[] functions;

    public abstract boolean cast(Player p, PlayerData pd, int level);
}
