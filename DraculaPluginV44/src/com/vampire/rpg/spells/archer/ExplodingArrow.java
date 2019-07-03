package com.vampire.rpg.spells.archer;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.metadata.FixedMetadataValue;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.spells.Spell;
import com.vampire.rpg.spells.SpellEffect;
import com.vampire.rpg.utils.VamMetadata;


public class ExplodingArrow extends SpellEffect {

    @Override
    public boolean cast(Player p, final PlayerData pd, int level) {
        int damage = pd.getDamage(true);
        switch (level) {
            case 1:
                damage *= 1.3;
                break;
            case 2:
                damage *= 1.5;
                break;
            case 3:
                damage *= 1.7;
                break;
            case 4:
                damage *= 1.9;
                break;
            case 5:
                damage *= 2.1;
                break;
            case 6:
                damage *= 2.3;
                break;
            case 7:
                damage *= 2.5;
                break;
            case 8:
                damage *= 2.7;
                break;
            case 9:
                damage *= 2.9;
                break;
            case 10:
                damage *= 3.1;
                break;
        }
        Projectile arrow = pd.shootArrow();
        arrow.setMetadata(VamMetadata.META_DAMAGE, new FixedMetadataValue(Spell.plugin, 1));
        arrow.setMetadata(VamMetadata.META_EXPLODE, new FixedMetadataValue(Spell.plugin, damage));
        Spell.notify(p, "You shoot an explosive arrow");
        return true;
    }

}