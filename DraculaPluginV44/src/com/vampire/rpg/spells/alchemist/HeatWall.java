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

public class HeatWall extends SpellEffect {

    @Override
    public boolean cast(final Player p, final PlayerData pd, int level) {
        Location loc = p.getLocation().add(0, p.getEyeHeight() * 0.8, 0);
        loc.add(p.getLocation().getDirection().normalize().multiply(0.4));
        final Item item = p.getWorld().dropItem(loc, new ItemStack(Material.FIREWORK_STAR));
        item.setMetadata(VamMetadata.META_NO_PICKUP, new FixedMetadataValue(Spell.plugin, 0));
        //        ItemManager.attachLabel(item, ChatColor.BOLD + "= " + p.getName() + "'s Heat Field =");
        Spell.plugin.getInstance(DropManager.class).attachLabel(item, ChatColor.DARK_GRAY.toString() + ChatColor.ITALIC + p.getName());
        Vector dir = p.getLocation().getDirection().normalize();
        dir.setY(dir.getY() * 1.1);
        dir.multiply(0.6);
        item.setVelocity(dir);
        int damage = pd.getDamage(true);
        damage *= functions[0].applyAsDouble(level) / 100.0;
        final int fDamage = damage;
        final int ticks = 3;
        for (int k = 1; k <= ticks; k++) {
            final int kVal = k;
            VamScheduler.schedule(Spell.plugin, new Runnable() {
                public void run() {
                    if (item == null || !item.isValid())
                        return;
                    ArrayList<Vector> vectors = new ArrayList<Vector>();
                    Vector v = item.getLocation().getDirection().normalize();
                    v.setY(0);
                    vectors.add(v);
                    double z = v.getZ();
                    double x = v.getX();
                    double radians = Math.atan(z / x);
                    if (x < 0)
                        radians += Math.PI;
                    for (int k = 1; k < 18; k++) {
                        Vector v2 = new Vector();
                        v2.setY(v.getY());
                        v2.setX(Math.cos(radians + k * Math.PI / 9));
                        v2.setZ(Math.sin(radians + k * Math.PI / 9));
                        vectors.add(v2.normalize());
                    }
                    ArrayList<Entity> hit = new ArrayList<Entity>();
                    Location loc = item.getLocation();
                    int height = 4;
                    for (int k = 0; k < height; k++) {
                        VamParticles.showWithSpeed(ParticleEffect.FLAME, loc, 0, 1);
                        hit.addAll(Spell.damageNearby(fDamage, p, loc, 0.7, hit));
                        loc.add(0, 0.6, 0);
                    }
                    for (Vector vec : vectors) {
                        loc = item.getLocation().clone().add(vec.multiply(kVal * 0.5 + 1)).add(0, 0.1, 0);
                        for (int k = 0; k < height; k++) {
                            VamParticles.showWithSpeed(ParticleEffect.FLAME, loc, 0, 1);
                            hit.addAll(Spell.damageNearby(fDamage, p, loc, 0.7, hit));
                            loc.add(0, 0.6, 0);
                        }
                    }
                    if (kVal == ticks) {
                        DropManager.removeLabel(item);
                        item.remove();
                    }
                }
            }, k * 20);
        }
        Spell.notify(p, "You throw a device that generates a heat wall.");
        return true;
    }
}
