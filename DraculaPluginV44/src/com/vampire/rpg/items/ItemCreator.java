package com.vampire.rpg.items;

import org.bukkit.inventory.ItemStack;

import com.vampire.rpg.items.stats.StatAccumulator;

public class ItemCreator {
	
	public static ItemStack createItemArmor(String sb,EquipType e,int level,int hp,int hpMul,int def,int defMul,int speed,int hpRegen,int TH,int manaReg,int spellDMG,boolean soulbound){
		StatAccumulator stats= new StatAccumulator();
		if(EquipType.isArmor(e)){
			stats.setHP(hp);
			stats.setDefense(def);
			stats.setDefenseMultiplier(defMul);
			stats.setHPMultiplier(hpMul);
			stats.setHPRegen(hpRegen);
			stats.setManaRegenRate(manaReg);
			stats.setRarityFinder(TH);
			stats.setSpeed(speed);
			stats.setSpellDamage(spellDMG);
		}
		stats.level=level;
		//stats.setHP(hp);
		//stats.setDefense(def);
		EquipItem ei = new EquipItem(e.getMaterial(level));
        ei.name = sb.toString();
        if(soulbound)
        ei.soulbound=true;
        ei.stats = stats;
        return ei.generate();
	}
}
