package com.vampire.rpg.mounts;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.utils.VamEntities;
import com.vampire.rpg.utils.VamMath;
import com.vampire.rpg.utils.entities.CustomHorse;

import net.minecraft.server.v1_10_R1.AttributeInstance;
import net.minecraft.server.v1_10_R1.EntityInsentient;
import net.minecraft.server.v1_10_R1.GenericAttributes;

public class Mount {
	Entity e;
	private MountRarity rarity;
	PlayerData owner;
	Horse.Color horseColor;
	Horse.Style horseStyle;
	Horse.Variant horseVariant;
	int tier;
	private static HashMap<String, Long> lastCommand = new HashMap<String, Long>();
	public Mount(MountRarity rarity){
	//	this.e=e;
		this.rarity=rarity;
		//this.owner=owner;
	}
	@SuppressWarnings("deprecation")
	public void spawn(Player p){
			PlayerData pd=Pluginc.getPD(p);
			if (pd.riding || p.getVehicle() != null) {
	            pd.sendMessage(ChatColor.RED + "You are already on a horse!");
	            return;
	        }
			if (lastCommand.containsKey(p.getName()) && System.currentTimeMillis() - lastCommand.get(p.getName()) < 5000) {
	            p.sendMessage(ChatColor.RED + "You can only summon your horse once every 5 seconds.");
	        }else {
	            lastCommand.put(p.getName(), System.currentTimeMillis());
	            if (pd.inCombat()) {
	                pd.sendMessage(ChatColor.RED + "Horses cannot be used while in combat.");
	                return;
	            }
		 	Horse horse = (Horse) VamEntities.createLivingEntity(CustomHorse.class, p.getLocation());
	        horse.setTamed(true);
	        this.getTier();
	        setHorseSpeed(horse, getSpeed(this.tier));
	        horse.setJumpStrength(getJump(this.tier));
	        horse.setColor(this.horseColor);
	        horse.setStyle(this.horseStyle);
	        horse.setVariant(this.horseVariant);
	        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
	        horse.setCustomName(p.getName() + "'s Mount");
	        horse.setCustomNameVisible(true);
	        pd.riding = true;
	        horse.setPassenger(p);
	        this.e=horse;
	        MountsManager.mountsUUID.put(horse.getUniqueId(),this);
	        }
	}
	public void spawn(Location l){
		 Horse horse = (Horse) VamEntities.createLivingEntity(CustomHorse.class, l);
	        horse.setTamed(true);
	        this.getTier();
	      //  PlayerData pd=Pluginc.getPD(p);
	        setHorseSpeed(horse, getSpeed(this.tier));
	        horse.setJumpStrength(getJump(this.tier));
	        horse.setColor(this.horseColor);
	        horse.setStyle(this.horseStyle);
	        horse.setVariant(this.horseVariant);
	        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
	        horse.setCustomName(this.getDisplay()+" Mount");
	        horse.setCustomNameVisible(true);
	      //  pd.riding = true;
	    //    horse.setPassenger(p);
	        this.e=horse;
	        MountsManager.mountsUUID.put(horse.getUniqueId(),this);
	}
	public void despawn(){
		if(e!=null){
			e.remove();
		}
	}
	public void remove(){
		if(e!=null){
			MountsManager.mountsUUID.remove(e.getUniqueId());
			e.remove();
		}
	}
	 public void  getColor() {
	        if (this.horseColor == null)
	            this.horseColor = VamMath.randObject(Horse.Color.values());	        
	    }

	    public void getStyle() {
	        if (this.horseStyle == null)
	            this.horseStyle = VamMath.randObject(Horse.Style.values());
	    }

	    public void getVariant() {
	        if (this.horseVariant == null)
	            this.horseVariant = VamMath.randObject(Horse.Variant.values());
	    }
	    public static void setHorseSpeed(Horse horse, double speed) {
	        AttributeInstance attributes = ((EntityInsentient) ((CraftLivingEntity) horse).getHandle()).getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
	        attributes.setValue(speed);
	    }
	    public void getTier(){
	    	if(rarity==MountRarity.COMMON)
	    		tier= 1;
	    	if(rarity==MountRarity.RARE)
	    		tier= 2;
	    	if(rarity==MountRarity.DRAKONIC)
	    		tier= 3;
	    }
	    public static double getSpeed(int tier) {
	        return 0.1500 + 0.0225 * tier;
	    }

	    //Jump 0.4 - 1.0
	    //Speed 0.1125 - 0.3375 (player speed is 0.1)
	    public static double getJump(int tier) {
	        return 0.38 + 0.065 * tier;
	    }
	    public String getDisplay(){
	    	return this.rarity.display;
	    }
	    @Override
	    public String toString(){
			return /*"rarity*/this.rarity.toString()/*+"horseVariant*/+":"+this.horseVariant.toString()+/*"horseColor*/":"+this.horseColor.toString()+/*"horseStyle:*/":"+this.horseStyle.toString();
	    	
	    }
}