package com.vampire.rpg.spells.barbarian;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.spells.Spell;
import com.vampire.rpg.spells.SpellEffect;
import com.vampire.rpg.spells.SpellManager;
import com.vampire.rpg.utils.VamParticles;
import com.vampire.rpg.utils.VamScheduler;
import com.vampire.rpg.utils.VamSound;
import com.vampire.rpg.utils.VamTicks;

import de.slikey.effectlib.util.ParticleEffect;

public class HulkSmash extends SpellEffect{

	@Override
	public boolean cast(Player p, PlayerData pd, int level) {
		 int damage = pd.getDamage(true);
	        switch (level) {
	            case 1:
	                damage *= 1.5;
	                break;
	            case 2:
	                damage *= 1.75;
	                break;
	            case 3:
	                damage *= 2.0;
	                break;
	            case 4:
	                damage *= 2.25;
	                break;
	            case 5:
	                damage *= 2.5;
	                break;
	            case 6:
	                damage *= 2.75;
	                break;
	            case 7:
	                damage *= 3.0;
	                break;
	            case 8:
	                damage *= 3.25;
	                break;
	            case 9:
	                damage *= 3.5;
	                break;
	            case 10:
	                damage *= 3.75;
	                break;
	        }
	    final int fDamage = damage;
		VamSound.playSound(p, Sound.ENTITY_GENERIC_EXPLODE);
		Location loc=p.getLocation();
		double pitch = ((loc.getPitch() + 90) * Math.PI) / 180;
		double yaw  = ((loc.getYaw() + 90)  * Math.PI) / 180;
		double x = Math.sin(pitch) * Math.cos(yaw);
		double y = Math.sin(pitch) * Math.sin(yaw);
		double z = Math.cos(pitch);
		Vector vector = new Vector(x, z, y);
	//	SpellManager.smashers.add(p);
		p.setVelocity(p.getLocation().getDirection().multiply(3));
		p.setVelocity(new Vector(p.getVelocity().getX(),1.0D,p.getVelocity().getZ()));
		VamScheduler.schedule(Spell.plugin, new Runnable() {
            public void run() {
                ArrayList<Entity> hit = new ArrayList<Entity>();
                Spell.damageNearby(fDamage, p, p.getLocation(), 3.0, hit);
                VamParticles.showWithOffset(ParticleEffect.EXPLOSION_LARGE, p.getLocation(), 3.0, 30);
            }
        }, VamTicks.seconds(0.7));
		Spell.notify(p, "You jump and smash your enemies");
		return true;
	}

}
