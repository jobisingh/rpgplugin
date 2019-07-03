package com.vampire.rpg.holograms;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import com.vampire.rpg.npcs.NPCManager;
import com.vampire.rpg.utils.VamScheduler;
import com.vampire.rpg.utils.VamTags;
import com.vampire.rpg.utils.VamTicks;

public class Hologram {

    public Location loc;
    public ArmorStand as = null;
    public String name;

    public Hologram(String name, Location loc) {
        this.name = name;
        this.loc = loc;
    }

    public void despawn() {
        if (as != null)
            as.remove();
        as = null;
    }

    public boolean spawn() {
        if (loc.getChunk().isLoaded()) {
            as = VamTags.makeStand(name, loc, true);
            VamScheduler.schedule(NPCManager.plugin, new Runnable() {
                public void run() {
                    if (loc.getChunk().isLoaded() && as != null && as.isValid()) {
                        as.teleport(loc);
                    }
                }
            }, VamTicks.seconds(1));
            return true;
        }
        return false;
    }

}
