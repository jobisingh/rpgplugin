package com.vampire.rpg.spells.priest;

import java.util.ArrayList;
import java.util.HashSet;

import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.vampire.rpg.ItemAPI;
import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.spells.Spell;
import com.vampire.rpg.spells.SpellEffect;
import com.vampire.rpg.spells.SpellManager;
import com.vampire.rpg.utils.CustomArmorStand;
import com.vampire.rpg.utils.VamEntities;
import com.vampire.rpg.utils.VamMath;
import com.vampire.rpg.utils.VamParticles;
import com.vampire.rpg.utils.VamScheduler;
import com.vampire.rpg.utils.VamSound;

import de.slikey.effectlib.util.ParticleEffect;

import com.vampire.rpg.utils.VamScheduler.Halter;

public class GodsHand extends SpellEffect {
	 public static final String BUFF_ID = "gods hand";
	@Override
	public boolean cast(Player p, PlayerData pd, int level) {
		int seconds=0;
		switch(level){
		case 1:
			seconds=2;
			break;
		case 2:
			seconds=3;
			break;
		case 3:
			seconds=4;
			break;
		case 4:
			seconds=5;
			break;
		case 5:
			seconds=6;
			break;
		case 6:
			seconds=7;
			break;
		case 7:
			seconds=8;
			break;
		case 8:
			seconds=9;
			break;
		case 9:
			seconds=10;		
			break;
		case 10:
			seconds=11;		
			break;
		case 11:
			seconds=12;		
			break;		
		case 12:
			seconds=13;		
			break;
		case 13:
			seconds=14;		
			break;
		case 14:
			seconds=15;		
			break;
		}
		ArrayList<Player> ps=pd.getNearbyAllies(9);
		for(Player p1:ps){
			PlayerData pd1=Pluginc.getPD(p1);
			pd1.giveBuff(BUFF_ID, 0, seconds*1000);
		}
		Spell.notify(p, "You protect your allies from an amount of damage");
		Spell.notifyDelayed(p, "Your blessing wears off", seconds);
		return true;
	/*	int dmg=pd.getDamage(true);
		//double value = 0;
		switch (level) {
        case 1:
            dmg *= 1.2;
            break;
        case 2:
            dmg*= 1.3;
            break;
        case 3:
            dmg *= 1.4;
            break;
        case 4:
            dmg *= 1.5;
            break;
        case 5:
            dmg *= 1.6;
            break;
        case 6:
       	 	dmg *= 1.7;
       	 	break;
        case 7:
        	dmg*=1.8;
            break;
    }
		@SuppressWarnings("deprecation")
	//	Entity e= p.getEyeHeight()
		Block b=p.getTargetBlock((HashSet<Byte>)null, 10);
		
		int fuckoff=dmg;
		ArrayList<Entity> en= VamMath.getNearbyEntities(b.getLocation(), 2);
		Entity[] ad = new Entity[en.size()];
		ad=en.toArray(ad);
		if(ad.length!=0){
			Entity e=VamMath.randObject(ad);
			ArmorStand am= (ArmorStand) VamEntities.createLivingEntity(CustomArmorStand.class, e.getLocation().add(0,7,0));
			am.getEquipment().setHelmet(ItemAPI.getSkull("http://textures.minecraft.net/texture/5bb28bb0bf1ad217d2a81191effcc69fe174714a432fd71fa60aa50f3712b97"));
			am.setGravity(false);
			Halter h= new Halter();
			VamScheduler.scheduleRepeating(SpellManager.plugin, new Runnable() {
				int count=10;
				@Override
				public void run() {
					if(count==0){
						h.halt=true;
						VamSound.playSound(p, Sound.ENTITY_ENDERDRAGON_FIREBALL_EXPLODE);
						VamParticles.showWithOffsetPositiveY(ParticleEffect.EXPLOSION_NORMAL, am.getLocation(),0.7, 250);
						 en.addAll(Spell.damageNearby(fuckoff, p, am.getLocation(), 1, en));
					}				
					    count--;
					    am.teleport(am.getLocation().subtract(0, 0.5, 0));
				}
			}, 10,h);
			return true;
		}
		Spell.notify(p, "You see no monsters...");
		//VamScheduler.schedule(Pluginc.getInstance(), new);
		return true;
	}*/
	}

}
