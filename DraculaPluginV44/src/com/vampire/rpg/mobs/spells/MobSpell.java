package com.vampire.rpg.mobs.spells;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.vampire.rpg.mobs.MobData;


public abstract class MobSpell {
    /*
     * target is optional
     */
    public abstract void castSpell(LivingEntity caster, MobData md, Player target);

    public abstract long getCastDelay();
}
