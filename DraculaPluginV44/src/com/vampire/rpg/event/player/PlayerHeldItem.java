package com.vampire.rpg.event.player;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerHeldItem implements Listener {
	public static HashMap<Player,ItemStack> lastItem = new  HashMap<Player,ItemStack>();
	@EventHandler
	public void onPlayerThing(PlayerItemHeldEvent e){
		Player p=e.getPlayer();
		ItemStack item = p.getInventory().getItem(e.getNewSlot());
		if(lastItem.containsKey(p)){
			if(lastItem.get(p).equals(item))
				return;
			lastItem.put(p,item);
		//	PlayerStats.caculateStats(p);
		}
		else{
			lastItem.put(p, item);
		//	PlayerStats.caculateStats(p);
		}
	}
}
