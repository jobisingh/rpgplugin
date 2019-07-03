package com.vampire.rpg.mobs.spells;


import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.mobs.MobData;
import com.vampire.rpg.utils.VamParticles;

import de.slikey.effectlib.util.ParticleEffect;

public class ManaDrainSpell extends MobSpell {

    private int amount;

    private long cooldown;

    public ManaDrainSpell(int amount, long cooldown) {
        this.amount = amount;
        this.cooldown = cooldown;
    }

    public void castSpell(final LivingEntity caster, final MobData md, Player target) {
        if (Pluginc.getPD(target) != null) {
            PlayerData pd = Pluginc.getPD(target);
            pd.mana -= amount;
            if (pd.mana < 0) {
                pd.mana = 0;
                pd.updateHealthManaDisplay();
                VamParticles.showWithOffset(ParticleEffect.CRIT_MAGIC, target.getLocation(), 2.0, 25);
            }
        }
    }

    @Override
    public long getCastDelay() {
        return cooldown;
    }
}
