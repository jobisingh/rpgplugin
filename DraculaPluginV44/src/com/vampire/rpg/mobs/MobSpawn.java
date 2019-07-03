package com.vampire.rpg.mobs;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.vampire.rpg.Pluginc;
import com.vampire.rpg.utils.VamMath;
import com.vampire.rpg.utils.VamScheduler;
import com.vampire.rpg.utils.VamTicks;

public class MobSpawn {

    private static int ID = 1;

    public int id = ID++;

    public MobType[] types;

    public Location loc;
    public int radius;
    public Chunk chunk;
    public int respawnDelay = 1;

    public int leash = 25;

    public MobData spawned = null;

    private boolean stopped = false;

    public MobSpawn(MobType[] types, Location loc, int radius, int respawnDelay, int leash) {
        this.types = types;
        this.loc = loc;
        this.leash = leash;
        this.radius = radius;
        this.chunk = loc.getChunk();
        this.respawnDelay = respawnDelay;
    }

    private String lastTriggeredPlayer = null;

    public void spawn(int counter) {
        if (stopped)
            return;
        VamScheduler.schedule(Pluginc.getInstance(), new Runnable() {
            public void run() {
                if (stopped)
                    return;
                if (!tick()) // if no mob is spawned, we have to keep checking
                    VamScheduler.schedule(MobManager.plugin, this, VamTicks.seconds(1));
            }
        }, counter % 20);
    }

    public void stop() {
        stopped = true;
    }

    /**
     * @return true if a mob is currently spawned for this spawner 
     */
    public boolean tick() {
        if (!this.chunk.isLoaded())
            return false;
        if (spawned != null && (spawned.dead || spawned.entity == null || !spawned.entity.isValid())) {
            spawned = null; // make sure mob is ded
        }
        if (spawned != null) {
            spawned.spawner = this;
            return true; // mob is already spawned
        }
        boolean playerNearby = false;
        Player temp = null;
        if (lastTriggeredPlayer != null) {
            if ((temp = MobManager.plugin.getServer().getPlayerExact(lastTriggeredPlayer)) != null && temp.isOnline()) {
                if (VamMath.flatDistance(temp.getLocation(), loc) < radius) {
                    playerNearby = true;
                }
            }
        }
        if (!playerNearby) {
            lastTriggeredPlayer = null;
            for (Player p : MobManager.plugin.getServer().getOnlinePlayers()) {
                if (VamMath.flatDistance(p.getLocation(), loc) < radius) {
                    playerNearby = true;
                    temp = p;
                    break;
                }
            }
        }
        if (playerNearby) {
            lastTriggeredPlayer = temp.getName();
            temp = null;
            spawned = VamMath.randObject(this.types).spawn(loc, leash);
            spawned.spawner = this;
            return true;
        } else {
            lastTriggeredPlayer = null;
            return false;
        }
    }

    @Override
    public String toString() {
        return types.toString() + "::" + loc.getX() + "," + loc.getY() + "," + loc.getZ() + "::" + radius + "::" + (spawned != null);
    }

}
