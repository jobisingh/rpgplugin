package com.vampire.rpg.spells.priest;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.lasers.Laser;
import com.vampire.rpg.lasers.LaserManager;
import com.vampire.rpg.spells.Spell;
import com.vampire.rpg.spells.SpellEffect;
import com.vampire.rpg.utils.VamEntities;
import com.vampire.rpg.utils.VamMath;
import com.vampire.rpg.utils.VamParticles;
import com.vampire.rpg.utils.VamScheduler;

import de.slikey.effectlib.util.ParticleEffect;
import net.minecraft.server.v1_10_R1.EntityGuardian;
public class DivineBeam extends SpellEffect {

	@Override
	public boolean cast(Player p, PlayerData pd, int level) {
	//	Guardian  g= (Guardian)VamEntities.createLivingEntity(EntityGuardian.class, p.getEyeLocation());
		int damage = pd.getDamage(true);
        switch (level) {
            case 1:
                damage *= 1.4;
                break;
            case 2:
                damage *= 1.6;
                break;
            case 3:
                damage *= 1.8;
                break;
            case 4:
                damage *= 2.0;
                break;
            case 5:
                damage *= 2.2;
                break;
            case 6:
                damage *= 2.4;
                break;
            case 7:
                damage *= 2.6;
                break;
            case 8:
                damage *= 2.8;
                break;
            case 9:
                damage *= 3.0;
                break;
        }
        final int fDamage = damage;
		Location L1=p.getLocation();
		Vector v=L1.getDirection();
		 final Vector dir = p.getLocation().getDirection();
         final Location start = p.getLocation().add(0, p.getEyeHeight() * 0.75, 0).add(dir.multiply(0.5));
         final ArrayList<Entity> hit = new ArrayList<Entity>();
         ArrayList<Location> locs = VamMath.calculateVectorPath(start.clone(), dir, 6, 4);
         int count = 0;
         for (int k = 0; k < locs.size(); k++) {
         //	p.sendMessage("TEST 3");
             final Location loc = locs.get(k);
             VamScheduler.schedule(Spell.plugin, new Runnable() {
                 public void run() {
                     VamParticles.show(ParticleEffect.SPELL_INSTANT, loc);
                  //   System.out.println("TEST 4");
                  //   int damage = pd.getDamage(true);
                  //   boolean crit = false;
                 //    if (Math.random() < pd.critChance) {
                //         crit = true;
                //         damage *= pd.critDamage;
                //     }
                     hit.addAll(Spell.damageNearby(fDamage, p, loc, 1.0, hit));
                 }
             }, count);
             if (k % 3 == 0)
                 count++;
                 
         }
    //     LaserManager.addLaser(new Laser(30), p);
         Spell.notify(p, "You shoot out a divine beam.");
	//	Location L2=L1.add(x, y, z)
	//	Vector 
		return true;
	}

}
