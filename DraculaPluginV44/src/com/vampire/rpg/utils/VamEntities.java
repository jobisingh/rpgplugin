package com.vampire.rpg.utils;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.minecraft.server.v1_13_R2.Entity;
import net.minecraft.server.v1_13_R2.EntityLiving;

public class VamEntities {

    public static LivingEntity createLivingEntity(Class<? extends Entity> type, Location loc) {
        net.minecraft.server.v1_13_R2.World world = ((CraftWorld) (loc.getWorld())).getHandle();
        net.minecraft.server.v1_13_R2.EntityLiving e = null;
        try {
            e = (EntityLiving) (type.getDeclaredConstructor(net.minecraft.server.v1_13_R2.World.class).newInstance(world));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        e.P = 2.5f;
        e.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        world.addEntity(e, SpawnReason.CUSTOM);
        LivingEntity le = (LivingEntity) (((net.minecraft.server.v1_13_R2.Entity) e).getBukkitEntity());
        le.setRemoveWhenFarAway(false);
        return le;
    }

}
