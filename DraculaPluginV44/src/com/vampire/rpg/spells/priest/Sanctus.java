package com.vampire.rpg.spells.priest;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.combat.HealType;
import com.vampire.rpg.spells.Spell;
import com.vampire.rpg.spells.SpellEffect;
import com.vampire.rpg.utils.VamSound;

public class Sanctus extends SpellEffect{

	@Override
	public boolean cast(Player p, PlayerData pd, int level) {
		int healing = pd.getDamage(true);
	//	p.sendMessage("damage:"+healing);
		switch(level){
			case 1:
				healing*=1.2;
				break;
			case 2:
				healing*=1.4;
				break;
			case 3:
				healing*=1.6;
				break;
			case 4:
				healing*=1.8;
				break;
			case 5:
				healing*=2.0;
				break;
			case 6:
				healing*=2.2;
				break;
			case 7:
				healing*=2.4;
				break;
			case 8:
				healing*=2.6;
				break;
			case 9:
				healing*=2.8;
				break;
			case 10:
				healing*=3.0;
				break;				
		}
		pd.heal(healing, HealType.NORMAL);
	//	p.sendMessage();
	//	p.sendMessage(healing+":");
		Spell.notify(p,"You heal yourself");
		VamSound.playSound(p, Sound.ENTITY_GENERIC_SPLASH);
		return true;
	}

}
