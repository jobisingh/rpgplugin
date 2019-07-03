package com.vampire.rpg.warps;

import org.bukkit.Location;
import org.bukkit.World;

import com.vampire.rpg.Pluginc;



public enum WarpLocation {

   	DEFAULT("world1", 615, 31, -274.5, -180, -23);
	//DEFAULT("world",-191.5, 70.0, 708.5 ,-1,-8);
    private Location loc = null;

    private String world;
    private double x, y, z;
    private float yaw, pitch;

    WarpLocation(String world, double x, double y, double z, float yaw, float pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Location getLocation() {
        if (loc != null)
            return loc;
        World w = Pluginc.getInstance().getServer().getWorld(world);
        if (w == null) {
            try {
                throw new Exception("Invalid WarpLocation world: " + world);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return getMainWorld().getSpawnLocation();
        }
        loc = new Location(w, x, y, z, yaw, pitch);
        return loc;
    }

    public Location getMutableLocation() {
        return getLocation().clone();
    }

    public static World getMainWorld() {
        return Pluginc.getInstance().getServer().getWorld("world");
    }

}
