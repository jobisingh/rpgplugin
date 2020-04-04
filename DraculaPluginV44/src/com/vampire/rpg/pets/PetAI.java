package com.vampire.rpg.pets;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.vampire.rpg.utils.VamMath;
import com.vampire.rpg.utils.VamScheduler;
import com.vampire.rpg.utils.VamTicks;

import net.minecraft.server.v1_13_R2.AttributeInstance;
import net.minecraft.server.v1_13_R2.EntityInsentient;
import net.minecraft.server.v1_13_R2.EntityLiving;
import net.minecraft.server.v1_13_R2.GenericAttributes;

public class PetAI {

    private Player owner;
    private LivingEntity le;
    private PetType type;

    private boolean started = false;

    public PetAI(PetType type, Player owner) {
        this.type = type;
        this.owner = owner;
    }

    public void start() {
        if (started)
            return;
        started = true;
        System.out.println(this + " starting pet AI for " + owner.getName() + "'s " + type);
        VamScheduler.schedule(PetManager.plugin, () -> {
            le = type.spawn(owner, owner.getLocation());
            PetManager.spawnedPets.put(le.getUniqueId(), owner.getUniqueId());
            VamScheduler.schedule(PetManager.plugin, () -> {
                tick();
            });
        }, VamTicks.seconds(1));
    }

    public void tick() {
        if (owner != null && owner.isOnline()) {
            VamScheduler.schedule(PetManager.plugin, () -> {
                tick();
            }, 10);
        } else {
            halt();
            return;
        }
        double diff = VamMath.flatDistance(le.getLocation(), owner.getLocation());
        if (diff < 3.7)
            return;
        if (diff > 35 || Math.abs(le.getLocation().getY() - owner.getLocation().getY()) > 10)
            le.teleport(owner);
        Location loc = owner.getLocation();
        //        Vector v = owner.getLocation().getDirection();
        //        loc.add(v.normalize().multiply(-1.5));
        //                loc.add(0, -5, 0);
        AttributeInstance ati = ((EntityLiving) (((CraftLivingEntity) le).getHandle())).getAttributeInstance(GenericAttributes.FOLLOW_RANGE);
        double old = ati.getValue();
        diff = VamMath.flatDistance(loc, le.getLocation());
        if (old < diff) {
            ati.setValue(diff + 10);
        }
        ((EntityInsentient) ((CraftLivingEntity) le).getHandle()).getNavigation().a(loc.getX(), loc.getY(), loc.getZ(), 1.1);
        ati.setValue(old);
    }

    public void halt() {
        if (PetManager.spawnedPets != null && le != null)
            PetManager.spawnedPets.remove(le.getUniqueId());
        if (le != null)
            le.remove();
        le = null;
        owner = null;
    }
}
