package com.vampire.rpg.buffs;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.vampire.rpg.AbstractManager;
import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.utils.VamParticles;
import com.vampire.rpg.utils.VamTicks;

import de.slikey.effectlib.util.ParticleEffect;

public class BuffManager extends AbstractManager {

    private static HashMap<UUID, Buff> buffMap = new HashMap<UUID, Buff>();

    public BuffManager(Pluginc plugin) {
        super(plugin);
    }

    @Override
    public void initialize() {
    }

    public enum Buff {
        SPEED(new BuffEffect() {
            public void apply(Player p, PlayerData pd) {
                p.sendMessage(ChatColor.GRAY + ">> " + ChatColor.GREEN + "You gain a bit of speed.");
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, VamTicks.seconds(30), 1), false);
            }
        }),
        SPEED2(new BuffEffect() {
            public void apply(Player p, PlayerData pd) {
                p.sendMessage(ChatColor.GREEN + "" + ChatColor.GREEN + "You gain a burst of speed.");
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, VamTicks.seconds(30), 2), false);
            }
        }),
        JUMP(new BuffEffect() {
            public void apply(Player p, PlayerData pd) {
                p.sendMessage(ChatColor.GREEN + "" + ChatColor.GREEN + "Your legs strengthen a bit.");
                p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, VamTicks.seconds(30), 1), false);
            }
        });

        public void apply(Player p, PlayerData pd) {
            this.be.apply(p, pd);
        }

        BuffEffect be;

        Buff(BuffEffect be) {
            this.be = be;
        }
    }

    public static abstract class BuffEffect {
        public abstract void apply(Player p, PlayerData pd);
    }

    public static void handleBuff(Entity player, Entity e) {
        if (!(player instanceof Player))
            return;
        Player p = (Player) player;
        PlayerData pd = plugin.getPD(p);
        if (pd != null && buffMap.containsKey(e.getUniqueId())) {
            try {
                Buff buff = buffMap.remove(e.getUniqueId());
                buff.apply(p, pd);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            Location loc = e.getLocation();
            VamParticles.showWithOffsetPositiveY(ParticleEffect.EXPLOSION_LARGE, loc, 1.0, 10);
            e.remove();
        }
    }

    @EventHandler
    public static void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() == EntityType.ENDER_CRYSTAL) {
            handleBuff(event.getPlayer(), event.getRightClicked());
            event.setCancelled(true);
        }
    }

    @EventHandler
    public static void onPlayerHitBuffEvent(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.ENDER_CRYSTAL) {
            if (event.getDamager() instanceof Player)
                handleBuff(event.getDamager(), event.getEntity());
            else if (event.getDamager() instanceof Projectile) {
                if (((Projectile) event.getDamager()).getShooter() instanceof Player)
                    handleBuff(((Player) ((Projectile) event.getDamager()).getShooter()), event.getEntity());
            }
            event.setCancelled(true);
        }
    }

    public static void createPowerup(Location loc) {
        Entity e = loc.getWorld().spawnEntity(loc, EntityType.ENDER_CRYSTAL);
        buffMap.put(e.getUniqueId(), getRandomBuff());
    }

    public static Buff getRandomBuff() {
        return Buff.values()[(int) (Math.random() * Buff.values().length)];
    }

}
