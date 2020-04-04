package com.vampire.rpg.spells.alchemist;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.drops.DropManager;
import com.vampire.rpg.spells.Spell;
import com.vampire.rpg.spells.SpellEffect;
import com.vampire.rpg.utils.VamMetadata;
import com.vampire.rpg.utils.VamParticles;
import com.vampire.rpg.utils.VamScheduler;
import com.vampire.rpg.utils.VamTicks;

import de.slikey.effectlib.util.ParticleEffect;

public class Bomb extends SpellEffect {

    @Override
    public boolean cast(final Player p, final PlayerData pd, int level) {
        Location loc = p.getLocation().add(0, p.getEyeHeight() * 0.8, 0);
        loc.add(p.getLocation().getDirection().normalize().multiply(0.4));
        final Item item = p.getWorld().dropItem(loc, new ItemStack(Material.LEGACY_FIREBALL));
        item.setMetadata(VamMetadata.META_NO_PICKUP, new FixedMetadataValue(Spell.plugin, 0));
        //        ItemManager.attachLabel(item, ChatColor.BOLD + "= " + p.getName() + "'s Bomb =");
        Spell.plugin.getInstance(DropManager.class).attachLabel(item, ChatColor.DARK_GRAY.toString() + ChatColor.ITALIC + p.getName());
        Vector dir = p.getLocation().getDirection().normalize();
        dir.setY(dir.getY() * 1.1);
        dir.multiply(0.6);
        item.setVelocity(dir);
        int damage = pd.getDamage(true);
        damage *= this.functions[0].applyAsDouble(level) / 100.0;
        final int fDamage = damage;
        VamScheduler.schedule(Spell.plugin, new Runnable() {
            public void run() {
                if (item == null || !item.isValid())
                    return;
                VamParticles.show(ParticleEffect.EXPLOSION_LARGE, item.getLocation(), 5);
                Spell.damageNearby(fDamage, p, item.getLocation(), 3, new ArrayList<Entity>());
                DropManager.removeLabel(item);
                item.remove();
            }
        }, VamTicks.seconds(1));
        Spell.notify(p, "You throw a small bomb.");
        return true;
    }
}
