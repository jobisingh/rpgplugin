package com.vampire.rpg.spells.barbarian;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.spells.Spell;
import com.vampire.rpg.spells.SpellEffect;
import com.vampire.rpg.spells.reaper.DarkBargain;
import com.vampire.rpg.utils.VamParticles;
import com.vampire.rpg.utils.VamSound;

import de.slikey.effectlib.util.ParticleEffect;

public class Wrath extends SpellEffect {
    public static final String BUFF_ID = "wrath";

	@Override
	public boolean cast(Player p, PlayerData pd, int level) {
		 double value = 0;
	        double cost = 0.2;
	        switch(level) {
	            case 1:
	                value = 1.7;
	                cost = 0.20;
	                break;
	            case 2:
	                value = 1.8;
	                cost = 0.25;
	                break;
	            case 3:
	                value = 1.9;
	                cost = 0.30;
	                break;
	            case 4:
	                value = 2.0;
	                cost = 0.35;
	                break;
	            case 5:
	                value = 2.1;
	                cost = 0.40;
	                break;
	        }
	        int selfDmg = (int) (cost * (pd.baseMaxHP + pd.maxHP));
	        if (pd.hp <= selfDmg) {
	            p.sendMessage(ChatColor.RED + "You don't have enough HP to cast Wrath!");
	            return false;
	        }
	        if (selfDmg >= pd.hp)
	            selfDmg = pd.hp - 1;
	        pd.damageSelfTrue(selfDmg);
	        final Location startLoc = p.getLocation().clone();
	        ArrayList<Location> locs = new ArrayList<Location>();
	        locs.add(startLoc.clone().add(1, 0, 1));
	        locs.add(startLoc.clone().add(1, 0, -1));
	        locs.add(startLoc.clone().add(-1, 0, 1));
	        locs.add(startLoc.clone().add(-1, 0, -1));
	        for(Location loc : locs) {
	            for(int k = 0 ; k < 10; k++) {
	                VamParticles.showWithSpeed(ParticleEffect.REDSTONE, loc, 0, 10);
	                loc = loc.add(0, 0.3, 0);
	            }
	        }
	     VamSound.playSound(p, Sound.ENTITY_ENDERMEN_SCREAM, 3);
		 pd.giveBuff(Wrath.BUFF_ID, value, 10000);
		 Spell.notify(p, "You lose some HP, but feel much stronger.");
	     Spell.notifyDelayed(p, "Your wrath wears off...", 10);
		return false;
	}

}
