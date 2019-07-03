package com.vampire.rpg.spells.priest;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.combat.HealType;
import com.vampire.rpg.spells.Spell;
import com.vampire.rpg.spells.SpellEffect;
import com.vampire.rpg.utils.VamMath;
import com.vampire.rpg.utils.VamParticles;

import de.slikey.effectlib.util.ParticleEffect;

public class Holy extends SpellEffect {

	@Override
	public boolean cast(Player p, PlayerData pd, int level) {
		 int healing = pd.getDamage(true);
		 switch (level) {
         case 1:
             healing *= 1.2;
             break;
         case 2:
             healing *= 1.3;
             break;
         case 3:
             healing *= 1.4;
             break;
         case 4:
             healing *= 1.5;
             break;
         case 5:
             healing *= 1.6;
             break;
         case 6:
        	 healing *= 1.7;
        	 break;
         case 7:
        	 healing*=1.8;
             break;
     }
		ArrayList<Entity> en= VamMath.getNearbyEntities(p.getLocation(), 7);
		for(Entity e:en){
			if(!(e instanceof Player))
				continue;
			Player p1=(Player)e;
			PlayerData pd1= Pluginc.getPD(p1);
			if(pd1==null || !pd1.loadedSQL)
				continue;			
			 Location location1 = p1.getEyeLocation();
             Location location2 = p1.getEyeLocation();
             Location location3 = p1.getEyeLocation();
             int particles = 50;
             float radius = 0.7f;
             for (int i = 0; i < particles; i++) {
                 double angle, x, z;
                 angle = 2 * Math.PI * i / particles;
                 x = Math.cos(angle) * radius;
                 z = Math.sin(angle) * radius;
                 location1.add(x, 0, z);
                 location2.add(x, -0.66, z);
                 location3.add(x, -1.33, z);
                 VamParticles.show(ParticleEffect.END_ROD, location1);
                 VamParticles.show(ParticleEffect.END_ROD, location2);
                 VamParticles.show(ParticleEffect.END_ROD, location3);
               // PlayerData pd1= Pluginc.getPD(p);
              //   ParticleEffect.HAPPY_VILLAGER.display(location1, 0, 0, 0, 0, 1);
           //      ParticleEffect.HAPPY_VILLAGER.display(location2, 0, 0, 0, 0, 1);
          //       ParticleEffect.HAPPY_VILLAGER.display(location3, 0, 0, 0, 0, 1);
                 location1.subtract(x, 0, z);
                 location2.subtract(x, -0.66, z);
                 location3.subtract(x, -1.33, z);
             }
             pd1.heal(healing,HealType.NORMAL);
		}
		Spell.notify(p,"You heal your friends");
		return true;
	}

}
