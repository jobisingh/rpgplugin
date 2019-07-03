package com.vampire.rpg.professions.mining;

import org.bukkit.Location;
import org.bukkit.Particle;

import com.vampire.rpg.Pluginc;
import com.vampire.rpg.utils.VamParticles;
import com.vampire.rpg.utils.VamScheduler;
import com.vampire.rpg.utils.VamScheduler.Halter;

import de.slikey.effectlib.util.ParticleEffect;

public class Ore {
	private Location loc;
	private OreType ore;
	private int respawn;
	public Halter h;
	public Ore(Location loc,OreType o,int respawnTimeinTicks){
		this.loc=loc;
		this.ore=o;
		this.respawn=respawnTimeinTicks;
	}
	public void spawn(){
		if(loc.getChunk().isLoaded()){
			h= new Halter();
			loc.getBlock().setType(ore.getMaterial());
			OreManager.ores.put(this.loc,this);
			VamScheduler.scheduleRepeating(Pluginc.getInstance(), new Runnable() {
				
				@Override
				public void run() {
					loc.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, loc.getX()+0.5, loc.getY()+0.5, loc.getZ()+0.5, 10,0.4,0.4,0.4);
				//	VamParticles.showWithOffsetPositiveY(ParticleEffect.VILLAGER_HAPPY, loc.add, 1, 10);
					
				}
			}, 30, h);
		}
	}
	public Location getLocation(){
		return this.loc;
	}
	public int getRespawn(){
		return this.respawn;
	}
	public OreType getType(){
		return this.ore;
	}
	public void despawn(){
		h.halt=true;
	//	OreManager.ores.remove(this.loc);
	}
}
