package com.vampire.rpg.event.player;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.sql.SQLManager;

public class PlayerLeave implements Listener{
	@EventHandler
	public void onPlayerDisconnect(PlayerQuitEvent e){
		Player p=e.getPlayer();
	//	if(!Pluginc.getInstance().getConfig().contains("backpacks."+p.getUniqueId())){
	//		Pluginc.getInstance().getConfig().createSection("backpakcs."+p.getUniqueId());
	//	}
	//	Pluginc.getInstance().saveContents(Pluginc.getInstance().getConfig().getConfigurationSection("backpacks."+p.getUniqueId()), Pluginc.backpacks.get(p.getUniqueId()));
	//	Pluginc.getInstance().saveConfig();
		PlayerData pd=Pluginc.getPD(p);
		if(pd.banner!=null)
			pd.getPlayer().getEquipment().setHelmet(pd.replaceBanner);
		SQLManager.saveData(p);
		if(pd.party!=null){
			pd.party.sendMessage(ChatColor.DARK_GREEN+p.getName()+" has logged off",pd.party.uuids);
			pd.party.LeavePlayer(p.getUniqueId(),p.getName());
			return;
		}
		
		pd.unload();
		return;
	}
}
