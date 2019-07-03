package com.vampire.rpg.event.block;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		if(e.getBlock().getType().equals(Material.BONE_BLOCK)){
			Player p=e.getPlayer();
			p.sendMessage(ChatColor.DARK_AQUA+"You cannot break bone blocks, those are Drakona's Markers");
			e.setCancelled(true);
			
		}
	}
}
