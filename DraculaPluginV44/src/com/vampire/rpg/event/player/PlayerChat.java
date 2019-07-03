package com.vampire.rpg.event.player;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChat implements Listener {
	public HashMap<Player,Long> lastChat= new HashMap<Player,Long>();
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e){
		Player p=e.getPlayer();
		if(!p.isOp()){
			if(lastChat.containsKey(p) &&System.currentTimeMillis()- lastChat.get(p) >2000){
				p.sendMessage("purchase a rank to talk every 2 seconds");
				e.setCancelled(true);
				return;
			}
			lastChat.put(p, System.currentTimeMillis());
			return;
		}
		
	}
}
