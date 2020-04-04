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

import de.slikey.effectlib.util.ParticleEffect;

public class Minefield extends SpellEffect {

    @Override
    public boolean cast(final Player p, final PlayerData pd, int level) {
        int damage = pd.getDamage(true);
        int amount = 0;
        damage *= functions[1].applyAsDouble(level) / 100.0;
        amount = (int) functions[0].applyAsDouble(level);
        Location loc = p.getLocation().add(0, p.getEyeHeight() * 0.8, 0);
        loc.add(p.getLocation().getDirection().normalize().multiply(0.4));
        for (int count = 0; count < amount; count++) {
            final Item item = p.getWorld().dropItem(loc, new ItemStack(Material.LEGACY_FIREWORK_CHARGE));
            item.setMetadata(VamMetadata.META_NO_PICKUP, new FixedMetadataValue(Spell.plugin, 0));
            //            ItemManager.attachLabel(item, ChatColor.BOLD + "= " + p.getName() + "'s Mine =");
            Spell.plugin.getInstance(DropManager.class).attachLabel(item, ChatColor.DARK_GRAY.toString() + ChatColor.ITALIC + p.getName());
            Vector dir = Vector.getRandom();
            dir.setX(dir.getX() - 0.5f);
            dir.setZ(dir.getZ() - 0.5f);
            dir = dir.normalize().multiply(Math.random() * 0.5 + 0.2);
            item.setVelocity(dir);
            final int fDamage = damage;
            VamScheduler.schedule(Spell.plugin, new Runnable() {
                int tick = 1;
                ArrayList<Entity> hit = new ArrayList<Entity>();

                public void run() {
                    if (item == null || !item.isValid())
                        return;
                    hit.addAll(Spell.damageNearby(fDamage, p, item.getLocation(), 2.0, hit));
                    if (hit.size() > 0) {
                        Spell.damageNearby(fDamage, p, item.getLocation(), 3, hit);
                        DropManager.removeLabel(item);
                        item.remove();
                        VamParticles.show(ParticleEffect.EXPLOSION_LARGE, item.getLocation(), 5);
                    }
                    if (tick == 20) {
                        VamParticles.show(ParticleEffect.CLOUD, item.getLocation().add(0, 0.1, 0));
                        DropManager.removeLabel(item);
                        item.remove();
                    }
                    tick++;
                    if (tick <= 20)
                        VamScheduler.schedule(Spell.plugin, this, (int) (Math.random() * 7 + 6));
                }
            }, 5);
        }
        Spell.notify(p, "You scatter a field of explosive mines.");
        return true;
    }
}
