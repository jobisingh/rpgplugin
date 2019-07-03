package com.vampire.rpg.crafting;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.npcs.NPCEntity;
import com.vampire.rpg.npcs.NPCType;

public class CraftingVillager extends NPCEntity {
	
	 public  CraftingVillager(int id, String name, double x, double y, double z, String world) {
	        super(id, name, NPCType.VILLAGER, x, y, z, world);
	        System.out.println(ChatColor.GOLD+String.valueOf(id)+", "+name+", "+world);
	    }
	@Override
	public ChatColor getColor() {
		return ChatColor.DARK_PURPLE;
	}

	@Override
	public void interact(Player p, PlayerData pd) {
		CraftedAPI.OpenCraftingInv(p);

	}

}
