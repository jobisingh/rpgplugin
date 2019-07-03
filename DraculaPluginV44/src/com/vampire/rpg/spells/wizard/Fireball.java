package com.vampire.rpg.spells.wizard;

import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.metadata.FixedMetadataValue;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.spells.Spell;
import com.vampire.rpg.spells.SpellEffect;
import com.vampire.rpg.utils.VamMetadata;

public class Fireball extends SpellEffect {

    @Override
    public boolean cast(Player p, PlayerData pd, int level) {
        SmallFireball fireball = (SmallFireball) p.launchProjectile(SmallFireball.class);
        fireball.setIsIncendiary(false);
        fireball.setShooter(p);
        int damage = pd.getDamage(true);
        damage *= functions[0].applyAsDouble(level) / 100.0;
        fireball.setMetadata(VamMetadata.META_DAMAGE, new FixedMetadataValue(Spell.plugin, damage));
        Spell.notify(p, "You shoot off a fireball.");
        return true;
    }

}
