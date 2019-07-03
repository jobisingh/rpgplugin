package com.vampire.rpg.spells.barbarian;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.spells.Spell;
import com.vampire.rpg.spells.SpellEffect;
import com.vampire.rpg.utils.VamScheduler;
import com.vampire.rpg.utils.VamSound;

public class Berserk extends SpellEffect {
    public static final String BUFF_ID = "Berserk"; //for attack speed
    public static final String BUFF_ID2="Berserk2"; //for dmg
	@Override
	public boolean cast(Player p, PlayerData pd, int level) {
		double cost=0.10;
		double dmg=1;
		double atckspeed=0;
		switch(level){
		case 1:
			cost=0.20;
			atckspeed=0.15;
			dmg=1.25;
			break;
		case 2:
			cost=0.25;
			atckspeed=0.20;
			dmg=1.35;
			break;
		case 3:
			cost=0.30;
			atckspeed=0.25;
			dmg=1.45;
			break;
		case 4:
			cost=0.35;
			atckspeed=0.30;
			dmg=1.55;
			break;
		case 5:
			cost=0.40;
			atckspeed=0.35;
			dmg=1.65;
			break;
		case 6: 
			cost=0.45;
			atckspeed=0.40;
			dmg=1.75;
			break;
		case 7:
			cost=0.50;
			atckspeed=0.45;
			dmg=1.85;
			break;
		}
		int selfDmg = (int) (cost * (pd.baseMaxHP + pd.maxHP));
        if (pd.hp <= selfDmg) {
            p.sendMessage(ChatColor.RED + "You don't have enough HP to cast Berserk!");
            return false;
        }
        if (selfDmg >= pd.hp)
            selfDmg = pd.hp - 1;
        pd.damageSelfTrue(selfDmg);
		Spell.notify(p, "You feel with rage, your eyesight becomes red");
	//	p.sou
		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_POLAR_BEAR_HURT, 3, 7);
		//VamSound.playSound(p, Sound.ENTITY_POLAR_BEAR_HURT,3,7);
		pd.giveBuff(BUFF_ID, atckspeed, 10*1000);
		pd.giveBuff(BUFF_ID2, dmg, 10*1000);
		Spell.notifyDelayed(p, "Your vision becomes normal again and you feel weaker...", 10);
		return true;
	}

}
