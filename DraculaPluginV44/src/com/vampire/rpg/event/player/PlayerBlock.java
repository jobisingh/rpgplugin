package com.vampire.rpg.event.player;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.vampire.rpg.Pluginc;

public class PlayerBlock implements Listener{
	@EventHandler
	public void onsomethng(BlockPlaceEvent e){
		Player p= e.getPlayer();
		if(!p.getName().equals(Pluginc.Owner_Name)){
			if(e.getBlock().getType()==Material.TNT || e.getBlock().getType()==Material.LAVA || e.getBlock().getType()==Material.LAVA_BUCKET){
				p.sendMessage("No griefing! if you want to place tnt and lava msg Vampire");
				e.setCancelled(true);
			}
		}
	}
}
