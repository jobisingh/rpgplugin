package com.vampire.rpg.lasers;

import java.lang.reflect.Field;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.google.common.collect.Sets;
import com.vampire.rpg.utils.CustomArmorStand;
import com.vampire.rpg.utils.VamEntities;
import com.vampire.rpg.utils.entities.CustomGuardian;

import net.minecraft.server.v1_10_R1.EntityCreature;
import net.minecraft.server.v1_10_R1.EntityGuardian;
import net.minecraft.server.v1_10_R1.EntityHuman;
import net.minecraft.server.v1_10_R1.EntityInsentient;
import net.minecraft.server.v1_10_R1.EntitySheep;
import net.minecraft.server.v1_10_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_10_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_10_R1.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_10_R1.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_10_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_10_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_10_R1.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_10_R1.PathfinderGoalSelector;

public class Laser {
	public Entity guardian=null;
	public Location loc=null;
	public ArmorStand armorstand=null;
	private int ticks;
	public Laser(int tickss){
		ticks=tickss;
		//loc=p.getl;
		//Spawn();
	}
	public ArmorStand getArmor(){
		return armorstand;
	}
	public int getTicks(){
		return ticks;
	}
	public void Spawn(Player p){
		Location loc=p.getLocation();
		guardian= (Guardian)VamEntities.createLivingEntity(CustomGuardian.class, p.getLocation().add(0,p.getEyeHeight()*0.75,0).add(loc.getDirection().multiply(0.5)));
	//	overrideBehavior((LivingEntity) guardian);
		Location l= p.getLocation().add(p.getLocation().getDirection().setY(0).normalize().multiply(6));
		armorstand= (ArmorStand) VamEntities.createLivingEntity(CustomArmorStand.class, l);
	//	armorstand=as;
		guardian.setCustomName("HEKLPPPP");
		guardian.setCustomNameVisible(true);
		//armorstand.setVisible(false);
		armorstand.setMarker(true);
		armorstand.setGravity(false);
		armorstand.setArms(false);
		armorstand.setBasePlate(false);
		armorstand.setCanPickupItems(false);
		armorstand.setRemoveWhenFarAway(false);
	   // armorstand=as;
	    ((Creature) guardian).setTarget(armorstand);
		//Location L1=p.getLocation();
		//Vector v=L1.getDirection();
		// final Vector dir = p.getLocation().getDirection();
	}
	public void Despawn(){
		if(armorstand!=null)
			armorstand.remove();
		if(guardian!=null)
			guardian.remove();
	}
	 public  void overrideBehavior(LivingEntity e) {
	     EntityCreature c = (EntityCreature) ((EntityInsentient)((CraftEntity)e).getHandle());
	     //This gets the EntityCreature, we need it to change the values
	    
	     try {
	     Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
	     bField.setAccessible(true);
	     Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
	     cField.setAccessible(true);
	     bField.set(c.goalSelector, Sets.newLinkedHashSet());
	     bField.set(c.targetSelector, Sets.newLinkedHashSet());
	     cField.set(c.goalSelector, Sets.newLinkedHashSet());
	     cField.set(c.targetSelector, Sets.newLinkedHashSet());
	     //this code clears fields B, C. so right now the mob wont walk
	  //   c.goalSelector.a(0, new PathfinderGoalFloat(c));
	//     c.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(c, 1.0D)); //The goal to move 
	     c.goalSelector.a(7, new PathfinderGoalRandomStroll(c, 1.0D)); //The goal to walk around
	     c.goalSelector.a(8, new PathfinderGoalLookAtPlayer(c, EntityHuman.class, 8.0F)); //The goal to look at players
	 //    c.goalSelector.a(8, new PathfinderGoalRandomLookaround(c)); //The goal to look around
	    // c.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(c, EntityVillager.class, false));
	     c.goalSelector.a(2, new PathfinderGoalMeleeAttack(c, 1.0, true)); //This adds melee attack to the mob
	//     c.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(c, EntitySheep.class, 0, true, false, null)); //This line basically sets EntitySheep as a target that the mob will try to find, you can add more if you  wish
	     } catch (Exception exc) {exc.printStackTrace();}
	     }
}
