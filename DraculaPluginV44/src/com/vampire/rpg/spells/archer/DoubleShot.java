package com.vampire.rpg.spells.archer;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.metadata.FixedMetadataValue;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.spells.Spell;
import com.vampire.rpg.spells.SpellEffect;
import com.vampire.rpg.utils.VamMetadata;
import com.vampire.rpg.utils.VamScheduler;


public class DoubleShot extends SpellEffect {

    @Override
    public boolean cast(Player p, final PlayerData pd, int level) {
        int damage = pd.getDamage(true);
        switch (level) {
            case 1:
                damage *= 0.8;
                break;
            case 2:
                damage *= 0.9;
                break;
            case 3:
                damage *= 1.0;
                break;
            case 4:
                damage *= 1.1;
                break;
            case 5:
                damage *= 1.2;
                break;
            case 6:
                damage *= 1.3;
                break;
            case 7:
                damage *= 1.4;
                break;
            case 8:
                damage *= 1.5;
                break;
            case 9:
                damage *= 1.6;
                break;
            case 10:
                damage *= 1.7;
                break;
        }
        final int fDamage = damage;
        for(int k = 0; k < 2; k++) {
            VamScheduler.schedule(Spell.plugin, new Runnable() {
                public void run() {
                    Projectile arrow = pd.shootArrow();
                    arrow.setMetadata(VamMetadata.META_DAMAGE, new FixedMetadataValue(Spell.plugin, fDamage));
                }
            }, k > 0 ? 0 : 5);
        }
        Spell.notify(p, "You quickly shoot two arrows.");
        return true;
    }

}
