package com.vampire.rpg.utils.entities;

import java.lang.reflect.Field;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import com.google.common.collect.Sets;

import net.minecraft.server.v1_13_R2.Entity;
import net.minecraft.server.v1_13_R2.EntityCaveSpider;
import net.minecraft.server.v1_13_R2.EntityHuman;
import net.minecraft.server.v1_13_R2.PathfinderGoalFloat;
import net.minecraft.server.v1_13_R2.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_13_R2.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_13_R2.PathfinderGoalSelector;
import net.minecraft.server.v1_13_R2.World;

public class CustomCaveSpider extends EntityCaveSpider implements Leashable {

    public CustomCaveSpider(World world) {
        super(world);
        try {
            Field gsa = PathfinderGoalSelector.class.getDeclaredField("b");
            gsa.setAccessible(true);
            gsa.set(this.goalSelector, Sets.newLinkedHashSet());
            gsa.set(this.targetSelector, Sets.newLinkedHashSet());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
//        this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 1.0D, 40));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
    }
    
    @Override
    public void allowWalk(int leash) {
        this.goalSelector.a(5, new PathfinderGoalMobWander(this, 1.0D, 40, leash));
    }

    @Override
    public boolean B(Entity paramEntity) {
        if (super.B(paramEntity)) {
            if (paramEntity.getBukkitEntity() instanceof Player) {
                ((Player) (paramEntity.getBukkitEntity())).removePotionEffect(PotionEffectType.POISON);
            }
            return true;
        }
        return false;
    }

}
