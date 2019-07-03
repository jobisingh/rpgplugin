package com.vampire.rpg.mounts;

import org.bukkit.ChatColor;

public enum MountRarity {
	COMMON("Common",ChatColor.GREEN+"Common"),
	RARE("Rare",ChatColor.AQUA+"Rare"),
	DRAKONIC("Drakonic",ChatColor.RED+"Drakonic"),
	;
	 public String display;
	 public String rarity;
	 
	  	MountRarity(String s,String full){
		 this.rarity=s;
		 this.display=full;				 
	  	}
}