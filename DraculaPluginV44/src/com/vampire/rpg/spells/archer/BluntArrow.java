package com.vampire.rpg.spells.archer;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.metadata.FixedMetadataValue;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.spells.Spell;
import com.vampire.rpg.spells.SpellEffect;
import com.vampire.rpg.utils.VamMetadata;


public class BluntArrow extends SpellEffect {

    @Override
    public boolean cast(Player p, final PlayerData pd, int level) {
        int knockback = 5;
        Projectile arrow = pd.shootArrow();
        arrow.setMetadata(VamMetadata.META_DAMAGE, new FixedMetadataValue(Spell.plugin, 1));
        arrow.setMetadata(VamMetadata.META_KNOCKBACK, new FixedMetadataValue(Spell.plugin, knockback));
        Spell.notify(p, "You shoot a blunt arrow designed for knockback.");
        return true;
    }

}
