package com.vampire.rpg.utils.entities;

import java.lang.reflect.Field;

import com.google.common.collect.Sets;

import net.minecraft.server.v1_13_R2.DamageSource;
import net.minecraft.server.v1_13_R2.Entity;
import net.minecraft.server.v1_13_R2.EntityHuman;
import net.minecraft.server.v1_13_R2.EntityVillager;
import net.minecraft.server.v1_13_R2.FluidType;
import net.minecraft.server.v1_13_R2.GenericAttributes;
import net.minecraft.server.v1_13_R2.PathfinderGoalFloat;
import net.minecraft.server.v1_13_R2.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_13_R2.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_13_R2.PathfinderGoalSelector;
import net.minecraft.server.v1_13_R2.SoundEffect;
import net.minecraft.server.v1_13_R2.SoundEffects;
import net.minecraft.server.v1_13_R2.Tag;
import net.minecraft.server.v1_13_R2.World;

public class CustomVillager extends EntityVillager implements Leashable {

    public CustomVillager(World world) {
        super(world);
        try {
            Field gsa = PathfinderGoalSelector.class.getDeclaredField("b");
            gsa.setAccessible(true);
            gsa.set(this.goalSelector, Sets.newLinkedHashSet());
            gsa.set(this.targetSelector, Sets.newLinkedHashSet());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalRandomLookaround(this));
        this.goalSelector.a(2, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();
        getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(30.0d);
    }

    public String toString() {
        return this.getCustomName() + " " + this.getUniqueID();
    }

    @Override
    public void allowWalk(int leash) {
        this.goalSelector.a(3, new PathfinderGoalNPCWander(this, 0.45D, 20, leash)); //the last param is time between moves, 120 is default
    }

    @Override
	protected void c(Tag<FluidType> tag) {
	}
    @Override
    protected void C(Entity entity) {
        
    }
    
    @Override
    public void collide(Entity entity) {

    }

    @Override
    public void setPosition(double d0, double d1, double d2) {

    }

    public boolean a(EntityHuman entityhuman) {
        return true;
    }

    @Override
    protected SoundEffect D() {
        return null;
    }

    @Override
    protected SoundEffect d(DamageSource damagesource) {
        return SoundEffects.ENTITY_PLAYER_HURT;
    }

    @Override
    protected SoundEffect cs() {
        return SoundEffects.ENTITY_PLAYER_DEATH;
    }

}
