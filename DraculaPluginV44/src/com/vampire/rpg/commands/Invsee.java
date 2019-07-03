package com.vampire.rpg.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import com.vampire.rpg.Pluginc;

public class Invsee implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] arg3) {
		if(!(sender instanceof Player)){
			sender.sendMessage("quit");
			return true;
		}
		Player p=(Player)sender;
		if(!p.getName().equals(Pluginc.Owner_Name) && !p.getName().equals(Pluginc.Host_Name) && !p.isOp()){

			p.sendMessage("no perms m8");
			return true;
		}
		if(arg3.length>1){
			p.sendMessage(ChatColor.RED+"/invsee <PlayerName>");
		}
		Player t= Bukkit.getPlayer(arg3[0]);
		if (t == null) {
		    p.sendMessage(ChatColor.RED + "Player doesn't exist!");
		    return true;
		}
		PlayerInventory tinv= t.getInventory();
		p.openInventory(tinv);
		//p.getStatistic(Statistic.PLAY_ONE_TICK)
		p.sendMessage("Opened inventory of "+t.getName());
		return false;
	}

}
